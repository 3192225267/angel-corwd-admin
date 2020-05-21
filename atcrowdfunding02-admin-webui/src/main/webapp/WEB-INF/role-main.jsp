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
<link rel="stylesheet" href="css/pagination.css">
<script type="text/javascript" src="jquery/jquery.pagination.js"></script>
<script type="text/javascript" src="corwd/my-role.js"></script>
<script type="text/javascript" src="layer/layer.js"></script>
<link rel="stylesheet"href="ztree/zTreeStyle.css"/>
<script type="text/javascript"src="ztree/jquery.ztree.all-3.5.min.js"></script>
<body>

<jsp:include page="/WEB-INF/include-nav.jsp"/>

<div class="container-fluid">
    <div class="row">
        <jsp:include page="/WEB-INF/include-sidebar.jsp"/>

        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input class="form-control has-success" id="keyWordInput" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button type="button" class="btn btn-warning" id="searchBtn"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>

                    <button type="button" class="btn btn-danger" style="float:right;margin-left:10px;" id="batchRemoveBtn"><i class=" glyphicon glyphicon-remove"></i> 删除</button>

                    <button type="button" class="btn btn-primary" style="float:right;" id="showModelBtn"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr >
                                <th width="30">#</th>
                                <th width="30"><input id="summaryBox" type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="rolePageBody">
                            </tbody>
                            <tfoot>
                            <tr >
                                <td colspan="6" align="center">
                                    <div id="Pagination" class="pagination"><!-- 这里显示分页 --></div>
                                </td>
                            </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="role-add-model.jsp"/>
<jsp:include page="role-edit-model.jsp"/>
<jsp:include page="role-remove-model.jsp"/>
<jsp:include page="modal-role-assingn-auth.jsp"/>
<script>
   $(function () {
       //为分页准备初始化数据
       window.pageNum=1;
       window.pageSize=5;
       window.keyWord="";
   })

   // 2.调用执行分页的函数，显示分页效果
   generatePage();

   // 3.给查询按钮绑定单击响应函数
   $("#searchBtn").click(function(){
   // ①获取关键词数据赋值给对应的全局变量
       window.keyWord = $("#keyWordInput").val();
   // ②调用分页函数刷新页面
   generatePage(); });

   // 新增按钮单击事件，（显示模态框）
   $("#showModelBtn").click(function () {
       $("#addModal").modal("show")
   })

   // // 修改按钮单击事件，（显示模态框）
   // $(".pencilBtn").click(function () {
   //
   // })

   //如果按照上面的click的方法直接绑定按钮,会出现只有第一页按钮有响应，后面页数的按钮无响应的情况

   // 使用 jQuery 对象的 on()函数可以解决上面问题
   // ①首先找到所有“动态生成”的元素所附着的“静态”元素
   // ②on()函数的第一个参数是事件类型
   // ③on()函数的第二个参数是找到真正要绑定事件的元素的选择器
   // ③on()函数的第三个参数是事件的响应函数
    $("#rolePageBody").on("click",".pencilBtn",function () {
        //打开模态框
        $("#editModal").modal("show")

        //获得当前模态框的角色名称(用以回显)
        var roleName = $(this).parent().prev().text();

        //获取当前角色ID
        window.roleId = this.id;
        $("#editModal [name=roleName]").val(roleName);
    })

   //执行更新
   $("#editRoleBtn").click(function () {
       var roleName = $("#editModal [name=roleName]").val();
       //
       $.ajax({
           "url":"role/edit.json",
           "type":"post",
           "data":{
               "name":roleName,
               "id":window.roleId
           },
           "dataType":"json",
           "success":function (response) {
               var result = response.result;
               if(result == "SUCCESS")
               { layer.msg("更新成功！");
               // 重新加载分页数据
                  generatePage();
               }
               if(result=="FAILED"){
                   layer.msg("更新失败"+response.message);

               }
           },
           "error":function (response) {
               layer.msg(response.start()+" "+response.statusText);
           }
       })
       $("#editModal").modal("hide");
   })

//    模态框保存按钮点击事件（发送请求）
    $("#saveBtn").click(function () {
        var roleName=$.trim($("#addModal [name=roleName]").val())
        $.ajax({
            "url":"role/save.json",
            "type":"post",
            "data":{
                "name":roleName
            },
            "dataType":"json",
            "success":function (response) {
                var result=response.result;
            if(result=="SUCCESS"){
                layer.msg("添加成功")
                // 将页码定位到最后一页
                window.pageNum=99999;
                //重新分页
                generatePage();
            }
                if(result=="FAILED"){
                    layer.msg("添加失败"+response.message);

                }
            },
            "error":function (response) {
                layer.msg(response.start()+" "+response.statusText);
            }

        })
        //清理模态框
        $("#addModal [name=roleName]").val("");
        //关闭模态框
        $("#addModal").modal("hide");

    })

    // 调用
   $("#rolePageBody").on("click",".removeBtn",function () {
      var  roleName=$(this).parent().prev().text();
       // 创建一个数组
       var roleArray=[{
           roleId:this.id,
           roleName:roleName
       }]

       //打开模态框
      showConfirmModal(roleArray);
   })
    //
    $("#removeRoleBtn").click(function () {
        var requestBody=JSON.stringify(window.roleIdArray)
          $.ajax({
              "url":"role/remove.json",
              "type":"post",
              "data":requestBody,
              "contentType":"application/json;charset=UTF-8",
              "dataType":"json",
              "success":function (response) {
                  var result=response.result;
                  if(result=="SUCCESS"){
                      layer.msg("删除成功")
                      //重新分页
                      generatePage();
                  }
                  if(result=="FAILED"){
                      layer.msg("删除失败"+response.message);
                  }
              },
              "error":function (response) {
                  layer.msg(response.start()+" "+response.statusText);
              }
          });
        $("#removeModal [id=roleNameSpan]").val("");
        //关闭模态框
        $("#removeModal").modal("hide");
    })


   // 主多选框设置（选中主多选框后副多选框全部选中）
   $("#summaryBox").click(function () {
       // ①获取复选框自身的状态
       var currentStatus=this.checked;
       // ②用多选框的状态去设置其他多选框
       $(".itemBox").prop("checked",currentStatus);
   })
    // 全选，全部选反向操作
   $("#rolePageBody").on("click",".itemBox",function () {
       // 获取当前已选中的，itmeBox数量
       var checkedBoxcCount=$(".itemBox:checked").length;

       // 获取当前所有的itemBox数量
       var totalBoxCount=$(".itemBox").length;

       // 使用二者的比较结果设置总的checkbox，是true就设置，不是就设置
       $("#summaryBox").prop("checked",checkedBoxcCount==totalBoxCount);
   })

       // 删除全部按钮单击事件
    $("#batchRemoveBtn").click(function () {
        // 创建一个数组对象，用来存放后面选择的对象
        var roleArry=[];
        $(".itemBox:checked").each(function () {
            // 使用this引用当前遍历的到的复选框
            var roleId=this.id;

            //通过DOM操作获取角色名称
          var roleName=  $(this).parent().next().text();

            roleArry.push({
                "roleId":roleId,
                "roleName":roleName
            })
        })
        //  检查roleArray的长度是否为0
        if(roleArry.length==0){
            layer.msg("请选择删除的数据");
            return ;
        }

        //调用专门的函数打开模态框
        showConfirmModal(roleArry);
    })


    // 角色分配权限
    $("#rolePageBody").on("click",".checkBtn",function () {
        window.id=this.id;
        $("#assingnModal").modal("show");
        fillAuthTree();
    })

    // 分配权限按钮单击事件
    $("#assingnBtn").click(function () {
        //①收集树形结构的各个节点中被勾选的节点
        // [1]声明一个专门的数组存放id
        var authIdArray=[];

        //[2]获取zTreeObj对象
        var zTreeObj=$.fn.zTree.getZTreeObj("authTreeDemo");

        //[3]获取全部被勾选的节点
        var checkedNodes=zTreeObj.getCheckedNodes();

        //[4]遍历checkedNodes
        for (var i=0;i<checkedNodes.length;i++){
            var checkedNode=checkedNodes[i];
            var authId=checkedNode.id;
            authIdArray.push(authId);
        }
        // 发送请求
        var requestBody={
            "authIdArray":authIdArray,
            "roleId":[window.id]
        }
       var requestarray=JSON.stringify(requestBody);
        $.ajax({
            "url":"assign/do/role/assign/auth.json",
            "data":requestarray,
            "type":"post",
            "dataType":"json",
            "contentType":"application/json;charset=UTF-8",
            "success":function(response){
                result=response.result;
                if(result=="SUCCESS"){
                    layer.msg("授权成功");
                }
                if(result=="FAILED"){
                    layer.msg("授权失败"+response.status);
                }
            },
            "error":function(response){
                layer.msg(response.status+""+response.statusText);
            }
        })

        // 关闭模态框
        $("#assingnModal").modal("hide");

    });
</script>
</body>
</html>

