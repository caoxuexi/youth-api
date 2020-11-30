package com.cao.youth.service;

import com.cao.youth.model.Sku;

import java.util.List;

/**
 * @author 曹学习
 * @description SkuService
 * @date 2020/8/31 12:23
 */
public interface SkuService {
    List<Sku> getSkuListByIds(List<Long> ids);
}
