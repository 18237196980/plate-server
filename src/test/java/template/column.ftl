package ${extraPackage};


public class Column {
<#list list as table>
    /**
    * ${table.comment}
    * 表名：${table.name}
    */
    public static class ${table.name?replace('_', ' ')?capitalize?replace(' ', '')} {

        <#list table.fields as field>
        <#if field.comment?? && field.comment!=''>
        /**
        * ${field.comment}
        */
        </#if>
        public final String ${field.name} = "${field.name}";

        </#list>
    }

</#list>
}