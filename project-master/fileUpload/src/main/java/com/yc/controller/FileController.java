package com.yc.controller;

import com.yc.payload.UploadFileResponse;
import com.yc.service.FileStorageSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * 应用模块名称<p>
 * 代码描述<p>
 * Copyright: Copyright (C) 2020 XXX, Inc. All rights reserved. <p>
 *
 * @author yuche
 * @since 2020/3/8 22:40
 */
@RestController
public class FileController {

    @Autowired
    private FileStorageSerivce fileStorageSerivce;

    //    public UploadFileResponse(String fileName, String fileDownloadUri, String fileType, long size) {
    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageSerivce.storeFile(file);
        String fileDownUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/").path(fileName).toUriString();

        return new UploadFileResponse(fileName, fileDownUri, file.getContentType(), file.getSize());
    }

}
