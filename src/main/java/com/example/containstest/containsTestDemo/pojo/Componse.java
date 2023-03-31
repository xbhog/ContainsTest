package com.example.containstest.containsTestDemo.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xbhog
 * @describe:
 * @date 2023/3/30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Componse<T> implements Serializable {

    private static final long seriaVerionUID = -4683131314684864L;

    T data;
}
