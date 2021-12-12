package cn.edu.nju.dc.projectgeneratorserver.service.impl;

import cn.edu.nju.dc.projectgeneratorserver.service.FileService;
import cn.edu.nju.dc.projectgeneratorserver.support.exception.ServiceException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

/**
 * @author dc
 * @date 2021/12/12 22:53
 */
@Service
public class FileServiceImpl implements FileService {

    @Override
    public Resource loadFileAsResource(String fileName) {
        // TODO 优化
        Path zipFile = Paths.get(System.getProperty("java.io.tmpdir")).resolve(fileName).normalize();
        try {
            // TODO 安全控制
            Resource resource = new UrlResource(zipFile.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new ServiceException("File not found " + zipFile);
            }
        }
        catch (MalformedURLException ex) {
            throw new ServiceException("File not found " + zipFile, ex);
        }
    }
}
