package com.kingwsi.bs.entity.role;

/**
 * description: RoleConvertMapper <br>
 * date: 2020/8/10 14:51 <br>
 * author: ws <br>
 * version: 1.0 <br>
 */
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface RoleConvertMapper {
    Role toRole(RoleVO roleVO);

    List<RoleVO> rolesToRoleVOs(List<Role> roles);
}
