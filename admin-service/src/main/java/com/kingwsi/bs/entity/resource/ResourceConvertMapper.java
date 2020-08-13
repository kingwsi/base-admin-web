package com.kingwsi.bs.entity.resource;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ResourceConvertMapper {
    Resource routeToResource(RouteVO vo);

    Resource resourceVOToResource(ResourceVO vo);

    List<ResourceVO> toResourceVOs(List<Resource> resources);
}
