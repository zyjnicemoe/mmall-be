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
    @Autowired
    public IProductService iProductService;

    /**
     * 商品列表
     * @param categoryId  产品id
     * @param keyword   关键字
     * @param pageNum  页数
     * @param pageSize  页号
     * @param orderBy   升序降序
     * @return  PageInfo
     * where 过滤 order by 排序/ limit 分页
     */
    @RequestMapping("list.do")
    public ServerResponse<PageInfo> list(@RequestParam(value = "categoryId",required = false)    Integer categoryId,
                                         @RequestParam(value = "keyword",required = false)    String keyword,
                                         @RequestParam(value = "pageNum"   ,defaultValue = "1")  Integer pageNum,
                                         @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
                                         @RequestParam(value = "orderBy",defaultValue = "")   String orderBy){

        System.out.println(keyword);
        return iProductService.getProductByKeywordCategory(keyword,categoryId,pageNum,pageSize,orderBy);
    }

    @RequestMapping("detail.do")
    public ServerResponse<ProductDetailVo> detail(Integer productId){
        System.out.println(productId);
        return iProductService.getProductDetail(productId);
    }

}
