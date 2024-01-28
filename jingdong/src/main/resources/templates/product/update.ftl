<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>商品添加</title>
    <link href="https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
    <#include "../common/header.ftl"/>
</head>
<body >
<div id="wrapper" class="toggled">
    <#--边栏-->
    <#include "../common/nav.ftl"/>
    <div id="page-content-wrapper">
<div class="container-fluid">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <form action="/jingdong/seller/product/update" method="post" enctype="multipart/form-data">
                <input type="hidden" name="productId"  value="${product.id}" >
                <input type="hidden" name="oldUrl"  value="${product.imgUrl}" >
                <table style="text-align: center; width: 700px">
                    <tr height="50" >
                        <td colspan="2">商品更新</td>
                    </tr>
                    <tr height="50" >
                        <td>商品名称</td>
                        <td><input type="text" name="name" value="${product.name}"/></td>
                    </tr>
                    <tr height="50" >
                        <td>商品价格</td>
                        <td><input type="text" name="price" value="${product.price}"/></td>
                    </tr>
                    <tr height="50" >
                        <td>商品原价</td>
                        <td><#if product.oldPrice??>
                                <input type="text" name="oldPrice" value="${product.oldPrice}"/>
                            <#else>
                                <input type="text" name="oldPrice" value="0"/>
                            </#if>
                        </td>
                    </tr>
                    <tr height="50" >
                        <td>商品库存</td>
                        <td><input type="text" name="productStock" value="${product.productStock}"/></td>
                    </tr>
                    <tr height="80" >
                        <td>商品预览</td>
                        <td><div style="height: 50px ;background: url('${product.imgUrl}') center no-repeat;background-size: contain"  ></div>
                            <div style="display:flex;justify-content:center;align-items:center;"><input type="file" name="imgUrl" hidden="hidden" style="width: 75px" value="${product.imgUrl}"/></div></td>
                    </tr>
                    <tr height="50" >
                        <td>商品分类</td>
                        <td>
                            <select name="type">
                                <option>--请下拉选择商品类型--</option>
                                <#list productCategories as productCategory>
                                    <option value="${productCategory.type }" ${ (productCategory.type == product.type) ?string("selected","")}>${productCategory.type }</option>
                                </#list >
                            </select>
                        </td>
                    </tr>
                    <tr height="50" >
                        <td>商品所属店铺</td>
                        <td>
                            <select name="shopId">
                                <option>--请下拉选择商品所属店铺--</option>
                                <#list shops as shop>
                                    <option value="${shop.id }" ${ (shop.id == product.shopId) ?string("selected","")}>${shop.name }</option>
                                </#list >
                            </select>
                        </td>
                    </tr>
                    <tr height="50" >
                        <td colspan="2">
                            <input type="submit" value="提交更新" />
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
    </div>
    </div>
</div>
</body>
</html>