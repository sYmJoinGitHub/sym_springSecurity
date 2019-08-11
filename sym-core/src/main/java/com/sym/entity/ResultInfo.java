package com.sym.entity;

import lombok.Builder;
import lombok.Data;

/**
 * 返回给前端的结果实体类
 * <p>
 * Created by 沈燕明 on 2019/6/2.
 */
@Data
@Builder
public class ResultInfo {

    private int code;
    private String message;

    public static ResultInfo success() {
        return success(200, "请求成功");
    }

    public static ResultInfo success(String message) {
        return success(200, message);
    }

    public static ResultInfo success(int code, String message) {
        return ResultInfo.builder().code(code).message(message).build();
    }

    public static ResultInfo failed() {
        return success(200, "请求失败");
    }

    public static ResultInfo failed(String message) {
        return success(200, message);
    }

    public static ResultInfo failed(int code, String message) {
        return ResultInfo.builder().code(code).message(message).build();
    }


}
