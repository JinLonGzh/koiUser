package com.koi.blog.enums;

import com.koi.common.enums.RpcConstants;

/**
 * API 相关的枚举
 *
 * @Author zjl
 * @Date 2023/8/5 17:43
 */
public class ApiConstants {

    /**
     * 服务名
     * <p>
     * 注意，需要保证和 spring.application.name 保持一致
     */
    public static final String NAME = "blog-server";

    public static final String PREFIX = RpcConstants.RPC_API_PREFIX + "/blog";


}
