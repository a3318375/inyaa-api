package com.inyaa.file.service.impl;

import com.inyaa.common.cache.ConfigCache;
import com.inyaa.common.constant.Constants;
import com.inyaa.common.util.FileUtil;
import com.inyaa.file.factory.UploadFileFactory;
import com.inyaa.file.service.UploadFileTemplateService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class DefaultUploadFileTemplateServiceImpl implements UploadFileTemplateService, InitializingBean {

    @Override
    public boolean doCheck(final MultipartFile file) {
        return true;
    }

    @Override
    public String doSaveFileStore(final MultipartFile file) {
        final String filePath = Constants.DEFAULT_PATH;
        final String fileName = FileUtil.createSingleFileName(file.getOriginalFilename());
        try {
            final File destFile = new File(filePath);
            if (!destFile.exists()) {
                destFile.mkdirs();
            }
            file.transferTo(new File(filePath + fileName));
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return ConfigCache.getConfig(Constants.DEFAULT_IMAGE_DOMAIN)+Constants.FILE_URL+fileName;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        UploadFileFactory.register(Constants.DEFAULT_TYPE, this);
    }


}
