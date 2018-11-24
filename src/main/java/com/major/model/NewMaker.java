package com.major.model;

import com.major.entity.Maker;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author xuquanming
 * @date 2018/11/1 11:15
 */
@Data
public class NewMaker extends Maker {
    private BigDecimal storeMoney;
}
