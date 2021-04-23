package com.zcc.controller;

import com.zcc.exception.UnauthorizedException;
import com.zcc.model.ResponseBean;
import com.zcc.model.UserBean;
import com.zcc.service.UserService;
import com.zcc.utils.JWTUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author zcc
 * @ClassName WebController
 * @description
 * @date 2021/4/23 17:36
 * @Version 1.0
 */
@RestController
public class WebController {
    private static final Logger LOGGER = LogManager.getLogger(WebController.class);

    private UserService userService;
    // ps：好久没看到这种形式注入service了，因为在UserService上面我们只加了个@Component注解，而不是@Service
    @Autowired
    public void setService(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseBean login(@RequestParam("username") String username,
                              @RequestParam("password") String password){
        UserBean userBean = userService.getUser(username);
        if (userBean.getPassword().equals(password)) {
            return new ResponseBean(200, "Login success", JWTUtil.sign(username, password));
        } else {
            throw new UnauthorizedException();
        }
    }
    @GetMapping("/article")
    public ResponseBean article(){
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return new ResponseBean(200, "you are already logged in", null);
        }else {
            return new ResponseBean(200, "you are guest", null);
        }
    }

    @GetMapping("/require_auth")
    @RequiresAuthentication
    public ResponseBean requireAuth(){
        return new ResponseBean(200,"you are authenticated", null);
    }

    @GetMapping("/require_role")
    @RequiresRoles("admin")
    public ResponseBean requireRole(){
        return new ResponseBean(200, "you are visiting require_role", null);
    }

    @GetMapping("/require_permission")
    @RequiresPermissions(logical = Logical.AND, value = {"view", "edit"})
    public ResponseBean requirePermission(){
        return new ResponseBean(200, "you are visiting permission require edit,view", null);
    }

    @RequestMapping(path = "401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseBean unauthorized(){
        return new ResponseBean(401, "Unauthorized", null);
    }
}
