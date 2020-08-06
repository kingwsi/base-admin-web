package ${package.Entity};

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ${entity}ConvertMapper {
    ${entity} ${entity}VoTo${entity}(${entity}VO vo);

    ${entity}VO ${entity}To${entity}VO(${entity}VO vo);
}