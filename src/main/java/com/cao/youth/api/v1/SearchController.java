package com.cao.youth.api.v1;

import com.cao.youth.bo.PageCounter;
import com.cao.youth.exception.http.NotFoundException;
import com.cao.youth.model.Spu;
import com.cao.youth.service.SearchService;
import com.cao.youth.util.CommonUtil;
import com.cao.youth.vo.PagingDozer;
import com.cao.youth.vo.SpuSimplifyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 曹学习
 * @description SearchController
 * @date 2020/8/28 16:09
 */
@RequestMapping("search")
@RestController
public class SearchController {

    @Autowired
    private SearchService searchService;
    @GetMapping("")
    public PagingDozer<Spu, SpuSimplifyVO> search(@RequestParam String q,
                                                  @RequestParam(defaultValue = "0") Integer start,
                                                  @RequestParam(defaultValue = "10") Integer count) {
        PageCounter counter = CommonUtil.convertToPageParameter(start, count);
        Page<Spu> page=null;
        if(q==null){
            return null;
        }else{
            page = this.searchService.search(q, counter.getPage(), counter.getCount());
        }
        return new PagingDozer<>(page, SpuSimplifyVO.class);
    }
}
