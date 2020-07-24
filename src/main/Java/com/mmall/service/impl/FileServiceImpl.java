package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.service.IFileService;
import com.mmall.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service("iFileService")
public class FileServiceImpl implements IFileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    public String upLoad(MultipartFile file,String path){
        String filename = file.getOriginalFilename();
        //获取文件扩展名
        String fileExtionsName = filename.substring(filename.lastIndexOf(".")+1);
        //上传的文件的名字
        String upLoadFileName = UUID.randomUUID()+ "."+fileExtionsName;
        logger.info("开始上传文件，上传的文件名:{}，上传的路径:{}，新文件名:{}",filename,path,upLoadFileName);
        File filedir = new File(path);
        if (!filedir.exists()) {
            filedir.setWritable(true);
            filedir.mkdirs();
        }
        File targetFile = new File(path,upLoadFileName);
        try {
            file.transferTo(targetFile);
            FTPUtil.upLoadFile(Lists.newArrayList(targetFile));
            targetFile.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return targetFile.getName();
    }
}
