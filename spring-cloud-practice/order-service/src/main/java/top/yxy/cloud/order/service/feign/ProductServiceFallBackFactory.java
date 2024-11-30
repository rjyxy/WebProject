package top.yxy.cloud.order.service.feign;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import top.yxy.cloud.api.pojo.dto.ProductDTO;
import top.yxy.cloud.api.pojo.query.ProductStockDeductQuery;

/**
 * @author mqxu
 * @date 2024/9/20
 * @description 容错类
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class ProductServiceFallBackFactory implements FallbackFactory<ProductService> {

    @Override
    public ProductService create(Throwable throwable) {
        log.error("调用商品服务异常：", throwable);
        return new ProductService() {
            @Override
            public ProductDTO queryProductInfo(Integer productId) {
                ProductDTO productDTO = new ProductDTO();
                productDTO.setId(-1);
                return productDTO;
            }

            @Override
            public boolean deductStock(ProductStockDeductQuery productStockDeductQuery) {
                return false;
            }
        };
    }
}