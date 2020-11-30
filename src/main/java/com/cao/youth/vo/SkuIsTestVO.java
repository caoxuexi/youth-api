
package com.cao.youth.vo;

import com.cao.youth.model.Sku;
import com.cao.youth.model.Spu;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class SkuIsTestVO extends Sku {
//    private Boolean isTest;

    public SkuIsTestVO(Sku sku, List<Spu> spuList) {
//        BeanUtils.copyProperties(sku, this);
//        Optional<Spu> s = spuList
//                .stream()
//                .filter(spu->spu.getId().equals(sku.getSpuId()))
//                .findFirst();
//        if(s.isPresent()){
//            this.isTest = s.get().getIsTest();
//        }
    }
}
