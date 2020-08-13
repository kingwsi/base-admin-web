package com.kingwsi.bs.api;

import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.kingwsi.bs.service.CodeGeneratorService;
import com.kingwsi.bs.common.bean.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * description: GeneratorCodeController <br>
 * date: 2020/8/7 16:49 <br>
 * author: ws <br>
 * version: 1.0 <br>
 */
@Api(tags = "代码生成")
@RestController
@RequestMapping("/api/generator-code")
public class GeneratorCodeController {
    private final CodeGeneratorService codeGeneratorService;

    public GeneratorCodeController(CodeGeneratorService codeGeneratorService) {
        this.codeGeneratorService = codeGeneratorService;
    }

    @ApiOperation("获取表信息")
    @GetMapping("/info")
    public ResponseData getTableInfo() {
        List<TableInfo> tableInfo = codeGeneratorService.getTableInfo();
        return ResponseData.OK(tableInfo.get(0));
    }
}
