package com.cao.youth.service;

import com.cao.youth.model.Theme;

import java.util.List;
import java.util.Optional;

/**
 * @author 曹学习
 * @description ThemeService
 * @date 2020/8/25 11:41
 */
public interface ThemeService {
    List<Theme> findByNames(List<String> names);
    Optional<Theme> findByName(String name);
}
