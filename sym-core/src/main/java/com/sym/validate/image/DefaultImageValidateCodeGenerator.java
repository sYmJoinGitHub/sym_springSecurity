package com.sym.validate.image;

import com.sym.entity.SymSecurityProperties;
import com.sym.validate.ValidateCode;
import com.sym.validate.ValidateCodeGenerator;
import lombok.Data;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 默认的图片验证码生成类
 * <p>
 * Created by 沈燕明 on 2019/6/29.
 */
@Data
public class DefaultImageValidateCodeGenerator implements ValidateCodeGenerator {

    private SymSecurityProperties symSecurityProperties;

    @Override
    public ValidateCode createValidateCode(ServletWebRequest request) {
        // 前端传过来的图片参数优先级最高，用户配置的其次，默认配置的最低
        int width = ServletRequestUtils.getIntParameter(request.getRequest(), "width", symSecurityProperties.getCode().getImageWidth());
        int height = ServletRequestUtils.getIntParameter(request.getRequest(), "height", symSecurityProperties.getCode().getImageHeight());

        // 创建画图工具
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();

        // 随机数工具
        Random random = new Random();

        //设置画图属性
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        g.setColor(getRandColor(160, 200));

        // 画出随机背景条纹
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        // 生成随机数，并且画图图片
        String sRand = "";
        for (int i = 0; i < symSecurityProperties.getCode().getImageLength(); i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(rand, 13 * i + 6, 16);
        }
        g.dispose();

        return new ImageValidateCode(sRand, image, symSecurityProperties.getCode().getImageExpireTime());
    }

    /**
     * 生成随机背景条纹
     *
     * @param fc
     * @param bc
     * @return
     */
    private Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

}
