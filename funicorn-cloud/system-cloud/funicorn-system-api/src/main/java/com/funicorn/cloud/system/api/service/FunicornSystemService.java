package com.funicorn.cloud.system.api.service;

import com.funicorn.basic.common.base.model.Result;
import com.funicorn.cloud.system.api.dto.SysLogDTO;
import com.funicorn.cloud.system.api.fallback.FunicornSystemFallBackFactory;
import com.funicorn.cloud.system.api.model.UploadFileData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Aimee
 * @since 2021/10/19 11:01
 */

@FeignClient(name = "funicorn-system-center", fallbackFactory = FunicornSystemFallBackFactory.class)
@Import(FunicornSystemFallBackFactory.class)
public interface FunicornSystemService {

    /**
     * 保存操作日志
     * @param sysLogDTO 日志参数
     * @return Result
     * */
    @PostMapping("/SysLog/saveOpsLog")
    Result<?> saveOpsLog(@RequestBody SysLogDTO sysLogDTO);

    /**
     * 单文件上传
     * @param file 文件流
     * @param bucketName 桶名称
     * @param downFlag 是否允许其他人下载 默认false
     * @param fileLevel 文件级别 private 私有 public 公共 默认private
     * @return Result
     * */
    @PostMapping(value = "/File/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    Result<UploadFileData> upload(@RequestPart("file") MultipartFile file, @RequestParam(required = false) String bucketName,
                                  @RequestParam(required = false,defaultValue = "false") boolean downFlag,
                                  @RequestParam(required = false,defaultValue = "private") String fileLevel);

    /**
     * 多文件上传
     * @param files 文件流数组
     * @param bucketName 桶名称
     * @param downFlag 是否允许其他人下载 默认false
     * @param fileLevel 文件级别 private 私有 public 公共 默认private
     * @return Result
     * */
    @PostMapping(value = "/File/multiUpload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    Result<List<UploadFileData>> multiUpload(@RequestPart("files") MultipartFile[] files, @RequestParam(required = false) String bucketName,
                                             @RequestParam(required = false,defaultValue = "false") boolean downFlag,
                                             @RequestParam(required = false,defaultValue = "private") String fileLevel);
}
