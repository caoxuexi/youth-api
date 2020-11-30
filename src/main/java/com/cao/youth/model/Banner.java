
package com.cao.youth.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
//where delete_time == null
@Where(clause = "delete_time is null ")
@Table(name = "banner") //可以把表名改了
public class Banner extends BaseEntity {
    @Id //选javax的那个包
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 16)
    private String name;
    @Transient //隐藏不生成该字段
    private String description;
    private String title;
    private String img;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "bannerId")
    private List<BannerItem> items;
}
