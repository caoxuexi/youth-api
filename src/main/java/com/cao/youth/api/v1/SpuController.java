package com.cao.youth.api.v1;

import com.cao.youth.bo.PageCounter;
import com.cao.youth.exception.http.NotFoundException;
import com.cao.youth.model.Spu;
import com.cao.youth.service.SpuService;
import com.cao.youth.util.CommonUtil;
import com.cao.youth.vo.PagingDozer;
import com.cao.youth.vo.SpuSimplifyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;



/**
 * @author Cao Study
 * @description BannerController
 * @date 2020/8/11 12:05
 */
@RestController
@RequestMapping("spu")
@Validated
public class SpuController {

    @Autowired
    private SpuService spuService;

    @GetMapping("/id/{id}/detail")
    public Spu getSpuById(@PathVariable @Positive Long id) throws NotFoundException {
        Spu spu = this.spuService.getSpuById(id);
        if (spu == null) {
            throw new NotFoundException(30002);
        }
        return spu;
    }


    @GetMapping("/latest")
    public PagingDozer<Spu,SpuSimplifyVO> getLatestSpuList(@RequestParam(defaultValue = "0") Integer start,
                                                @RequestParam(defaultValue = "20") Integer count) {
        PageCounter pageCounter= CommonUtil.convertToPageParameter(start,count);
        Page<Spu> page=this.spuService.getLatestPagingSpu(pageCounter.getPage(), pageCounter.getCount());
        return new PagingDozer<>(page,SpuSimplifyVO.class);
    }

    @GetMapping("/by/category/{id}")
    public PagingDozer<Spu,SpuSimplifyVO> getSpuByCategoryId(@PathVariable @Positive(message = "{id.positive}") Long id,
                                                          @RequestParam(name = "is_root",defaultValue = "false")Boolean isRoot,
                                                          @RequestParam(defaultValue = "0")Integer start,
                                                          @RequestParam(defaultValue = "10")Integer count){
        PageCounter pageCounter=CommonUtil.convertToPageParameter(start, count);
        Page<Spu> page = this.spuService.getByCategoryId(id, isRoot, start, count);
        return new PagingDozer<>(page,SpuSimplifyVO.class);
    }
}
