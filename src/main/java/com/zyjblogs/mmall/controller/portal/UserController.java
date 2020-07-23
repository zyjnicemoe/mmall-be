package com.zyjblogs.mmall.controller.portal;

import com.zyjblogs.mmall.common.Const;
import com.zyjblogs.mmall.common.ResponseCode;
import com.zyjblogs.mmall.common.ServerResponse;
import com.zyjblogs.mmall.pojo.User;
import com.zyjblogs.mmall.service.IUserService;
import com.zyjblogs.mmall.util.CreateVerifiCodeImage;
import org.apache.ibatis.javassist.compiler.JvstCodeGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
@RequestMapping(value="/user/",method = {RequestMethod.GET,RequestMethod.POST})
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     *  获取并显示验证码图片
     */
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
        request.getSession().setAttribute("verifiCode", verifiCode);
//        System.out.println(verifiCode);
    }

    @RequestMapping(value = "login.do")
    public ServerResponse<User> login(String username, String password,String code, HttpSession session,HttpServletRequest request){
        // 校验验证码信息
        String vcode = (String) request.getSession().getAttribute("verifiCode");
        if (vcode.equalsIgnoreCase(code)){
            ServerResponse<User> response = userService.login(username,password);
            if(response.isSuccess()){
                session.setAttribute(Const.CURRENT_USER,response.getData());
            }
            return response;
        }
        return ServerResponse.createByErrorMessage("验证码错误");
    }

    @RequestMapping(value = "logout.do",method = RequestMethod.POST)
    public ServerResponse<String> logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }

    @RequestMapping(value = "register.do",method = RequestMethod.POST)
    public ServerResponse<String> register(User user, Integer sms, HttpSession session){

        Integer smsCode= (Integer) session.getAttribute("smsCode");
        System.out.println("smsCode"+smsCode);
        System.out.println("sms"+sms);
        if (smsCode.equals(sms)){
            return userService.register(user);
        }
        return ServerResponse.createByErrorMessage("手机验证码有误");
    }


    @RequestMapping(value = "check_valid.do",method = RequestMethod.POST)
    public ServerResponse<String> checkValid(String str,String type){
        return userService.checkValid(str,type);
    }


    @RequestMapping(value = "get_user_info.do",method = RequestMethod.POST)
    public ServerResponse<User> getUserInfo(HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user != null){
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
    }


    @RequestMapping(value = "forget_get_question.do",method = RequestMethod.POST)
    public ServerResponse<String> forgetGetQuestion(String username){
        return userService.selectQuestion(username);
    }


    @RequestMapping(value = "forget_check_answer.do",method = RequestMethod.POST)
    public ServerResponse<String> forgetCheckAnswer(String username,String question,String answer){
        return userService.checkAnswer(username,question,answer);
    }


    @RequestMapping(value = "forget_reset_password.do",method = RequestMethod.POST)
    public ServerResponse<String> forgetRestPassword(String username,String passwordNew,String forgetToken){
        return userService.forgetResetPassword(username,passwordNew,forgetToken);
    }



    @RequestMapping(value = "reset_password.do",method = RequestMethod.POST)
    public ServerResponse<String> resetPassword(HttpSession session, String passwordOld, String passwordNew){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return userService.resetPassword(passwordOld,passwordNew,user);
    }

    @RequestMapping(value = "update_information.do",method = RequestMethod.POST)
    public ServerResponse<User> update_information(HttpSession session, User user){
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if(currentUser == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        ServerResponse<User> response = userService.updateInformation(user);
        if(response.isSuccess()){
            response.getData().setUsername(currentUser.getUsername());
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }

    @RequestMapping(value = "get_information.do",method = RequestMethod.POST)
    public ServerResponse<User> get_information(HttpSession session){
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if(currentUser == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"未登录,需要强制登录status=10");
        }
        return userService.getInformation(currentUser.getId());
    }


}
