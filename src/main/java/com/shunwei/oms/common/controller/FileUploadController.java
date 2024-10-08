package com.shunwei.oms.common.controller;

import com.shunwei.oms.common.dto.ApiResult;
import com.shunwei.oms.common.dto.FileUploadResponse;
import com.shunwei.oms.common.service.OssService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Tag(name = "上传文件", description = "FileUpload Controller")
public class FileUploadController extends BaseController {

    @Autowired
    private OssService ossService;

    @PostMapping("/upload")
    @Operation(summary = "Upload a file to OSS",
            description = "Uploads a file to Alibaba Cloud OSS",
            responses = {
                    @ApiResponse(description = "File uploaded successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = FileUploadResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    public ApiResult<FileUploadResponse> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("filename") String filename) {
        return handleRequest(() -> {
            try {
                String fileUrl = ossService.uploadFile(filename, file.getInputStream());
                FileUploadResponse response = new FileUploadResponse(filename, fileUrl);
                return ApiResult.success(response, 200, "File uploaded successfully");
            } catch (IOException e) {
                return ApiResult.error(500, "Failed to upload file", null);
            }
        }).getBody();
    }
}
