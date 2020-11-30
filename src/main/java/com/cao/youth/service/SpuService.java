package com.cao.youth.service;

import com.cao.youth.model.Spu;
import org.springframework.data.domain.Page;

/**
 * @author 曹学习
 * @description SpuService
 * @date 2020/8/21 23:14
 */
public interface SpuService {
    Spu getSpuById(Long id);
    Page<Spu> getLatestPagingSpu(Integer pageNum, Integer size);
    Page<Spu> getByCategoryId(Long cid,Boolean isRoot,Integer pageNum,Integer size);
}
