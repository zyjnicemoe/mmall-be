package com.zyjblogs.mmall.controller.backend;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.zyjblogs.mmall.common.Const;
import com.zyjblogs.mmall.common.ResponseCode;
import com.zyjblogs.mmall.common.ServerResponse;
import com.zyjblogs.mmall.pojo.Product;
import com.zyjblogs.mmall.pojo.User;
import com.zyjblogs.mmall.service.IFileService;
import com.zyjblogs.mmall.service.IProductService;
import com.zyjblogs.mmall.service.IUserService;
import com.zyjblogs.mmall.util.PropertiesUtil;
import com.zyjblogs.mmall.vo.ProductDetailVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/manage/product/")
public class ProductManageController {
    @Autowired
    public IProductService iProductService;
    @Autowired
    public IUserService iUserService;
    @Autowired
    public IFileService iFileService;

    @RequestMapping("list.do")
    public ServerResponse<PageInfo> list(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                         HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_Admin);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请先登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iProductService.getProductList(pageNum, pageSize);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @RequestMapping("search.do")
    public ServerResponse<PageInfo> search(@RequestParam(value = "productId", required = false) Integer productId,
                                           @RequestParam(value = "productName", required = false) String productName,
                                           @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                           HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_Admin);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请先登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iProductService.searchProduct(productName, productId, pageNum, pageSize);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }
    @RequestMapping("upload.do")
    public ServerResponse upload(MultipartFile upload_file, HttpSession session ) {
        User user = (User) session.getAttribute(Const.CURRENT_Admin);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请先登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {

            String path =session.getServletContext().getRealPath("upload");
            String targetfileName= iFileService.upload(upload_file,path);
            Map map = new HashMap();
            map.put("uri",targetfileName);
            map.put("url",path+"/"+targetfileName);
            return ServerResponse.createBySuccess(map);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }

    }
    @RequestMapping("detail.do")
    public ServerResponse<ProductDetailVo> detail(Integer productId,HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_Admin);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请先登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iProductService.getProductDetail(productId);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @RequestMapping("set_sale_status.do")
    public ServerResponse<String> setSaleStatus(Integer productId,Integer status,HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_Admin);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请先登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {

            return iProductService.setSaleStatus(productId,status);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @RequestMapping("save.do")
    public ServerResponse save(Product product,HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_Admin);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请先登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iProductService.saveOrUpdateProduct(product);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }


    @RequestMapping("richtext_img_upload.do")
    @ResponseBody
    public Map richtextImgUpload(HttpSession session, @RequestParam(value = "upload_file",required = false) MultipartFile upload_file, HttpServletRequest request, HttpServletResponse response) {
        Map resultMap = Maps.newHashMap();
        User user = (User) session.getAttribute(Const.CURRENT_Admin);
        if (user == null) {
            resultMap.put("success", false);
            resultMap.put("msg", "请登录管理员");
            return resultMap;
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = iFileService.upload(upload_file, path);
            if (StringUtils.isBlank(targetFileName)) {
                resultMap.put("success", false);
                resultMap.put("msg", "上传失败");
                return resultMap;
            }
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;
//            String url =  "http://qdsytx1ka.bkt.clouddn.com/"+targetFileName;
            resultMap.put("success", true);
            resultMap.put("msg", "上传成功");
            resultMap.put("file_path", url);
            response.addHeader("Access-Control-Allow-Headers", "X-File-Name");
            return resultMap;
        } else {
            resultMap.put("success", false);
            resultMap.put("msg", "无权限操作");
            return resultMap;
        }
    }


}
