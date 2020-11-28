<#noparse>
[#ftl]
[#assign ctx=request.contextPath /]
[#assign assets="${ctx}/assets" /]
[#assign biz="${ctx}/biz" /]
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title></title>
    <base href="${ctx}">
    <link rel="stylesheet" href="${assets}/libs/layui/css/layui.css"/>
    <link rel="stylesheet" href="${assets}/css/admin.css"/>
    <link rel="stylesheet" href="${biz}/css/app.css"/>
    ${csrf()}
</head>
</#noparse>
<body>

<!-- 页面加载loading -->
[#include "/layout/loading.ftl"]
<!-- 正文开始 -->
<div class="layui-fluid">
    <div class="layui-card">
        <div class="layui-card-body">

            <!-- 搜索栏 -->
            <div class="layui-form toolbar">
                <div class="layui-form-item">
                    <#list searchFields as f>
                        <div class="layui-inline">
                            <label class="layui-form-label w-auto">${f.comment}：</label>
                            <div class="layui-input-inline mr0">
                                <input id="${f.name}" class="layui-input" type="text" placeholder="请输入${f.comment}"/>
                            </div>
                        </div>
                    </#list>
                    <div class="layui-inline">
                        <button id="btnSearch" class="layui-btn icon-btn">&emsp;<i class="layui-icon">&#xe615;</i>搜索&emsp;
                        </button>
                        <button id="btnReset" class="layui-btn layui-btn-primary">&emsp;重置&emsp;</button>
                    </div>
                </div>
            </div>
            <!-- 表格 -->
            <table class="layui-table" id="table1" lay-filter="table1"></table>

        </div>
    </div>

</div>

<!-- 表格工具栏 -->
<script type="text/html" id="toolbar1">
        <div class="layui-btn-container">
            <button class="layui-btn layui-btn-sm" lay-event="add">添加</button>
        </div>
</script>

<!-- 表格操作列 -->
<script type="text/html" id="actionbar1">
        <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="edit">修改</a>
        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delete">删除</a>
</script>

<!-- js部分 -->
[#include "/layout/js.ftl"]

<script type="text/javascript">

    var tableId = "table1";
    var toolbarId = "toolbar1";
    var url = '<#noparse>${ctx}</#noparse>/${t.name}/list';
    var defaultSort = "id";
    var defaultOrder = "asc";
    var addUrl = '<#noparse>${ctx}</#noparse>/${t.name}/add';
    var editUrl = '<#noparse>${ctx}</#noparse>/${t.name}/edit?id=';
    var deleteUrl = "<#noparse>${ctx}</#noparse>/${t.name}/delete?id=";

    var columns = [[
        <#list fields as f>
            <#if f.name=='id'>
        {field: '${f.name}', title: '${f.comment}', width: 100, sort: true, hide: true},
            <#else>
        {field: '${f.name}', title: '${f.comment}'},
            </#if>
        </#list>
        {align: 'center', fixed: 'right', title: '操作', toolbar: '#actionbar1', width: 150}
    ]];

    //获取收索对象，包含排序字段
    function getSearchObject(sort, order) {
        if (!sort) {
            sort = defaultSort;
        }

        if (!order) {
            order = defaultOrder;
        }

        return {
            sort: sort,
            order: order
            <#list searchFields as f>
            ,${f.name}: $("#${f.name}").val()
            </#list>
        };
    }

    function resetSearchObject() {
        <#list searchFields as f>
        $("#${f.name}").val("");
        </#list>
    }

    //当前页面初始化动作
    $(function () {
        initTable();

        $("#btnSearch").click(function () {
            var searchObj = getSearchObject();
            doSearch(searchObj);
        });

        $("#btnReset").click(function () {
            resetSearchObject();
            var searchObj = getSearchObject();
            doSearch(searchObj);
        });
    });

    //点击搜索
    function doSearch(searchObj) {
        layui.table.reload(tableId, {
            initSort: {
                field: defaultSort,
                type: defaultOrder
            },
            where: searchObj,
            page: {
                curr: 1
            }
        });
    }

    //初始化表格
    function initTable() {
        var searchObj = getSearchObject();

        layui.table.render({
            elem: '#' + tableId,
            toolbar: '#' + toolbarId,
            defaultToolbar: ['filter'],
            url: url,
            initSort: {
                field: defaultSort, //排序字段，对应 cols 设定的各字段名
                type: defaultOrder //排序方式  asc: 升序、desc: 降序、null: 默认排序
            },
            where: searchObj,
            cols: columns
        });

        //监听排序事件
        ex.table.onSortEvent(tableId, function (obj) {
            var sort = obj.field;
            var order = obj.type;
            var searchObj = getSearchObject(sort, order);

            layui.table.reload(tableId, {
                initSort: obj, //记录初始排序，如果不设的话，将无法标记表头的排序状态。
                where: searchObj
            });
        });

        //监听顶部工具栏事件
        ex.table.onToolbarEvent(tableId, function (obj) {
            switch (obj.event) {
                case 'add':
                    doAdd();
                    break;
            }
        });

        //监听行工具事件
        ex.table.onToolEvent(tableId, function (obj) {
            switch (obj.event) {
                case 'edit':
                    doEdit(obj);
                    break;
                case 'delete':
                    doDelete(obj);
                    break;
            }
        });

        //监听行双击事件
        ex.table.onRowDblClickEvent(tableId, function (obj) {
            doEdit(obj);
        });
    }

    function doAdd() {
        ex.common.putTempData("isOk", false);
        top.layui.admin.open({
            type: 2,
            title: '添加',
            area: ['600px', '400px'],
            content: addUrl,
            end: function () {
                // 成功刷新表格
                if (ex.common.getTempDataAndRemove("isOk")) {
                    layui.table.reload(tableId);
                }
            }
        });
    }

    function doEdit(obj) {
        var data = obj.data;

        ex.common.putTempData("form", {isOk: false, data: null});
        top.layui.admin.open({
            type: 2,
            title: '修改',
            area: ['600px', '400px'],
            content: editUrl + data.id,
            end: function () {
                // 成功，刷新编辑行
                var result = ex.common.getTempDataAndRemove("form");
                if (result.isOk) {
                    var newModel = result.data;
                    if (newModel) {
                        obj.update(newModel);
                    } else {
                        layui.table.reload(tableId);
                    }
                }
            }
        });
    }

    function doDelete(obj) {
        var data = obj.data;
        var url = deleteUrl + data.id;

        top.layer.confirm('确定要删除吗？', function () {
            layui.layer.load(2);
            ex.ajax.post(url, function (result) {
                layui.layer.closeAll('loading');
                if (result.code == 1) {
                    top.layer.msg("删除成功", {icon: 1});
                    obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                } else {
                    top.layer.msg(result.message);
                }
            });
        });
    }

</script>
</body>
</html>
