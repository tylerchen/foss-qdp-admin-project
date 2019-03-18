/*******************************************************************************
 * Copyright (c) 2018-07-02 @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@gmail.com">Tyler Chen</a> - initial API and implementation.
 * Auto Generate By foreveross.com Quick Deliver Platform. 
 ******************************************************************************/
package com.foreveross.common.config;

import com.foreveross.common.util.EncryptDecryptUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

/**
 * CustomSerializerConfiguration
 *
 * @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a>
 * @since 2018-07-02
 * auto generate by qdp.
 */
@Configuration
@EnableFeignClients(basePackages = "com.foreveross.common")
public class FeignClientConfiguration {

    @Bean
    public RequestInterceptor customHeaderRequestInterceptor() {
        return new RequestInterceptor() {
            public void apply(RequestTemplate requestTemplate) {
                requestTemplate.header("zuultoken", EncryptDecryptUtil.deflate2Base62Encrypt("zuul@admin.com"));
                requestTemplate.header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE);
                requestTemplate.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            }
        };
    }

}
