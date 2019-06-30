//package com.zm.zhidan.util;
//
//import javax.persistence.Column;
//import javax.persistence.MappedSuperclass;
//
///**
// * 数据库公共类
// * @author liuchao
// * 20160315
// */
//@MappedSuperclass
//public class SyncFlag {
//
//    //状态
//    protected byte status = Status.NORMAL;
//
//    @Column(name = "create_time")
//    /*
//       @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//       @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", locale = "zh", timezone = "UTC")
//       @Type(type="datetime")
//    */
//    private String createTime;
//
//    @Column(name = "update_time")
//    /*
//       @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//       @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", locale = "zh", timezone = "UTC")
//       @Type(type="datetime")
//    */
//    private String updateTime;
//
//    //备用字段1
//    @Column(length=128)
//    protected String text1;
//
//    //备用字段2
//    @Column(length=128)
//    protected String text2;
//
//    public byte getStatus() {
//        return status;
//    }
//
//    public void setStatus(byte status) {
//        this.status = status;
//    }
//
//    public String getCreateTime() {
//        return createTime;
//    }
//
//    public void setCreateTime(String createTime) {
//        this.createTime = createTime;
//    }
//
//    public String getUpdateTime() {
//        return updateTime;
//    }
//
//    public void setUpdateTime(String updateTime) {
//        this.updateTime = updateTime;
//    }
//
//    public String getText1() {
//        return text1;
//    }
//
//    public String getText2() {
//        return text2;
//    }
//
//    public void setText1(String text1) {
//        this.text1 = text1;
//    }
//
//    public void setText2(String text2) {
//        this.text2 = text2;
//    }
//}
//
