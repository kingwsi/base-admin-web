package com.kingwsi.bs.entity.resource;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResourceConvertMapper {
    Resource routeToResource(RouteVO vo);
}
