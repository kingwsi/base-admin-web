package com.kingwsi.bs.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kingwsi.bs.entity.dictionary.Dictionary;
import com.kingwsi.bs.entity.dictionary.DictionaryVO;
import com.kingwsi.bs.mapper.DictionaryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * description: DictionaryService <br>
 * date: 2020/8/6 10:29 <br>
 * author: ws <br>
 * version: 1.0 <br>
 */
@Service
public class DictionaryService {

    @Autowired
    private DictionaryMapper dictionaryMapper;

    public IPage<Dictionary> listOfPage(Page<Dictionary> page, DictionaryVO dictionaryVO) {
        return dictionaryMapper.selectPage(page, dictionaryVO);
    }
}
