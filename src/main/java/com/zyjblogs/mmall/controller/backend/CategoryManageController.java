package com.zyjblogs.mmall.controller.backend;

import com.github.pagehelper.PageInfo;
import com.zyjblogs.mmall.common.Const;
import com.zyjblogs.mmall.common.ResponseCode;
import com.zyjblogs.mmall.common.ServerResponse;
import com.zyjblogs.mmall.pojo.Category;
import com.zyjblogs.mmall.pojo.User;
import com.zyjblogs.mmall.service.ICategoryService;
import com.zyjblogs.mmall.service.IProductService;
import com.zyjblogs.mmall.service.IUserService;
import com.zyjblogs.mmall.vo.ProductDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/manage/category/")
public class CategoryManageController {
    @Autowired
    public ICategoryService iCategoryService;
    @Autowired
    public IUserService iUserService;

    @RequestMapping("get_category.do")
    public ServerResponse<List<Category>> getCategory(@RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId,
                                                      HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_Admin);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请先登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iCategoryService.getChildrenParallelCategory(categoryId);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }
    @RequestMapping("add_category.do")
    public ServerResponse addCategory(@RequestParam(value = "parentId", defaultValue = "0") Integer parentId,
                                                      @RequestParam(value = "categoryName", required = false) String categoryName,
                                                      HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_Admin);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请先登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iCategoryService.addCategory(categoryName,parentId);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @RequestMapping("set_category_name.do")
    public ServerResponse setCategoryName(@RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId,
                                                      @RequestParam(value = "categoryName", required = false) String categoryName,
                                                      HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_Admin);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请先登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iCategoryService.updateCategoryName(categoryId,categoryName);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @RequestMapping("get_deep_category.do")
    public ServerResponse<List<Category>> getDeepCategory(@RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId,
                                                          HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_Admin);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请先登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iCategoryService.getChildrenParallelCategory(categoryId);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }
}
