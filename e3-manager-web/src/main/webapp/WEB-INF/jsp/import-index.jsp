<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="${pageContext.request.contextPath }/layout/inc.jsp"></jsp:include>
<script type="text/javascript">
	function importAll() {
		$.post("/index/importAll", '', function(data) {
			if (data.status == 200) {
				$.messager.alert('提示', '导入成功!');
				$('#p').progressbar('setValue', 100);
			}
		});
		$('#p').progressbar({
			value: 1
		});
		var timer = setInterval(function () {
			var value = $('#p').progressbar('getValue');
			if (value < 100){
				value+=1;
				$('#p').progressbar('setValue', value);
			} else {
				clearInterval(timer);
			}
		},500);
	}
</script>
<div style="padding: 5px">
	<a href="javascript:void(0)" class="easyui-linkbutton"
		onclick="importAll();">商品全量导入索引库</a>
</div>
<div id="p" style="width:400px;"></div>