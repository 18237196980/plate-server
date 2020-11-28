package ${extraPackage};

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public class ${className} {
<#list items as item>
    public static QueryWrapper<${item.modelName}> ${item.modelName}(){
        return new QueryWrapper<>();
    }

    public static LambdaQueryWrapper<${item.modelName}> ${item.modelName}Lambda(){
        return new LambdaQueryWrapper<>();
    }

</#list>
}