package com.koi.system.mapper.redis.oauth2;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.koi.common.utils.collection.CollectionUtils;
import com.koi.common.utils.json.JsonUtils;
import com.koi.framework.redis.core.utils.RedisUtils;
import com.koi.system.constants.RedisKeyConstants;
import com.koi.system.domain.oauth2.entity.Oauth2AccessToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.koi.system.constants.RedisKeyConstants.formatAccessTokenKey;

/**
 * -
 *
 * @Author zjl
 * @Date 2023/8/10 11:28
 */
@Repository
public class Oauth2AccessTokenRedisDAO {

    public void setAccessToken(Oauth2AccessToken oauth2AccessToken) {
        String redisKey = formatAccessTokenKey(oauth2AccessToken.getAccessToken());
        // 清理多余字段，避免缓存
        oauth2AccessToken.setUpdateTime(null);
        oauth2AccessToken.setCreateTime(null);
        oauth2AccessToken.setDeleted(null);
        long time = LocalDateTimeUtil.between(LocalDateTime.now(), oauth2AccessToken.getExpiresTime(), ChronoUnit.SECONDS);
        if (time > 0) {
            RedisUtils.set(redisKey, JsonUtils.toJsonString(oauth2AccessToken), time, TimeUnit.SECONDS);
        }
    }

    public Oauth2AccessToken getAccessToken(String accessToken) {
        String redisKey = formatAccessTokenKey(accessToken);
        String result = RedisUtils.getStr(redisKey);
        return StringUtils.isNotEmpty(result) ? JsonUtils.parseObject(result, Oauth2AccessToken.class) : null;
    }

    public void deleteAccessToken(String accessToken) {
        String redisKey = RedisKeyConstants.formatAccessTokenKey(accessToken);
        RedisUtils.del(redisKey);
    }

    public void deleteList(Set<String> accessTokens) {
        List<String> redisKeys = CollectionUtils.convertList(accessTokens, RedisKeyConstants::formatAccessTokenKey);
        RedisUtils.del(redisKeys.toArray(new String[0]));
    }

}
