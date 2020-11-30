
package com.cao.youth.service.impl;

import com.cao.youth.model.SaleExplain;
import com.cao.youth.repository.SaleExplainRepository;
import com.cao.youth.service.SaleExplainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleExplainServiceImpl implements SaleExplainService {
    @Autowired
    private SaleExplainRepository saleExplainRepository;

    public List<SaleExplain> getSaleExplainFixedList() {
        return this.saleExplainRepository.findByFixedOrderByIndex(true);
    }
}
