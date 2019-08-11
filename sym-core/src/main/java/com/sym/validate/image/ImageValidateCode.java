package com.sym.validate.image;

import com.sym.validate.ValidateCode;
import lombok.Data;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * 图片验证码
 * <p>
 * Created by 沈燕明 on 2019/6/29.
 */
@Data
public class ImageValidateCode extends ValidateCode {

    private BufferedImage bufferedImage;

    /**
     * 创建一个指定过期时间点的图片验证码
     *
     * @param code
     * @param bufferedImage
     * @param dateTime
     */
    public ImageValidateCode(String code, BufferedImage bufferedImage, LocalDateTime dateTime) {
        super(code, dateTime);
        this.bufferedImage = bufferedImage;
    }

    /**
     * 创建一个指定过期时间的图片验证码
     *
     * @param code
     * @param bufferedImage
     * @param expire
     */
    public ImageValidateCode(String code, BufferedImage bufferedImage, int expire) {
        super(code, expire);
        this.bufferedImage = bufferedImage;
    }


}
