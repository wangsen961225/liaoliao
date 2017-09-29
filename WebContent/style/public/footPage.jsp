<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<body>
	<div class="pagination-centered" align="center">
			<div class="pagination-centered" align="center">
			  <ul class="pagination">
				<c:if test="${pageNo==1}">
				  <li class="disabled"><a href="#">首页</a></li>
				  <li class="disabled"><a href="#">上一页</a></li>
				  <c:if test="${count ==1}">
				    <li class="active"><a href="${ctx}${url}?pageNo=${pageNo}${condition}">${pageNo}</a></li>
				    <li class="disabled"><a href="#">下一页</a></li>
				    <li class="disabled"><a href="#">尾页</a></li>
				  </c:if>
				  <c:if test="${count ==2}">
				    <li class="active"><a href="${ctx}${url}?pageNo=${pageNo}${condition}">${pageNo}</a></li>
				    <li><a href="${ctx}${url}?pageNo=${pageNo+1}${condition}">${pageNo+1}</a></li>
				    <li><a href="${ctx}${url}?pageNo=${pageNo+1}${condition}">下一页</a></li>
				    <li><a href="${ctx}${url}?pageNo=${count}${condition}">尾页</a></li>
				  </c:if>
				  <c:if test="${count == 3}">
				    <li class="active"><a href="${ctx}${url}?pageNo=${pageNo}${condition}">${pageNo}</a></li>
				    <li><a href="${ctx}${url}?pageNo=${pageNo+1}${condition}">${pageNo+1}</a></li>
				    <li><a href="${ctx}${url}?pageNo=${pageNo+2}${condition}">${pageNo+2}</a></li>
				    <li><a href="${ctx}${url}?pageNo=${pageNo+1}${condition}">下一页</a></li>
				    <li><a href="${ctx}${url}?pageNo=${count}${condition}">尾页</a></li>
				  </c:if>
				  <c:if test="${count == 4}">
				    <li class="active"><a href="${ctx}${url}?pageNo=${pageNo}${condition}">${pageNo}</a></li>
				    <li><a href="${ctx}${url}?pageNo=${pageNo+1}${condition}">${pageNo+1}</a></li>
				    <li><a href="${ctx}${url}?pageNo=${pageNo+2}${condition}">${pageNo+2}</a></li>
				    <li><a href="${ctx}${url}?pageNo=${pageNo+3}${condition}">${pageNo+3}</a></li>
				    <li><a href="${ctx}${url}?pageNo=${pageNo+1}${condition}">下一页</a></li>
				    <li><a href="${ctx}${url}?pageNo=${count}${condition}">尾页</a></li>
				  </c:if>
				  <c:if test="${count >= 5}">
				    <li class="active"><a href="${ctx}${url}?pageNo=${pageNo}${condition}">${pageNo}</a></li>
				    <li><a href="${ctx}${url}?pageNo=${pageNo+1}${condition}">${pageNo+1}</a></li>
				    <li><a href="${ctx}${url}?pageNo=${pageNo+2}${condition}">${pageNo+2}</a></li>
				    <li><a href="${ctx}${url}?pageNo=${pageNo+3}${condition}">${pageNo+3}</a></li>
				    <li><a href="${ctx}${url}?pageNo=${pageNo+4}${condition}">${pageNo+4}</a></li>
				    <li><a href="${ctx}${url}?pageNo=${pageNo+1}${condition}">下一页</a></li>
				    <li><a href="${ctx}${url}?pageNo=${count}${condition}">尾页</a></li>
				  </c:if>
				</c:if>
				<c:if test="${pageNo==2}">
				  <li><a href="${ctx}${url}?pageNo=1${condition}">首页</a></li>
				  <li><a href="${ctx}${url}?pageNo=${pageNo-1}${condition}">上一页</a></li>
				  <li ><a href="${ctx}${url}?pageNo=${pageNo-1}${condition}">${pageNo-1}</a></li>
				  <li class="active"><a href="${ctx}${url}?pageNo=${pageNo}${condition}">${pageNo}</a></li>
				  <c:if test="${count ==2}">
				   <li class="disabled"><a href="#">下一页</a></li>
				   <li class="disabled"><a href="#">尾页</a></li>
				  </c:if>
				  <c:if test="${count ==3}">
				   <li><a href="${ctx}${url}?pageNo=${pageNo+1}${condition}">${pageNo+1}</a></li>
				   <li><a href="${ctx}${url}?pageNo=${pageNo+1}${condition}">下一页</a></li>
				   <li><a href="${ctx}${url}?pageNo=${count}${condition}">尾页</a></li>
				  </c:if>
				  <c:if test="${count ==4}">
				   <li><a href="${ctx}${url}?pageNo=${pageNo+1}${condition}">${pageNo+1}</a></li>
				   <li><a href="${ctx}${url}?pageNo=${pageNo+2}${condition}">${pageNo+2}</a></li>
				   <li><a href="${ctx}${url}?pageNo=${pageNo+1}${condition}">下一页</a></li>
				   <li><a href="${ctx}${url}?pageNo=${count}${condition}">尾页</a></li>
				  </c:if>
				  <c:if test="${count >=5}">
				   <li><a href="${ctx}${url}?pageNo=${pageNo+1}${condition}">${pageNo+1}</a></li>
				   <li><a href="${ctx}${url}?pageNo=${pageNo+2}${condition}">${pageNo+2}</a></li>
				   <li><a href="${ctx}${url}?pageNo=${pageNo+3}${condition}">${pageNo+3}</a></li>
				   <li><a href="${ctx}${url}?pageNo=${pageNo+1}${condition}">下一页</a></li>
				   <li><a href="${ctx}${url}?pageNo=${count}${condition}">尾页</a></li>
				  </c:if>
				</c:if>
				<c:if test="${pageNo==3}">
				  <li><a href="${ctx}${url}?pageNo=1${condition}">首页</a></li>
				  <li><a href="${ctx}${url}?pageNo=${pageNo-1}${condition}">上一页</a></li>
				  <li  ><a href="${ctx}${url}?pageNo=${pageNo-2}${condition}">${pageNo-2}</a></li>
				  <li><a href="${ctx}${url}?pageNo=${pageNo-1}${condition}">${pageNo-1}</a></li>
				  <li class="active"><a href="${ctx}${url}?pageNo=${pageNo}${condition}">${pageNo}</a></li>
				  <c:if test="${count ==3}">
				   <li class="disabled"><a href="#">下一页</a></li>
				   <li class="disabled"><a href="#">尾页</a></li>
				  </c:if>
				  <c:if test="${count ==4}">
				   <li><a href="${ctx}${url}?pageNo=${pageNo+1}${condition}">${pageNo+1}</a></li>
				   <li><a href="${ctx}${url}?pageNo=${pageNo+1}${condition}">下一页</a></li>
				   <li><a href="${ctx}${url}?pageNo=${count}${condition}">尾页</a></li>
				  </c:if>
				  <c:if test="${count >=5}">
				   <li><a href="${ctx}${url}?pageNo=${pageNo+1}${condition}">${pageNo+1}</a></li>
				   <li><a href="${ctx}${url}?pageNo=${pageNo+2}${condition}">${pageNo+2}</a></li>
				   <li><a href="${ctx}${url}?pageNo=${pageNo+1}${condition}">下一页</a></li>
				   <li><a href="${ctx}${url}?pageNo=${count}${condition}">尾页</a></li>
				  </c:if>
				</c:if>
				<c:if test="${pageNo >= 4 && pageNo<=(count-2)}">
				   <li><a href="${ctx}${url}?pageNo=1${condition}">首页</a></li>
				   <li><a href="${ctx}${url}?pageNo=${pageNo-1}${condition}">上一页</a></li>
				   <li ><a href="${ctx}${url}?pageNo=${pageNo-2}${condition}">${pageNo-2}</a></li>
				   <li ><a href="${ctx}${url}?pageNo=${pageNo-1}${condition}">${pageNo-1}</a></li>
				   <li class="active"><a href="${ctx}${url}?pageNo=${pageNo}${condition}">${pageNo}</a></li>
				  <c:if test="${count ==4}">
				   <li><a href="${ctx}${url}?pageNo=${pageNo+1}${condition}">下一页</a></li>
				   <li><a href="${ctx}${url}?pageNo=${count}${condition}">尾页</a></li>
				  </c:if>
				  <c:if test="${count >=5}">
				   <li><a href="${ctx}${url}?pageNo=${pageNo+1}">${pageNo+1}</a></li>
				    <li><a href="${ctx}${url}?pageNo=${pageNo+2}">${pageNo+2}</a></li>
				   <li><a href="${ctx}${url}?pageNo=${pageNo+1}${condition}">下一页</a></li>
				   <li><a href="${ctx}${url}?pageNo=${count}${condition}">尾页</a></li>
				  </c:if>
				</c:if>
				<c:if test="${pageNo >= 4 && pageNo==(count-1)}">
				   <li><a href="${ctx}${url}?pageNo=1${condition}">首页</a></li>
				   <li><a href="${ctx}${url}?pageNo=${pageNo-1}${condition}">上一页</a></li>
				   <li ><a href="${ctx}${url}?pageNo=${pageNo-2}${condition}">${pageNo-3}</a></li>
				   <li ><a href="${ctx}${url}?pageNo=${pageNo-2}${condition}">${pageNo-2}</a></li>
				   <li ><a href="${ctx}${url}?pageNo=${pageNo-1}${condition}">${pageNo-1}</a></li>
				   <li class="active"><a href="${ctx}${url}?pageNo=${pageNo}${condition}">${pageNo}</a></li>
				   <li ><a href="${ctx}${url}?pageNo=${pageNo+1}${condition}">${pageNo+1}</a></li>
				   <li><a href="${ctx}${url}?pageNo=${pageNo+1}${condition}">下一页</a></li>
				   <li><a href="${ctx}${url}?pageNo=${count}${condition}">尾页</a></li>
				</c:if>
				<c:if test="${pageNo >= 4 && pageNo==count}">
				   <li><a href="${ctx}${url}?pageNo=1${condition}">首页</a></li>
				   <li><a href="${ctx}${url}?pageNo=${pageNo-1}${condition}">上一页</a></li>
				   <li ><a href="${ctx}${url}?pageNo=${pageNo-3}${condition}">${pageNo-3}</a></li>
				   <li ><a href="${ctx}${url}?pageNo=${pageNo-2}${condition}">${pageNo-2}</a></li>
				   <li ><a href="${ctx}${url}?pageNo=${pageNo-1}${condition}">${pageNo-1}</a></li>
				   <li class="active"><a href="${ctx}${url}?pageNo=${pageNo}${condition}">${pageNo}</a></li>
				   <li class="disabled"><a href="#">下一页</a></li>
				   <li class="disabled"><a href="#">尾页</a></li>
				</c:if>
			</ul>  
		</div>
	</div>
		
</body>
</html>