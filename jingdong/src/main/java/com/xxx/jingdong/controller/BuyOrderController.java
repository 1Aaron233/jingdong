package com.xxx.jingdong.controller;

import com.xxx.jingdong.bo.CartDTO;
import com.xxx.jingdong.bo.OrderDTO;
import com.xxx.jingdong.converter.OrderFormToOrderDTOConverter;
import com.xxx.jingdong.domain.Result;
import com.xxx.jingdong.enums.ResultEnum;
import com.xxx.jingdong.exception.SellException;
import com.xxx.jingdong.form.OrderForm;
import com.xxx.jingdong.service.OrderService;
import com.xxx.jingdong.service.ProductService;
import com.xxx.jingdong.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author；Aaron
 */
@RestController
@RequestMapping("/api/v1")
@Slf4j
public class BuyOrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    /**
     * 创建订单
     * @param orderForm
     * @param bindingResult
     * @return Result<OrderDTO>
     */
    @PostMapping("/create")
    public Result<OrderDTO> create(@Valid OrderForm orderForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            log.info("[创建订单]参数不正确，orderForm={}",orderForm);
            throw new SellException(ResultEnum.ORDER_PARAMS_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        };
        OrderDTO orderDTO = OrderFormToOrderDTOConverter.converter(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("[创建订单]购物车不能为空");
            throw new SellException(ResultEnum.ORDER_CART_EMPTY);
        }
        OrderDTO result=orderService.create(orderDTO);
        //减库存
        List<CartDTO> cartDTOList =orderDTO.getOrderDetailList().stream().map(e->new
                CartDTO(e.getProductId(),e.getQuantity())).collect(Collectors.toList());
        productService.decreaseStock(cartDTOList);
        return ResultUtil.success(result);
    }

    //以下为卖家端

    /**
     * 订单列表
     * @param openid
     * @param page
     * @param size
     * @return Result<List<OrderDTO>>
     */
    @GetMapping("/orders")
    public Result<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                       @RequestParam(value = "page",defaultValue = "0") Integer page,
                                       @RequestParam(value = "size",defaultValue = "10") Integer size){
        if (StringUtils.isEmpty(openid)){
            log.error("[查询订单列表]openid为空");
            throw new SellException(ResultEnum.ORDER_PARAMS_ERROR);
        }
        PageRequest request=PageRequest.of(page,size, Sort.by(Sort.Direction.DESC,"userOpenid"));
        Page<OrderDTO> orderDTOPage=orderService.findList(openid,request);
        return ResultUtil.success(orderDTOPage.getContent());
    }

    /**
     * 订单详情
     * @param orderNo
     * @return Result<OrderDTO>
     */
    @GetMapping("/detail")
    public Result<OrderDTO> detail(@RequestParam("orderNo") String orderNo){
        OrderDTO orderDTO=orderService.findOne(orderNo);
        return ResultUtil.success(orderDTO);
    }

    /**
     * 取消订单
     * @param orderNo
     * @return Result<OrderDTO>
     */
    @GetMapping("/cancel")
    public Result<OrderDTO> cancel(@RequestParam("orderNo") String orderNo){
        OrderDTO orderDTO=orderService.cancel(orderNo);
        List<CartDTO> cartDTOList=orderDTO.getOrderDetailList().stream().map(e->new
                CartDTO(e.getProductId(),e.getQuantity())).collect(Collectors.toList());
        productService.increaseStock(cartDTOList);
        return ResultUtil.success(orderDTO);
    }
}
