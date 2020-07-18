package com.zyjblogs.mmall.controller.backend;

import com.zyjblogs.mmall.common.Const;
import com.zyjblogs.mmall.common.ResponseCode;
import com.zyjblogs.mmall.common.ServerResponse;
import com.zyjblogs.mmall.pojo.Product;
import com.zyjblogs.mmall.pojo.User;
import com.zyjblogs.mmall.service.IOrderService;
import com.zyjblogs.mmall.service.IProductService;
import com.zyjblogs.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/statistic/")
public class StatisticManageController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    public IProductService iProductService;
    @Autowired
    public IOrderService iOrderService;
    @RequestMapping("base_count.do")
    public ServerResponse baseCount(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "请用管理员登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            int userCount=iUserService.userCount();
            int productCount=iProductService.productCount();
            int orderCount=iOrderService.orderCount();
            Map map=new HashMap();
            map.put("userCount",userCount);
            map.put("productCount",productCount);
            map.put("orderCount",orderCount);
            return ServerResponse.createBySuccess(map);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }
}
