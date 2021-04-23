package com.cao.youth.repository;

import com.cao.youth.model.Iot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * @author Cao Study
 * @description IotRepository
 * @date 2021/4/23 8:20
 */
@Repository
public interface IotRepository extends JpaRepository<Iot, Long> {
    public Optional<Iot> findById(Long id);

    @Transactional
    @Modifying
    @Query("update Iot i set i.humidity=:humidity, i.temperature=:temperature where i.id =1L")
    public int updateTemAndHum(Double temperature,Double humidity);
}
