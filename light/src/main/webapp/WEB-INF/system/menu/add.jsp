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

<title>添加菜单 - 管理员管理</title>
</head>
<body>
<article class="page-container">
	<form class="form form-horizontal" id="form-menu-add">
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">父级菜单：</label>
		<div class="formControls col-xs-8 col-sm-9"> <span class="select-box" style="width:150px;">
			<select class="select" name="parentid" id="parentid" onchange="showLink(this.value)" size="1">
				<option value="0">顶级菜单</option>
				<c:forEach var="menu" items="${list }">
					<option value="${menu.id }">${menu.name }</option>
				</c:forEach>
			</select>
			</span> </div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>菜单名称：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" value="" placeholder="" id="name" name="name">
		</div>
	</div>
	<div class="row cl" id="showLink" style="display: none;">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>链接地址：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" value="" placeholder="" id="link" name="link">
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>状态：</label>
		<div class="formControls col-xs-8 col-sm-9 skin-minimal">
			<div class="radio-box">
				<input type="radio" id="enable-1" name="enable" value="1" checked>
				<label for="sex-1">启用</label>
			</div>
			<div class="radio-box">
				<input type="radio" id="enable-2" name="enable" value="2">
				<label for="sex-2">禁用</label>
			</div>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>显示顺序：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" value="" placeholder="" id="displayorder" name="displayorder">
		</div>
	</div>
	<div class="row cl">
		<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
			<input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
		</div>
	</div>
	</form>
</article>

<jsp:include page="/include/js.jsp"></jsp:include>
<!--请在下方写此页面业务相关的脚本--> 
<script type="text/javascript">
$(function(){
	$('.skin-minimal input').iCheck({
		checkboxClass: 'icheckbox-blue',
		radioClass: 'iradio-blue',
		increaseArea: '20%'
	});
	
	$("#form-menu-add").validate({
		rules:{
			name:{
				required:true
			},
			link:{
				required:true
			},
			enable:{
				required:true
			},
			displayorder:{
				required:true,
				digits:true
			}
		},
		onkeyup:false,
		focusCleanup:true,
		success:"valid",
		submitHandler:function(form){
			$.post("menuAdd", $(form).serialize(), function(data){
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
function showLink(field){
	if(field == 0){
		$("#showLink").hide();
	}
	else if(field > 0){
		$("#showLink").show();
	}
}
</script> 
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>