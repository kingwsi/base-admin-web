package com.kingwsi.bs.entity.resource;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ResourceMapper {

    @Select("SELECT * FROM resources WHERE type = #{route}")
    List<ResourceNode> selectAllByType(@Param("route") ResourceTypeEnum route);

    List<ResourceNode> selectByUserId(@Param("userId") String userId);
}
