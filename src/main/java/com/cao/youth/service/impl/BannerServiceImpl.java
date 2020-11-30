package com.cao.youth.service.impl;

import com.cao.youth.model.Banner;
import com.cao.youth.repository.BannerRepository;
import com.cao.youth.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 曹学习
 * @description BannerService
 * @date 2020/8/19 12:12
 */
@Service
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerRepository bannerRepository;

    public Banner getByName(String name){
        return bannerRepository.findOneByName(name);
    }
}
