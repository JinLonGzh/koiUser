package com.koi.system.convert.oauth2;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.koi.common.enums.UserTypeEnum;
import com.koi.common.utils.bean.BeanCopyUtils;
import com.koi.system.domain.oauth2.entity.Oauth2AccessToken;
import com.koi.system.domain.oauth2.vo.response.OAuth2OpenAccessTokenResp;
import com.koi.system.domain.oauth2.vo.response.OAuth2OpenCheckTokenResp;
import com.koi.system.utils.oauth2.OAuth2Utils;
import org.springframework.stereotype.Component;

import static com.koi.common.utils.json.JsonUtils.stringListFromJson;

/**
 * 描述
 *
 * @Author zjl
 * @Date 2023/8/1 16:21
 */
@Component
public class Oauth2OpenConvert {

    public OAuth2OpenAccessTokenResp convertAccessToken(Oauth2AccessToken bean) {
        OAuth2OpenAccessTokenResp oAuth2OpenAccessTokenResp = BeanCopyUtils.copyObject(bean, OAuth2OpenAccessTokenResp.class);
        oAuth2OpenAccessTokenResp.setScope(OAuth2Utils.buildScopeStr(stringListFromJson(bean.getScopes())));
        oAuth2OpenAccessTokenResp.setExpiresIn(OAuth2Utils.getExpiresIn(bean.getExpiresTime()));
        return oAuth2OpenAccessTokenResp;
    }

    public OAuth2OpenCheckTokenResp convertCheckToken(Oauth2AccessToken bean) {
        OAuth2OpenCheckTokenResp respVO = BeanCopyUtils.copyObject(bean, OAuth2OpenCheckTokenResp.class);
        respVO.setScopes(stringListFromJson(bean.getScopes()));
        respVO.setExp(LocalDateTimeUtil.toEpochMilli(bean.getExpiresTime()) / 1000L);
        respVO.setUserType(UserTypeEnum.ADMIN.getValue());
        return respVO;
    }

}