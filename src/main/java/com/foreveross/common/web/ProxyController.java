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
import com.foreveross.common.shiro.JWTTokenHelper;
import com.foreveross.common.util.EncryptDecryptUtil;
import org.apache.commons.lang3.StringUtils;
import org.iff.infra.util.HttpHelper;
import org.iff.infra.util.Logger;
import org.iff.infra.util.MapHelper;
import org.iff.infra.util.SocketHelper;
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

    @RequestMapping(path = "/{host}/{port}/**")//path=/host/port/realUri
    public void proxy(final HttpServletRequest request, HttpServletResponse response) {
        String requestURI = request.getRequestURI();
        System.out.println("URI:" + requestURI);
        String reportPath = StringUtils.substringAfter(requestURI, "/proxy/");
        String[] pathSplit = StringUtils.split(reportPath, "/");
        if (pathSplit.length < 2) {
            try {
                response.setContentType("text/html;charset=UTF-8");
                String html = "UNKOWN URL:" + reportPath;
                response.getWriter().print(html);
                SocketHelper.closeWithoutError(response.getWriter());
            } catch (Exception e) {
            }
        }
        final String hostPort = "/proxy/" + pathSplit[0] + "/" + pathSplit[1];
        final HttpServletRequest wrapper = new HttpServletRequestWrapper(request) {
            public String getPathInfo() {
                String path = request.getRequestURI();
                return StringUtils.substringAfter(path, hostPort);
            }

            public String getContextPath() {
                return hostPort;
            }
        };
        try {
            System.out.println(wrapper.getRequestURI());
            System.out.println(wrapper.getRequestURL());
            System.out.println(wrapper.getContextPath());

            String targetUrl = "http://" + pathSplit[0] + ":" + pathSplit[1];
            Logger.info("Proxy target url: " + targetUrl + ", origin url: " + requestURI);

            ProxyServlet proxyServlet = new ProxyServlet(MapHelper.toMap(ProxyServlet.P_TARGET_URI, targetUrl),
                    MapHelper.toMap("zuultoken", EncryptDecryptUtil.deflate2Base62Encrypt("zuul@admin.com"), "x-forwarded-for", HttpHelper.getRemoteIpAddr(request), "proxy-enable", "1"));
            proxyServlet.service(wrapper, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getZuulHeader(String user) {
        return JWTTokenHelper.encodeToken(user);
    }
}
