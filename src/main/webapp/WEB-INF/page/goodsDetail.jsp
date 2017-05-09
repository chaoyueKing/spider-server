<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="stringUtils" uri="/WEB-INF/tld/stringUtils.tld"%>

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
<title>${goodsInfo.goodsName}</title>
<!-- Set render engine for 360 browser -->
<meta name="renderer" content="webkit">
<link rel="stylesheet" href="http://cdn.amazeui.org/amazeui/2.5.0/css/amazeui.css">
</head>
<body>
	<header data-am-widget="header" class="am-header am-header-default">
		<div class="am-header-left am-header-nav">
			<a href="javascript:history.go(-1)" class=""> <span class="am-header-nav-title"> 返回</span>
			</a>
		</div>

		<h1 class="am-header-title">${goodsInfo.goodsName}</h1>
		
	<!-- 
		<div class="am-header-right am-header-nav">
			<a href="#right-link" class=""> <span class="am-header-nav-title"> 菜单 </span> <i class="am-header-icon am-icon-bars"></i>
			</a>
		</div>
	 -->
	</header>

	<table class="am-table am-table-bordered am-table-striped am-table-compact">
		    <thead>
		        <tr >
		            <th >描述</th>
		            <th>信息</th>
		        </tr>
		    </thead>
		    <tbody>
		        <tr>
		            <td>产品名</td>
		            <td>${goodsInfo.goodsName}</td>
		        </tr>
		       	<tr>
		            <td>来源平台</td>
		            <td>${goodsInfo.goodSource}</td>
		        </tr>
		        <tr>
		            <td>店铺名称</td>
		            <td>${goodsInfo.shopName}</td>
		        </tr>
		        <tr>
		            <td>商品原价</td>
		            <td>￥${goodsInfo.goodOrgPrice}</td>
		        </tr>
		        <tr>
		            <td>折扣价</td>
		            <td>￥${goodsInfo.nowPrice}</td>
		        </tr>
		         <tr>
		            <td>商品原链接</td>
		            <td>
		            	<div style="width:100%" class="am-text-truncate">
		            		${goodsInfo.pageUrl}		            	
		            	</div>
		            </td>
		        </tr>
		         <tr>
		            <td>抓取链接</td>
		            <td>
		            	<div style="width:100%" class="am-text-truncate">
		            	${goodsInfo.detailPage}
		            	</div>
		            </td>
		        </tr>
		         <tr>
		            <td>列表图片</td>
		            <td><img src="${goodsInfo.goodsImg}" alt="..." class="am-img-thumbnail am-circle" style="max-width: 40%"></td>
		        </tr>
		         <tr>
		            <td>略缩图片</td>
		            <td>
		            	<c:forEach items='${stringUtils:split(goodsInfo.goodsThumPics, ",")}' var="d">
		            		<img src="${d}" class="am-img-thumbnail am-circle" style="max-width: 20%;">
		            	</c:forEach>
		            </td>
		        </tr>
		         <tr>
		            <td>展示图片</td>
		            <td>
		            	<c:forEach items='${stringUtils:split(goodsInfo.goodsShowPics, ",")}' var="d">
		            		<img src="${d}" class="am-img-thumbnail am-circle" style="max-width: 10%; ">
		            	</c:forEach>
		            </td>
		        </tr>
		        
		        <tr>
		            <td>商品属性</td>
		            <td>
		            	${goodsInfo.goodsAttribute}
		            </td>
		        </tr>
		    </tbody>
		</table>
	  	
	  	<article class="am-article">
		  <div class="am-article-bd">
		    <p class="am-article-lead">${goodsInfo.goodsDesc}</p>
		  </div>
		</article>

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
	
	
</body>
</html>