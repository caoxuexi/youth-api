
package com.cao.youth.bo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PageCounter {
    private Integer page;
    private Integer count;
}
