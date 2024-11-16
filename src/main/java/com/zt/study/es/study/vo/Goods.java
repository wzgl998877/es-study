package com.zt.study.es.study.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhengtao on 2024/11/16
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Goods {
    private String skuId;
    private String skuName;
    private String price;
    private String num;
}
