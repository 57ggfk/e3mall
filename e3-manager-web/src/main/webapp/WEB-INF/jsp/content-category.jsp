<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="${pageContext.request.contextPath }/layout/inc.jsp"></jsp:include>
<div>
	 <ul id="contentCategory" class="easyui-tree">
    </ul>
</div>
<div id="contentCategoryMenu" class="easyui-menu" style="width:120px;" data-options="onClick:menuHandler">
    <div data-options="iconCls:'icon-add',name:'add'">添加</div>
    <div data-options="iconCls:'icon-remove',name:'rename'">重命名</div>
    <div class="menu-sep"></div>
    <div data-options="iconCls:'icon-remove',name:'delete'">删除</div>
</div>
<script type="text/javascript">
    var $tree = $("#contentCategory");
$(function(){
	$("#contentCategory").tree({
		url : '/contentCategory/list',
		animate: true,
		method : "GET",
		onContextMenu: function(e,node){
            e.preventDefault();
            $(this).tree('select',node.target);
            $('#contentCategoryMenu').menu('show',{
                left: e.pageX,
                top: e.pageY
            });
            //强制展开父节点
            if (!$(this).tree("isLeaf",node.target)) {
                if (node.state == "closed") {
                    $(this).tree("expand",node.target);
                }
            }
        },
        onClick : function(node) {
            $tree.tree("toggle",node.target);
        },
        //当光标离开编辑框，自动触发此方法
        onAfterEdit : function(node){
        	//把新建节点变成jquery对象
        	var _tree = $(this);
        	if(node.id == 0){
        		// 新增节点
        		$.post("/contentCategory/create",{parentId:node.parentId,name:node.text},function(data){
        			if(data.status == 200){
        				_tree.tree("update",{
            				target : node.target,
            				id : data.data.id
            			});
        			}else{
        				$.messager.alert('提示','创建'+node.text+' 分类失败!');
        			}
        		});
        	}else{
        		$.post("/contentCategory/update",{id:node.id,name:node.text},function (data) {
                    if (data.status != 200) {
                        $.messager.alert("提示","修改"+node.text+" 分类失败，"+data.msg,"warning");
                        //s_tree.tree("select",node.target).tree('beginEdit',node.target);
                        //怎么恢复编辑之前的样子
                        $tree.tree("reload");
                        //$tree.tree("reload",$tree.tree("getSelected").target);
                    }
                });
        	}
        }
	});
});
function menuHandler(item) {

	//获取整颗树dom对象
	var tree = $("#contentCategory");
	//获取树形节点选中节点对象
	var node = tree.tree("getSelected");

	//如果传递参数是add,表示是添加节点
	if(item.name === "add"){

		//调用EasyUI 的 append方法添加节点
		tree.tree('append', {
            parent: (node?node.target:null),
            data: [{
                text: '新建分类',
                id : 0,
                parentId : node.id
            }]
        });

		//获取新添加节点dom对象
		var _node = tree.tree('find',0);
		//开始编辑，鼠标离开，触发事件onAfterEdit
		tree.tree("select",_node.target).tree('beginEdit',_node.target);
	}else if(item.name === "rename"){
		tree.tree('beginEdit',node.target);
	}else if(item.name === "delete"){
		$.messager.confirm('确认','确定删除名为 '+node.text+' 的分类吗？',function(r){
			if(r){
				$.post("/contentCategory/delete/",{parentId:node.parentId,id:node.id},function(data){
                    if (data.status==200) {
                        tree.tree("remove",node.target);
                    } else {
                        $.messager.alert("提示",data.msg);
                    }
				});
			}
		});
	}
}
</script>