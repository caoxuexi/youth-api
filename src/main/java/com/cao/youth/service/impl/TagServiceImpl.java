package com.cao.youth.service.impl;

import com.cao.youth.model.Tag;
import com.cao.youth.repository.TagRepository;
import com.cao.youth.service.TagService;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 曹学习
 * @description TagServiceImpl
 * @date 2020/12/29 11:10
 */
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository tagRepository;
    
    @Override
    public List<Tag> getHotSearchTag(Integer isHot) {
        List<Tag> hotTags = tagRepository.findAllByType(isHot);
        return hotTags;
    }
}
