package com.example.containstest.containsTestDemo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author 22511
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {
    String invoiceNo;

    BigDecimal price;
}