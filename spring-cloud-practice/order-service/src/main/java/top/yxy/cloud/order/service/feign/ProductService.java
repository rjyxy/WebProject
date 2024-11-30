package top.yxy.cloud.order.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import top.yxy.cloud.api.facade.ProductFacade;
import top.yxy.cloud.common.constants.ServiceConsts;

/**
 * @author mqxu
 * @date 2024/9/20
 * @description ProductService
 **/
@FeignClient(value = ServiceConsts.PRODUCT_SERVICE, fallbackFactory = ProductServiceFallBackFactory.class)
public interface ProductService extends ProductFacade {

}