package com.zyjblogs.mmall.controller.portal;

import com.zyjblogs.mmall.common.Const;
import com.zyjblogs.mmall.common.ResponseCode;
import com.zyjblogs.mmall.common.ServerResponse;
import com.zyjblogs.mmall.pojo.User;
import com.zyjblogs.mmall.service.ICartService;
import com.zyjblogs.mmall.vo.CartVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/cart/")
public class CartController {
    @Resource
    public ICartService iCartService;
    @RequestMapping("list.do")
    public ServerResponse<CartVo> list(HttpSession session){
        User user=(User) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }else
        {
            return iCartService.list(user.getId());
        }
    }

    @RequestMapping("add.do")
    public ServerResponse<CartVo> add(HttpSession session,Integer productId,Integer count){
        User user=(User) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }else
        {
            return iCartService.add(user.getId(),productId,count);
        }
    }

    @RequestMapping("update.do")
    public ServerResponse<CartVo> update(HttpSession session,Integer productId,Integer count){
        User user=(User) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }else
        {
            return iCartService.update(user.getId(),productId,count);
        }
    }

    @RequestMapping("delete_product.do")
    public ServerResponse<CartVo> delete_product(HttpSession session,String productIds){
        User user=(User) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }else
        {
            return iCartService.deleteProduct(user.getId(),productIds);
        }
    }

    @RequestMapping("select.do")
    public ServerResponse<CartVo> select(HttpSession session,Integer productId){
        User user=(User) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }else
        {
            return iCartService.selectOrUnSelect(user.getId(),productId,1);
        }
    }

    @RequestMapping("un_select.do")
    public ServerResponse<CartVo> un_select(HttpSession session,Integer productId){
        User user=(User) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }else
        {
            return iCartService.selectOrUnSelect(user.getId(),productId,0);
        }
    }

    @RequestMapping("select_all.do")
    public ServerResponse<CartVo> selectAll(HttpSession session){
        User user=(User) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }else
        {
            return iCartService.selectOrUnSelect(user.getId(),null,1);
        }

    } @RequestMapping("un_select_all.do")
    public ServerResponse<CartVo> unSelectAll(HttpSession session){
        User user=(User) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }else{
            return iCartService.selectOrUnSelect(user.getId(),null,0);
        }
    }

    @RequestMapping("get_cart_product_count.do")
    public ServerResponse<Integer> getCartProduCtcount(HttpSession session){
        User user=(User) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.createByError();
        }else
        {
            return iCartService.getCartProductCount(user.getId());
        }
    }


}
