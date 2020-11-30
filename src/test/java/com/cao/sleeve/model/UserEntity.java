package com.cao.sleeve.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author 曹学习
 * @description UserEntity
 * @date 2020/8/27 15:06
 */
@Entity
@Table(name = "user", schema = "glorious-youth")
public class UserEntity {

    private int id;
    private String openid;
    private String nickname;
    private Integer unifyUid;
    private String email;
    private String password;
    private String mobile;
    private Object wxProfile;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Timestamp deleteTime;
    private Integer group;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "openid")
    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    @Basic
    @Column(name = "nickname")
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Basic
    @Column(name = "unify_uid")
    public Integer getUnifyUid() {
        return unifyUid;
    }

    public void setUnifyUid(Integer unifyUid) {
        this.unifyUid = unifyUid;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "mobile")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Basic
    @Column(name = "wx_profile")
    public Object getWxProfile() {
        return wxProfile;
    }

    public void setWxProfile(Object wxProfile) {
        this.wxProfile = wxProfile;
    }

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "update_time")
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "delete_time")
    public Timestamp getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Timestamp deleteTime) {
        this.deleteTime = deleteTime;
    }

    @Basic
    @Column(name = "group")
    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return id == that.id &&
                Objects.equals(openid, that.openid) &&
                Objects.equals(nickname, that.nickname) &&
                Objects.equals(unifyUid, that.unifyUid) &&
                Objects.equals(email, that.email) &&
                Objects.equals(password, that.password) &&
                Objects.equals(mobile, that.mobile) &&
                Objects.equals(wxProfile, that.wxProfile) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(deleteTime, that.deleteTime) &&
                Objects.equals(group, that.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, openid, nickname, unifyUid, email, password, mobile, wxProfile, createTime, updateTime, deleteTime, group);
    }
}
