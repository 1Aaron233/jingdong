<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>店铺分类页面</title>
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
                    <th>店铺ID</th>
                    <th>分类名称</th>
                    <th>商品类型</th>
                    <th>分类状态</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <#list productCategoryList.content as productCategory>
                <tr>
                    <td>${productCategory.shopId}</td>
                    <td>${productCategory.name}</td>
                    <td>${productCategory.type}</td>
                    <td>${productCategory.getStatusEnum().message}</td>
                    <td>
                        1
<#--                        <a href="/jingdong/seller/order/detail?orderNo=${orderDTO.orderNo}">详情</a>
                        |
                        <#if orderDTO.getOrderStatusEnum().message!="已取消" &&orderDTO.getOrderStatusEnum().message=="未支付">
                            <a href="/jingdong/seller/order/cancel?orderNo=${orderDTO.orderNo}">取消</a>
                        <#elseif orderDTO.getOrderStatusEnum().message!="已取消" &&orderDTO.getOrderStatusEnum().message!="未支付">
                            <a href="javascript:void(0);">取消</a>
                        <#else >
                            <a href="javascript:void(0);">已取消</a>
                        </#if>-->
                    </td>
                </tr>
                </#list>
                </tbody>
            </table>
        </div>

        <div class="col-md-12 column">
            <ul class="pagination">
                <li>
                    <a href="javascript:void(0);">共${productCategoryList.getTotalPages()}页</a>
                </li>
                <li>
                    <a href="javascript:void(0);">当前${page}页</a>
                </li>
                <li>
                    <a href="/jingdong/seller/productCategory/list?page=1">首页</a>
                </li>

                <#if page lte 1>
                <li class="disabled">
                    <a href="javascript:void(0);">上一页</a>
                </li>
                <#else/>
                <li>
                    <a href="/jingdong/seller/productCategory/list?page=${page-1}">上一页</a>
                </li>
                </#if>

                <#if page gte productCategoryList.getTotalPages()>
                <li class="disabled">
                    <a href="javascript:void(0);">下一页</a>
                </li>
                <#else/>
                <li>
                    <a href="/jingdong/seller/productCategory/list?page=${page+1}">下一页</a>
                </li>
                </#if>

                <li>
                    <a href="/jingdong/seller/productCategory/list?page=${productCategoryList.getTotalPages()}">尾页</a>
                </li>
            </ul>
        </div>
    </div>
    </div>
    </div>
</div>
</body>
</html>