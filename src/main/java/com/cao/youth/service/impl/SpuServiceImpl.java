package com.cao.youth.service.impl;

import com.cao.youth.model.Spu;
import com.cao.youth.repository.SpuRepository;
import com.cao.youth.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * @author 曹学习
 * @description SpuServiceImpl
 * @date 2020/8/21 23:14
 */
@Service
public class SpuServiceImpl implements SpuService {
    @Autowired
    SpuRepository spuRepository;

    @Override
    public Spu getSpuById(Long id) {
        return this.spuRepository.findOneById(id);
    }

    @Override
    public Page<Spu> getLatestPagingSpu(Integer pageNum, Integer size) {
        Pageable page = PageRequest.of(pageNum, size, Sort.by("createTime").descending());//操作的是对象，所以是驼峰命名
        return this.spuRepository.findAll(page);
    }

    @Override
    public Page<Spu> getByCategoryId(Long cid, Boolean isRoot, Integer pageNum, Integer size) {
        Pageable page=PageRequest.of(pageNum,size);
        Page<Spu> spuPage=null;
        if(isRoot){
            spuPage=this.spuRepository.findByRootCategoryIdOrderByCreateTime(cid,page);
            return spuPage;
        }else{
            spuPage=this.spuRepository.findByCategoryIdOrderByCreateTimeDesc(cid,page);
            return spuPage;
        }
    }
}
