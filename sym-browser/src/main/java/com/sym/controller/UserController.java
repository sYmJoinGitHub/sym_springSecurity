package com.sym.controller;

import com.sym.entity.UserDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * Created by 沈燕明 on 2019/5/29.
 */
@RestController
public class UserController {

    @GetMapping("list")
    public List<UserDto> list(){
        return Arrays.asList(
            new UserDto(1L,"张三",23),
            new UserDto(2L ,"李四",28)
        );
    }
}
