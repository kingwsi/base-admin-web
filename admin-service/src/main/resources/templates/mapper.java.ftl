package ${package.Mapper};

import ${package.Entity}.${entity};
import ${package.Entity}.${entity}VO;
import ${superMapperClassPackage};
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * ${table.comment!} Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {
    IPage<${entity}VO> selectPage(Page<${entity}VO> page, @Param("vo") ${entity}VO vo);
}
</#if>
