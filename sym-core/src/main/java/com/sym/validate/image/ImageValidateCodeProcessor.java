package com.sym.validate.image;

import com.sym.validate.AbstractValidateCodeProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;

/**
 * Created by 沈燕明 on 2019/6/29.
 */
@Component("imageValidateCodeProcessor")
public class ImageValidateCodeProcessor extends AbstractValidateCodeProcessor<ImageValidateCode> {


    @Override
    protected void send(ServletWebRequest request, ImageValidateCode code) throws Exception {
        ImageIO.write(code.getBufferedImage(), "JPEG", request.getResponse().getOutputStream());
    }
}
