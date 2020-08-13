package com.kingwsi.bs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kingwsi.bs.entity.resource.Resource;
import com.kingwsi.bs.common.enumerate.ResourceTypeEnum;
import com.kingwsi.bs.entity.resource.ResourceQuery;
import com.kingwsi.bs.entity.resource.ResourceVO;
import com.kingwsi.bs.entity.role.RoleVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ResourceMapper extends BaseMapper<Resource> {

    @Select("SELECT * FROM sys_resources WHERE type = #{route} AND deleted = 0")
    List<ResourceVO> selectAllByType(@Param("route") ResourceTypeEnum route);

    List<Resource> selectRouteByUserId(@Param("userId") String userId);

    List<Resource> selectByUserId(@Param("userId") String userId);

    List<Resource> selectByMethodAndUserIdAndUri(@Param("method") String method, @Param("userId") String userId, @Param("uri") String uri);

    List<String> selectUriByUser(@Param("userId") String userId);

    List<Resource> selectByUser(@Param("userId") String userId);

    List<Resource> selectByUserAndMethod(@Param("userId") String userId, @Param("method") String method);

    IPage<ResourceVO> selectOfPage(Page page, @Param("query") ResourceQuery query);

    int countRepeat(ResourceVO resourceVO);
}
