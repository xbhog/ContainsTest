package com.example.containstest.containsTestDemo.service;

import com.example.containstest.containsTestDemo.pojo.Componse;

/**
 * @author xbhog
 * @describe:
 * @date 2023/3/30
 */
public class payResultimpl implements payresultController{
    @Override
    public Componse<String> payReult() {
        return Componse.<String>builder().data("success").build();
    }
}
