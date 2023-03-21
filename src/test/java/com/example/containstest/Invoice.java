package com.example.containstest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

public class Invoice {
    String invoiceNo;

    BigDecimal price;

    public Invoice() {
    }

    public Invoice(String invoiceNo, BigDecimal price) {
        this.invoiceNo = invoiceNo;
        this.price = price;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}