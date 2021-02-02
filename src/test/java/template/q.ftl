package ${modelPackage};

import com.ex.framework.mybatisplus.LambdaQueryBuilder;
import com.ex.framework.mybatisplus.QueryBuilder;
import com.ex.framework.util.SpringUtils;
import ${daoPackage}.*;
import ${modelPackage}.*;

public class ${className} {
<#list items as item>
    public static QueryBuilder<${item.modelName}> ${item.modelName}(){
        ${item.modelName}Mapper mapper = SpringUtils.getBean(${item.modelName}Mapper.class);
        return QueryBuilder.build(mapper);
    }

    public static LambdaQueryBuilder<${item.modelName}> ${item.modelName}Lambda(){
        ${item.modelName}Mapper mapper = SpringUtils.getBean(${item.modelName}Mapper.class);
        return LambdaQueryBuilder.build(mapper);
    }

</#list>
}
