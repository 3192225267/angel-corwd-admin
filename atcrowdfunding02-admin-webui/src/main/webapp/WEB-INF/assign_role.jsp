<%--
  Created by IntelliJ IDEA.
  User: 刘振河
  Date: 2020/3/28
  Time: 20:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<jsp:include page="/WEB-INF/include-head.jsp"/>
<style>
    .tree li {
        list-style-type: none;
        cursor:pointer;
    }
</style>
<script type="text/javascript">
    $(function () {
        $("#btn_right").click(function () {
            $("select:eq(0)>option:selected").appendTo("select:eq(1)");
        })

        $("#btn_left").click(function () {
            $("select:eq(1)>option:selected").appendTo("select:eq(0)");
        })
    })



    function submmitclick() {
        // 在提交表单前把“已分配”部分的 option 全部选中
        $("select:eq(1)>option").prop("selected","selected");
        // 为了看到上面代码的效果，暂时不让表单提交
        return false;
    }
</script>
<body>

<jsp:include page="/WEB-INF/include-nav.jsp"/>
<div class="container-fluid">
    <div class="row">
        <jsp:include page="/WEB-INF/include-sidebar.jsp"/>

        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="#">首页</a></li>
                <li><a href="#">数据列表</a></li>
                <li class="active">分配角色</li>
            </ol>

            <div class="panel panel-default">
                <form role="form" class="form-inline"  action="/assign/do/role/assign.html" method="post">
                    <div class="panel-body">

                        <div class="form-group">
                            <label >未分配角色列表</label><br>
                            <select class="form-control" size="10" multiple="multiple" style="width:100px;overflow-y:auto;" >
                                <c:forEach items="${requestScope.unAssignedRoleList}" var="role">
                                    <option value="${role.id}">${role.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <ul>
                                <li id="btn_right" class="btn btn-default glyphicon glyphicon-chevron-right"></li>
                                <br>
                                <li id="btn_left" class="btn btn-default glyphicon glyphicon-chevron-left" style="margin-top:20px;"></li>
                            </ul>
                        </div>
                        <div class="form-group" style="margin-left:40px;">
                            <label >已分配角色列表</label><br>
                            <input name="pageNum" value="${param.pageNum}" type="hidden">
                            <input name="keyWord" value="${param.keyWord}" type="hidden">
                            <input name="adminId" value="${param.adminId}" type="hidden">
                            <select class="form-control" multiple="multiple" size="10" style="width:100px;overflow-y:auto;" name="roleIdList">
                                <c:forEach items="${requestScope.assignedRoleList}"  var="role">
                                    <option value="${role.id}">${role.name}</option>
                                </c:forEach>
                            </select>

                        </div>

                    </div>
                    <button id="submitBtn" onclick="submmitclick()" type="submit" class="btn btn-success" style="margin-left: 15px;width: 70px">确认</button>
                </form>
            </div>
        </div>
    </div>
</div>

</div>
</div>

</body>
</html>

