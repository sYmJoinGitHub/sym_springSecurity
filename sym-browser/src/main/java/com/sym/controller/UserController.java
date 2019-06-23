package com.sym.controller;

import com.sun.deploy.net.HttpResponse;
import com.sym.constant.BrowserConstant;
import com.sym.entity.*;
import com.sym.util.ImageCodeUtil;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by 沈燕明 on 2019/5/29.
 */
@RestController
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    /*
     * springSecurity会把请求Request缓存起来,他提供了 RequestCache 接口供保存/获取Request
     * 默认实现类是 HttpSessionRequestCache，它是把Request保存到session中,
     */
    private RequestCache requestCache = new HttpSessionRequestCache();

    /*
     * springSecurity提供的用于重定向的工具类
     */
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    /*
     * springSecurity提供的用于操作session的工具类
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /*
     * 映射application.yml的配置属性类，提供多样的选择
     */
    @Autowired
    private SymSecurityProperties symSecurityProperties;


    /**
     * 当springSecurity发现一个请求需要认证时，会把请求转发到这里
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("loginHandler")
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ResultInfo signIn(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 首先取出封装了用户请求的 HttpServletRequest 对象
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (null != savedRequest) {
            logger.info("请求的待认证的URL地址为={}", savedRequest.getRedirectUrl());
            // 获取请求头部信息
            List<String> accept = savedRequest.getHeaderValues("accept");
            logger.info("HTTP-header,accept={}", accept);
            for (String s : accept) {
                // 带有html字样的说明是浏览器发起的请求
                if (s.contains("html")) {
                    //response.sendRedirect("/signIn.html");
                    redirectStrategy.sendRedirect(request,response,symSecurityProperties.getBrowser().getSignInHtmlPath());
                    return null;
                }
            }
        }
        // 其它情况返回JSON字符串
        return ResultInfo.failed("请先登录~！");
    }

    /**
     * 如果配置登陆成功转发地址：成功登录会将请求转发到这个方法上
     *
     * @return
     */
    @RequestMapping("/succeedAfterLogin")
    public String loginSucceed() {
        return "恭喜你已经成功登录了";
    }

    /**
     * 如果配置登陆失败转发地址：登录失败会将请求转发到这个方法上
     *
     * @return
     */
    @RequestMapping("/failedAfterLogin")
    public String loginFailed() {
        return "账户或密码错误";
    }

    /**
     * 需要认证的请求
     *
     * @return
     */
    @GetMapping("list")
    public List<UserDto> list() {
        return Arrays.asList(new UserDto(1L, "张三", 23), new UserDto(2L, "李四", 28));
    }

    @GetMapping("me")
    public Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取图片验证码
     * @param request
     * @param response
     */
    @RequestMapping("getImage")
    public void getImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ImageCodeProperties imageCodeProperties = symSecurityProperties.getImage();
        int width = imageCodeProperties.getWidth();
        int height = imageCodeProperties.getHeight();
        int length = imageCodeProperties.getLength();
        int expireTime = imageCodeProperties.getExpireTime();
        ImageCodeBean imageCodeBean = ImageCodeUtil.getImageCode(width,height,length,expireTime);
        sessionStrategy.setAttribute(new ServletWebRequest(request), BrowserConstant.SESSION_IMAGE_CODE,imageCodeBean);
        ImageIO.write(imageCodeBean.getImage(),"jpeg",response.getOutputStream());
    }


    /**
     * 获取短信验证码
     * @param request
     * @param response
     */
    @RequestMapping("getSmsCode")
    public void getSmsCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuffer sb = new StringBuffer();
        Random r = new Random();
        for( int i=0;i<4;i++ ){
            sb.append( r.nextInt(10) );
        }
        sessionStrategy.setAttribute(new ServletWebRequest(request), BrowserConstant.SESSION_SMS_CODE,sb.toString());
        response.setContentType("text/html;charset=utf-8");
        StringBuilder jsBuilder = new StringBuilder();
        jsBuilder.append("<script>");
        jsBuilder.append("alert(\"您的验证码是").append(sb.toString()).append("\");");
        jsBuilder.append("</script>");
        response.getWriter().println(jsBuilder.toString());
    }
}
