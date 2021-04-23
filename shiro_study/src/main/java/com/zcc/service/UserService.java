package com.zcc.service;

import com.zcc.model.DataSource;
import com.zcc.model.UserBean;
import org.springframework.stereotype.Component;

import java.util.Map;


/**
 * @author zcc
 * @ClassName UserService
 * @description
 * @date 2021/4/23 13:35
 * @Version 1.0
 */
@Component
public class UserService {

    public UserBean getUser(String username){
        //没有此用户直接返回null
        if(! DataSource.getData().containsKey(username))
            return null;
        UserBean user = new UserBean();
        Map<String, String> detail = DataSource.getData().get(username);

        user.setUsername(username);
        user.setPassword(detail.get("password"));
        user.setRole(detail.get("role"));
        user.setPermission(detail.get("permission"));
        return user;
    }

}
