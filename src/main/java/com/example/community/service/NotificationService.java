package com.example.community.service;

import com.example.community.dto.NotificationDTO;
import com.example.community.dto.Pagination;
import com.example.community.enums.NotificationStatus;
import com.example.community.exception.CustomErrorCode;
import com.example.community.exception.CustomException;
import com.example.community.mapper.NotificationMapper;
import com.example.community.mapper.UserMapper;
import com.example.community.model.Notification;
import com.example.community.model.NotificationExample;
import com.example.community.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zyh
 * @version 1.0
 * @date 2019/7/10 22:42
 */
@Service
public class NotificationService {
    @Autowired
    NotificationMapper mapper;

    @Autowired
    UserMapper userMapper;
    public Pagination list(Integer id, Integer page, Integer size) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(id);
        notificationExample.setOrderByClause("gmt_create desc");
        int totalNotifications = (int) mapper.countByExample(notificationExample);
        Pagination<NotificationDTO> pagination = new Pagination();
        if (totalNotifications == 0) {
            return pagination;
        }
        int totalPage = totalNotifications / size;
        if(totalNotifications % size != 0)
            totalPage++;
        if(page < 1) page = 1;
        if(page > totalPage) page = totalPage;
        Integer offset = size * (page - 1);

        List<Notification> notifications = mapper.selectByExampleWithRowbounds(notificationExample, new RowBounds(offset, size));
        if (notifications.size() == 0) {
            return pagination;
        }
        //TODO
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTOS.add(notificationDTO);
        }
        pagination.setData(notificationDTOS);
        pagination.set(page, size, totalPage);
        return pagination;
    }

    public NotificationDTO read(Long id, User user) {
        Notification notification = mapper.selectByPrimaryKey(id);
        if (notification == null) {
            throw new CustomException(CustomErrorCode.NOTIFICATION_NOT_EXIST);
        }
        if (notification.getReceiver() != user.getId()) {
            throw new CustomException(CustomErrorCode.READ_NOTIFICATION_FAIL);
        }
        notification.setStatus(NotificationStatus.READ.getStatus());
        mapper.updateByPrimaryKey(notification);
        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        return notificationDTO;
    }

    public long unReadCount(Integer id) {
        NotificationExample example = new NotificationExample();

        example.createCriteria().andReceiverEqualTo(id).andStatusEqualTo(NotificationStatus.UNREAD.getStatus());
        long l = mapper.countByExample(example);
        return l;
    }
}
