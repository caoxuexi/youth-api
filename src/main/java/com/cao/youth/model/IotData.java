package com.cao.youth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author 曹学习
 * @description IotData
 * @date 2021/4/3 14:12
 */
@Entity
@Data
public class IotData {
    @Id
    private Long id;
    private double temperature;
    private double humidity;

    @JsonIgnore
    @Column(insertable=false, updatable=false)
    private Date updateTime;
}
