<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />

<jsp:include page="/include/css.jsp"></jsp:include>

<title>菜单管理</title>
</head>
<body>
	<nav class="breadcrumb">
		<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 管理员管理 <span class="c-gray en">&gt;</span> 菜单管理 <a class="btn btn-success radius r"
			style="line-height: 1.6em; margin-top: 3px" id="btn-refresh" href="javascript:;" onclick="search1()" title="刷新"><i class="Hui-iconfont">&#xe68f;</i></a>
	</nav>
	<div class="page-container">
		<div class="text-c">
			<form class="Huiform">
				<input type="text" class="input-text" style="width: 250px" placeholder="菜单名称" id="name" name="name">
				<button type="button" class="btn btn-success" id="" name="" onclick="search1()">
					<i class="Hui-iconfont">&#xe665;</i> 搜菜单节点
				</button>
			</form>
		</div>
		<div class="cl pd-5 bg-1 bk-gray mt-20">
			<span class="l"><a href="javascript:;" onclick="datadel()" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a></span>
			<span class="l"> <a href="javascript:;" onclick="menu_add('添加菜单节点','menuAddInput','','410')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 添加菜单节点</a></span>
		</div>
		<div class="mt-20">
			<table id="example" class="table table-border table-bordered table-bg table-hover table-sort">
				<thead>
					<tr class="text-c">
						<th width="25"><input type="checkbox" name="" value="" class="checkall"></th>
						<th width="80">ID</th>
						<th width="80">菜单名称</th>
						<th width="80">父级菜单名称</th>
						<th width="120">链接地址</th>
						<th width="80">显示顺序</th>
						<th width="75">状态</th>
						<th width="120">操作</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<jsp:include page="/include/js.jsp"></jsp:include>
	<script type="text/javascript">
		var table;
		$(document).ready(function() {
			table = $('#example').DataTable({
				"pagingType" : "simple_numbers",//设置分页控件的模式  
				searching : false,//屏蔽datatales的查询框  
				aLengthMenu : [ 10, 20, 30 ],//设置一页展示10条记录  
				"bLengthChange" : true,//屏蔽tables的一页展示多少条记录的下拉列表  
				"bSort": false, //设置每列不允许排序
				"oLanguage" : { //对表格国际化  
					"sLengthMenu" : "每页显示 _MENU_条",
					"sZeroRecords" : "没有找到符合条件的数据",
					"sProcessing" : "数据加载中...",
					"sInfo" : "当前第 _START_ - _END_ 条　共计 _TOTAL_ 条",
					"sInfoEmpty" : "木有记录",
					"sInfoFiltered" : "",
					"sSearch" : "搜索：",
					"oPaginate" : {
						"sFirst" : "首页",
						"sPrevious" : "前一页",
						"sNext" : "后一页",
						"sLast" : "尾页"
					}
				},
				"processing" : true, //打开数据加载时的等待效果  
				"serverSide" : true,//打开后台分页  
				"ajax" : {
					"url" : "menuList",
					"dataSrc" : "aaData",
					"type" : "post",
					"data" : function(d) {
						//添加额外的参数传给服务器  
						d.name = $('#name').val();
					}
				},
				"columns" : [ {
	                 "sClass": "text-center",
	                 "data": "ID",
	                 "render": function (data, type, full, meta) {
	                     return '<input type="checkbox"  class="checkchild"  value="' + data + '" />';
	                 },
	                 "bSortable": false
            	},{
					"data" : "id"
				}, {
					"data" : "name"
				}, {
					"data" : "parentName"
				}, {
					"data" : "link"
				}, {
					"data" : "displayorder"
				}, {
					"data" : "enable"
				} ],
				"columnDefs" : [ {
					"targets" : [ 6 ],
					"data" : "enable",
					"sClass" : "td-status",
					"render" : function(data, type, full) {
						var str = "";
						if(data == 1){
							str = "<span class='label label-success radius'>启用</span>"
						}
						if(data == 2){
							str = "<span class='label radius'>禁用</span>"
						}
						return str;
					}
				},{
					"targets" : [ 7 ],
					"data" : "id",
					"sClass" : "f-14",
					"render" : function(data, type, full) {
						var str = "<a title='编辑' style='text-decoration:none' class='ml-5' href='javascript:;' onclick='menu_edit(\"编辑菜单\",\"menuUpdateInput?id="+data+"\",800,500)'><i class='Hui-iconfont'>&#xe6df;</i></a>";
						   str += "<a title='删除' style='text-decoration:none' class='ml-5' href='javascript:;' onclick='menu_del(this,"+data+")'><i class='Hui-iconfont'>&#xe6e2;</i></a>";
						return str;
					}
				} ]
			});
		});

		function search1() {
			table.ajax.reload();
		}
		
		//刷新当前页
		function reloadCurrent(){
			table.draw(false);
		}

		/*
		 参数解释：
		 title	标题
		 url		请求的url
		 id		需要操作的数据id
		 w		弹出层宽度（缺省调默认值）
		 h		弹出层高度（缺省调默认值）
		 */
		/*管理员-菜单-添加*/
		function menu_add(title, url, w, h) {
			layer_show(title, url, w, h);
		}
		/*管理员-菜单-编辑*/
		function menu_edit(title, url, w, h) {
			layer_show(title, url, w, h);
		}

		/*管理员-菜单-删除*/
		function menu_del(obj, id) {
			layer.confirm('菜单删除须谨慎，确认要删除吗？', function(index) {
				$.post("menuDelete",{id:id},function(data){
					if(data.success){
						search1();
						layer.msg(data.msg, {
							icon : 1,
							time : 1000
						});
					}
					else{
						layer.msg(data.msg, {
							icon : 2,
							time : 1500
						});
					}
				},"json");
				
			});
		}
		
		$(function(){$(window).on("scroll",$backToTopFun);$backToTopFun();});
	</script>
</body>
</html>