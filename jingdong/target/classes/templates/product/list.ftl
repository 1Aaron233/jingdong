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
                    <th>商品ID</th>
                    <th>商品名称</th>
                    <th>商品销量</th>
                    <th>商品价格</th>
                    <th>商品原价</th>
                    <th>商品库存</th>
                    <th>商品图片</th>
                    <th>商品类型</th>
                    <th>所属商铺ID</th>
                    <th>商品状态</th>
                    <th>商品热度</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <#list productList.content as product>
                <tr>
                    <td>${product.id}</td>
                    <td>${product.name}</td>
                    <td>${product.sales}</td>
                    <td>${product.price}</td>
                    <td><#if product.oldPrice??>
                            ${product.oldPrice}
                        <#else>
                            0
                        </#if></td>
                    <td>${product.productStock}</td>
                    <td><img src="${product.imgUrl}"></td>
                    <td>${product.type}</td>
                    <td>${product.shopId}</td>
                    <td>${product.getStatusEnum().message}</td>
                    <td>${product.getStateEnum().message}</td>
                    <td>
                        <a href="/jingdong/seller/product/toUpdate?productId=${product.id}">更新</a>
                        |
                        <a href="/jingdong/seller/product/delete?productId=${product.id}">删除</a>
                        |
                        <#if product.getStatusEnum().message=="上架">
                            <a href="/jingdong/seller/product/toDown?productId=${product.id}">下架商品</a>
                        <#else >
                            <a href="/jingdong/seller/product/toUp?productId=${product.id}">上架商品</a>
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
                    <a href="javascript:void(0);">共${productList.getTotalPages()}页</a>
                </li>
                <li>
                    <a href="javascript:void(0);">当前${page}页</a>
                </li>
                <li>
                    <a href="/jingdong/seller/product/list?page=1">首页</a>
                </li>

                <#if page lte 1>
                <li class="disabled">
                    <a href="javascript:void(0);">上一页</a>
                </li>
                <#else/>
                <li>
                    <a href="/jingdong/seller/product/list?page=${page-1}">上一页</a>
                </li>
                </#if>

                <#if page gte productList.getTotalPages()>
                <li class="disabled">
                    <a href="javascript:void(0);">下一页</a>
                </li>
                <#else>
                <li>
                    <a href="/jingdong/seller/product/list?page=${page+1}">下一页</a>
                </li>
                </#if>

                <li>
                    <a href="/jingdong/seller/product/list?page=${productList.getTotalPages()}">尾页</a>
                </li>
            </ul>
        </div>
    </div>
    </div>
    </div>
</div>
</body>
</html>