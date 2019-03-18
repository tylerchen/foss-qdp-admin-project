/*******************************************************************************
 * Copyright (c) 2018-07-03 @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@gmail.com">Tyler Chen</a> - initial API and implementation.
 * Auto Generate By foreveross.com Quick Deliver Platform. 
 ******************************************************************************/
package com.foreveross.common.application;

import com.foreveross.common.ResultBean;
import com.foreveross.common.infra.vo.AuthAccountVO;
import com.foreveross.common.infra.vo.EditPasswordVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * AuthAccount
 *
 * @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a>
 * @version 1.0.0
 * auto generate by qdp v5.0.
 * @since 2018-07-03
 */
@FeignClient(value = "foss-qdp-auth", path = "/api/AuthAccount")
public interface AuthAccountClientApplication {

    /**
     * <pre>
     * get AuthAccountVO by id.
     * USAGE:
     *   GET /api/AuthAccount/get/{id}
     * SUCCESS:
     *   {header:{status:success},
     *   body:{AuthAccountVO}}
     * ERROR:
     *   {header: {status: error}, body:{Exception.getMessage()}}
     * </pre>
     *
     * @param id
     * @return ResultBean
     * @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a>
     * @since 2018-07-03
     * auto generate by qdp v5.0
     */
    @GetMapping(value = "/get/{id}")
    ResultBean getAuthAccountById(@PathVariable(value = "id") String id);

    /**
     * <pre>
     * page find AuthAccountVO.
     * USAGE:
     *   GET /api/AuthAccount/page/{loginEmail}/{status}/{currentPage}/{pageSize}/{asc}/{desc}
     * SUCCESS:
     *   {header:{status:success},
     *   body:{pageSize,totalCount,currentPage,offset,offsetPage,orderBy:[],rows:[{AuthAccountVO}]}}
     * ERROR:
     *   {header: {status: error}, body:{Exception.getMessage()}}
     * </pre>
     *
     * @param loginEmail
     * @param status
     * @param currentPage
     * @param pageSize
     * @param asc
     * @param desc
     * @return ResultBean
     * @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a>
     * @since 2018-07-03
     * auto generate by qdp v5.0
     */
    @GetMapping(value = "/page/{loginEmail}/{status}/{currentPage}/{pageSize}/{asc}/{desc}")
    ResultBean pageFindAuthAccount(
            @PathVariable(required = false, value = "loginEmail") String loginEmail,
            @PathVariable(required = false, value = "status") String status,
            @PathVariable(required = false, value = "currentPage") Integer currentPage,
            @PathVariable(required = false, value = "pageSize") Integer pageSize,
            @PathVariable(required = false, value = "asc") String asc,
            @PathVariable(required = false, value = "desc") String desc);

    /**
     * <pre>
     * page find AuthAccountVO.
     * USAGE:
     *   GET /api/AuthAccount/pageMap/{loginEmail}/{status}/{currentPage}/{pageSize}/{asc}/{desc}
     * SUCCESS:
     *   {header:{status:success},
     *   body:{pageSize,totalCount,currentPage,offset,offsetPage,orderBy:[],rows:[{AuthAccountVO}]}}
     * ERROR:
     *   {header: {status: error}, body:{Exception.getMessage()}}
     * </pre>
     *
     * @param loginEmail
     * @param status
     * @param currentPage
     * @param pageSize
     * @param asc
     * @param desc
     * @return ResultBean
     * @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a>
     * @since 2018-07-03
     * auto generate by qdp v5.0
     */
    @GetMapping(value = "/pageMap/{loginEmail}/{status}/{currentPage}/{pageSize}/{asc}/{desc}")
    ResultBean pageFindAuthAccountMap(
            @PathVariable(required = false, value = "loginEmail") String loginEmail,
            @PathVariable(required = false, value = "status") String status,
            @PathVariable(required = false, value = "currentPage") Integer currentPage,
            @PathVariable(required = false, value = "pageSize") Integer pageSize,
            @PathVariable(required = false, value = "asc") String asc,
            @PathVariable(required = false, value = "desc") String desc);

    /**
     * <pre>
     * add AuthAccount.
     * USAGE:
     *   POST /api/AuthAccount/
     *   {AuthAccountVO}
     * SUCCESS:
     *   {header:{status:success},
     *   body:{AuthAccountVO}}
     * ERROR:
     *   {header: {status: error}, body:{Exception.getMessage()}}
     * </pre>
     *
     * @param vo
     * @return AuthAccountVO
     * @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a>
     * @since 2018-07-03
     * auto generate by qdp v5.0.
     */
    @PostMapping(value = "/")
    ResultBean addAuthAccount(@RequestBody AuthAccountVO vo);

    /**
     * <pre>
     * update AuthAccount.
     * USAGE:
     *   POST /api/AuthAccount/
     *   {AuthAccountVO}
     * SUCCESS:
     *   {header:{status:success},
     *   body:{AuthAccountVO}}
     * ERROR:
     *   {header: {status: error}, body:{Exception.getMessage()}}
     * </pre>
     *
     * @param vo
     * @return AuthAccountVO
     * @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a>
     * @since 2018-07-03
     * auto generate by qdp v5.0.
     */
    @PutMapping(value = "/")
    ResultBean updateAuthAccount(@RequestBody AuthAccountVO vo);

    /**
     * <pre>
     * remove AuthAccount multi ids join by comma ','.
     * USAGE:
     *   DELETE /api/AuthAccount/{id}
     * SUCCESS:
     *   {header:{status:success},
     *   body:{}}
     * ERROR:
     *   {header: {status: error}, body:{Exception.getMessage()}}
     * </pre>
     *
     * @param id
     * @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a>
     * @since 2018-07-03
     * auto generate by qdp v5.0.
     */
    @DeleteMapping(value = "/{id}")
    ResultBean removeAuthAccountById(@PathVariable(value = "id") String id);

    /**
     * <pre>
     * get AuthAccount by unique name
     * USAGE:
     *   GET /api/AuthAccount/get/loginEmail/{loginEmail}
     * SUCCESS:
     *   {header:{status:success},
     *   body:{AuthAccountVO}}
     * ERROR:
     *   {header: {status: error}, body:{Exception.getMessage()}}
     * </pre>
     *
     * @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a>
     * @since 2018-07-03
     * auto generate by qdp v5.0.
     */
    @GetMapping(value = "/get/loginEmail/{loginEmail}")
    ResultBean getByLoginEmail(@PathVariable(value = "loginEmail") String loginEmail);

    /**
     * <pre>
     * page find assign AuthRoleVO.
     * USAGE:
     *   GET /api/AuthAccount/pageAssignAuthRole/{loginEmail}/{status}/{currentPage}/{pageSize}/{asc}/{desc}
     * SUCCESS:
     *   {header:{status:success},
     *   body:{pageSize,totalCount,currentPage,offset,offsetPage,orderBy:[],rows:[{AuthRoleVO}]}}
     * ERROR:
     *   {header: {status: error}, body:{Exception.getMessage()}}
     * </pre>
     *
     * @param loginEmail
     * @param status
     * @param currentPage
     * @param pageSize
     * @param asc
     * @param desc
     * @return ResultBean
     * @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a>
     * @since 2018-07-03
     * auto generate by qdp v5.0
     */
    @GetMapping(value = "/pageAssignAuthRole/{loginEmail}/{status}/{currentPage}/{pageSize}/{asc}/{desc}")
    ResultBean pageFindAssignAuthRole(
            @PathVariable(required = false, value = "loginEmail") String loginEmail,
            @PathVariable(required = false, value = "status") String status,
            @PathVariable(required = false, value = "currentPage") Integer currentPage,
            @PathVariable(required = false, value = "pageSize") Integer pageSize,
            @PathVariable(required = false, value = "asc") String asc,
            @PathVariable(required = false, value = "desc") String desc);

    /**
     * <pre>
     * assign AuthRole by id(s).
     * USAGE:
     *   POST /api/AuthAccount/assignAuthRole
     *   {AuthAccountVO}
     * SUCCESS:
     *   {header:{status:success},
     *   body:{}
     * ERROR:
     *   {header: {status: error}, body:{Exception.getMessage()}}
     * </pre>
     *
     * @param vo
     * @return ResultBean
     * @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a>
     * @since 2018-07-03
     * auto generate by qdp v5.0.
     */
    @PostMapping(value = "/assignAuthRole")
    ResultBean assignAuthRole(AuthAccountVO vo);

    /**
     * <pre>
     * editPassword.
     * USAGE:
     *   POST /api/AuthAccount/editPassword
     *   {EditPasswordVO}
     * SUCCESS:
     *   {header:{status:success},
     *   body:{}
     * ERROR:
     *   {header: {status: error}, body:{Exception.getMessage()}}
     * </pre>
     *
     * @param vo
     * @return ResultBean
     * @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a>
     * @since 2018-07-03
     * auto generate by qdp v5.0.
     */
    @PostMapping(value = "/editPassword")
    ResultBean editPassword(EditPasswordVO vo);
}
