package cn.edu.nju.dc.projectgeneratorserver.service;

import org.springframework.core.io.Resource;

/**
 * @author dc
 * @date 2021/12/12 22:53
 */
public interface FileService {

    Resource loadFileAsResource(String filePath);
}
