package com.sym.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by 沈燕明 on 2019/5/29.
 */
@Data
@AllArgsConstructor
public class UserDto {
    private Long userId;
    private String username;
    private Integer age;
}
