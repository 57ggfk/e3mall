<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${item.title }-宜立方</title>
<script>
	var jdpts = new Object();
	jdpts._st = new Date().getTime();
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="/css/e3.css"
	media="all" />
<link rel="stylesheet" type="text/css" href="/css/pshow.css" media="all" />
<script type="text/javascript">
	window.pageConfig = {
		compatible : true,
		product : {
			skuid : 1221882,
			name : '\u957f\u8679\uff08\u0043\u0048\u0041\u004e\u0047\u0048\u004f\u004e\u0047\uff09\u004c\u0045\u0044\u0034\u0032\u0035\u0033\u0038\u0045\u0053\u0020\u0034\u0032\u82f1\u5bf8\u0020\u7a84\u8fb9\u84dd\u5149\u004c\u0045\u0044\u6db2\u6676\u7535\u89c6\uff08\u9ed1\u8272\uff09',
			skuidkey : 'E804B1D153D29E36088A33A134D85EEA',
			href : 'http://item.jd.com/1221882.html',
			src : 'jfs/t304/157/750353441/93159/e4ee9876/54227256N20d4f5ec.jpg',
			cat : [ 737, 794, 798 ],
			brand : 20710,
			nBrand : 20710,
			tips : false,
			type : 1,
			venderId : 0,
			shopId : '0',
			TJ : '0',
			specialAttrs : [ "HYKHSP-0", "isHaveYB", "isSelfService-0",
					"isWeChatStock-0", "isCanUseJQ", "isOverseaPurchase-0",
					"YuShou", "is7ToReturn-1", "isCanVAT" ],
			videoPath : '',
			HM : '0'
		}
	};
</script>
</head>
<body version="140120">
	<script type="text/javascript">
		try {
			(function(flag) {
				if (!flag) {
					return;
				}
				if (window.location.hash == '#m') {
					var exp = new Date();
					exp.setTime(exp.getTime() + 30 * 24 * 60 * 60 * 1000);
					document.cookie = "pcm=1;expires=" + exp.toGMTString()
							+ ";path=/;domain=jd.com";
					return;
				} else {
					var cook = document.cookie.match(new RegExp(
							"(^| )pcm=([^;]*)(;|$)"));
					var flag = false;
					if (cook && cook.length > 2 && unescape(cook[2]) == "1") {
						flag = true;
					}
				}
				var userAgent = navigator.userAgent;
				if (userAgent) {
					userAgent = userAgent.toUpperCase();
					if (userAgent.indexOf("PAD") > -1) {
						return;
					}
					var mobilePhoneList = [ "IOS", "IPHONE", "ANDROID",
							"WINDOWS PHONE" ];
					for (var i = 0, len = mobilePhoneList.length; i < len; i++) {
						if (userAgent.indexOf(mobilePhoneList[i]) > -1) {
							var url = "http://m.jd.com/product/"
									+ pageConfig.product.skuid + ".html";
							if (flag) {
								pageConfig.product.showtouchurl = true;
							} else {
								window.location.href = url;
							}
							break;
						}
					}
				}
			})((function() {
				var json = {
					"6881" : 3,
					"1195" : 3,
					"10011" : 3,
					"6980" : 3,
					"12360" : 3
				};
				if (json[pageConfig.product.cat[0] + ""] == 1
						|| json[pageConfig.product.cat[1] + ""] == 2
						|| json[pageConfig.product.cat[2] + ""] == 3) {
					return false;
				} else {
					return true;
				}
			})());
		} catch (e) {
		}
	</script>
	<!-- header start -->
	<jsp:include page="commons/header.jsp" />
	<!-- header end -->
	<div class="w">
		<div class="breadcrumb">
			<strong><a href="http://channel.jd.com/electronic.html">家用电器</a></strong><span>&nbsp;&gt;&nbsp;<a
				href="http://channel.jd.com/737-794.html">大 家 电</a>&nbsp;&gt;&nbsp;<a
				href="http://list.jd.com/737-794-798.html">平板电视</a>&nbsp;&gt;&nbsp;
			</span>
			<script type="text/javascript">
				pageConfig.product.cat = [ 737, 794, 798 ];
			</script>
			<span><a href="http://www.jd.com/pinpai/798-20710.html">长虹（CHANGHONG）</a>&nbsp;&gt;&nbsp;<a
				href="http://item.jd.com/1221882.html">长虹LED42538ES</a></span>
		</div>
	</div>
	<!--breadcrumb end-->
	<div class="w">
		<div id="product-intro">
			<div id="name">
				<h1>${item.title }</h1>
				<strong>${item.sellPoint}</strong>
			</div>
			<!--name end-->
			<script type='text/javascript'>
				var warestatus = 1;
				var eleSkuIdKey = [];
			</script>
			<div class="clearfix" clstag="shangpin|keycount|product|share">
				<script type="text/javascript">
					pageConfig.product.marketPrice = '';
				</script>
				<ul id="summary">
					<li id="summary-price">
						<div class="dt">宜立方价：</div>
						<div class="dd">
							<strong class="p-price" id="jd-price">￥<fmt:formatNumber
									groupingUsed="false" maxFractionDigits="2"
									minFractionDigits="2" value="${item.price / 100 }" />
							</strong> <a id="notice-downp" href="#none" target="_blank"
								clstag="shangpin|keycount|product|jiangjia">(降价通知)</a>
						</div>
					</li>
					<li id="summary-market"><div class="dt">商品编号：</div>
						<div class="dd">
							<span>${item.id }</span>
						</div></li>
					<li id="summary-grade">
						<div class="dt">商品评分：</div>
						<div class="dd">
							<span class="star  sa0"></span> <a href="#comment"></a>
						</div>
					</li>
					<!-- 商品评分-->
					<li id="summary-stock" style="display: none;">
						<div class="dt">配&nbsp;送&nbsp;至：</div>
						<div class="dd">
							<div id="store-selector" class="">
								<div class="text">
									<div></div>
									<b></b>
								</div>
								<div class="content">
									<span class="clr"></span>
								</div>
								<div class="close"
									onclick="$('#store-selector').removeClass('hover')"></div>
							</div>
							<!--store-selector end-->
							<div id="store-prompt"></div>
							<!--store-prompt end--->
						</div> <span class="clr"></span>
					</li>
					<li id="summary-service" class="hide">
						<div class="dt">服&#x3000;&#x3000;务：</div>
						<div class="dd">由 宜立方 发货并提供售后服务。</div>
					</li>
					<li id="summary-tips" class="hide">
						<div class="dt">温馨提示：</div>
						<div class="dd"></div>
					</li>
					<li id="summary-gifts" class="hide">
						<div class="dt">赠&#x3000;&#x3000;品：</div>
						<div class="dd"></div>
					</li>
					<li id="summary-promotion-extra" class="none">
						<div class="dt">促销信息：</div>
						<div class="dd"></div>
					</li>
				</ul>
				<!--summary end-->
				<div id="brand-bar" clstag="shangpin|keycount|product|btn-coll">
					<dl class="slogens">
						<dt>宜立方·正品保证</dt>
						<dd class="fore1">
							<a target="_blank"
								href="http://help.360buy.com/help/question-67.html"><b></b><span>货到</span><span>付款</span></a>
						</dd>
						<dd class="fore2"></dd>
						<dd class="fore3">
							<a target="_blank"
								href="http://help.360buy.com/help/question-65.html"><b></b><span>满39</span><span>免运费</span></a>
						</dd>
						<dd class="fore4"></dd>
						<dd class="fore5">
							<a target="_blank"
								href="http://help.360buy.com/help/question-97.html"><b></b><span>售后</span><span>上门</span></a>
						</dd>
					</dl>
					<div class="seller hide">
						<p class="seller-link"></p>
						<p id="evaluate">
							<em class="dt">服务评价：</em> <span class="heart-white"><span
								class="heart-red h4">&nbsp;</span></span> <em class="evaluate-grade"></em>
						</p>
					</div>
				</div>
				<!--brand-bar-->
				<ul id="choose" clstag="shangpin|keycount|product|choose">
					<li id='choose-type'></li>
					<script type="text/javascript">
						var ColorSize = [ {
							"SkuId" : 1221882,
							"Size" : "",
							"Color" : ""
						} ];
					</script>
					<li id="choose-amount">
						<div class="dt">购买数量：</div>
						<div class="dd">
							<div class="wrap-input">
								<a class="btn-reduce" href="javascript:;"
									onclick="setAmount.reduce('#buy-num')">减少数量</a> <a
									class="btn-add" href="javascript:;"
									onclick="setAmount.add('#buy-num')">增加数量</a> <input
									class="text" id="buy-num" value="1"
									onkeyup="setAmount.modify('#buy-num');" />
							</div>
						</div>
					</li>
					<li id="choose-result"><div class="dt"></div>
						<div class="dd"></div></li>
					<li id="choose-btns">
						<div id="choose-btn-append" class="btn">
							<a class="btn-append " id="InitCartUrl"
								href="javascript:addCart();"
								clstag="shangpin|keycount|product|initcarturl">加入购物车<b></b></a>
						</div>
						<div id="choose-btn-easybuy" class="btn"></div>
						<div id="choose-btn-divide" class="btn"></div>
					</li>
				</ul>
				<!--choose end-->
				<span class="clr"></span>
			</div>

			<div id="preview">
				<div id="spec-n1" class="jqzoom"
					clstag="shangpin|keycount|product|spec-n1">
					<img data-img="1" width="350" height="350" src="${item.images[0]}"
						alt="${item.title}" jqimg="${item.images[0]}" />
				</div>

				<div id="spec-list" clstag="shangpin|keycount|product|spec-n5">
					<a href="javascript:;" class="spec-control" id="spec-forward"></a>
					<a href="javascript:;" class="spec-control" id="spec-backward"></a>
					<div class="spec-items">
						<ul class="lh">
							<c:forEach items="${item.images}" var="pic" varStatus="status">
								<c:choose>
									<c:when test="${status.index == 0 }">
										<li><img data-img="1" class="img-hover"
											alt="${item.title}" src="${pic}" width="50" height="50"
											data-url="${pic}"></li>
									</c:when>
									<c:otherwise>
										<li><img data-img="1" alt="${item.title}" src="${pic}"
											width="50" height="50" data-url="${pic}"></li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</ul>
					</div>
				</div>
				<div id="short-share">
					<div id="view-bigimg" class="fl">
						<b></b><a href="http://www.jd.com/bigimage.aspx?id=1221882"
							target="_blank">查看大图</a>
					</div>
					<div id="share-list" class="share-list"
						clstag="shangpin|keycount|product|share">
						<div class="share-bd">
							<em class="share-hd">分享到：</em>
							<ul class="share-list-item clearfix">
								<li><a href="javascript:;" id="site-sina" title="分享到新浪微博">新浪微博</a></li>
								<li><a href="javascript:;" id="site-qq" title="分享到给QQ好友">QQ</a></li>
								<li><a href="javascript:;" id="site-qzone" title="分享到腾讯微博">腾讯微博</a></li>
								<li><a href="javascript:;" id="site-renren" title="分享到人人网">人人网</a></li>
								<li><a href="javascript:;" id="site-kaixing" title="分享到开心网">开心网</a></li>
								<li><a href="javascript:;" id="site-douban" title="分享到豆瓣">豆瓣</a></li>
								<li><a href="javascript:;" id="site-msn" title="分享给MSN好友">MSN</a></li>
								<li><a href="javascript:;" id="site-email" title="邮件分享给好友">邮件</a></li>
							</ul>
						</div>
						<div class="share-ft">
							<b></b>
						</div>
					</div>
					<div class="clb"></div>
				</div>
			</div>
			<!--preview end-->
		</div>
		<!--product-intro end-->
	</div>
	<div class="w">
		<div class="right">
			<div id="product-detail" class="m m1" data-widget="tabs"
				clstag="shangpin|keycount|product|detail">
				<div class="mt">
					<ul class="tab">
						<li clstag="shangpin|keycount|product|pinfotab"
							data-widget="tab-item" class="curr"><a href="javascript:;">商品介绍</a></li>
						<li clstag="shangpin|keycount|product|pcanshutab"
							data-widget="tab-item"><a href="javascript:;">规格参数</a></li>
						<li clstag="shangpin|keycount|product|packlisttab"
							data-widget="tab-item"><a href="javascript:;">包装清单</a></li>
						<li clstag="shangpin|keycount|product|pingjiatab"
							data-widget="tab-item"><a href="javascript:;">商品评价</a></li>
						<li clstag="shangpin|keycount|product|psaleservice"
							data-widget="tab-item"><a href="javascript:;">售后保障</a></li>
						<li clstag="shangpin|keycount|product|zhinan"
							data-widget='tab-item'><a href='javascript:;'>京博士</a></li>
					</ul>
				</div>
				<div class="mc" data-widget="tab-content" id="product-detail-1">
					<ul class="detail-list">
						<li title="长虹LED42538ES">商品名称：长虹LED42538ES</li>
						<li>商品编号：1221882</li>
						<li>品牌：<a href="http://www.jd.com/pinpai/798-20710.html"
							target="_blank">长虹（CHANGHONG）</a></li>
						<li>上架时间：2014-09-24 15:45:26</li>
						<li>商品毛重：13.5kg</li>
						<li>商品产地：中国大陆</li>
						<li>分辨率：全高清（1920*1080）</li>
						<li>居室场景：卧室电视 ，客厅电视</li>
						<li>品类：LED电视（主流）</li>
						<li>3D：不支持</li>
						<li>功能：普通电视</li>
						<li>尺寸：40-43英寸</li>
					</ul>
					<div class="detail-correction">
						<b></b>如果您发现商品信息不准确，欢迎纠错
					</div>
					<div id="item-desc" class="detail-content">
						${item.description }</div>
				</div>
				<div class="ui-switchable-panel mc hide" data-widget="tab-content" id="product-detail-2">
					<table cellpadding="0" cellspacing="1" width="100%" border="0"
						class="Ptable">
						<tr>
							<th class="tdTitle" colspan="2">主体</th>
							<tr>
								<tr>
									<td class="tdTitle">品牌</td>
									<td>Apple</td>
								</tr>
								<tr>
									<td class="tdTitle">型号</td>
									<td>iPhone 6S</td>
								</tr>
								<tr>
									<td class="tdTitle">颜色</td>
									<td>金色</td>
								</tr>
								<tr>
									<td class="tdTitle">上市年份</td>
									<td>2015年</td>
								</tr>
								<tr>
									<td class="tdTitle">上市月份</td>
									<td>9月</td>
								</tr>
								<tr>
									<td class="tdTitle">输入方式</td>
									<td>触控</td>
								</tr>
								<tr>
									<td class="tdTitle">智能机</td>
									<td>是</td>
								</tr>
								<tr>
									<td class="tdTitle">操作系统</td>
									<td>苹果（IOS）</td>
								</tr>
								<tr>
									<td class="tdTitle">操作系统版本</td>
									<td>iOS</td>
								</tr>
								<tr>
									<td class="tdTitle">CPU品牌</td>
									<td>苹果</td>
								</tr>
								<tr>
									<td class="tdTitle">CPU说明</td>
									<td>64 位架构的 A9 芯片，嵌入式 M9 运动协处理器</td>
								</tr>
								<tr>
									<td class="tdTitle">运营商标志或内容</td>
									<td>无</td>
								</tr>
								<tr>
									<th class="tdTitle" colspan="2">网络</th>
									<tr>
										<tr>
											<td class="tdTitle">4G网络制式</td>
											<td>移动4G(TD-LTE)/联通4G(FDD-LTE)/电信4G(FDD-LTE)</td>
										</tr>
										<tr>
											<td class="tdTitle">3G网络制式</td>
											<td>移动3G(TD-SCDMA)/联通3G(WCDMA)/电信3G（CDMA2000）</td>
										</tr>
										<tr>
											<td class="tdTitle">2G网络制式</td>
											<td>移动2G/联通2G(GSM)/电信2G(CDMA)</td>
										</tr>
										<tr>
											<td class="tdTitle">网络频率</td>
											<td>FDD-LTE (频段 1, 2, 3, 4, 5, 7, 8, 12, 13, 17, 18, 19,
												20, 25, 26, 27, 28, 29)<br />TD-LTE (频段 38, 39, 40, 41)<br />TD-SCDMA
												1900 (F), 2000 (A)<br />CDMA EV-DO Rev. A (800, 1700/2100,
												1900, 2100 MHz)<br />UMTS(WCDMA)/HSPA+/DC-HSDPA (850, 900,
												1700/2100, 1900, 2100 MHz)<br />GSM/EDGE (850, 900, 1800,
												1900 MHz)
											</td>
										</tr>
										<tr>
											<th class="tdTitle" colspan="2">存储</th>
											<tr>
												<tr>
													<td class="tdTitle">机身内存</td>
													<td>16GB ROM</td>
												</tr>
												<tr>
													<td class="tdTitle">储存卡类型</td>
													<td>不支持</td>
												</tr>
												<tr>
													<th class="tdTitle" colspan="2">显示</th>
													<tr>
														<tr>
															<td class="tdTitle">屏幕尺寸</td>
															<td>4.7英寸</td>
														</tr>
														<tr>
															<td class="tdTitle">触摸屏</td>
															<td>具备 3D Touch 技术的 Retina HD 显示屏<br />1400：1 对比度
																(标准)<br />500 cd/m2 最大亮度 (标准)<br />全 sRGB 标准<br />支持广阔视角的双域像素<br />正面采用防油渍防指纹涂层<br />支持多种语言文字同时显示<br />放大显示<br />便捷访问功能
															</td>
														</tr>
														<tr>
															<td class="tdTitle">分辨率</td>
															<td>1334 x 750</td>
														</tr>
														<tr>
															<td class="tdTitle">PPI</td>
															<td>326</td>
														</tr>
														<tr>
															<th class="tdTitle" colspan="2">感应器</th>
															<tr>
																<tr>
																	<td class="tdTitle">GPS模块</td>
																	<td>支持</td>
																</tr>
																<tr>
																	<td class="tdTitle">重力感应</td>
																	<td>支持</td>
																</tr>
																<tr>
																	<td class="tdTitle">光线感应</td>
																	<td>支持</td>
																</tr>
																<tr>
																	<td class="tdTitle">距离感应</td>
																	<td>支持</td>
																</tr>
																<tr>
																	<td class="tdTitle">陀螺仪</td>
																	<td>支持</td>
																</tr>
																<tr>
																	<th class="tdTitle" colspan="2">摄像功能</th>
																	<tr>
																		<tr>
																			<td class="tdTitle">后置摄像头</td>
																			<td>1200万像素</td>
																		</tr>
																		<tr>
																			<td class="tdTitle">前置摄像头</td>
																			<td>500万像素</td>
																		</tr>
																		<tr>
																			<td class="tdTitle">闪光灯</td>
																			<td>True Tone 闪光灯</td>
																		</tr>
																		<tr>
																			<td class="tdTitle">自动对焦</td>
																			<td>Focus Pixels 自动对焦</td>
																		</tr>
																		<tr>
																			<td class="tdTitle">其他性能</td>
																			<td>全新 1200 万像素 iSight 摄像头，单个像素尺寸为 1.22 微米<br />Live
																				Photos<br />全景模式 (高达 6300 万像素)<br />自动 HDR 照片<br />曝光控制<br />连拍快照模式<br />计时模式<br />F/2.2
																				光圈<br />五镜式镜头<br />混合红外线滤镜<br />背照式感光元件<br />蓝宝石水晶镜头表面<br />自动图像防抖功能<br />优化的局部色调映射功能<br />优化的降噪功能<br />面部识别功能<br />照片地理标记功能
																			</td>
																		</tr>
																		<tr>
																			<th class="tdTitle" colspan="2">娱乐功能</th>
																			<tr>
																				<tr>
																					<td class="tdTitle">音乐播放</td>
																					<td>AAC (8 至 320 Kbps)、Protected AAC (来自
																						iTunes Store)、HE-AAC、MP3 (8 至 320 Kbps)、MP3
																						VBR、Audible (格式 2、3、4，Audible Enhanced Audio、AAX 与
																						AAX+)、Apple Lossless、AIFF 与 WAV</td>
																				</tr>
																				<tr>
																					<td class="tdTitle">视频播放</td>
																					<td>H.264 视频，最高可达 1080p，60 fps；High Profile
																						level 4.2 和 AAC-LC 音频，最高可达 160
																						Kbps，48kHz；立体声音频，文件格式为 .m4v、.mp4 和 .mov；MPEG-4
																						视频，最高可达 2.5 Mbps，640 x 480 像素，30 fps；Simple
																						Profile 和 AAC-LC 音频，每声道最高可达 160
																						Kbps，48kHz，立体声音频，文件格式为 .m4v, .mp4 和 .mov；Motion
																						JPEG (M-JPEG)，最高可达 35 Mbps，1280 x 720 像素，30
																						fps；ulaw 音频和 PCM 立体声音频，文件格式为 .avi</td>
																				</tr>
																				<tr>
																					<th class="tdTitle" colspan="2">传输功能</th>
																					<tr>
																						<tr>
																							<td class="tdTitle">Wi-Fi</td>
																							<td>支持</td>
																						</tr>
																						<tr>
																							<td class="tdTitle">蓝牙</td>
																							<td>支持</td>
																						</tr>
																						<tr>
																							<td class="tdTitle">NFC(近场通讯)</td>
																							<td>支持</td>
																						</tr>
																						<tr>
																							<td class="tdTitle">WIFI热点</td>
																							<td>支持</td>
																						</tr>
																						<tr>
																							<th class="tdTitle" colspan="2">其他</th>
																							<tr>
																								<tr>
																									<td class="tdTitle">SIM卡尺寸</td>
																									<td>Nano SIM卡</td>
																								</tr>
																								<tr>
																									<td class="tdTitle">电池类型</td>
																									<td>锂电池</td>
																								</tr>
																								<tr>
																									<td class="tdTitle">电池容量（mAh）</td>
																									<td>理论待机时间长达10天</td>
																								</tr>
																								<tr>
																									<td class="tdTitle">电池更换</td>
																									<td>不支持</td>
																								</tr>
																								<tr>
																									<td class="tdTitle">理论通话时间（小时）</td>
																									<td>3G 网络时长达 14 小时</td>
																								</tr>
																								<tr>
																									<td class="tdTitle">理论待机时间（小时）</td>
																									<td>长达 10 天 (250 小时)</td>
																								</tr>
																								<tr>
																									<td class="tdTitle">数据线</td>
																									<td>Lightning</td>
																								</tr>
																								<tr>
																									<td class="tdTitle">耳机接口</td>
																									<td>3.5mm</td>
																								</tr>
																								<tr>
																									<td class="tdTitle">机身尺寸（mm）</td>
																									<td>138.3*67.1*7.1</td>
																								</tr>
																								<tr>
																									<td class="tdTitle">机身重量（g）</td>
																									<td>143</td>
																								</tr>
																								<tr>
																									<td class="tdTitle">其他</td>
																									<td>* 要识别你的 iPhone 的型号，请参阅
																										http：//support.apple.com/kb/HT3939。4G LTE
																										Advanced 和 4G LTE 功能适用于特定国家或地区的特定运营商。有关 4G LTE
																										支持的详情，请联系你的运营商并查看 www.apple.com/iphone/LTE/。<br />1.
																										1GB = 10 亿字节； 内置主内存容量 16GB / 64GB/ 128GB
																										(依所购机型而定)；格式化之后的实际容量可能较小。<br />2.
																										实际尺寸和重量依配置和制造工艺的不同可能有所差异。<br />3. FaceTime
																										视频通话要求通话双方使用支持 FaceTime
																										的设备和无线网络连接。能否通过蜂窝网络使用此功能，取决于运营商政策；可能需要支付数据费用。<br />4.
																										Siri
																										可能仅支持部分语言或地区，并且功能可能因地区而异。需使用互联网接入。可能需要支付蜂窝网络数据费用。<br />5.
																										所有电池性能信息取决于网络设置和许多其他因素，实际结果可能有所不同。电池充电周期次数有限，最终可能需由
																										Apple 服务提供商更换。
																										电池使用时间和充电周期次数依设置和使用情况的不同而可能有所差异。详情请参阅
																										http：//www.apple.com/cn/batteries/ 和
																										http：//www.apple.com/cn/iphone/battery.html。<br />6.
																										2013 年 9 月 1 日或之后初次激活且符合条件，并兼容 iOS 9 的设备，可从
																										App Store 免费下载 iMovie、Pages、Numbers 和
																										Keynote。2014 年 9 月 1 日或之后初次激活且符合条件，并兼容 iOS 9
																										的设备，可从 App Store 免费下载 GarageBand。请参阅
																										www.apple.com/cn/ios/whats-new/ 了解兼容 iOS 9
																										的设备。下载 app 需要使用 Apple ID。<br />7.
																										推荐使用无线宽带网络；可能需要付费。<br />8. 基于接收方和 app
																										的自定义建议功能不适用于中文 (简体、繁体) 和日语。
																									</td>
																								</tr>
					</table>
				</div>
				<div class="mc  hide" data-widget="tab-content"
					id="product-detail-3">
					<div class="item-detail">液晶电视×1、底座×1、遥控器×1、使用说明书×1、电源线×1</div>
				</div>
				<div class="mc  hide" data-widget="tab-content"
					id="product-detail-4"></div>
				<div class="mc hide " data-widget="tab-content"
					id="product-detail-5">
					<div class="item-detail">
						本产品全国联保，享受三包服务，质保期为：一年质保<br />本产品提供上门安装调试、提供上门检测和维修等售后服务，自收到商品之日起，如您所购买家电商品出现质量问题，请先联系厂家进行检测
						，凭厂商提供的故障检测证明，在“我的宜立方-客户服务-返修退换货”页面提交退换申请，将有专业售后人员提供服务。宜立方承诺您：30天内可为您退货或换货，180天内无需修理直接换货，超过180天按国家三包规定享受服务。<br />您可以查询本品牌在各地售后服务中心的联系方式，<a
							class="link_1" href="http://www.changhong.com">请点击这儿查询......</a><br />
						<br />售后服务电话：400-811-1666<br />品牌官方网站：<a
							href="http://www.changhong.com" target="_blank">http://www.changhong.com</a>

					</div>
				</div>
				<div id="product-detail-6" class="mc hide" data-widget="tab-content"></div>
				<!--知识库二级标签、标题-->
				<div id="promises">
					<strong>服务承诺：</strong><br />
					宜立方向您保证所售商品均为正品行货，宜立方自营商品开具机打发票或电子发票。凭质保证书及宜立方发票，可享受全国联保服务（奢侈品、钟表除外；奢侈品、钟表由宜立方联系保修，享受法定三包售后服务），与您亲临商场选购的商品享受相同的质量保证。宜立方还为您提供具有竞争力的商品价格和<a
						href="http://www.jd.com/help/kdexpress.aspx" target="_blank">运费政策</a>，请您放心购买！
					<br /> <br />
					注：因厂家会在没有任何提前通知的情况下更改产品包装、产地或者一些附件，本司不能确保客户收到的货物与商城图片、产地、附件说明完全一致。只能确保为原厂正货！并且保证与当时市场上同样主流新品一致。若本商城没有及时更新，请大家谅解！
				</div>
				<div id="state">
					<strong>权利声明：</strong><br />宜立方上的所有商品信息、客户评价、商品咨询、网友讨论等内容，是宜立方重要的经营资源，未经许可，禁止非法转载使用。
					<p>
						<b>注：</b>本站商品信息均来自于合作方，其真实性、准确性和合法性由信息拥有者（合作方）负责。本站不提供任何保证，并不承担任何法律责任。
					</p>
				</div>
			</div>
			<!--product-detail end-->
		</div>
		<!--right end-->

		<div class="left">
			<div id="miaozhen7886" class="m">
				<a
					href="http://c.nfa.jd.com/adclick?sid=2&amp;cid=163&amp;aid=817&amp;bid=7853&amp;unit=69570&amp;advid=156740&amp;guv=&amp;url=http://sale.jd.com/act/IFkpQYSVnG1Jet.html"
					target="_blank"><img data-img="2" width="211" height="261"
					src="http://image.taotao.com/images/2014/10/23/2014102305423212301343.jpg"
					class="loading-style2"></a>
			</div>
			<div id="miaozhen7886" class="m">
				<a
					href="http://c.nfa.jd.com/adclick?sid=2&amp;cid=163&amp;aid=817&amp;bid=7853&amp;unit=69570&amp;advid=156740&amp;guv=&amp;url=http://sale.jd.com/act/IFkpQYSVnG1Jet.html"
					target="_blank"><img data-img="2" width="211" height="261"
					src="http://image.taotao.com/images/2014/10/23/2014102305423212301343.jpg"
					class="loading-style2"></a>
			</div>
		</div>
		<!--left end-->
		<span class="clr"></span>
	</div>
	<!-- footer start -->
	<jsp:include page="commons/footer.jsp" />
	<!-- footer end -->
	<script type="text/javascript" src="/js/jquery-1.6.4.js"></script>
	<script type="text/javascript" src="/js/lib-v1.js"></script>
	<script type="text/javascript" src="/js/product.js"></script>
	<script type="text/javascript" src="/js/iplocation_server.js"></script>
	<script type="text/javascript">
		function addCart(){
			var num = $("#buy-num").val();
			location.href="http://localhost:8090/cart/add/${item.id}/"+num;
		}
	</script>
</body>
</html>