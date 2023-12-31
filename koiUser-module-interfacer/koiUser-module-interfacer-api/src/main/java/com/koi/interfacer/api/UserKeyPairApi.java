package com.koi.interfacer.api;

import com.koi.common.domain.CommonResult;
import com.koi.interfacer.api.dto.response.UserKeyPairRespDTO;
import com.koi.interfacer.enums.ApiConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * userKeyPair API 接口
 *
 * @Author zjl
 * @Date 2023/8/31 14:34
 */
@FeignClient(name = ApiConstants.NAME)
public interface UserKeyPairApi {

    String PREFIX = ApiConstants.PREFIX + "/keyPair";

    /**
     * 主要是提供给 Gateway 使用
     */
    @SuppressWarnings("HttpUrlsUsage")
    String URL_KEYPAIR = "http://" + ApiConstants.NAME + PREFIX + "/getByKeyPair";

    @GetMapping(PREFIX + "/getByKeyPair")
    CommonResult<UserKeyPairRespDTO> getUserKeyPairByAccessKey(@RequestParam("accessKey") String accessKey);

}
