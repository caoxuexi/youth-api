package com.cao.youth.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author 曹学习
 * @description Tag
 * @date 2020/12/29 11:20
 */
@Entity
@Getter
@Setter
public class Tag extends BaseEntity{
    @Id
    private Long id;
    private String title;
    private String description;
    private Integer highlight;
    private Integer type;

}
