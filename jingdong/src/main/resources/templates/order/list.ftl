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
                    <th>姓名</th>
                    <th>手机号</th>
                    <th>地址</th>
                    <th>店铺</th>
                    <th>金额</th>
                    <th>订单状态</th>
                    <th>支付状态</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <#list orderDTOPage.content as orderDTO>
                <tr>
                    <td>${orderDTO.orderNo}</td>
                    <td>${orderDTO.userName}</td>
                    <td>${orderDTO.userPhone}</td>
                    <td>${orderDTO.userAddress}</td>
                    <td>${orderDTO.shopName}</td>
                    <td>${orderDTO.orderAmount}</td>
                    <td>${orderDTO.getOrderStatusEnum().message}</td>
                    <td>${orderDTO.getPayStatusEnum().message}</td>
                    <td>
                        <a href="/jingdong/seller/order/detail?orderNo=${orderDTO.orderNo}">详情</a>
                        |
                        <#if orderDTO.getOrderStatusEnum().message!="已取消" &&orderDTO.getOrderStatusEnum().message=="未支付">
                            <a href="/jingdong/seller/order/cancel?orderNo=${orderDTO.orderNo}">取消</a>
                        <#elseif orderDTO.getOrderStatusEnum().message!="已取消" &&orderDTO.getOrderStatusEnum().message!="未支付">
                            <a href="javascript:void(0);">取消</a>
                        <#else >
                            <a href="javascript:void(0);">已取消</a>
                        </#if>
                    </td>
                </tr>
                </#list>
                </tbody>
            </table>
        </div>

        <div class="col-md-12 column">
            <ul class="pagination">
                <li>
                    <a href="javascript:void(0);">共${orderDTOPage.getTotalPages()}页</a>
                </li>
                <li>
                    <a href="javascript:void(0);">当前${page}页</a>
                </li>
                <li>
                    <a href="/jingdong/seller/order/list?page=1">首页</a>
                </li>

                <#if page lte 1>
                <li class="disabled">
                    <a href="javascript:void(0);">上一页</a>
                </li>
                <#else/>
                <li>
                    <a href="/jingdong/seller/order/list?page=${page-1}">上一页</a>
                </li>
                </#if>

                <#if page gte orderDTOPage.getTotalPages()>
                <li class="disabled">
                    <a href="javascript:void(0);">下一页</a>
                </li>
                <#else/>
                <li>
                    <a href="/jingdong/seller/order/list?page=${page+1}">下一页</a>
                </li>
                </#if>

                <li>
                    <a href="/jingdong/seller/order/list?page=${orderDTOPage.getTotalPages()}">尾页</a>
                </li>
            </ul>
        </div>
    </div>
    </div>
    </div>
</div>
</body>
</html>