package cn.zyjblogs.mmallbe.controller;

import cn.zyjblogs.mmallbe.entity.User;
import cn.zyjblogs.mmallbe.mapper.UserMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping(value = "/user")
public class UserController{
    @Resource
    UserMapper userMapper;

    @RequestMapping(value = "/login.do",method = {RequestMethod.GET})
   @ResponseBody//把返回变成json格式
    public JsonRes login(User user){
        //输入
        System.out.println("come in..."+ user.getUsername()+","+user.getPassword());
        //处理
        user=userMapper.login(user);
        if (user!=null){
            System.out.println("id:"+ user.getId());
            //输出
            JsonRes res = new JsonRes(1,"恭喜你，登录成功",user);
            return res;
        }
        JsonRes res = new JsonRes(0,"用户密码错误",user);
        return res;
    }
    @RequestMapping(value = "/addUser",method = {RequestMethod.GET})
    @ResponseBody//把返回变成json格式
    public JsonRes addUser(User user){
        User user1=userMapper.findUserById(user);
        if (user1==null){
            if (userMapper.addUser(user)>0){
                System.err.println("添加成功");
                JsonRes res = new JsonRes(1,"添加成功",null);
                return res;
            }else {
                JsonRes res = new JsonRes(0,"添加失败! (ಥ_ಥ)服务器端发生异常!",null);
                return res;
            }
        }else {
            System.err.println("用户已经存在");
            JsonRes res = new JsonRes(0,"添加失败，用户已经存在",null);
            return res;
        }
    }
}
