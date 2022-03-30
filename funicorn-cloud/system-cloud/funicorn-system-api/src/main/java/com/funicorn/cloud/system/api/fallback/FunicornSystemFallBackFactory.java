package com.funicorn.cloud.system.api.fallback;

import com.funicorn.basic.common.base.model.Result;
import com.funicorn.cloud.system.api.dto.SysLogDTO;
import com.funicorn.cloud.system.api.model.UploadFileData;
import com.funicorn.cloud.system.api.service.FunicornSystemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Aimee
 * @since 2021/10/19 11:01
 */
@Slf4j
@Component
public class FunicornSystemFallBackFactory implements FallbackFactory<FunicornSystemService> {
    @Override
    public FunicornSystemService create(Throwable cause) {
        return new FunicornSystemService() {
            @Override
            public Result<?> saveOpsLog(SysLogDTO sysLogDTO) {
                log.error("保存日志失败",cause);
                return null;
            }

            @Override
            public Result<UploadFileData> upload(MultipartFile file, String bucketPrefix, boolean downFlag) {
                log.error("文件上传失败",cause);
                return Result.error("文件上传失败");
            }

            @Override
            public Result<List<UploadFileData>> multiUpload(MultipartFile[] files, String bucketPrefix, boolean downFlag) {
                log.error("文件上传失败",cause);
                return Result.error("文件上传失败");
            }
        };
    }
}
