package com.xxx.jingdong.controller;

import com.xxx.jingdong.pojo.Shop;
import com.xxx.jingdong.service.ShopService;
import com.xxx.jingdong.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;


/**
 * Author；Aaron
 */
@Controller
@RequestMapping("/seller/shop")
@Slf4j
public class SellShopController {
    @Autowired
    private ShopService shopService;

    /**
     * 渲染超市分类
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/list")
    //请求参数注解可以设置默认值，由名称以及默认value对应
    public ModelAndView getShops(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                 @RequestParam(value = "size",defaultValue = "10") Integer size,
                                 HttpSession session){
        String sellerId=(String) session.getAttribute("sellerId");
        PageRequest request=PageRequest.of(page-1,size);
        Page<Shop> shopList=shopService.findAllByPage(request,sellerId);
        ModelAndView mv=new ModelAndView();
        mv.addObject("shopList",shopList);
        mv.addObject("page",page);
        mv.setViewName("/shop/list");
        System.out.println(shopList);
        return mv;
    }

//    /**
//     * 取消订单
//     * @param orderNo
//     * @return ModelAndView
//     */
//    @GetMapping("cancel")
//    public ModelAndView cancel(@RequestParam("orderNo") String orderNo){
//        ModelAndView mv=new ModelAndView();
//        mv.addObject("url","/jingdong/seller/order/list");
//        try {
//            OrderDTO orderDTO = shopCategoryService.cancel(orderNo);
//        }catch (Exception e){
//            log.error("【卖家端取消订单】发生异常{}",e);
//            mv.addObject("msg",e.getMessage());
//            mv.setViewName("common/error");
//        };
//        mv.addObject("msg", ResultEnum.ORDER_CANCEL_SUCCESS.getMessage());
//        mv.setViewName("common/success");
//        return mv;
//    }
//
//    /**
//     * 完结订单
//     * @param orderNo
//     * @return ModelAndView
//     */
//    @GetMapping("finish")
//    public ModelAndView finish(@RequestParam("orderNo") String orderNo){
//        ModelAndView mv=new ModelAndView();
//        mv.addObject("url","/jingdong/seller/order/list");
//        try {
//            OrderDTO orderDTO = shopCategoryService.finish(orderNo);
//        }catch (Exception e){
//            log.error("【卖家端取消订单】发生异常{}",e);
//            mv.addObject("msg",e.getMessage());
//            mv.setViewName("common/error");
//        };
//        mv.addObject("msg", ResultEnum.ORDER_FINISH_SUCCESS.getMessage());
//        mv.setViewName("common/success");
//        return mv;
//    }
//
//    /**
//     * 订单详情
//     * @param orderNo
//     * @return ModelAndView
//     */
//    @GetMapping("detail")
//    public ModelAndView detail(@RequestParam("orderNo") String orderNo){
//        ModelAndView mv=new ModelAndView();
//        mv.addObject("url","/jingdong/seller/order/list");
//        OrderDTO orderDTO =new OrderDTO();
//        try {
//            orderDTO=orderService.findOne(orderNo);
//        }catch (Exception e){
//            log.error("【卖家端订单详情】发生异常{}",e);
//            mv.addObject("msg",e.getMessage());
//            mv.setViewName("common/error");
//        };
//        mv.addObject("orderDTO",orderDTO);
//        mv.setViewName("order/detail");
//        return mv;
//    }


//    //订单删除接口
//    @GetMapping("deleteOrder")
//    //请求参数注解可以设置默认值，由名称以及默认value对应
//    public ModelAndView deleteOrder(@RequestParam(value = "page",defaultValue = "1") Integer page,
//                                    @RequestParam(value = "size",defaultValue = "10") Integer size,
//                                    @RequestParam(value = "sellerId") String sellerId,
//                                    @RequestParam(value = "orderNo") String orderNo){
//        orderService.delete(orderNo);
//        PageRequest request=PageRequest.of(page-1,size);
//        Page<OrderDTO> orderDTOPage=orderService.findAllBySellerId(request,sellerId);
//        ModelAndView mv=new ModelAndView();
//        mv.addObject("orderDTOPage",orderDTOPage);
//        mv.addObject("sellerId",sellerId);
//        mv.addObject("page",page);
//        mv.addObject("totalPage",orderDTOPage.getTotalPages());
//        mv.setViewName("order/orders");
//        return mv;
//    }
//
//    //订单更新页面跳转接口
//    @GetMapping("orderUpdate")
//    //请求参数注解可以设置默认值，由名称以及默认value对应
//    public ModelAndView updateOrderJump(@RequestParam(value = "orderNo") String orderNo,
//                                        @RequestParam(value = "sellerId") String sellerId){
//        OrderDTO orderDTO =orderService.findOne(orderNo);
//        ModelAndView mv=new ModelAndView();
//        mv.addObject("sellerId",sellerId);
//        mv.addObject("orderDTO",orderDTO);
//        mv.setViewName("order/order_update");
//        return mv;
//    }
//
//    //订单更新接口
//    @GetMapping("updateOrder")
//    //请求参数注解可以设置默认值，由名称以及默认value对应
//    public ModelAndView updateOrder(@RequestParam(value = "page",defaultValue = "1") Integer page,
//                                    @RequestParam(value = "size",defaultValue = "10") Integer size,
//                                    @RequestParam(value = "sellerId") String sellerId,
//                                    @RequestParam(value = "orderNo") String orderNo,
//                                    @RequestParam(value = "userName") String userName,
//                                    @RequestParam(value = "userPhone") String userPhone,
//                                    @RequestParam(value = "userAddress") String userAddress){
//        orderService.update(orderNo,userName,userPhone,userAddress);
//        PageRequest request=PageRequest.of(page-1,size);
//        Page<OrderDTO> orderDTOPage=orderService.findAllBySellerId(request,sellerId);
//        ModelAndView mv=new ModelAndView();
//        mv.addObject("orderDTOPage",orderDTOPage);
//        mv.addObject("sellerId",sellerId);
//        mv.addObject("page",page);
//        mv.addObject("totalPage",orderDTOPage.getTotalPages());
//        mv.setViewName("order/orders");
//        return mv;
//    }
//

//
//    //订单发货接口
//    @GetMapping("orderStatusRoad")
//    //请求参数注解可以设置默认值，由名称以及默认value对应
//    public void orderStatusRoad(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        // 获取请求参数 -- 用户ID
//        String orderNo = req.getParameter("orderNo");;
//        orderService.road(orderNo);
//        OrderDTO orderDTO= orderService.findOne(orderNo);
//        JSONObject json = new JSONObject();
//        if(orderDTO.getOrderStatus() == 2) {
//            json.put("status", "success");
//            json.put("data", 2);
//        }else {
//            json.put("status", "fail");
//        }
//        resp.getWriter().write(json.toString());
//    }
//
//    //订单完成接口
//    @GetMapping("orderStatusOk")
//    //请求参数注解可以设置默认值，由名称以及默认value对应
//    public void orderStatusOk(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        // 获取请求参数 -- 用户ID
//        String orderNo = req.getParameter("orderNo");;
//        System.out.println(1111111111);
//        orderService.finish(orderNo);
//        OrderDTO orderDTO= orderService.findOne(orderNo);
//        JSONObject json = new JSONObject();
//        if(orderDTO.getOrderStatus() == 4) {
//            json.put("status", "success");
//            json.put("data", 4);
//        }else {
//            json.put("status", "fail");
//        }
//        resp.getWriter().write(json.toString());
//    }




//    //订单模糊查询接口
//    @GetMapping("oneOrder")
//    //请求参数注解可以设置默认值，由名称以及默认value对应
//    public ModelAndView getOrder(@RequestParam(value = "page",defaultValue = "1") Integer page,
//                                 @RequestParam(value = "size",defaultValue = "10") Integer size,
//                                 @RequestParam(value = "sellerId") String sellerId,
//                                 @RequestParam(value = "orderNo") String orderNo){
//        PageRequest request=PageRequest.of(page-1,size);
//        Page<OrderDTO> orderDTOPage=orderService.findAllByOrderNoContainingAndSellerId(request,orderNo,sellerId);
//        ModelAndView mv=new ModelAndView();
//        mv.addObject("orderDTOPage",orderDTOPage);
//        mv.addObject("sellerId",sellerId);
//        mv.addObject("page",page);
//        mv.addObject("totalPage",orderDTOPage.getTotalPages());
//        mv.setViewName("order/orders");
//        return mv;
//    }
//
//    //订单详情跳转接口
//    @GetMapping("/orderDetail")
//    public ModelAndView orderDetail(@RequestParam("orderNo") String orderNo,
//                                    @RequestParam("sellerId") String sellerId){
//        OrderDTO orderDTO = orderService.findOne(orderNo);
//        ModelAndView mv= new ModelAndView();
//        mv.addObject("orderDTO",orderDTO);
//        mv.addObject("sellerId",sellerId);
//        mv.setViewName("order/order_detail");
//        return mv;
//    }
}
