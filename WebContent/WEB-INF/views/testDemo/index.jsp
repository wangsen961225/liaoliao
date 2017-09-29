<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/style/public/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/style/public/meta.jsp"%>
<link href="${ctx}/style/css/form.css" rel="stylesheet">
<title>${sys_title}</title>
</head>
<body>
	<div class="data_list">
		<div class="data_list_title">宿舍管理员管理</div>
		<form name="myForm" class="form-inline" method="post" action="http://127.0.0.1:8080/dormitory/dormManager?action=search">
				<button class="btn btn-success" type="button" style="margin-right: 50px;" onclick="javascript:window.location=&#39;dormManager?action=preSave&#39;">添加</button>
				<button class="btn btn-primary" type="button" style="margin-right: 50px;" onclick="javascript:window.location=&#39;dormManager?action=preSave&#39;">提交表单样式</button>
				<label for="exampleInput">输入框示例</label>
				<input type="text" class="form-control" placeholder="输入框示例" id="exampleInput" />
				<span class="data_search">
					<select class="form-control" id="searchType" name="searchType" style="width: 80px;">
					<option value="name">姓名</option>
					<option value="userName">用户名</option>
					</select>
					<input id="s_dormManagerText" name="s_dormManagerText" type="text" style="border-radius:50px" class="form-control" value="">
					<button type="submit" class="btn btn-info" onkeydown="if(event.keyCode==13) myForm.submit()">搜索</button>
				</span>
		</form>
		<div>
			<table class="table table-hover table-striped table-bordered">
				<tbody>
				<tr>
					<th>编号</th>
					<th>姓名</th>
					<th>性别</th>
					<th>电话</th>
					<th>宿舍楼</th>
					<th>用户名</th>
					<th>操作</th>
				</tr>
				<tr>
					<td>1</td>
					<td>王师傅</td>
					<td>男</td>
					<td>123</td>
					<td>1号楼</td>
					<td>dorm1</td>
					<td>
					<button class="btn btn-info" type="button" onclick="javascript:window.location=&#39;dormManager?action=preSave&amp;dormManagerId=1&#39;">修改</button>&nbsp;
					<button class="btn btn-danger" type="button" onclick="dormManagerDelete(1)">删除</button></td>
				</tr>
				<tr>
					<td>2</td>
					<td>李师傅</td>
					<td>男</td>
					<td>123</td>
					<td>2号楼</td>
					<td>dorm2</td>
					<td>
					<button class="btn btn-info" type="button" onclick="javascript:window.location=&#39;dormManager?action=preSave&amp;dormManagerId=2&#39;">修改</button>&nbsp;
					<button class="btn btn-danger" type="button" onclick="dormManagerDelete(2)">删除</button>
					</td>
				</tr>
				<tr>
					<td>3</td>
					<td>刘阿姨</td>
					<td>女</td>
					<td>123</td>
					<td>3号楼</td>
					<td>dorm3</td>
					<td>
					<button class="btn btn-info" type="button" onclick="javascript:window.location=&#39;dormManager?action=preSave&amp;dormManagerId=3&#39;">修改</button>&nbsp;
					<button class="btn btn-danger" type="button" onclick="dormManagerDelete(3)">删除</button>
					</td>
				</tr>
				<tr>
					<td>4</td>
					<td>朱师傅</td>
					<td>男</td>
					<td>123</td>
					<td>4号楼</td>
					<td>dorm4</td>
					<td>
					<button class="btn btn-info" type="button" onclick="javascript:window.location=&#39;dormManager?action=preSave&amp;dormManagerId=4&#39;">修改</button>&nbsp;
					<button class="btn btn-danger" type="button" onclick="dormManagerDelete(4)">删除</button>
					</td>
				</tr>
				<tr>
					<td>5</td>
					<td>张阿姨</td>
					<td>女</td>
					<td>123</td>
					<td>5号楼A座</td>
					<td>dorm5</td>
					<td>
					<button class="btn btn-info" type="button" onclick="javascript:window.location=&#39;dormManager?action=preSave&amp;dormManagerId=5&#39;">修改</button>&nbsp;
					<button class="btn btn-danger" type="button" onclick="dormManagerDelete(5)">删除</button>
					</td>
				</tr>
				</tbody>
			</table>
		</div>
		<div class="pagination-centered" align="center">
			<ul class="pagination">
				<li><a href="http://127.0.0.1:8080/dormitory/dormManager?page=1">首页</a></li>
				<li class="disabled"><a href="http://127.0.0.1:8080/dormitory/dormManager?action=list#">上一页</a></li>
				<li class="active"><a href="http://127.0.0.1:8080/dormitory/dormManager?action=list#">1</a></li>
				<li><a href="http://127.0.0.1:8080/dormitory/dormManager?page=2">2</a></li>
				<li><a href="http://127.0.0.1:8080/dormitory/dormManager?page=2">下一页</a></li>
				<li><a href="http://127.0.0.1:8080/dormitory/dormManager?page=2">尾页</a></li>
			</ul>
		</div>
	</div>
</body>
</html>