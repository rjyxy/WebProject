package top.yxy.cloud.order.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.yxy.cloud.api.facade.OrderFacade;
import top.yxy.cloud.api.pojo.dto.OrderDTO;
import top.yxy.cloud.api.pojo.query.OrderCreateQuery;
import top.yxy.cloud.order.entity.OrderDO;
import top.yxy.cloud.order.mapping.OrderMapping;
import top.yxy.cloud.order.service.OrderService;

import java.util.List;

/**
 * @author mqxu
 * @date 2024/9/11
 * @description OrderController
 **/
@RequiredArgsConstructor
@RestController
@Slf4j
public class OrderController implements OrderFacade {
    private final OrderService orderService;

    private final OrderMapping orderMapping;

    private final HttpServletRequest request;

    @SentinelResource
    @Override
    public OrderDTO order(@RequestBody OrderCreateQuery orderCreateQuery) {
        String uid = request.getHeader("uid");
        String username = request.getHeader("username");
        log.info("用户 [{}, {}] 正在下单：{}", uid, username, orderCreateQuery);
        return orderService.createOrder(orderCreateQuery);
    }

    @SentinelResource
    @Override
    public List<OrderDTO> orders(@PathVariable("uid") Integer uid) {
        LambdaQueryWrapper<OrderDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderDO::getUid, uid);
        IPage<OrderDO> page = orderService.page(new Page<>(2, 3), queryWrapper);
        return orderMapping.convertTo(page.getRecords());
    }

}