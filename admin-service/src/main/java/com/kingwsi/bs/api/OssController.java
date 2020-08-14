package com.kingwsi.bs.api;

import com.kingwsi.bs.common.bean.ResponseData;
import com.kingwsi.bs.service.OssService;
import io.swagger.annotations.Api;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * description: OssController <br>
 * date: 2020/8/13 15:27 <br>
 * author: ws <br>
 * version: 1.0 <br>
 */
@Api(tags = "对象存储")
@RestController
@RequestMapping("/api/oss")
public class OssController {

    private final OssService ossService;

    public OssController(OssService ossService) {
        this.ossService = ossService;
    }

    @PostMapping("/image")
    public ResponseData uploadFile(@RequestParam("file") MultipartFile file){
        String path = ossService.uploadImage(file);
        if (StringUtils.isEmpty(path)) {
            return ResponseData.FAIL();
        }
        return ResponseData.OK(path);
    }
}
