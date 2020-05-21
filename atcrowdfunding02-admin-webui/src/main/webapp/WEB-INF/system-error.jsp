<%--
  Created by IntelliJ IDEA.
  User: 刘振河
  Date: 2020/3/27
  Time: 18:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<base href="http://${pageContext.request.serverName }:${pageContext.request.serverPort }${pageContext.request.contextPath }/"/>
<!DOCTYPE html>

<html lang="en" class="app">

<head>

    <meta charset="utf-8" />

    <title>404</title>

    <meta name="description" content="app, web app, responsive, admin dashboard, admin, flat, flat ui, ui kit, off screen nav" />

    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />


    <link rel="stylesheet" href="css/404/bootstrap.css" type="text/css" />

    <link rel="stylesheet" href="css/404/animate.css" type="text/css" />

    <link rel="stylesheet" href="css/404/font-awesome.min.css" type="text/css" />

    <link rel="stylesheet" href="css/404/simple-line-icons.css" type="text/css" />

    <link rel="stylesheet" href="css/404/font.css" type="text/css" />

    <link rel="stylesheet" href="css/404/app.css" type="text/css" />

    <!--[if lt IE 9]>

    <script src="js/ie/html5shiv.js"></script>

    <script src="js/ie/respond.min.js"></script>

    <script src="js/ie/excanvas.js"></script>

    <![endif]-->

</head>

<body class="bg-light dk">

<section id="content">

    <div class="row m-n">

        <div class="col-sm-4 col-sm-offset-4">

            <div class="text-center m-b-lg">

                <h1 class="h text-white animated fadeInDownBig">404</h1>

            </div>

            <div class="list-group auto m-b-sm m-b-lg">

                <a href="javascript:history.go(-1)" class="list-group-item">

                    <%--<i class="fa fa-chevron-right icon-muted"></i>--%>

                    <%--<i class="fa fa-fw fa-home icon-muted"></i>--%> 返回

                </a>

                <%--<a href="#" class="list-group-item">--%>

                    <%--&lt;%&ndash;<i class="fa fa-chevron-right icon-muted"></i>&ndash;%&gt;--%>

                    <%--&lt;%&ndash;<i class="fa fa-fw fa-question icon-muted"></i>&ndash;%&gt;--%>

                <%----%>
                <%--</a>--%>

                <a href="#" class="list-group-item">

                    <%--<i class="fa fa-chevron-right icon-muted"></i>--%>

                    <span class="badge bg-info lt">021-215-584</span>

                    <%--<i class="fa fa-fw fa-phone icon-muted"></i> --%>
                    联系我们

                </a>

            </div>

        </div>

    </div>

</section>

<!-- footer -->

<footer id="footer">

    <div class="text-center padder clearfix">

        <p>

            <small>Web app framework base on Bootstrap<br>&copy; 2014</small>

        </p>

    </div>

</footer>

<!-- / footer -->

<script src="js/jquery.min.js"></script>

<!-- Bootstrap -->

<script src="js/bootstrap.js"></script>

<!-- App -->

<script src="js/app.js"></script>

<script src="js/slimscroll/jquery.slimscroll.min.js"></script>

<script src="js/app.plugin.js"></script>

<script type="text/javascript" src="js/jPlayer/jquery.jplayer.min.js"></script>

<script type="text/javascript" src="js/jPlayer/add-on/jplayer.playlist.min.js"></script>

<script type="text/javascript" src="js/jPlayer/demo.js"></script>



</body>

</html>
