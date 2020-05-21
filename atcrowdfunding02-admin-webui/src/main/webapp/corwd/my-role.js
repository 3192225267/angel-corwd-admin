function fillAuthTree(){
    var ajaxReturn= $.ajax({
        "url":"assgin/get/all/auth.json",
        "type":"post",
        "async":false,
        "dataType":"json"
    })
    if(ajaxReturn.status!=200){
        layer.msg("请求处理出错！响应状态码是："+ajaxReturn.status+"说明是："+ajaxReturn.statusText);
        return;
    }
    //2.从响应结果a中获取Auth的JSON数据

    var authList=ajaxReturn.responseJSON.data;

    //3.准备对zTree进行设置的JSON对象
    var setting={
        "data":{
            "simpleData":{
                //开启简单JSON功能
                "enable":true,
                //使用categoryId属性关联父节点，不用默认的pId了
                "pIdKey":"categoryId"
            },
            "key":{
                "name":"title"
            }
        },
        "check":{
            "enable":true
        }
    };
    //4.生成树形结构
    //<ulid="authTreeDemo"class="ztree"></ul>
    $.fn.zTree.init($("#authTreeDemo"),setting,authList);

    //获取zTreeObj对象
    var zTreeObj=$.fn.zTree.getZTreeObj("authTreeDemo");

    //调用zTreeObj对象的方法，把节点展开
    zTreeObj.expandAll(true);

    //5.查询已分配的Auth的id组成的数组
   var ajaxreturn= $.ajax({
        "url":"assign/get/assigned/auth/id/by/role/id.json",
        "type":"post",
        "data":{
            "roleId":window.id
        },
        "dataType":"json",
        "async":false
    })
    if(ajaxReturn.status!=200){
       layer.msg(ajaxreturn.status+" "+ajaxreturn.statusText);
       return ;
    }

    // 从响应结果中获得数据
   var authIdArray= ajaxreturn.responseJSON.data;
   // 6.根据authIdArray把树形结构中对应的节点勾选上
   // 遍历 authIdArray
    for(var i=0;i<authIdArray.length;i++){
        var authId=authIdArray[i];

        // ②根据id查询属性结构中对应的节点
        var treeNode=zTreeObj.getNodeByParam("id",authId);

        // 将 treeNode设为被勾选

        // cheacked 设置为true，勾选

        var checked=true;

        // checkTypeFlag设置为false，表示不联动，不联动是为了避免把不该勾选的勾选上
        var checkTypeFlag=false;

        // 执行
        zTreeObj.checkNode(treeNode,checked,checkTypeFlag);
    }
}




// 打开删除模态框
function showConfirmModal(roleArry) {
    // 打开模态框
    $("#removeModal").modal("show");

    //清除旧的数据
    $("#roleNameDiv").empty();

    // 在全局变量范围创建数组用来存放角色id
    window.roleIdArray= [];

    // 遍历roleArry数组
    for (var i=0;i<roleArry.length;i++){
        var role=roleArry[i];
        var roleName=role.roleName;
        $("#roleNameSpan").append(roleName+"</br>")

        var roleId=role.roleId;
        //调用数组对象的push存入新元素
       window.roleIdArray.push(roleId);
    }

}




// 执行分页，生成页面效果，任何时候调用这个函数都会重新加载页面
function generatePage() {

    // 1.获取分页数据
    var pageInfo = getPageInfoRemote();

    // 2.填充表格
    fillTableBody(pageInfo);

}

// 远程访问服务器端程序获取pageInfo数据
function getPageInfoRemote() {

    // 调用$.ajax()函数发送请求并接受$.ajax()函数的返回值
    var ajaxResult = $.ajax({
        "url": "role/get/page/info.json",
        "type":"post",
        "data": {
            "pageNum": window.pageNum,
            "pageSize": window.pageSize,
            "keyWord": window.keyWord
        },
        "async":false,
        "dataType":"json"
    });

    console.log(ajaxResult);

    // 判断当前响应状态码是否为200
    var statusCode = ajaxResult.status;

    // 如果当前响应状态码不是200，说明发生了错误或其他意外情况，显示提示消息，让当前函数停止执行
    if(statusCode != 200) {
        layer.msg("失败！响应状态码="+statusCode+" 说明信息="+ajaxResult.statusText);
        return null;
    }

    // 如果响应状态码是200，说明请求处理成功，获取pageInfo
    var resultEntity = ajaxResult.responseJSON;

    // 从resultEntity中获取result属性
    var result = resultEntity.result;

    // 判断result是否成功
    if(result == "FAILED") {
        layer.msg(resultEntity.message);
        return null;
    }

    // 确认result为成功后获取pageInfo
    var pageInfo = resultEntity.data;

    // 返回pageInfo
    return pageInfo;
}

// 填充表格
function fillTableBody(pageInfo) {

    // 清除tbody中的旧的内容
    $("#rolePageBody").empty();

    // 这里清空是为了让没有搜索结果时不显示页码导航条
    $("#Pagination").empty();

    // 判断pageInfo对象是否有效
    if(pageInfo == null || pageInfo == undefined || pageInfo.list == null || pageInfo.list.length == 0) {
        $("#rolePageBody").append("<tr><td colspan='4' align='center'>抱歉！没有查询到您搜索的数据！</td></tr>");

        return ;
    }

    // 使用pageInfo的list属性填充tbody
    for(var i = 0; i < pageInfo.list.length; i++) {

        var role = pageInfo.list[i];

        var roleId = role.id;

        var roleName = role.name;

        var numberTd = "<td>"+(i+1)+"</td>";
        var checkboxTd = "<td><input id='"+roleId+"' class='itemBox' type='checkbox'></td>";
        var roleNameTd = "<td>"+roleName+"</td>";

        var checkBtn = "<button  id='"+roleId+"' type='button'  class='btn btn-success btn-xs checkBtn'><i class='glyphicon  glyphicon-check'></i></button>";


        // 通过button标签的id属性（别的属性其实也可以）把roleId值传递到button按钮的单击响应函数中，在单击响应函数中使用this.id
        var pencilBtn = "<button id='"+roleId+"' type='button' class='btn btn-primary btn-xs pencilBtn'><i class=' glyphicon glyphicon-pencil'></i></button>";

        // 通过button标签的id属性（别的属性其实也可以）把roleId值传递到button按钮的单击响应函数中，在单击响应函数中使用this.id
        var removeBtn = "<button id='"+roleId+"' type='button' class='btn btn-danger btn-xs removeBtn'><i class=' glyphicon glyphicon-remove'></i></button>";

        var buttonTd = "<td>"+checkBtn+" "+pencilBtn+" "+removeBtn+"</td>";

        var tr = "<tr>"+numberTd+checkboxTd+roleNameTd+buttonTd+"</tr>";

        $("#rolePageBody").append(tr);
    }

    // 生成分页导航条
    generateNavigator(pageInfo);
}

// 生成分页页码导航条
function generateNavigator(pageInfo) {

    // 获取总记录数
    var totalRecord = pageInfo.total;

    // 声明相关属性
    var properties = {
        "num_edge_entries": 3,
        "num_display_entries": 5,
        "callback": paginationCallBack,
        "items_per_page": pageInfo.pageSize,
        "current_page": pageInfo.pageNum - 1,
        "prev_text": "上一页",
        "next_text": "下一页"
    }

    // 调用pagination()函数
    $("#Pagination").pagination(totalRecord, properties);
}

// 翻页时的回调函数
function paginationCallBack(pageIndex, jQuery) {

    // 修改window对象的pageNum属性
    window.pageNum = pageIndex + 1;

    // 调用分页函数
    generatePage();

    // 取消页码超链接的默认行为
    return false;

}