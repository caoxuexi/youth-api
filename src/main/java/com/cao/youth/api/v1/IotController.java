package com.cao.youth.api.v1;

import com.cao.youth.model.IotData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 曹学习
 * @description IotController
 * @date 2021/4/3 14:10
 */
@RestController
@RequestMapping("iot")
public class IotController {
    //获取温湿度数据
    @RequestMapping("/getTemAndHum")
    public IotData getTemAndHum(){
        return null;
    }
}
