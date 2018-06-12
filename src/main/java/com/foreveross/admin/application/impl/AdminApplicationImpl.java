/*******************************************************************************
 * Copyright (c) 2018-06-08 @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@gmail.com">Tyler Chen</a> - initial API and implementation.
 * Auto Generate By foreveross.com Quick Deliver Platform. 
 ******************************************************************************/
package com.foreveross.admin.application.impl;

import com.foreveross.admin.application.AdminApplication;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.iff.infra.util.GsonHelper;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;

import javax.inject.Inject;
import javax.inject.Named;
import java.net.URI;
import java.util.*;

/**
 * AdminApplicationImpl
 *
 * @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a>
 * @since 2018-06-08
 * auto generate by qdp.
 */
@Named("adminApplication")
public class AdminApplicationImpl implements AdminApplication {
    @Inject
    DiscoveryClient discoveryClient;

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
    public Map<String, List<String>> restInfos() {
        Map<String, List<String>> restUris = new TreeMap<String, List<String>>();
        Map<String, List<String>> serverInfos = serverInfos();
        for (List<String> uris : serverInfos.values()) {
            if (uris == null || uris.isEmpty()) {
                continue;
            }
            for (String uri : uris) {
                try {
                    HttpGet get = new HttpGet(uri.substring(uri.indexOf(',') + 1) + "/system/info");
                    get.setHeader("Content-Type", "application/json");
                    CloseableHttpResponse execute = HttpClients.createDefault().execute(get);
                    String json = EntityUtils.toString(execute.getEntity());
                    if (json == null || !json.startsWith("{")) {
                        continue;
                    }
                    Object o = GsonHelper.toJson(json);
                    if (o == null || !(o instanceof Map)) {
                        continue;
                    }
                    Map<String, Map<String, String>> map = (Map<String, Map<String, String>>) o;
                    Map<String, String> restConfig = map.get("restConfig");
                    if (restConfig == null || restConfig.isEmpty()) {
                        continue;
                    }
                    for (String key : restConfig.keySet()) {
                        if (key.startsWith("[GET]") || key.startsWith("[POST]") || key.startsWith("[PUT]") || key.startsWith("[DELETE]")) {
                            restUris.put(key, new ArrayList<String>());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return restUris;
    }

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
    public Map<String, List<String>> serverInfos() {
        Map<String, List<String>> infos = new LinkedHashMap<String, List<String>>();
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            List<ServiceInstance> instances = discoveryClient.getInstances(service);
            List<String> uris = new ArrayList<String>();
            for (ServiceInstance si : instances) {
                if (si instanceof EurekaDiscoveryClient.EurekaServiceInstance) {
                    EurekaDiscoveryClient.EurekaServiceInstance esi = (EurekaDiscoveryClient.EurekaServiceInstance) si;
                    uris.add(esi.getInstanceInfo().getStatus().name() + "," + esi.getUri().toString());
                } else {
                    URI uri = si.getUri();
                    uris.add("UP," + uri.toString());
                }
            }
            infos.put(service, uris);
        }
        return infos;
    }
}
