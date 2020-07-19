package com.zyjblogs.mmall.controller.portal;

import com.github.pagehelper.PageInfo;
import com.zyjblogs.mmall.common.ServerResponse;
import com.zyjblogs.mmall.service.IProductService;
import com.zyjblogs.mmall.vo.ProductDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product/")
public class ProductController {
    //alt+enter @Resource
    @Autowired
    public IProductService iProductService;

    //where 过滤 / order by 排序/ limit 分页
    @RequestMapping("list.do")
    public ServerResponse<PageInfo> list(@RequestParam(value = "categoryId",required = false) Integer categoryId,
                                         @RequestParam(value = "keyword",required = false) String keyword,
                                         @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                         @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
                                         @RequestParam(value = "orderBy",defaultValue = "") String orderBy){
        System.out.println("list:"+keyword);
        return iProductService.getProductByKeywordCategory(keyword,categoryId,pageNum,pageSize,orderBy);
    }

    @RequestMapping("detail.do")
    public ServerResponse<ProductDetailVo> detail(Integer productId){
        return iProductService.getProductDetail(productId);
    }
}
