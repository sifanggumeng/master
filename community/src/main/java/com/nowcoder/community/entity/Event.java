package com.nowcoder.community.entity;

import java.util.HashMap;
import java.util.Map;

public class Event {
    private String topic;
    private int userId; //触发事件的用户id
    private int entityType;
    private int entityId;
    private int entityUserId;   //实体作者（被评论、被点赞、被关注的人）
    private Map<String, Object> data = new HashMap<>(); //封装其他数据

    public String getTopic() {
        return topic;
    }

    //public void setTopic(String topic) {
    //    this.topic = topic;
    //}
    //对原本的set方法进行改写，有利于多次连写，较为方便；同时相对于构造方法来说可以任意选择传入的参数类型和个数，较为灵活，如：event.setTopic().setUserId()...
    public Event setTopic(String topic) {
        this.topic = topic;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public Event setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public Event setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public Event setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public int getEntityUserId() {
        return entityUserId;
    }

    public Event setEntityUserId(int entityUserId) {
        this.entityUserId = entityUserId;
        return this;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public Event setData(String key, Object value) {
        this.data.put(key, value);
        return this;
    }
}
