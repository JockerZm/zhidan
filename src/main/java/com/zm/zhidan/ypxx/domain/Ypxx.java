package com.zm.zhidan.ypxx.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;


//@Entity
//@Table(name = "ypxx")
//public class Ypxx extends SyncFlag implements Serializable {
@Document
public class Ypxx {

    @Id
    private String id;

    @Indexed
    private String bianma;

    @Indexed
    private String pinming;

    private String guige;

    @Indexed
    private String pihao;

    private String youxiaoqi;

    private String danwei;

    private String shuliang;

    private String danjia;

    private String shengchanchangjia;

    private String pizhunwenhao;

    private String createTime;

    private String updateTime;

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBianma() {
        return bianma;
    }

    public void setBianma(String bianma) {
        this.bianma = bianma;
    }

    public String getPinming() {
        return pinming;
    }

    public void setPinming(String pinming) {
        this.pinming = pinming;
    }

    public String getGuige() {
        return guige;
    }

    public void setGuige(String guige) {
        this.guige = guige;
    }

    public String getPihao() {
        return pihao;
    }

    public void setPihao(String pihao) {
        this.pihao = pihao;
    }

    public String getYouxiaoqi() {
        return youxiaoqi;
    }

    public void setYouxiaoqi(String youxiaoqi) {
        this.youxiaoqi = youxiaoqi;
    }

    public String getDanwei() {
        return danwei;
    }

    public void setDanwei(String danwei) {
        this.danwei = danwei;
    }

    public String getShuliang() {
        return shuliang;
    }

    public void setShuliang(String shuliang) {
        this.shuliang = shuliang;
    }

    public String getDanjia() {
        return danjia;
    }

    public void setDanjia(String danjia) {
        this.danjia = danjia;
    }

    public String getShengchanchangjia() {
        return shengchanchangjia;
    }

    public void setShengchanchangjia(String shengchanchangjia) {
        this.shengchanchangjia = shengchanchangjia;
    }

    public String getPizhunwenhao() {
        return pizhunwenhao;
    }

    public void setPizhunwenhao(String pizhunwenhao) {
        this.pizhunwenhao = pizhunwenhao;
    }
}