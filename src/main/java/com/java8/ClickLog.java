package com.java8;


import lombok.Data;

/**
 * phoenix 中：zb8_clicklog 表新增字段操作步骤：
 * 1、对poenix中的表zb8_clicklog执行：ALTER TABLE ZB8_CLICKLOG ADD usersports VARCHAR
 * 2、在zhibo8Warehouse 项目中的javaBean ClickLog中添加 usersports 属性
 * 3、在 Streaming4Kafka2OssAPP 中的 javaBean ClickLog中添加 usersports 属性
 */
@Data
public class ClickLog {
    private String id;
    private String time;
//    private String timeStemp;
//    private String futureDTime;
//    private String year;
//    private String month;
//    private String day;
//    private String hour;
//    private String weekOfMonth;
//    private String dayOfWeek;
    private String platform;
    private String only_care;
    private String appname;
    private String os;
    private String os_version;
    private String version_code;
    private String version_name;
    private String model;
    private String event;
    private String event_type;
    private String param_type;
    private String device;
    private String market;
    private String pk;
    private String channel;
    private String subtab;
    private String url;
    private String title;
    private String content;
    private String content_type;
    private String content_label;
    private String tid;
    private String duration;
    private String tab;
    private String scheme_id;
    private String sidebar;
    private String product_id;
    private String action;
    private String topic_id;
    private String tag;
    private String usercode;
    private String label;
    private String list_;
    private String channel_url;
    private String visit_team;
    private String name;
    private String from_;
    private String home_team;
    private String author_id;
    private String matchid;
//    private long lastTime;
//    private long durTime;
    private String udid;
//    private String android_id;
//    private String openudid;
//    private String iemi;
//    private String imei;
//    private String IDFA;
//    private String mac;
    private String loginStatus;
    private String info;
    private String like_team;
    private String opponent_team;
    private String liked_id;
    private String disliked_id;
    private String cid;
    private String position;
    private String usersports;
    private String uid;
    private String vendor;
    private String ip;
    private String headers;
    private String baichuan_type;
    private String blacks_status;
    private String upsertTime;
}