package top.yxy.cloud.product.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.yxy.cloud.api.facade.ProductFacade;
import top.yxy.cloud.api.pojo.dto.ProductDTO;
import top.yxy.cloud.api.pojo.query.ProductStockDeductQuery;
import top.yxy.cloud.product.entity.ProductDO;
import top.yxy.cloud.product.mapping.ProductMapping;
import top.yxy.cloud.product.service.ProductService;

/**
 * @author mqxu
 * @date 2024/9/20
 * @description ProductController
 **/
@RequiredArgsConstructor
@RestController
@Slf4j
public class ProductController implements ProductFacade {

    private final ProductService productService;

    private final ProductMapping productMapping;

    private final HttpServletRequest request;

    private final Environment env;

    @Override
    public ProductDTO queryProductInfo(@PathVariable("id") Integer productId) {
        String uid = request.getHeader("uid");
        String username = request.getHeader("username");
        log.info("用户 [{}, {}] 正在查询产品信息：{}", uid, username, productId);
        ProductDO productDO = productService.getById(productId);
        // 负载均衡测试
        //productDO.setProductDesc(productDO.getProductDesc() + "@" + env.getProperty("server.port"));
        return productMapping.convertTo(productDO);
    }

    @Override
    public boolean deductStock(@RequestBody ProductStockDeductQuery productStockDeductQuery) {
        log.info("正在更新商品库存：{}", productStockDeductQuery);
        int successCount = productService.deduct(productStockDeductQuery);
        log.info("更新商品 ID[{}] 库存影响行数：{}", productStockDeductQuery.getProductId(), successCount);
        return successCount == 1;
    }

}