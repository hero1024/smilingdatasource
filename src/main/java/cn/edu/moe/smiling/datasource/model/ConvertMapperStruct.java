package cn.edu.moe.smiling.datasource.model;

import cn.edu.moe.smiling.datasource.entity.KnowledgeFileEntity;
import cn.edu.moe.smiling.datasource.vo.KnowledgeFileVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 类型转换映射接口: Entity - Vo转换
 * @author songpeijiang
 * @since 2024/4/10
 */
@Mapper
public interface ConvertMapperStruct {

    ConvertMapperStruct INSTANCE = Mappers.getMapper(ConvertMapperStruct.class);

    KnowledgeFileVo knowledgeFileEntityToVo(KnowledgeFileEntity knowledgeFileEntity);

}
