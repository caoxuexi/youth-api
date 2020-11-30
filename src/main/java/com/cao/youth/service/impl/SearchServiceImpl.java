
package com.cao.youth.service.impl;

import com.cao.youth.model.Spu;
import com.cao.youth.repository.SpuRepository;
import com.cao.youth.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 曹学习
 * @description SearchController
 * @date 2020/8/28 16:10
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SpuRepository spuRepository;

    public Page<Spu> search(String q, Integer page, Integer count) {
        Pageable paging = PageRequest.of(page, count);
        String likeQ = "%" +q + "%";
        return spuRepository.findByTitleLikeOrSubtitleLike(likeQ,likeQ, paging);
    }
}
