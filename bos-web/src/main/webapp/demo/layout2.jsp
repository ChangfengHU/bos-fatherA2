<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/ztree/zTreeStyle.css">
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/ztree/jquery.ztree.all-3.5.js"></script>

<script type="text/javascript">
var setting = {
		data: {
			simpleData: {
				enable: true
			}
		}
	};

	var zNodes =[
	             {id:"1",pId:"0",name:"百度公司"},
	             {id:"11",pId:"1",name:"百度音乐"},
	             {id:"12",pId:"1",name:"百度视频"},
	             {id:"13",pId:"1",name:"百度图片"},
	             {id:"2",pId:"0",name:"传媒公司"},
	             {id:"21",pId:"2",name:"华宇公司"},
	             {id:"22",pId:"2",name:"华谊公司"},
	             {id:"23",pId:"2",name:"万达公司"}

	];

	$(function(){
		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	   }
	);

</script>

</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" style="height:60px;background:#B3DFDA;padding:10px">xxxx</div>
	<div class="easyui-accordion" data-options="region:'west',split:false,title:'菜单导航'" style="width:180px;">
	       <div title="基本功能">
				<!-- 菜单  -->
				<ul id="treeDemo" class="ztree"></ul>
			</div>
			<div title="系统管理">系统菜单</div>
			<div title="联系我们">联系我们</div>
	</div>
	<div data-options="region:'south',border:false" style="height:50px;background:#A9FACD;padding:10px;">south region</div>
	<div class="easyui-tabs" data-options="region:'center'">
	   <div title="取派员设置" data-options="closable:'true'">tab1 </div> 
		
		<div title="区域设置">tab2 </div> 
		
		<div title="分区设置">tab3 </div> 

	
	
	</div>
</body>

</html>