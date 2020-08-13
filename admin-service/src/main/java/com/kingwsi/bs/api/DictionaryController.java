package com.kingwsi.bs.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kingwsi.bs.entity.dictionary.DictionaryVO;
import com.kingwsi.bs.service.DictionaryService;
import com.kingwsi.bs.common.bean.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * description: DictionaryController <br>
 * date: 2020/8/6 11:04 <br>
 * author: ws <br>
 * version: 1.0 <br>
 */
@Api(tags = "字典管理")
@RestController
@RequestMapping("/api/dictionary")
public class DictionaryController {

    private final DictionaryService dictionaryService;

    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @PutMapping
    @ApiOperation("更新字典")
    public ResponseData updateById(@RequestBody DictionaryVO dictionaryVO) {
        if (StringUtils.isEmpty(dictionaryVO.getId())) {
            ResponseData.FAIL("ID不能为空");
        }
        boolean result = dictionaryService.updateById(dictionaryVO);
        return result ? ResponseData.OK() : ResponseData.FAIL();
    }

    @PostMapping
    @ApiOperation("新增字典")
    public ResponseData create(@RequestBody DictionaryVO dictionaryVO) {
        boolean result = dictionaryService.create(dictionaryVO);
        return result ? ResponseData.OK() : ResponseData.FAIL();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除字典")
    public ResponseData deleteById(@PathVariable String id) {
        if (StringUtils.isEmpty(id)) {
            ResponseData.FAIL("ID不能为空");
        }
        boolean result = dictionaryService.removeById(id);
        return result ? ResponseData.OK() : ResponseData.FAIL();
    }

    @GetMapping("/page")
    @ApiOperation("获取字典分页")
    public ResponseData listOfPage(Page<DictionaryVO> page, DictionaryVO vo) {
        IPage<DictionaryVO> pageInfo = dictionaryService.listOfPage(page, vo);
        return ResponseData.OK(pageInfo);
    }
}
