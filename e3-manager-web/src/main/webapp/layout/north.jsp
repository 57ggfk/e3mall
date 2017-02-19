<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" charset="utf-8">
	/**
	 * 
	 * 
	 * @requires jQuery,EasyUI,jQuery cookie plugin
	 * 
	 * 更换EasyUI主题的方法
	 * 
	 * @param themeName
	 *            主题名称
	 */
	function changeThemeFun(themeName) {
		if ($.cookie('easyuiThemeName')) {
			$('#layout_north_pfMenu').menu('setIcon', {
				target : $('#layout_north_pfMenu div[title=' + $.cookie('easyuiThemeName') + ']')[0],
				iconCls : 'emptyIcon'
			});
		}
		$('#layout_north_pfMenu').menu('setIcon', {
			target : $('#layout_north_pfMenu div[title=' + themeName + ']')[0],
			iconCls : 'tick'
		});

		var $easyuiTheme = $('#easyuiTheme');
		var url = $easyuiTheme.attr('href');
		var href = url.substring(0, url.indexOf('themes')) + 'themes/' + themeName + '/easyui.css';
		$easyuiTheme.attr('href', href);

		var $iframe = $('iframe');
		if ($iframe.length > 0) {
			for ( var i = 0; i < $iframe.length; i++) {
				var ifr = $iframe[i];
				try {
					$(ifr).contents().find('#easyuiTheme').attr('href', href);
				} catch (e) {
					try {
						ifr.contentWindow.document.getElementById('easyuiTheme').href = href;
					} catch (e) {
					}
				}
			}
		}

		$.cookie('easyuiThemeName', themeName, {
			expires : 7
		});

	};

	function logoutFun(b) {
		$.getJSON('${pageContext.request.contextPath}/userController/logout', {
			t : new Date()
		}, function(result) {
			if (b) {
				location.replace('${pageContext.request.contextPath}/');
			} else {
				$('#sessionInfoDiv').html('');
				$('#loginDialog').dialog('open');
			}
		});
	}

	function editCurrentUserPwd() {
		parent.$.modalDialog({
			title : '修改密码',
			width : 300,
			height : 250,
			href : '${pageContext.request.contextPath}/userController/editCurrentUserPwdPage',
			buttons : [ {
				text : '修改',
				handler : function() {
					var f = parent.$.modalDialog.handler.find('#editCurrentUserPwdForm');
					f.submit();
				}
			} ]
		});
	}
	function currentUserRole() {
		parent.$.modalDialog({
			title : '我的角色',
			width : 300,
			height : 250,
			href : '${pageContext.request.contextPath}/userController/currentUserRolePage'
		});
	}
	function currentUserResource() {
		parent.$.modalDialog({
			title : '我可以访问的资源',
			width : 300,
			height : 250,
			href : '${pageContext.request.contextPath}/userController/currentUserResourcePage'
		});
	}
</script>
<div id="sessionInfoDiv" style="position: absolute; right: 0px; top: 0px;" class="alert alert-info">
	<c:if test="${sessionInfo.id != null}">[<strong>${sessionInfo.name}</strong>]，欢迎你！您使用[<strong>${sessionInfo.ip}</strong>]IP登录！</c:if>
</div>
<div style="position: absolute; right: 0px; bottom: 0px;">
	<a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_pfMenu',iconCls:'cog'">更换皮肤</a> <a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_kzmbMenu',iconCls:'cog'">控制面板</a> <a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_zxMenu',iconCls:'cog'">注销</a>
</div>
<div id="layout_north_pfMenu" style="width: 120px; display: none;">
	<div onclick="changeThemeFun('default');" title="default">默认主题</div>
	<div onclick="changeThemeFun('gray');" title="gray">星空灰</div>
	<div onclick="changeThemeFun('black');" title="black">梦幻黑</div>
	<div onclick="changeThemeFun('material');" title="material">棉花糖</div>
	<div onclick="changeThemeFun('bootstrap');" title="bootstrap">布特斯穿破</div>
	<div class="menu-sep"></div>
	<div onclick="changeThemeFun('ui-cupertino');" title="ui-cupertino">蓝瘦香菇</div>
	<div onclick="changeThemeFun('ui-dark-hive');" title="ui-dark-hive">黑暗蜂巢</div>
	<div onclick="changeThemeFun('ui-pepper-grinder');" title="ui-pepper-grinder">胡椒粉</div>
	<div onclick="changeThemeFun('ui-sunny');" title="ui-sunny">夕阳晚霞</div>
	<div class="menu-sep"></div>
	<div onclick="changeThemeFun('metro');" title="metro">美俏象牙白</div>
	<div onclick="changeThemeFun('metro-blue');" title="metro-blue">美俏天空蓝</div>
	<div onclick="changeThemeFun('metro-gray');" title="metro-gray">美俏都市灰</div>
	<div onclick="changeThemeFun('metro-green');" title="metro-green">美俏典雅绿</div>
	<div onclick="changeThemeFun('metro-orange');" title="metro-orange">美俏动感橙</div>
	<div onclick="changeThemeFun('metro-red');" title="metro-red">美俏樱桃红</div>
</div>
<div id="layout_north_kzmbMenu" style="width: 100px; display: none;">
	<div onclick="editCurrentUserPwd();">修改密码</div>
	<div class="menu-sep"></div>
	<div onclick="currentUserRole();">我的角色</div>
	<div class="menu-sep"></div>
	<div onclick="currentUserResource();">我的权限</div>
</div>
<div id="layout_north_zxMenu" style="width: 100px; display: none;">
	<div onclick="logoutFun();">锁定窗口</div>
	<div class="menu-sep"></div>
	<div onclick="logoutFun();">重新登录</div>
	<div onclick="logoutFun(true);">退出系统</div>
</div>