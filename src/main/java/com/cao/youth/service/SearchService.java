package com.cao.youth.service;

import com.cao.youth.model.Spu;
import org.springframework.data.domain.Page;

/**
 * @author 曹学习
 * @description SearchService
 * @date 2020/8/28 16:12
 */
public interface SearchService {
    Page<Spu> search(String q, Integer page, Integer count);
}
