/*******************************************************************************
 * Copyright (c) 2018-06-08 @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@gmail.com">Tyler Chen</a> - initial API and implementation.
 * Auto Generate By foreveross.com Quick Deliver Platform. 
 ******************************************************************************/
package com.foreveross.admin.application;

import java.util.List;
import java.util.Map;

/**
 * AdminApplication
 *
 * @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a>
 * @since 2018-06-08
 * auto generate by qdp.
 */
public interface AdminApplication {

    /**
     * <pre>
     * 获得REST服务的信息，数据格式：
     * MAP={
     *     [GET|POST|PUT|DELETE]/rest/url : [roles]
     * }
     * </pre>
     *
     * @return
     */
    Map<String, List<String>> restInfos();

    /**
     * <pre>
     * 获得服务实例的信息，数据格式：
     * MAP={
     *     eureka-registry-name : [http://ip:port/zone,http://ip:port/zone]
     * }
     * </pre>
     *
     * @return
     */
    Map<String, List<String>> serverInfos();
}
