package com.zyjblogs.mmall.controller.portal;

import com.github.pagehelper.PageInfo;
import com.zyjblogs.mmall.common.Const;
import com.zyjblogs.mmall.common.ResponseCode;
import com.zyjblogs.mmall.common.ServerResponse;
import com.zyjblogs.mmall.pojo.Shipping;
import com.zyjblogs.mmall.pojo.User;
import com.zyjblogs.mmall.service.IShippingService;
import com.zyjblogs.mmall.vo.CartVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/shipping/")
public class ShippingController {
    @Resource
    public IShippingService iShippingService;
    @RequestMapping("list.do")
    public ServerResponse<PageInfo> list(HttpSession session,
                                         @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        User user=(User) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }else
        {
            return iShippingService.list(user.getId(),pageNum,pageSize);
        }
    }

    @RequestMapping("add.do")
    public ServerResponse<CartVo> add(HttpSession session, Shipping shipping){
        User user=(User) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }else
        {
            return iShippingService.add(user.getId(),shipping);
        }
    }

    @RequestMapping("update.do")
    public ServerResponse<CartVo> update(HttpSession session,Shipping shipping){
        User user=(User) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }else
        {
            return iShippingService.update(user.getId(),shipping);
        }
    }

    @RequestMapping("del.do")
    public ServerResponse<String> delete_product(HttpSession session,Integer shippingid){
        User user=(User) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }else
        {
            return iShippingService.del(user.getId(),shippingid);
        }
    }

    @RequestMapping("select.do")
    public ServerResponse<Shipping> select(HttpSession session,Integer shippingid){
        User user=(User) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }else
        {
            return iShippingService.select(user.getId(),shippingid);
        }
    }


}
