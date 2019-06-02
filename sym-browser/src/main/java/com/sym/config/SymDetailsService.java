package com.sym.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * UserDetailsService是springSecurity提供用来获取用户的信息的接口
 * 我们需要自己实现此接口，并将其实现类注入到IOC容器中，然后用户登录
 * 时，springSecurity会调用此方法，将用户名username传递过来，让我们
 * 根据用户名去查询用户的信息
 *
 * Created by 沈燕明 on 2019/5/29.
 */
@Component
public class SymDetailsService implements UserDetailsService {

    private final static Logger logger = LoggerFactory.getLogger(SymDetailsService.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 我们在 loadUserByUsername() 方法中，可以通过读取数据库获取用户的信息
     * 将其封装成UserDetails对象，springSecurity会自动验证密码
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("用户输入的username={}",username);

        // 这里我们就可以通过username去查询数据库获取用户的信息
        // ...省略读取数据库的步骤

        // org.springframework.security.core.userdetails.User是springSecurity提供的UserDetails默认实现类
        // 因为我们不会讲用户密码明文保存到数据库，所以一般会加密存储，springSecurity提供了PasswordEncoder来实现密码加密
        String encodePassword = passwordEncoder.encode("123456");
        logger.info("加密后的密码={}",encodePassword);
        return new User(username,encodePassword,true,
                true,true,true,
                AuthorityUtils.NO_AUTHORITIES);
    }
}
