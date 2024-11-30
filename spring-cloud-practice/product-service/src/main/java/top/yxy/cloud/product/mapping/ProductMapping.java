package top.yxy.cloud.product.mapping;

import org.mapstruct.Mapper;
import top.yxy.cloud.api.pojo.dto.ProductDTO;
import top.yxy.cloud.common.handler.BaseMapping;
import top.yxy.cloud.product.entity.ProductDO;

/**
 * @author mqxu
 * @date 2024/9/20
 * @description ProductMapping
 **/
@Mapper(componentModel = "spring")
public interface ProductMapping extends BaseMapping<ProductDO, ProductDTO> {

}