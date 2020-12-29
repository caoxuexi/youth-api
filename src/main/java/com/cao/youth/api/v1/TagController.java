package com.cao.youth.api.v1;

import com.cao.youth.core.UnifyResponse;
import com.cao.youth.model.Tag;
import com.cao.youth.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 曹学习
 * @description TagController 热门搜索tag返回
 * @date 2020/12/29 11:04
 */
@RestController
@RequestMapping("tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/type/{isHot}")
    public List<Tag> getHotSearchTag(@PathVariable Integer isHot) throws Exception{
        List<Tag> hotSearchTag = this.tagService.getHotSearchTag(isHot);
        return hotSearchTag;
    }

}
