<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>失败提示</title>
    <link href="https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
           <div class="alert alert-danger alert-dismissable">
               <button type="button" class="close" data-dismiss="alert" aria-hidden="true">x</button>
               <h4>
                   错误!
               </h4>
               <h4>
                   <strong>${msg}</strong><a href="${url}" class="alert-link">三秒后自动跳转</a>
               </h4>
           </div>
        </div>
    </div>
</div>
</body>
<script>
    setTimeout('location.href="${url}"',3000);
</script>
</html>