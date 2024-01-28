<!doctype html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>卖家账户登录页面</title>
    <!-- Bootstrap -->
    <link href="https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
    <#include "../common/header.ftl"/>
</head>
<body>
<#--内容区域-->
    <section class="container">
        <section class="row" style="text-align: center">
            <section class="col-lg-6" height="420px " style="text-align: center" >
                <ul class="welcome  ">
                    <li style="height: 130px"><h2>欢迎来到我们的卖家平台登录页面</h2></li>
                    <li style="height: 130px"><h3>请在此处进行登录操作</h3></li>
                    <li style="height: 130px;color: red"><h3>请您自由选择右侧账号密码登录或者扫码登录。</h3></li>
                </ul>
            </section>
 <#--密码登录表单区域-->
            <section class="col-lg-6" height="310px" style="text-align: center">
                <section style="height: 10px"></section>
                <form action="/jingdong/seller/password" method="get" >
                    <table style="text-align: center; width: 700px">
                        <tr height="80" >
                            <td colspan="2">卖家登录</td>
                        </tr>
                        <tr height="80" >
                            <td>卖家账号</td>
                            <td><input type="text" name="username"/></td>
                        </tr>
                        <tr height="80" >
                            <td>卖家密码</td>
                            <td><input type="text" name="password"/></td>
                        </tr>
                        <tr height="50" >
                            <td colspan="2">
                                <input type="submit" value="登录" />
                            </td>
                        </tr>
                    </table>
                </form>
 <#--微信扫码登录跳转区域-->
                <section height="100px" style="text-align: center">
                    <a href="/jingdong/wechat/qrAuthorize?returnUrl=http://qiaomai.natapp4.cc/jingdong/seller/login/scan"><i style="color: green;font-weight: bold;font-size: x-large">点击此处跳转微信登录</i></a>
                </section>
            </section>
        </section>
    </section>
</body>
</html>