package com.cao.youth.service;

import com.cao.youth.dto.IotDTO;
import com.cao.youth.dto.OrderDTO;
import com.cao.youth.model.Iot;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

/**
 * @author Cao Study
 * @description IotService
 * @date 2021/4/23 8:22
 */
public interface IotService {
    public Optional<Iot> getTemAndHum();

    public int updateTemAndHum(IotDTO iotDTO);
}
