<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>订单列表页面</title>
    <link href="https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
    <#include "../common/header.ftl"/>
</head>
<body>
<div id="wrapper" class="toggled">
    <#--边栏-->
    <#include "../common/nav.ftl"/>
    <div id="page-content-wrapper">
<div class="container-fluid">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <table class="table">
                <thead>
                <tr>
                    <th>订单ID</th>
                    <th>订单总金额</th>
                </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>${orderDTO.orderNo}</td>
                        <td>${orderDTO.orderAmount}</td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="col-md-12 column">
            <table class="table">
                <thead>
                <tr>
                    <th>商品ID</th>
                    <th>商品名称</th>
                    <th>数量</th>
                    <th>总额</th>
                </tr>
                </thead>
                <tbody>
                <#list orderDTO.orderDetailList as order>
                <tr>
                    <td>${order.productId}</td>
                    <td>${order.name}</td>
                    <td>${order.quantity}</td>
                    <td>${order.price}</td>
                </tr>
                </#list>
                </tbody>
            </table>
        </div>
        <div class="col-md-12 column">
            <td>
                <#if orderDTO.getOrderStatusEnum().message!="已取消" &&orderDTO.getOrderStatusEnum().message!="已完结">
                    <a href="/jingdong/seller/order/finish?orderNo=${orderDTO.orderNo}">
                        <button type="button" class="btn btn-default btn-primary">完结订单</button>
                    </a>
                <#elseif orderDTO.getOrderStatusEnum().message!="已取消" &&orderDTO.getOrderStatusEnum().message=="已完结">
                    <a href="javascript:void(0);">
                        <button type="button" class="btn btn-default btn-primary">已完结</button>
                    </a>
                <#else >
                    <a href="javascript:void(0);"><button type="button" class="btn btn-default btn-primary">完结订单</button></a>
                </#if>
                <#if orderDTO.getOrderStatusEnum().message!="已取消" &&orderDTO.getOrderStatusEnum().message=="未支付">
                    <a href="/jingdong/seller/order/cancel?orderNo=${orderDTO.orderNo}">
                        <button type="button" class="btn btn-default btn-danger">取消</button>
                    </a>
                <#elseif orderDTO.getOrderStatusEnum().message!="已取消" &&orderDTO.getOrderStatusEnum().message!="未支付">
                    <a href="javascript:void(0);">
                        <button type="button" class="btn btn-default btn-danger">取消</button>
                    </a>
                <#else >
                    <a href="javascript:void(0);"><button type="button" class="btn btn-default btn-danger">已取消</button></a>
                </#if>
            </td>
        </div>
    </div>
    </div>
    </div>
</div>
</body>
</html>