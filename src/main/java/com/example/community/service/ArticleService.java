package com.example.community.service;

import com.example.community.dto.ArticleDTO;
import com.example.community.dto.Pagination;
import com.example.community.exception.CustomErrorCode;
import com.example.community.exception.CustomException;
import com.example.community.model.Article;
import com.google.gson.JsonObject;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author zyh
 * @version 1.0
 * @date 2019/7/13 22:01
 */
@Service
public class ArticleService {

    @Autowired
    JestClient jestClient;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Value("${coder.elasticsearch.index}")
    private String index;

    @Value("${coder.elasticsearch.type}")
    private String type;


    public Pagination<ArticleDTO> list(Integer page, Integer size) {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        int from = (page - 1) * size;
        sourceBuilder.from(from);
        sourceBuilder.size(size);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        Search search = new Search.Builder(sourceBuilder.toString())
                .addIndex(index)
                .addType(type)
                .build();
        Pagination<ArticleDTO> pagination = mapper(search);
        return pagination;
    }


    public ArticleDTO getById(Integer id) {
        Get get = new Get.Builder(index, String.valueOf(id)).type(type).build();
        ArticleDTO articleDTO = new ArticleDTO();
        try {
            JestResult result = jestClient.execute(get);
            JsonObject json = result.getJsonObject();
            JsonObject source = json.getAsJsonObject("_source");
            articleDTO.setId(source.get("id").getAsInt());
            articleDTO.setTitle(source.get("title").getAsString());
            articleDTO.setContent(source.get("content").getAsString());
            articleDTO.setTag(source.get("tag").getAsString());
            articleDTO.setAuthorName(source.get("authorName").getAsString());
            articleDTO.setGmtCreate(source.get("gmtCreate").getAsLong());
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomException(CustomErrorCode.COMMENT_NOT_FOUND);
        }
        return  articleDTO;
    }
    public int insert(Article article) {
        String articleId = redisTemplate.opsForValue().get("articleId");
        int id = Integer.parseInt(articleId);
        article.setId(id);
        Index insert = new Index.Builder(article).index(index).type(type).build();
        try {
            jestClient.execute(insert);
        } catch (IOException e) {
            e.printStackTrace();
            throw  new CustomException(CustomErrorCode.ARTICLE_ADD_FAIL);
        }
        //{"_index":"coder","_type":"article","_id":"3","_version":1,"result":"created","_shards":{"total":2,"successful":1,"failed":0},"_seq_no":0,"_primary_term":3}
        redisTemplate.opsForValue().increment("articleId");
        return id;
    }

    public Pagination<ArticleDTO> searchByCondition(String condition) {
        String json = "{\n" +
                "  \"query\": {\n" +
                "    \"bool\": {\n" +
                "      \"should\":[\n" +
                "\t\t\t\t{\"match\":{\"title\":\""+ condition +"\"}},\n" +
                "\t\t\t\t{\"match_phrase\":{\"content\":\""+ condition +"\"}},\n" +
                "\t\t\t\t{\"match_phrase\":{\"content\":\""+ condition +"\"}}\n" +
                "\t\t\t]\n" +
                "\t}\n" +
                "  }\n" +
                "}";

//        String highlight = "{\n" +
//                "  \"query\": {\n" +
//                "    \"bool\": {\n" +
//                "      \"should\":[\n" +
//                "\t\t\t\t{\"match\":{\"title\":\""+ condition +"\"}},\n" +
//                "\t\t\t\t{\"match_phrase\":{\"content\":\""+ condition +"\"}},\n" +
//                "\t\t\t\t{\"match_phrase\":{\"content\":\""+ condition +"\"}}\n" +
//                "\t\t\t]\n" +
//                "\t\t}\n" +
//                "\t},\n" +
//                "\t\"highlight\": {\n" +
//                "        \"fields\" : {\n" +
//                "            \"content\" : {}\n" +
//                "        }\n" +
//                "    }\n" +
//                "}";
        Search search = new Search.Builder(json).addIndex(index).addType(type).build();
        Pagination<ArticleDTO> pagination = mapper(search);
        return pagination;
    }

    private Pagination<ArticleDTO> mapper(Search search) {
        SearchResult result;
        try {
            result = jestClient.execute(search);
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomException(CustomErrorCode.ARTICLE_NOT_FOUND);
        }
        List<ArticleDTO> list = new ArrayList<>();
        result.getHits(ArticleDTO.class).forEach(item -> {
            list.add(item.source);
        });
        Pagination<ArticleDTO> pagination = new Pagination<>();
        pagination.setData(list);
        return pagination;
    }
}

