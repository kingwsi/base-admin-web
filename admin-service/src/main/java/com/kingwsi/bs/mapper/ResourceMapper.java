package com.kingwsi.bs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kingwsi.bs.entity.resource.Resource;
import com.kingwsi.bs.entity.resource.ResourceNode;
import com.kingwsi.bs.entity.resource.ResourceTypeEnum;
import com.kingwsi.bs.entity.resource.ResourceVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ResourceMapper extends BaseMapper<Resource> {

    @Select("SELECT * FROM sys_resources WHERE type = #{route}")
    List<ResourceNode> selectAllByType(@Param("route") ResourceTypeEnum route);

    List<Resource> selectRouteByUserId(@Param("userId") String userId);

    List<ResourceVO> selectWithResource();

    List<Resource> selectByUserId(@Param("userId") String userId);
}
