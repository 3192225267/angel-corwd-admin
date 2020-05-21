<%--
  Created by IntelliJ IDEA.
  User: 刘振河
  Date: 2020/4/2
  Time: 21:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="modal fade" tabindex="-1" role="dialog" id="removeModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">提示信息</h4>
            </div>
            <div class="modal-body" >
            <h4>确定要删除吗？</h4>
                <span id="roleNameSpan" ></span>
            </div>
            <div class="modal-footer">

                <button type="button" id="removeRoleBtn" class="btn btn-danger">删除</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
