package com.sym.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * Created by 沈燕明 on 2019/5/29.
 */
@Mapper
public interface DemoMapper {
    Map list();
}
