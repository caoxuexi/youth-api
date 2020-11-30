package com.cao.youth.vo;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 曹学习
 * @description PagingDozer
 * @date 2020/8/22 23:50
 * 两个范型一个是源，一个是目标
 */
public class PagingDozer<T,K> extends Paging {
    @SuppressWarnings("unchecked")//取消编译器因为K和Paging中范型T冲突的警告
    public PagingDozer(Page<T> pageT,Class<K> classK){
        this.initPageParameters(pageT);
        List<T> tList=pageT.getContent();
        Mapper mapper= DozerBeanMapperBuilder.buildDefault();
        List<K> voList=new ArrayList<>();
        tList.forEach(t->{
            K vo=mapper.map(t,classK);
            voList.add(vo);
        });
        this.setItems(voList);
    }
}
