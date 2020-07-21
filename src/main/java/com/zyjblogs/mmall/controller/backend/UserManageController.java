package com.zyjblogs.mmall.controller.backend;

import com.zyjblogs.mmall.common.Const;
import com.zyjblogs.mmall.common.ResponseCode;
import com.zyjblogs.mmall.common.ServerResponse;
import com.zyjblogs.mmall.pojo.User;
import com.zyjblogs.mmall.service.IUserService;
import com.zyjblogs.mmall.util.CreateVerifiCodeImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/manage/user/",method = {RequestMethod.GET,RequestMethod.POST})
public class UserManageController {


    @Autowired
    private IUserService userService;

    @GetMapping("getVerifiCodeImage.do")
    public void getVerifiCodeImage(HttpServletRequest request, HttpServletResponse response) {
        // 验证码图片
        BufferedImage verifiCodeImage = CreateVerifiCodeImage.getVerifiCodeImage();
        // 验证码
        String verifiCode = String.valueOf(CreateVerifiCodeImage.getVerifiCode());
        // 将验证码图片输出到登录页面
        try {
            ImageIO.write(verifiCodeImage, "JPEG", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 存储验证码Session
        request.getSession().setAttribute("AdminverifiCode", verifiCode);
//        System.out.println(verifiCode);
    }

    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    public ServerResponse<User> login(String username, String password,String code, HttpSession session,HttpServletRequest request){
        System.out.println(code);
        String vcode = (String) request.getSession().getAttribute("AdminverifiCode");
        if (vcode.equalsIgnoreCase(code)) {
            ServerResponse<User> response = userService.login(username, password);
            if (response.isSuccess()) {
                User user = response.getData();
                if (user.getRole() == Const.Role.ROLE_ADMIN) {
                    session.setAttribute(Const.CURRENT_Admin, user);
                } else {
                    return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(), "请以管理员身份登录");
                }
            }
            return response;
        }else {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(), "验证码错误");
        }
    }

    @RequestMapping(value = "logout.do",method = RequestMethod.POST)
    public ServerResponse<String> logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_Admin);
        return ServerResponse.createBySuccess();
    }


}
