package com.sym.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * Created by 沈燕明 on 2019/6/13 17:00.
 */
@Data
@NoArgsConstructor
public class ImageCodeBean {

    private String code;
    private BufferedImage image;
    private LocalDateTime expireTime;

    public ImageCodeBean(String code,BufferedImage image,int expireTime){
        this.code = code;
        this.image = image;
        this.expireTime = LocalDateTime.now().plusSeconds(expireTime);
    }

    /**
     * 判断是否过期
     * @return true-已过期，false-未过期
     */
    public boolean isExpire(){
        Assert.notNull(expireTime,"expireTime未被初始化");
        return this.expireTime.isBefore(LocalDateTime.now());
    }
}
