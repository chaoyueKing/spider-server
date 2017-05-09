<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	int port = request.getServerPort();
	String basePath = null;
	if (port == 80) {
		basePath = request.getScheme() + "://" + request.getServerName() + path + "";
	} else {
		basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "";
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
<title>${siteName}产品列表</title>
<!-- Set render engine for 360 browser -->
<meta name="renderer" content="webkit">

<link rel="stylesheet" href="http://cdn.amazeui.org/amazeui/2.5.0/css/amazeui.css">
<link rel="stylesheet" href="assets/css/app.css">

</head>
<body>
	<header data-am-widget="header" class="am-header am-header-default">
		<div class="am-header-left am-header-nav">
			<a href="${basePath}" class=""> <span class="am-header-nav-title"> 首页 </span> <i class="am-header-icon am-icon-home"></i>
			</a>
		</div>

		<h1 class="am-header-title">网站${siteName}当前分类产品总量${goodsListCount} </h1>

		<div class="am-header-right am-header-nav">
			<a href="#right-link" class=""> <span class="am-header-nav-title"> </span> <i class="am-header-icon am-icon-bars"></i>
			</a>
		</div>
	</header>
	
	  <nav data-am-widget="menu" class="am-menu  am-menu-offcanvas1"  data-am-menu-offcanvas> 
	    <a href="javascript: void(0)" class="am-menu-toggle">
	          <i class="am-menu-toggle-icon am-icon-bars"></i>
	    </a>
	    <div class="am-offcanvas" >
	      <div class="am-offcanvas-bar am-offcanvas-bar-flip">
	
	      <ul class="am-menu-nav am-avg-sm-1">
	          <li class="">
	            <a href="${basePath}/goodsList/${pcode}/jujia" class="" >居家</a>
	          </li>
	          <li class="">
	            <a href="${basePath}/goodsList/${pcode}/shuma" class="" >数码</a>
	          </li>
	          <li class="">
	            <a href="${basePath}/goodsList/${pcode}/meizhuang" class="" >美妆</a>
	          </li>
	          <li class="">
	            <a href="${basePath}/goodsList/${pcode}/peishi" class="" >配饰</a>
	          </li>
	          <li class="">
	            <a href="${basePath}/goodsList/${pcode}/shipin" class="" >食品</a>
	          </li>
	      </ul>
	      </div>
	    </div>
	  </nav>
	

	<ul class="am-list am-list-border">
		<c:forEach var="c" items="${goodsList}">
			<li>
				<a href="${basePath}/goodsDetail/${c.webCode}/${c.id}"> 
					<img class="am-img-thumbnail am-circle" style="max-width: 20%; min-height: 30px" src="${basePath}/assets/js/echo/blank.gif" alt="Photo" data-echo="${c.goodsImg}"> 
					${c.goodSource} -- ${c.goodsName} 
				</a>
			</li>
		</c:forEach>
	</ul>
	
	<footer data-am-widget="footer" class="am-footer am-footer-default" data-am-footer="{}">
		<div class="am-footer-switch">
			<span class="am-footer-ysp" data-rel="mobile" data-am-modal="{target: '#am-switch-mode'}"> 手机版本</span> <span class="am-footer-divider"> | </span> <a id="godesktop"
				data-rel="desktop" class="am-footer-desktop" href="javascript:"> 电脑版 </a>
		</div>
		<div class="am-footer-miscs ">
			<p>
				信息由 <a href="http://m.qmcaifu.com/" title="上海欣亨金融信息服务有限公司" target="_blank" class="">上海欣亨金融信息服务有限公司</a>提供
			</p>
			<p>CopyRight©2016 QMCAIFU Inc.</p>
			<p>沪ICP备 15000682号-1</p>
		</div>
	</footer>

	<div id="am-footer-modal" class="am-modal am-modal-no-btn am-switch-mode-m am-switch-mode-m-default">
		<div class="am-modal-dialog">
			<div class="am-modal-hd am-modal-footer-hd">
				<a href="javascript:void(0)" data-dismiss="modal" class="am-close am-close-spin " data-am-modal-close>&times;</a>
			</div>
			<div class="am-modal-bd">
				您正在浏览的是 <span class="am-switch-mode-owner"> 云适配 </span> <span class="am-switch-mode-slogan"> 为您当前手机订制的移动网站。 </span>
			</div>
		</div>
	</div>



 <div data-am-widget="gotop" class="am-gotop am-gotop-fixed" >
    <a href="#top" title="回到顶部">
        <span class="am-gotop-title">回到顶部</span>
          <i class="am-gotop-icon am-icon-chevron-up"></i>
    </a>
  </div>
	
	<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
	<script src="http://libs.baidu.com/jquery/1.11.3/jquery.min.js"></script>
	<script src="http://cdn.amazeui.org/amazeui/2.5.0/js/amazeui.widgets.helper.js"></script>
	<script src="http://cdn.amazeui.org/amazeui/2.5.0/js/amazeui.js"></script>
	<script type="text/javascript" src="${basePath}/assets/js/echo/echo.min.js"></script>
	
</body>
</html>