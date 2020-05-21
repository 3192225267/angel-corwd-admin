<%--
  Created by IntelliJ IDEA.
  User: 刘振河
  Date: 2020/3/28
  Time: 20:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<jsp:include page="/WEB-INF/include-head.jsp"/>

<body>

<jsp:include page="/WEB-INF/include-nav.jsp"/>

<div class="container-fluid">
    <div class="row">
        <jsp:include page="/WEB-INF/include-sidebar.jsp"/>

        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="/admin/to/main/page.html">首页</a></li>
                <li><a href="/admin/page.html?pageNum=${pageNum}&keyWord=${keyWord}">数据列表</a></li>
                <li class="active">新增</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-heading">表单数据<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
                <div class="panel-body">
                    <p style="color:red">${requestScope.exception.message}</p>
                    <form role="form" id="adminFrom" action="/admin/add.html" method="post">
                        <div class="form-group">
                            <label for="exampleInputPassword1">用户昵称</label>
                            <input type="text" class="form-control" name="userName" id="exampleInputPassword1" placeholder="请输入用户名称">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputPassword1">登陆账号</label>
                            <input type="text" class="form-control" name="loginAcct" id="exampleInputPassword1" placeholder="请输入登陆账号">
                        </div>

                        <div class="form-group">
                            <label for="exampleInputPassword1">用户密码</label>
                            <input type="text" class="form-control" name="userPswd" id="exampleInputPassword1" placeholder="请输入用户密码">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputEmail1">邮箱地址</label>
                            <input type="email"  class="form-control" name="email" id="exampleInputEmail1" placeholder="请输入邮箱地址">
                            <p class="help-block label label-warning">请输入合法的邮箱地址, 格式为： xxxx@xxxx.com</p>
                        </div>
                        <button id="btn1" type="submit" class="btn btn-success"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                        <button type="button" class="btn btn-danger"><i class="glyphicon glyphicon-refresh"></i> 重置</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>

