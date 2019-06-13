package com.sym.util;

import com.sym.entity.ImageCodeBean;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 图片验证码生成类
 *
 * Created by 沈燕明 on 2019/6/13 17:00.
 */
public class ImageCodeUtil {

    public static ImageCodeBean getImageCode(int width,int height,int length,int expireTime){

        if( length < 1 ){
            throw new IllegalArgumentException("验证码长度不能小于1");
        }

        //图像数据缓冲区，生成的图片在内存里有一个图像缓冲区，利用这个缓冲区我们可以很方便的操作这个图片
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        //绘制图片
        Graphics img = bi.getGraphics();

        //设置颜色
        Color c = new Color(200,150,255);//这个什么意思，待查？
        img.setColor(c);

        //画图片的背景
        img.fillRect(0, 0, width, height);

        //从这里面随机的获取字符
        char[] charArr = "ABCDEFGHIJKLMNOPQRSTUVWSYZ0123456789qazwsxedcrfvtgbyhnujmkiolp".toCharArray();
        int len = charArr.length;//数组的长度，也是随机数的取值范围
        int index = 0;//数组下标

        Random rd = new Random();//随机数工具
        StringBuffer checkCode = new StringBuffer();//用于保存验证码
        for(int i=0;i<length;i++){
            //循环几次就获取几个随机数
            index = rd.nextInt(len);//获得随机下标
            img.setColor(new Color(rd.nextInt(88), rd.nextInt(188), rd.nextInt(255)));//每个验证码的颜色随机
            img.drawString(charArr[index]+"",(i*15)+3,18);//将随机数画到图片中
            checkCode.append(charArr[index]+"");//保存验证码
        }

        return new ImageCodeBean(checkCode.toString(),bi,expireTime);
    }
}
