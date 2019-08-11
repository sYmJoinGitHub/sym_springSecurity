package com.sym.validate;

import lombok.Data;
import org.springframework.util.Assert;

import javax.validation.Valid;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 验证码实体父类
 * <p>
 * Created by 沈燕明 on 2019/6/29.
 */
@Data
public class ValidateCode implements Serializable {

    private static final long serialVersionUID = -6499651500719680842L;

    // 默认3分钟的有效时间
    private final static int DEFAULT_EXPIRE = 3 * 60;

    // 验证码
    private String code;

    // 到期时间
    private LocalDateTime localDateTime;

    /**
     * 创建一个默认3分钟过期的验证码
     *
     * @param code
     */
    public ValidateCode(String code) {
        this.code = code;
        localDateTime = LocalDateTime.now().plusSeconds(DEFAULT_EXPIRE);
    }

    /**
     * 创建一个指定过期时间点的验证码
     *
     * @param code
     * @param expireTime
     */
    public ValidateCode(String code, LocalDateTime expireTime) {
        this.code = code;
        this.localDateTime = expireTime;
    }

    /**
     * 创建一个指定过期时间的验证码
     *
     * @param code
     * @param expire
     */
    public ValidateCode(String code, int expire) {
        this.code = code;
        this.localDateTime = LocalDateTime.now().plusSeconds(expire);
    }

    /**
     * 判断是否过期了
     *
     * @return true-过期,false-未过期
     */
    public boolean isExpire() {
        Assert.notNull(localDateTime, "过期时间 localDateTime 未定义");
        return localDateTime.isBefore(LocalDateTime.now());
    }


}
