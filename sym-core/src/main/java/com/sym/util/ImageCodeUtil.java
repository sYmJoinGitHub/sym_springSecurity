package com.sym.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 生成图片验证码
 * <p>
 * Created by 沈燕明 on 2019/6/12.
 */
public class ImageCodeUtil {

    /**
     * 画图片
     * @param width 68-图片宽度
     * @param height 22-图片高度
     * @return
     */
    public static Map<String, Object> getImageAndCode(int width, int height) {

        Map<String, Object> resultMap = new HashMap<>();

        //图像数据缓冲区，生成的图片在内存里有一个图像缓冲区，利用这个缓冲区我们可以很方便的操作这个图片
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        //绘制图片
        Graphics img = bi.getGraphics();

        //设置颜色
        Color c = new Color(200, 150, 255);//这个什么意思，待查？
        img.setColor(c);

        //画图片的背景
        img.fillRect(0, 0, width, height);

        //从这里面随机的获取字符
        char[] charArr = "ABCDEFGHIJKLMNOPQRSTUVWSYZ0123456789".toCharArray();
        int len = charArr.length;//数组的长度，也是随机数的取值范围
        int index = 0;//数组下标

        Random rd = new Random();//随机数工具
        StringBuffer checkCode = new StringBuffer();//用于保存验证码
        for (int i = 0; i < 4; i++) {
            //循环几次就获取几个随机数
            index = rd.nextInt(len);//获得随机下标
            img.setColor(new Color(rd.nextInt(88), rd.nextInt(188), rd.nextInt(255)));//每个验证码的颜色随机
            img.drawString(charArr[index] + "", (i * 15) + 3, 18);//将随机数画到图片中
            checkCode.append(charArr[index] + "");//保存验证码
        }
        resultMap.put("image", bi);
        resultMap.put("code", checkCode.toString());
        return resultMap;
    }
}
