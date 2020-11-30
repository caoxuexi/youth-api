package com.cao.youth.service.impl;

import com.cao.youth.model.Sku;
import com.cao.youth.repository.SkuRepository;
import com.cao.youth.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 曹学习
 * @description SkuServiceImpl
 * @date 2020/8/31 12:24
 */
@Service
public class SkuServiceImpl implements SkuService {
    @Autowired
    private SkuRepository skuRepository;

    public List<Sku> getSkuListByIds(List<Long> ids){
        return this.skuRepository.findAllByIdIn(ids);
    }
}
