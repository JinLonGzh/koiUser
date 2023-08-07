package com.koi.system.service.auth.impl;

import cn.hutool.core.util.ObjectUtil;
import com.koi.common.enums.CommonStatusEnum;
import com.koi.common.enums.UserTypeEnum;
import com.koi.common.exception.ServiceException;
import com.koi.system.convert.auth.AuthConvert;
import com.koi.system.domain.auth.vo.request.AuthLoginReq;
import com.koi.system.domain.auth.vo.response.AuthLoginResp;
import com.koi.system.domain.oauth2.entity.Oauth2AccessToken;
import com.koi.system.domain.user.entity.AdminUser;
import com.koi.system.enums.oauth2.OAuth2ClientConstants;
import com.koi.system.service.auth.AuthService;
import com.koi.system.service.oauth2.Oauth2TokenService;
import com.koi.system.service.user.AdminUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.koi.common.exception.enums.GlobalErrorCodeConstants.BAD_REQUEST;

/**
 * -
 *
 * @Author zjl
 * @Date 2023/8/4 21:14
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    private AdminUserService adminUserService;
    @Resource
    private Oauth2TokenService oauth2TokenService;


    @Override
    public AdminUser authenticate(String username, String password) {
        // 校验账号是否存在
        AdminUser user = adminUserService.getUserByUsername(username);
        if (user == null) {
            throw new ServiceException(BAD_REQUEST.getCode(), "登录失败，账号密码不正确");
        }
        if (!adminUserService.isPasswordMatch(password, user.getPassword())) {
            throw new ServiceException(BAD_REQUEST.getCode(), "登录失败，账号密码不正确");
        }
        // 校验是否禁用
        if (ObjectUtil.notEqual(user.getStatus(), CommonStatusEnum.ENABLE.getStatus())) {
            throw new ServiceException(BAD_REQUEST.getCode(), "登录失败，账号已禁用");
        }
        return user;
    }

    @Override
    public AuthLoginResp login(AuthLoginReq reqVO) {
        // 使用账号密码，进行登录
        AdminUser user = authenticate(reqVO.getUsername(), reqVO.getPassword());

        // 创建 Token 令牌
        return createTokenAfterLoginSuccess(user.getId());
    }

    private AuthLoginResp createTokenAfterLoginSuccess(Long userId) {
        // 创建访问令牌
        Oauth2AccessToken oauth2AccessToken = oauth2TokenService.createAccessToken(userId, getUserType().getValue(),
                OAuth2ClientConstants.CLIENT_ID_DEFAULT, null);
        // 构建返回结果
        return AuthConvert.convertAuthLogin(oauth2AccessToken);
    }


    private UserTypeEnum getUserType() {
        return UserTypeEnum.ADMIN;
    }


}
