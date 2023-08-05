package com.koi.system.api.oauth2;

import com.koi.common.domain.CommonResult;
import com.koi.system.api.oauth2.dto.request.OAuth2AccessTokenCreateReqDTO;
import com.koi.system.api.oauth2.dto.response.OAuth2AccessTokenCheckRespDTO;
import com.koi.system.api.oauth2.dto.response.OAuth2AccessTokenRespDTO;
import com.koi.system.convert.auth.Oauth2TokenConvert;
import com.koi.system.service.oauth2.Oauth2TokenService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

/**
 * OAuth2.0 Token API 实现类
 *
 * @Author zjl
 * @Date 2023/8/1 21:30
 */
@Service
@Validated
public class OAuth2TokenApiImpl implements OAuth2TokenApi{

    @Resource
    private Oauth2TokenService oauth2TokenService;

    @Override
    public OAuth2AccessTokenRespDTO createAccessToken(OAuth2AccessTokenCreateReqDTO reqDTO) {
        return null;
    }

    @Override
    public CommonResult<OAuth2AccessTokenCheckRespDTO> checkAccessToken(String accessToken) {
        return CommonResult.success(Oauth2TokenConvert.convertAccessTokenCheck(oauth2TokenService.checkAccessToken(accessToken)));
    }

    @Override
    public OAuth2AccessTokenRespDTO removeAccessToken(String accessToken) {
        return null;
    }

    @Override
    public OAuth2AccessTokenRespDTO refreshAccessToken(String refreshToken, String clientId) {
        return null;
    }
}
