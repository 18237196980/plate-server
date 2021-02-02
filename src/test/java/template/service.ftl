package ${servicePackage};


import ${modelPackage}.${modelName};
import ${mapperPackage}.${modelName}Mapper;
import ${BaseCRUDService};
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ${modelName}Service
 */
@Service
@Transactional(readOnly = true)
public class ${modelName}Service extends BaseCRUDService<${modelName}Mapper, ${modelName}> {

}
