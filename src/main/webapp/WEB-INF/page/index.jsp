<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	int port = request.getServerPort();
	String basePath = null;
	if (port == 80) {
		basePath = request.getScheme() + "://"
				+ request.getServerName() + path + "";
	} else {
		basePath = request.getScheme() + "://"
				+ request.getServerName() + ":"
				+ request.getServerPort() + path + "";
	}
	pageContext.setAttribute("basePath", basePath);
%>
<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="description" content="">
<meta name="keywords" content="">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>Hello Amaze UI</title>
<!-- Set render engine for 360 browser -->
<meta name="renderer" content="webkit">

<link rel="stylesheet" href="http://cdn.amazeui.org/amazeui/2.5.0/css/amazeui.css">
<link rel="stylesheet" href="assets/css/app.css">
</head>
<body>
	<a href="${basePath}/goodsList/ZHE_800">看看折800的产品列表</a>
	<br />
	<a href="${basePath}/goodsList/FAN_LI">看看返利网的产品列表</a>
	<br />
	<input type="button" value="手动执行抓取折800的数据" id="forzhe800" />
	<br />
	<input type="button" value="手动执行抓取返利网的数据" id="forfanli" />
	<br /><br /><br /><br />

	<input id="fetchUrl" type="text" placeholder="请输入您想直接抓取的网络地址tmall, taobao">
	<input type="button" value="开始抓取" id="forDuxuan" />

	<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
	<script src="http://libs.baidu.com/jquery/1.11.3/jquery.min.js"></script>
	<script src="http://cdn.amazeui.org/amazeui/2.5.0/js/amazeui.widgets.helper.js"></script>
	<script src="http://cdn.amazeui.org/amazeui/2.5.0/js/amazeui.js"></script>
	<script type="text/javascript" src="${basePath}/assets/js/layer2/layer/layer.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {

			$('#forzhe800').click(function() {
				$.getJSON("${basePath}/api/schedule/begOneSchedule", {
					parasitiferCode : "ZHE_800",
					userName : "zhuxinyu"
				}, function(json) {
					alert(json.infoMsg);
				});
			});

			$('#forfanli').click(function() {
				$.getJSON("${basePath}/api/schedule/begOneSchedule", {
					parasitiferCode : "FAN_LI",
					userName : "zhuxinyu"
				}, function(json) {
					alert(json.infoMsg);
				});
			});
			
			$('#forDuxuan').click(function() {
				var fetchUrl = $.trim($('#fetchUrl').val());
				if (fetchUrl != null) {
					$.getJSON("${basePath}/api/fetchDuxuan", {
						fetchUrl : fetchUrl,
						userName : "zhuxinyu"
					}, function(json) {
						if (json.infoCode == '200') {
							var content = '';
							for (var p in json.data) {
								content = content + p + ":" + json.data[p] + ",\n\n"
							}
							
							layer.alert(content);
						} else {
							alert(json.infoMsg);
						}
					});
				}
			});
		});
	</script>
</body>
</html>