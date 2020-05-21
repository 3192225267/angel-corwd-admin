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
<link rel="stylesheet" href="ztree/zTreeStyle.css"/>
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="corwd/my-menu.js"></script>
<script type="text/javascript" src="layer/layer.js"></script>
<script type="text/javascript">
    $(function () {
        generateTree();

    })
</script>
<body>

<jsp:include page="/WEB-INF/include-nav.jsp"/>

<div class="container-fluid">
    <div class="row">
        <jsp:include page="/WEB-INF/include-sidebar.jsp"/>

        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading"><i class="glyphicon glyphicon-th-list"></i> 权限菜单列表
                    <div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal">
                        <i class="glyphicon glyphicon-question-sign"></i>
                    </div>
                </div>
                <div class="panel-body">
                    <ul id="treeDemo" class="ztree"></ul>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="modal-menu-add.jsp"/>
<jsp:include page="modal-menu-confirm.jsp"/>
<jsp:include page="modal-menu-edit.jsp"/>

<script type="text/javascript">
    // 打开模态框并传入id
    $("#treeDemo").on("click", ".addBtn", function () {
        // 获得到它的父类id
        window.pid = this.id;
        // 打开模态框
        $("#menuAddModal").modal("show");

        return false;
    })

    // 模态框保存按钮单击事件
    $("#menuSaveBtn").click(function () {
        // 收集表单项中用户输入的数据
        var name = $.trim($("#menuAddModal [name=name]").val());
        var url = $.trim($("#menuAddModal [name=url]").val());
        // 单选按钮要定位到“被选中”的那一个
        var icon = $("#menuAddModal [name=icon]:checked").val();

        $.ajax({
            "url":"menu/save.json",
            "type":"post",
            "data":{
                "name":name,
                "url":url,
                "pid":window.pid,
                "icon":icon
            },
            "dataType":"json",
            "success":function (response) {
                result=response.result;
                alert(result)
                if(result == "SUCCESS"){
                    layer.msg("操作成功！");
                    // 重新加载树形结构，注意：要在确认服务器端完成保存操作后再刷新 // 否则有可能刷新不到最新的数据，因为这里是异步的
                    generateTree();
                }
                if(result=="FAILED"){
                    layer.msg("添加失败"+response.message);
                }
            },
            "error":function (response) {
                Layer.msg("异常"+response.status+" "+response.statusText);
            }
        })
        $("#menuAddModal").modal("hide");
    })
    $("#treeDemo").on("click", ".editBtn", function () {

        // 将当前节点的 id 保存到全局变量
        window.id=this.id;

        // 打开模态框
        $("#menuEditModal").modal("show");

        // 获取 zTreeObj 对象
        // 将当前节点的 id 保存到全局变量
        window.id = this.id;
        // 打开模态框
        $("#menuEditModal").modal("show");
        // 获取 zTreeObj 对象
        var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");
        // 根据 id 属性查询节点对象
        // 用来搜索节点的属性名
        var key = "id";
        // 用来搜索节点的属性值
        var value = window.id;
        var currentNode = zTreeObj.getNodeByParam(key, value);
        // 回显表单数据
        $("#menuEditModal [name=name]").val(currentNode.name);
        $("#menuEditModal [name=url]").val(currentNode.url);
        // 回显 radio 可以这样理解：被选中的 radio 的 value 属性可以组成一个数组， // 然后再用这个数组设置回 radio，就能够把对应的值选中
        $("#menuEditModal [name=icon]").val([currentNode.icon]);
        return false;
    })

    $("#menuEditBtn").click(function () {
        // 收集表单项中用户输入的数据
        var name=$.trim($("#menuEditModal [name=name]").val());
        var icon=$("#menuEditModal [name=icon]:checked").val();
        var url=$.trim($("#menuEditModal [name=url]").val());


        $.ajax({
            "url":"menu/edit.json",
            "type":"post",
            "data":{
                "name":name,
                "url":url,
                "icon":icon,
                "id":window.id
            },
            "dataType":"json",
            "success":function (response) {
                var result=response.result;
                if(result=="SUCCESS"){
                    layer.msg("修改成功");
                    generateTree();
                }
                if(result=="FAILED"){
                    layer.msg("修改失败"+response.message);
                }
            },
            "error":function (response) {
                layer.msg(response.status=""+response.statusText)
            }
        })
        $("#menuEditModal").modal("hide");
    })


    // 打开删除模态框
    $("#treeDemo").on("click",".removeBtn",function () {
        window.id=this.id;
        // 打开模态框
        $("#menuConfirmModal").modal("show");
        // 获取 zTreeObj 对象
        var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");
        // 根据 id 属性查询节点对象
        // 用来搜索节点的属性名
        var key = "id";
        // 用来搜索节点的属性值
        var value = window.id;
        var currentNode = zTreeObj.getNodeByParam(key, value);
        // 回显表单数据
        $("#menuEditModal [name=name]").val();
        // 回显 radio 可以这样理解：被选中的 radio 的 value 属性可以组成一个数组， // 然后再用这个数组设置回 radio，就能够把对应的值选中
        $("#menuEditModal [name=icon]").val([currentNode.icon]);

        $("#removeNodeSpan").html("【<i class='"+currentNode.icon+"'></i>"+currentNode.name+"】")

        return false;
    })

    // 删除事件提交
    $("#confirmBtn").click(function () {

        $.ajax({
            "url":"menu/remove.json",
            "type":"post",
            "data":{
                "id":window.id
            },
            "dataType":"json",
            "success":function (response) {
                result=response.result;
                if(result=="SUCCESS"){
                    layer.msg("删除成功");
                    generateTree();
                }
                if(result=="FAILED"){
                    layer.msg("删除失败"+response.message);
                }
            },
            "error":function (response) {
                layer.msg(response.status=""+response.statusText)
            }
        })
        $("#menuConfirmModal").modal("hide");

    })
</script>
</body>
</html>

