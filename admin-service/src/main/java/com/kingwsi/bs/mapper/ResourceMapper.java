package com.kingwsi.bs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kingwsi.bs.entity.resource.Resource;
import com.kingwsi.bs.common.enumerate.ResourceTypeEnum;
import com.kingwsi.bs.entity.resource.ResourceVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ResourceMapper extends BaseMapper<Resource> {

    @Select("SELECT * FROM sys_resources WHERE type = #{route}")
    List<ResourceVO> selectAllByType(@Param("route") ResourceTypeEnum route);

    List<Resource> selectRouteByUserId(@Param("userId") String userId);

    List<ResourceVO> selectWithResource();

    List<Resource> selectByUserId(@Param("userId") String userId);

    List<Resource> selectByMethodAndUserIdAndUri(@Param("method") String method, @Param("userId") String userId, @Param("uri") String uri);

    List<String> selectUriByUser(@Param("userId") String userId);

    List<Resource> selectByUser(@Param("userId") String userId);

    List<Resource> selectByUserAndMethod(@Param("userId") String userId, @Param("method") String method);
}
