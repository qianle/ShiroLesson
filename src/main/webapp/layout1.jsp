<!DOCTYPE html>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>layout.html</title>

<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="this is my page">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<!--<link rel="stylesheet" type="text/css" href="./styles.css">-->
<link rel="stylesheet" type="text/css" href="themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="themes/icon.css">
<style type="text/css">
body {
	font-size: 15px;
}
</style>
<script type="text/javascript" src="jquery.min.js"></script>
<script type="text/javascript" src="jquery.easyui.min.js"></script>
<script type="text/javascript">
	function urlClick(myTitle,myUrl) {
		//判断title='菜品管理'tab页是否 
		var ifExist = $("#myTabs").tabs("exists",myTitle);
		if(!ifExist){
		
			$("#myTabs")
				.tabs(
						"add",
						{
							title : myTitle,
							closable : true,
							content : '<iframe width="100%" height="100%" frameborder=0 scrolling="no" src="'+myUrl+'" style="display: block"></iframe>'
						});
		}else{
			$("#myTabs").tabs("select",myTitle);
		}
	}
</script>
<!--<link rel="stylesheet" type="text/css" href="./styles.css">-->

</head>

<body style="margin: 0px;padding: 0px;">
	<div class="easyui-layout" data-options="fit:true">
		<!-- 北部  只能设置高度 一般不会设置宽度 -->
		<div data-options="region:'north'"
			style="height:15%;background-image:url('img/b.gif');background-repeat: no-repeat;background-size:100px 131px;">
			<div style="height: 60%"></div>
			<div style="text-align: right;width: 80%;">
				<a href="">退出</a>
			</div>
		</div>
		<div data-options="region:'west',split:true" title="导航菜单"
			style="width:15%;">
			<div class="easyui-accordion" style="width:500px;height:300px;">
				<div title="权限管理" data-options=""
					style="overflow:auto;padding:10px;">
					<c:forEach var="menu" items="${requestScope.menuList}">
					
					<a href="javascript:urlClick('${menu.menuName}','${pageContext.request.contextPath}${menu.menuUrl }')"
						style="text-decoration: none;color:black"><img alt=""
						src="themes/icons/tip.png">学生管理</a><br/>
					</c:forEach>

				</div>
				<div title="系统设置" data-options="" style="padding:10px;"></div>

			</div>
		</div>
		<div data-options="region:'center',iconCls:'icon-ok'" style="height:85% ">
			<div id="myTabs" class="easyui-tabs" style="width:100%;height:100%;">
				<div title="欢迎使用" style="padding:10px"></div>
			</div>
		</div>
	</div>
</body>
</html>
