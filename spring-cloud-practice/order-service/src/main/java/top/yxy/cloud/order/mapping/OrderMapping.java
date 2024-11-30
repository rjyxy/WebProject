package top.yxy.cloud.order.mapping;

import org.mapstruct.Mapper;
import top.yxy.cloud.api.pojo.dto.OrderDTO;
import top.yxy.cloud.common.constants.DateConsts;
import top.yxy.cloud.common.handler.BaseMapping;
import top.yxy.cloud.order.entity.OrderDO;

import java.time.format.DateTimeFormatter;

/**
 * @author mqxu
 * @date 2024/9/20
 * @description OrderMapping
 **/
@Mapper(componentModel = "spring")
public interface OrderMapping extends BaseMapping<OrderDO, OrderDTO> {

    @Override
    default void afterConvertTo(OrderDO src, OrderDTO dest) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DateConsts.YYYY_MM_DD_HH_MM_SS);
        dest.setCreateTime(src.getCreateTime().format(dateTimeFormatter));
        dest.setUpdateTime(src.getUpdateTime().format(dateTimeFormatter));
    }

}