package com.cao.youth.api.v1;

import com.cao.youth.core.interceptors.ScopeLevel;
import com.cao.youth.exception.http.NotFoundException;
import com.cao.youth.model.Banner;
import com.cao.youth.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.Map;


/**
 * @author Cao Study
 * @description BannerController
 * @date 2020/8/11 12:05
 */
@RestController
@RequestMapping("banner")
@Validated
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @GetMapping("/name/{name}")
    public Banner getByName(@PathVariable @NotBlank String name) throws NotFoundException {
        Banner banner=bannerService.getByName(name);
        if(banner==null){
            throw new NotFoundException(30005);
        }
        return banner;
    }

}
