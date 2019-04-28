package com.wizz.draw.util;




import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;

import java.util.Map;
import java.util.UUID;

@PropertySource({"application.properties"})
@Component
public class OneImgUpload {

    @Value("${web.upload-path}")
    public    String filePath ;
    @Value("${web.swiper-maxsize}")
    public    int MAXSIZE ;
    @Value("${img.domain.name}")
    public String imgDomain;
    public  Object saveFile(MultipartFile file) throws Exception {

        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        long fileSize=file.getSize();
        int maxSize=this.MAXSIZE;
        if (fileSize>maxSize){
            throw new Exception("大小超过限制");
        }
        fileName = UUID.randomUUID() + suffixName;
        File dest = new File(filePath+fileName);
        file.transferTo(dest);
        Map<String ,String> data= new HashMap<String,String>();
        data.put("name",fileName);
        data.put("url",imgDomain+fileName);
        return data;
    }


}
