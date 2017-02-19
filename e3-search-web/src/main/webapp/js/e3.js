var E3 = E3MALL = {
	checkLogin : function() {
		var _ticket = $.cookie("E3_TOKEN");
		if (!_ticket) {
			return;
		}
		$
				.ajax({
					url : "http://localhost:8088/sso/token/" + _ticket,
					dataType : "jsonp",
					type : "GET",
					success : function(data) {
						if (data.status == 200) {
							var username = data.data.username;
							var html = username
									+ "，欢迎来到宜立方！<a href=\"http://localhost:8082/user/logout.html\" class=\"link-logout\">[退出]</a>";
							$("#loginbar").html(html);
						}
					}
				});
	}
}

$(function() {
	// 查看是否已经登录，如果已经登录查询登录信息
	E3.checkLogin();
});