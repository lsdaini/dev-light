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

<title>修改管理员 - 管理员管理</title>
</head>
<body>
<article class="page-container">
	<form method="post" class="form form-horizontal" id="form-admin-update">
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>状态：</label>
			<div class="formControls col-xs-8 col-sm-9 skin-minimal">
				<div class="radio-box">
					<input type="radio" id="enable-1" name="enable" value="1" <c:if test="${admin.enable == 1 }">checked</c:if>>
					<label for="sex-1">启用</label>
				</div>
				<div class="radio-box">
					<input type="radio" id="enable-2" name="enable" value="2" <c:if test="${admin.enable == 2 }">checked</c:if>>
					<label for="sex-2">禁用</label>
				</div>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">角色：</label>
			<div class="formControls col-xs-8 col-sm-9"> <span class="select-box" style="width:150px;">
				<select class="select" name="roleid" size="1">
					<c:forEach var="role" items="${list }">
						<option value="${role.id }" <c:if test="${admin.roleid == role.id }">selected="selected"</c:if>>${role.name }</option>
					</c:forEach>
				</select>
				</span> </div>
		</div>
		<input type="hidden" value="${admin.id }" id="id" name="id" />
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
				<button type="submit" class="btn btn-success radius"><i class="icon-ok"></i> 确定</button>
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
	
	$("#form-admin-update").validate({
		rules:{
			enable:{
				required:true
			},
			roleid:{
				required:true
			}
		},
		onkeyup:false,
		focusCleanup:true,
		success:"valid",
		submitHandler:function(form){
			$.post("adminUpdate", $(form).serialize(), function(data){
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