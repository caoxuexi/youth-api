package com.cao.youth.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author 曹学习
 * @description Paging
 * @date 2020/8/22 23:39
 */
@Getter
@Setter
@NoArgsConstructor
public class Paging<T> {
    private Long total;
    private Integer count;
    private Integer page;
    private Integer totalPage;
    private List<T> items;

    public Paging(Page<T> pageT){
        initPageParameters(pageT);
        this.items=pageT.getContent();
    }

    void initPageParameters(Page<T> pageT){
        this.total=pageT.getTotalElements();
        this.count=pageT.getSize();
        this.page=pageT.getNumber();
        this.totalPage=pageT.getTotalPages();
    }
}
