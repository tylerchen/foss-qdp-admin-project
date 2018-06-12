/*******************************************************************************
 * Copyright (c) 七月 14 2016 @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@gmail.com">Tyler Chen</a> - initial API and implementation.
 * Auto Generate By foreveross.com Quick Deliver Platform. 
 ******************************************************************************/
package com.foreveross.common.web;

import com.foreveross.common.proxy.ProxyServlet;
import org.apache.commons.lang3.StringUtils;
import org.iff.infra.util.MapHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

/**
 * proxy
 *
 * @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a>
 * @since Aug 9, 2015
 * auto generate by qdp.
 */
@Controller
@RequestMapping("/proxy")
public class ProxyController {

    @RequestMapping(path = "/{appName}/**")//path=/proxy/app/realUri
    public void proxy(HttpServletRequest request, HttpServletResponse response) {
        String requestURI = request.getRequestURI();
        System.out.println("URI:"+requestURI);
        String reportPath = StringUtils.substringAfter(requestURI, "/proxy/");
        String[] pathSplit = StringUtils.split(reportPath, "/");
        final String appPath = "/proxy/" + pathSplit[0];
        final HttpServletRequest wrapper = new HttpServletRequestWrapper(request) {
            public String getPathInfo() {
                String path = super.getPathInfo();
                if (path.startsWith(appPath)) {
                    final int length = appPath.length();
                    path = path.substring(length);
                }
                return path;
            }
        };
        try {
            ProxyServlet proxyServlet = new ProxyServlet(MapHelper.toMap(ProxyServlet.P_TARGET_URI, "http://localhost:9200/" + pathSplit[0]));
            proxyServlet.service(wrapper, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
