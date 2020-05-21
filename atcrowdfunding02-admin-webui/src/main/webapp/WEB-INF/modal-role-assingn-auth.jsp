<%--
  Created by IntelliJ IDEA.
  User: 刘振河
  Date: 2020/4/2
  Time: 21:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="modal fade" tabindex="-1" role="dialog" id="assingnModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">天使众筹提示信息</h4>
            </div>
            <div class="modal-body" >
            <h4>权限分配</h4>
                <span id="roleNameSpan" ></span>
            </div>
            <div class="modal-footer">
                <ul id="authTreeDemo" class="ztree"></ul>

                <button type="button" id="assingnBtn" class="btn btn-success">确认分配权限</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
