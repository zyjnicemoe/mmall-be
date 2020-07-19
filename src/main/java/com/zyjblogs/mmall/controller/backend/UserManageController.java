package com.zyjblogs.mmall.controller.backend;

import com.zyjblogs.mmall.common.Const;
import com.zyjblogs.mmall.common.ResponseCode;
import com.zyjblogs.mmall.common.ServerResponse;
import com.zyjblogs.mmall.pojo.User;
import com.zyjblogs.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/manage/user/",method = {RequestMethod.GET,RequestMethod.POST})
public class UserManageController {

    @Autowired
    private IUserService userService;
    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    public ServerResponse<User> login(String username, String password, HttpSession session){
        ServerResponse<User> response = userService.login(username,password);
        if(response.isSuccess()){
            User  user=response.getData();
            if (user.getRole()==Const.Role.ROLE_ADMIN) {
                session.setAttribute(Const.CURRENT_Admin, user);
            }else {
                return  ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),"请以管理员身份登录");
            }
        }
        return response;
    }

    @RequestMapping(value = "logout.do",method = RequestMethod.POST)
    public ServerResponse<String> logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_Admin);
        return ServerResponse.createBySuccess();
    }


}
