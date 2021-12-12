package cn.edu.nju.dc.projectgeneratorserver.controller;

import cn.edu.nju.dc.projectgeneratorserver.service.FileService;
import java.io.IOException;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dc
 * @date 2021/12/12 22:51
 */
@RestController
@RequestMapping(path = "/api/file")
@Slf4j
public class FileController {

    @Autowired
    private FileService fileService;

    @PreAuthorize("hasAuthority('file:download')")
    @RequestMapping(path = "/downloadFile", method = RequestMethod.GET)
    public ResponseEntity<Resource> downloadFile(@RequestParam String targetFileName,
        @RequestParam String downloadFileName, HttpServletRequest request) {
        // TODO delete zip file
        Resource resource = fileService.loadFileAsResource(targetFileName);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        }
        catch (IOException ex) {
            log.info("Could not determine file type.");
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + downloadFileName + "\"")
            .body(resource);
    }
}
