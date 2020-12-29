package com.cao.youth.service;

import com.cao.youth.model.Tag;

import java.util.List;

/**
 * @author 曹学习
 * @description TagService
 * @date 2020/12/29 11:10
 */
public interface TagService {
    public List<Tag> getHotSearchTag(Integer isHot);
}
