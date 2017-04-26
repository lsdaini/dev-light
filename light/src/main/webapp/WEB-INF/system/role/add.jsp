<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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

<title>添加角色 - 管理员管理</title>
</head>
<body>
<article class="page-container">
	<form method="post" class="form form-horizontal" id="form-role-add">
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>角色名称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="" placeholder="" id="name" name="name">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">描述：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="" placeholder="" id="description" name="description">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">权限菜单：</label>
			<div id="menus" class="formControls col-xs-8 col-sm-9">
				<c:forEach var="menu" items="${parentList }">
					<dl class="permission-list">
						<dt>
							<label>
								<input type="checkbox" value="${menu.id }">
								${menu.name }
							</label>
						</dt>
						<c:if test="${!empty menu.childs }">
							<dd>
								<c:forEach var="child" items="${menu.childs }">
									<dl class="cl permission-list2">
										<dt>
											<label class="">
												<input type="checkbox" value="${child.id }">
												${child.name }
												</label>
										</dt>
									</dl>
								</c:forEach>
							</dd>
						</c:if>
					</dl>
				</c:forEach>
			</div>
		</div>
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
				<button type="submit" class="btn btn-success radius"><i class="icon-ok"></i> 确定</button>
			</div>
		</div>
	</form>
</article>

<jsp:include page="../../../include/js.jsp"></jsp:include>
<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript">
$(function(){
	$(".permission-list dt input:checkbox").click(function(){
		$(this).closest("dl").find("dd input:checkbox").prop("checked",$(this).prop("checked"));
	});
	$(".permission-list2 dt input:checkbox").click(function(){
		var l2=$(this).parents(".permission-list").find(".permission-list2 dt").find("input:checked").length;
		if($(this).prop("checked")){
			$(this).parents(".permission-list").find("dt").first().find("input:checkbox").prop("checked",true);
		}
		else{
			if(l2==0){
				$(this).parents(".permission-list").find("dt").first().find("input:checkbox").prop("checked",false);
			}
		}
	});
	
	$("#form-role-add").validate({
		rules:{
			name:{
				required:true
			},
			description:{
				required:true
			}
		},
		onkeyup:false,
		focusCleanup:true,
		success:"valid",
		submitHandler:function(form){
			var arry = $("#menus").find("input:checked");
			var menus = new Array();
			for(var i=0;i<arry.length;i++){
				menus[i] = $(arry[i]).val();
			}
			var param = {};
			param["name"] = $("#name").val();
			param["description"] = $("#description").val();
			param["menus"] = JSON.stringify(menus);
			$.post("roleAdd", param, function(data){
				if(data.success){
					layer.alert(data.msg,{icon: 1},function(index){
					  	var index = parent.layer.getFrameIndex(window.name);
						parent.search1();
						parent.layer.close(index);
					  	layer.close(index);
					});
				}
				else{
					layer.msg(data.msg, {
						icon : 2,
						time : 1500
					});
				}
			},"json");
		}
	});
});
</script>
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>