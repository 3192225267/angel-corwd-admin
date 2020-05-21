<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="utf-8"
%>
<base href="http://${pageContext.request.serverName }:${pageContext.request.serverPort }${pageContext.request.contextPath }/"/>
<html>
<body>
<h2><a href="test/ssm.html">测试</a></h2>

<h2><a href="ok.html">测试</a></h2>

<button id="btn1">点我发送信息</button>
</body>
<script type="text/javascript" src="js/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="layer/layer.js"></script>
<script>
    $("#btn1").click(function () {
       layer.msg("这是layer的弹框")
    })
</script>
</html>
