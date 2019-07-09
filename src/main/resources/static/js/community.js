/**
 * 提交回复
 */
function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    // if (!content.trim()) {
    //     alert("回复不能为空!");
    //     return;
    // }
    // $.ajax({
    //     type: "POST",
    //     url: "/comment",
    //     contentType: "application/json",
    //     data: JSON.stringify({
    //         "parentId": questionId,
    //         "content": content,
    //         "type": 1
    //     }),
    //     success: function (response) {
    //         if (response.code == 200) {
    //             //$("#comment_content").val("");
    //             window.location.reload();
    //         } else {
    //             alert(response.message);
    //         }
    //     },
    //     dataType: "json"
    // });
    comment2Target(questionId, content, 1);
}


function comment2Target(parentId, content, type) {
    if (!content.trim()) {
        alert("回复不能为空!");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": parentId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if (response.code == 200) {
                //$("#comment_content").val("");
                window.location.reload();
            } else {
                alert(response.message);
            }
        },
        dataType: "json"
    });
}
function comment(e) {
    var parentId = e.getAttribute("data-id");
    var content = $("#input" + parentId).val();
    comment2Target(parentId, content, 2);
}

/**
 * 展开二集评论
 */
function getSecComment(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment" + id);
    if (comments.hasClass("in")) {
        comments.removeClass("in");
        e.classList.remove("blue");
    } else {

        // $("#list" + id).load("/comment/" + id,function (response, status, xhr) {
        //     if (status == "success") {
        //         console.log(response)
        //     }
        // });
        if (comments.children().length != 1) {
            comments.addClass("in");
            e.classList.add("blue");
        } else {
            $.getJSON( "/comment/" + id, function( data ) {
                $.each(data.data.reverse(), function (index, comment) {


                    var media_left = $("<div/>", {
                        "class" : "media-left"
                    }).append($("<img/>", {
                        "class" : "media-object img-rounded",
                        "src": comment.user.avatarUrl
                    }));

                    var media_body = $("<div/>",{
                        "class" : "media-body"
                    }).append($("<span/>", {
                        "class" : "comment-name",
                        html: comment.user.name
                    })).append($("<br>")).append($("<span/>", {
                        "class" : "comment-content",
                        html: comment.content
                    })).append($("<span/>", {
                        "class" : "pull-right",
                        html: getMyDate(comment.gmtCreate)
                    }));

                    var media = $("<div/>", {
                        "class" : "media"
                    }).append(media_left).append(media_body);

                    var comment = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments",
                    }).append(media);
                    comments.prepend(comment);
                })
            });
        }
        comments.addClass("in");
        e.classList.add("blue");
    }
}


function getMyDate(str){
    var oDate = new Date(str),
        oYear = oDate.getFullYear(),
        oMonth = oDate.getMonth()+1,
        oDay = oDate.getDate(),
        // oHour = oDate.getHours(),
        // oMin = oDate.getMinutes(),
        // oSen = oDate.getSeconds(),
        oTime = oYear +'-'+ get(oMonth) +'-'+ get(oDay);//最后拼接时间
    return oTime;
}
function get(num) {
    if (parseInt(num) < 10) {
        num = '0' + num;
    }
    return num;
}
