package com.cao.youth.service.impl;

import com.cao.youth.dto.IotDTO;
import com.cao.youth.model.Iot;
import com.cao.youth.repository.IotRepository;
import com.cao.youth.service.IotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Cao Study
 * @description IotServiceImpl
 * @date 2021/4/23 8:22
 */
@Service
public class IotServiceImpl implements IotService {
    @Autowired
    private IotRepository iotRepository;

    @Override
    public Optional<Iot> getTemAndHum() {
        return iotRepository.findById(1L);
    }

    @Override
    public int updateTemAndHum(IotDTO iotDTO) {
        if(iotDTO!=null){
            return iotRepository.updateTemAndHum(iotDTO.getTemperature(), iotDTO.getHumidity());
        }else{
            return 0;
        }
    }
}
