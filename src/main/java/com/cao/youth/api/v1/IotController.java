package com.cao.youth.api.v1;

import com.cao.youth.dto.IotDTO;
import com.cao.youth.model.Iot;
import com.cao.youth.service.IotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 曹学习
 * @description IotController
 * @date 2021/4/3 14:10
 */
@RestController
@RequestMapping("iot")
public class IotController {
    @Autowired
    private IotService iotService;
    //获取温湿度数据
    @GetMapping("/getTemAndHum")
    public Iot getTemAndHum(){
        Iot iot= iotService.getTemAndHum().orElse(new Iot());
        return iot;
    }

    @PostMapping("/updateTemAndHum")
    public String updateTemAndHum(@RequestBody IotDTO iotDTO){
        int isOk=iotService.updateTemAndHum(iotDTO);
        if (isOk==1){
            return "OK";
        }else{
            return "Failed";
        }
    }
}
