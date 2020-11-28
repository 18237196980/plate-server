<#noparse>
[#ftl]
[#assign ctx=request.contextPath /]
[#assign assets="${ctx}/assets" /]
[#assign biz="${ctx}/biz" /]
<!DOCTYPE html>
<html class="bg-white">
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
<body>
</#noparse>
<form id="form" lay-filter="form" class="layui-form model-form">
    <#list fields as f>
        <#if f.name=='id'>
    <input name="id" type="hidden"/>
        <#else>
    <div class="layui-form-item">
          <label class="layui-form-label">${f.comment}</label>
          <div class="layui-input-block">
               <input id="${f.name}" name="${f.name}" placeholder="请输入${f.comment}" type="text" class="layui-input" maxlength="50"
                           lay-verify="required" required/>
          </div>
    </div>
        </#if>
    </#list>

    <div class="layui-form-item text-right">
        <button class="layui-btn layui-btn-primary" type="button" ew-event="closeDialog">取消</button>
        <button class="layui-btn" lay-filter="btnSubmit" lay-submit>保存</button>
    </div>
</form>

<!-- js部分 -->
[#include "/layout/js.ftl"]
<script type="text/javascript">

    var url = "<#noparse>${ctx}</#noparse>/${t.name}/create";

    $(function () {
        init();
    });

    function init() {

        ex.form.onSubmitEvent("btnSubmit", function (data) {
            layui.layer.load(2);
            ex.ajax.postJson(url, data.field, function (result) {
                layui.layer.closeAll('loading');
                if (result.code == 1) {
                    top.layer.msg("添加成功", {icon: 1});
                    ex.common.putTempData("isOk", true);
                    // 关闭当前iframe弹出层
                    parent.layer.close(parent.layer.getFrameIndex(window.name));
                } else {
                    top.layer.msg(result.message);
                }
            });
            return false;
        });
    }

</script>

</body>