package com.koi.system.convert.auth;

import com.koi.common.utils.bean.BeanCopyUtils;
import com.koi.system.domain.auth.vo.response.AuthLoginResp;
import com.koi.system.domain.oauth2.entity.Oauth2AccessToken;

/**
 * -
 *
 * @Author zjl
 * @Date 2023/8/4 21:54
 */
public interface AuthConvert {

    static AuthLoginResp convertAuthLogin(Oauth2AccessToken bean) {
        return BeanCopyUtils.copyObject(bean, AuthLoginResp.class);
    }


}
