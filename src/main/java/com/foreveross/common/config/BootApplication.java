/*******************************************************************************
 * Copyright (c) Oct 23, 2017 @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@gmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.common.config;

import com.foreveross.common.shiro.JWTTokenHelper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.iff.infra.util.HttpHelper;
import org.iff.infra.util.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.boot.web.filter.OrderedCharacterEncodingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;

/**
 * @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a>
 * @since Oct 23, 2017
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class, FreeMarkerAutoConfiguration.class,
        TransactionAutoConfiguration.class, CacheAutoConfiguration.class})
//@ImportResource({ "classpath:META-INF/spring/root.xml" }) =>这个放到application.properties中配置了
@ComponentScan(basePackages = {"com.foreveross.qdp", "com.foreveross.common",
        "com.foreveross.extension"}, excludeFilters = {
        @Filter(type = FilterType.REGEX, pattern = {"com.foreveross.extension.activiti.*"})})
@EnableZuulProxy
@EnableDiscoveryClient
public class BootApplication extends WebMvcConfigurerAdapter {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(BootApplication.class, args);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        registry.addResourceHandler("/resource/**").addResourceLocations("/resource/");
    }

    /**
     * 要注入这个OrderedCharacterEncodingFilter才行呀，CharacterEncodingFilter没有Order属性就无法设置Bean的先后顺序
     *
     * @return
     * @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a>
     * @since Oct 27, 2017
     */
    @Bean
    @ConditionalOnMissingBean(CharacterEncodingFilter.class)
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter filter = new OrderedCharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceRequestEncoding(true);
        filter.setForceResponseEncoding(true);
        return filter;
    }

    /**
     * @return
     * @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a>
     * @date 2018-06-26
     * @since 2018-06-26
     */
    @Bean
    public FilterRegistrationBean shiroFilterRegistration() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        filterRegistration.setEnabled(true);
        filterRegistration.setOrder(0);
        filterRegistration.addUrlPatterns("/*");
        filterRegistration.setDispatcherTypes(DispatcherType.REQUEST);
        return filterRegistration;
    }
//    @Bean
//    public DelegatingFilterProxy shiroFilter() {
//        DelegatingFilterProxy filter = new DelegatingFilterProxy();
//        filter.setTargetFilterLifecycle(true);
//        filter.setTargetBeanName("shiroFilter");
//        return filter;
//    }
//
//    @Bean
//    public DispatcherServlet dispatcherServlet() {
//        DispatcherServlet servlet = new DispatcherServlet();
//        servlet.setDispatchOptionsRequest(true);
//        return servlet;
//    }
//
//    @Bean
//    public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet) {
//        ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet, "/*");
//        registration.setLoadOnStartup(1);
//        return registration;
//    }

    @Bean
    public PreRequestFilter simpleFilter() {
        return new PreRequestFilter();
    }

    public class PreRequestFilter extends ZuulFilter {

        private String ipsMd5 = "";

        public String filterType() {//"pre" for pre-routing filtering, "route" for routing to an origin, "post" for post-routing filters, "error" for error handling.
            return FilterConstants.PRE_TYPE;
        }

        public int filterOrder() {
            return 1;
        }

        public boolean shouldFilter() {
            return true;
        }

        public Object run() {
            RequestContext ctx = RequestContext.getCurrentContext();
            HttpServletRequest request = ctx.getRequest();
            Logger.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));
            String token = request.getHeader("token");
            if (token == null) {
                token = request.getParameter("token");
            }
            ctx.addZuulRequestHeader("zuul", getZuulHeader("admin@admin.com"));
            ctx.addZuulRequestHeader("x-forwarded-for", HttpHelper.getRemoteIpAddr(request));
            ctx.addZuulRequestHeader("proxy-enable", "1");
            return null;
        }

        private String getZuulHeader(String user) {
            return JWTTokenHelper.encodeToken(user);
        }
    }
}
