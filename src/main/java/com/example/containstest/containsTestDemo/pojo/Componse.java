package com.example.containstest.containsTestDemo.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xbhog
 * @describe:
 * @date 2023/3/30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Componse<T> {

    T data;
}
