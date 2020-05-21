<%--
  Created by IntelliJ IDEA.
  User: 刘振河
  Date: 2020/4/2
  Time: 21:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="modal fade" tabindex="-1" role="dialog" id="editModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">修改角色信息</h4>
            </div>
            <div class="modal-body">
                <input class="form-control has-success" id="name" name="roleName" type="text" placeholder="请输入角色名称">
            </div>
            <div class="modal-footer">

                <button type="button" id="editRoleBtn" class="btn btn-primary">保存</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
