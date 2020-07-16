package com.zyjblogs.mmall.controller.portal;

import com.github.pagehelper.PageInfo;
import com.zyjblogs.mmall.common.Const;
import com.zyjblogs.mmall.common.ResponseCode;
import com.zyjblogs.mmall.common.ServerResponse;
import com.zyjblogs.mmall.pojo.User;
import com.zyjblogs.mmall.service.IOrderService;
import com.zyjblogs.mmall.vo.OrderProductVo;
import com.zyjblogs.mmall.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/order/")
public class OrderController {

    @Resource
    public IOrderService iOrderService;

    @RequestMapping("get_order_cart_product.do")
    public ServerResponse<OrderProductVo> list(HttpSession session){
        User user=(User) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }else
        {
            return iOrderService.getOrderCartProduct(user.getId());
        }
    }

    @RequestMapping("create.do")
    public ServerResponse<OrderProductVo> add(HttpSession session, Integer shipping){
        User user=(User) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }else
        {
            return iOrderService.createOrder(user.getId(),shipping);
        }
    }

    @RequestMapping("list.do")
    public ServerResponse<PageInfo> list(HttpSession session,
                                         @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        User user=(User) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }else
        {
            return iOrderService.getOrderList(user.getId(),pageNum,pageSize);
        }
    }

    @RequestMapping("detail.do")
    public ServerResponse<OrderVo> detail(HttpSession session, Long orderNo){
        User user=(User) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }else
        {
            return iOrderService.manageDetail(orderNo);
        }
    }
    @RequestMapping("cancel.do")
    public ServerResponse<String> cancel(HttpSession session, Long orderNo){
        User user=(User) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }else
        {
            return iOrderService.cancel(user.getId(),orderNo);
        }
    }




}
