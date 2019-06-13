package com.sym.filter;

import com.sym.config.SymSignInFailedHandler;
import com.sym.constant.BrowserConstant;
import com.sym.entity.ImageCodeBean;
import com.sym.entity.SymSecurityProperties;
import com.sym.exception.BrowserBaseException;
import com.sym.exception.imageCode.ImageCodeCheckFailedException;
import com.sym.exception.imageCode.ImageCodeExpireException;
import com.sym.exception.imageCode.ImageCodeNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 沈燕明 on 2019/6/13 17:19.
 */
public class ImageCodeFilter extends OncePerRequestFilter {

    private final static Logger LOGGER = LoggerFactory.getLogger(ImageCodeFilter.class);


    private SymSecurityProperties symSecurityProperties;

    private SymSignInFailedHandler symSignInFailedHandler;

    public ImageCodeFilter(SymSecurityProperties symSecurityProperties,SymSignInFailedHandler symSignInFailedHandler){
        this.symSecurityProperties = symSecurityProperties;
        this.symSignInFailedHandler = symSignInFailedHandler;
    }

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 如果需要拦截
        if( whetherToIntercept( request.getRequestURI() ) ){
            try{
                checkImageCode(request.getParameter(BrowserConstant.REQUEST_IMAGE_CODE),request);
            }catch(BrowserBaseException e){
                LOGGER.warn("验证码验证失败，原因："+e.getMessage());
                symSignInFailedHandler.onAuthenticationFailure(request,response,e);
                return;
            }
        }
        filterChain.doFilter(request,response);
    }

    /**
     * 判断是否需要拦截
     * @param requestUrl 此次请求的URI
     * @return
     */
    private boolean whetherToIntercept(String requestUrl){
        boolean flag = true;
        String urls = symSecurityProperties.getImage().getUrl();
        if(!StringUtils.isEmpty( urls )){
            String[] urlArr = urls.split(",");
            for( String url : urlArr ){
                if( url.equals(requestUrl) ){
                    return true;
                }
            }
            flag = false;
        }
        return flag;
    }

    /**
     * 校验验证码
     * @param requestCode
     */
    private void checkImageCode(String requestCode,HttpServletRequest request){
        ServletWebRequest webRequest = new ServletWebRequest(request);
        ImageCodeBean imageCode = (ImageCodeBean)sessionStrategy.getAttribute(webRequest, BrowserConstant.SESSION_IMAGE_CODE);
        if( StringUtils.isEmpty(requestCode) || null == imageCode || StringUtils.isEmpty(imageCode.getCode()) ){
            throw new ImageCodeNotFoundException("验证码不能为空");
        }
        if( imageCode.isExpire() ){
            sessionStrategy.removeAttribute(webRequest,BrowserConstant.SESSION_IMAGE_CODE);
            throw new ImageCodeExpireException("验证码已过期");
        }
        if( !requestCode.equalsIgnoreCase( imageCode.getCode() ) ){
            sessionStrategy.removeAttribute(webRequest,BrowserConstant.SESSION_IMAGE_CODE);
            throw new ImageCodeCheckFailedException("验证码输入有误");
        }
    }
}
