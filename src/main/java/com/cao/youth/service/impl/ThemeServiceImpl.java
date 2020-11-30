package com.cao.youth.service.impl;

import com.cao.youth.model.Theme;
import com.cao.youth.repository.ThemeRepository;
import com.cao.youth.service.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author 曹学习
 * @description ThemeServiceImpl
 * @date 2020/8/25 11:42
 */
@Service
public class ThemeServiceImpl implements ThemeService {
    @Autowired
    private ThemeRepository themeRepository;

    public List<Theme> findByNames(List<String> names){
        return themeRepository.findByNames(names);
    }

    public Optional<Theme> findByName(String name) {
        return  themeRepository.findByName(name);
    }
}
