package top.yxy.cloud.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import top.yxy.cloud.api.pojo.query.ProductStockDeductQuery;
import top.yxy.cloud.product.entity.ProductDO;
import top.yxy.cloud.product.mapper.ProductMapper;
import top.yxy.cloud.product.service.ProductService;

/**
 * @author mqxu
 * @date 2024/9/20
 * @description ProductServiceImpl
 **/
@RequiredArgsConstructor
@Service
@Slf4j
public class ProductServiceImpl extends ServiceImpl<ProductMapper, ProductDO> implements ProductService {

    private final ProductMapper productMapper;

    @Override
    public int deduct(@RequestBody ProductStockDeductQuery productStockDeductQuery) {
        return productMapper.deduct(productStockDeductQuery.getProductId(),
                productStockDeductQuery.getCount());
    }

}