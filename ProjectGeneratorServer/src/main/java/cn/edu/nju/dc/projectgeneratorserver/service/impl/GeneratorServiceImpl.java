package cn.edu.nju.dc.projectgeneratorserver.service.impl;

import cn.edu.nju.dc.projectgeneratorserver.api.bean.GenerateProjectResultDTO;
import cn.edu.nju.dc.projectgeneratorserver.api.bean.ProjectDTO;
import cn.edu.nju.dc.projectgeneratorserver.api.bean.ProjectDTO.ParamValueDTO;
import cn.edu.nju.dc.projectgeneratorserver.dao.TemplateDao;
import cn.edu.nju.dc.projectgeneratorserver.dao.po.ParamPO;
import cn.edu.nju.dc.projectgeneratorserver.dao.po.TemplatePO;
import cn.edu.nju.dc.projectgeneratorserver.domain.TemplateContent;
import cn.edu.nju.dc.projectgeneratorserver.service.GeneratorService;
import cn.edu.nju.dc.projectgeneratorserver.support.exception.ServiceException;
import cn.edu.nju.dc.projectgeneratorserver.support.freemarker.FreemarkerUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dc
 * @date 2021/12/4 17:35
 */
@Service
@Slf4j
public class GeneratorServiceImpl implements GeneratorService {

    @Autowired
    private TemplateDao templateDao;

    private Set<String> supportFieldTypeSet = Sets.newHashSet("string", "number");

    @Override
    public GenerateProjectResultDTO generateProject(ProjectDTO projectDTO) {
        log.info("projectDTO is {}", projectDTO);
        TemplatePO templatePO = templateDao.getByID(projectDTO.getTemplateID());
        String contentStr = templatePO.getContent();
        boolean isArray = StringUtils.startsWith(contentStr, "[") && StringUtils.endsWith(contentStr, "]");
        if (!isArray) {
            throw new ServiceException("template content is not array type, object type is  not support yet");
        }
        List<TemplateContent> contentList = JSONObject.parseArray(contentStr, TemplateContent.class);

        String randomValue = String.valueOf(ThreadLocalRandom.current().nextInt());

        // 创建临时文件目录
        Map<String, Object> attrMap = projectDTO.getParamValueDTOList()
            .stream()
            .filter(Objects::nonNull)
            .filter(paramValueDTO -> StringUtils.isNotEmpty(paramValueDTO.getName()))
            .collect(Collectors.toMap(ParamValueDTO::getName,
                ParamValueDTO::getValue,
                (oldValue, newValue) -> newValue));
        try {
            String baseDir = String.format("ProjectGenerator%s", randomValue);
            Path projectBasePath = Files.createTempDirectory(baseDir);
            log.info("projectBasePath is {}", projectBasePath);
            generateFileRecursive(projectBasePath, contentList, attrMap);
            // 生成压缩文件
            Path targetZipFile = Files.createTempFile(baseDir, ".zip");
            createZipFile(projectBasePath, targetZipFile);
            // 删除临时文件
            FileUtils.deleteDirectory(projectBasePath.toFile());
            // 构造返回值
            GenerateProjectResultDTO resultDTO = new GenerateProjectResultDTO();
            String targetZipPath = targetZipFile.getFileName().normalize().toString();
            resultDTO.setTargetZipPath(targetZipPath);
            String downloadFileName = Optional.ofNullable(attrMap.get("projectName"))
                .map(Objects::toString)
                .filter(StringUtils::isNotBlank)
                .map(name -> name + ".zip")
                .orElse(targetZipPath);
            resultDTO.setDownloadFileName(downloadFileName);
            return resultDTO;
        }
        catch (IOException ex) {
            throw new ServiceException("create file fail", ex);
        }
    }

    private void createZipFile(Path fileToZip, Path targetZipFile) throws IOException {
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(targetZipFile))) {
            Files.walkFileTree(fileToZip, new SimpleFileVisitor<Path>() {
                // 拷贝文件
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    zipOutputStream.putNextEntry(new ZipEntry(fileToZip.relativize(file).toString()));
                    Files.copy(file, zipOutputStream);
                    zipOutputStream.closeEntry();
                    return FileVisitResult.CONTINUE;
                }

                // 支持创建空文件夹
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    zipOutputStream.putNextEntry(new ZipEntry(fileToZip.relativize(dir).toString() + "/"));
                    zipOutputStream.closeEntry();
                    return FileVisitResult.CONTINUE;
                }
            });
        }
    }

    private void generateFileRecursive(Path currentPath, List<TemplateContent> contentList, Map<String, Object> attrMap)
        throws IOException {
        // TODO 文件名称格式化，避免 ../ 等
        for (TemplateContent content : contentList) {
            // 处理文件夹
            if (content.isDirectory()) {
                // TODO 文件名支持渲染
                Path newDir = currentPath.resolve(content.getName());
                // 不存在则创建文件夹
                if (Files.notExists(newDir)) {
                    Files.createDirectory(newDir);
                }
                // 递归处理子文件、子文件夹
                if (CollectionUtils.isNotEmpty(content.getChildren())) {
                    generateFileRecursive(newDir, content.getChildren(), attrMap);
                }
            }
            // 处理文件
            if (content.isTextFile()) {
                Path textFile = currentPath.resolve(content.getName());
                // 不存在则创建文件
                if (Files.notExists(textFile)) {
                    Files.createFile(textFile);
                }
                // 渲染文件内容
                String renderContent = FreemarkerUtil.getInstance().renderInMemory(content.getContent(), attrMap);
                // 写文件内容
                Files.write(textFile, Optional.ofNullable(renderContent).orElse(StringUtils.EMPTY).getBytes());
            }
        }
    }

    @Override
    public JSONObject generateFormSchema(int templateID) {
        TemplatePO templatePO = templateDao.getByID(templateID);

        JSONObject schema = new JSONObject();
        schema.put("type", "object");
        JSONObject properties = new JSONObject();
        schema.put("properties", properties);

        // 项目名称（生成的项目的文件名）
        JSONObject nameProperty = new JSONObject();
        nameProperty.put("type", "string");
        JSONObject nameUIProperty = new JSONObject();
        nameUIProperty.put("label", "项目名称");

        JSONObject widgetConfig = new JSONObject();
        JSONObject compoundConfig = new JSONObject();
        compoundConfig.put("appendLabel", "string");
        widgetConfig.put("compound", compoundConfig);
        nameUIProperty.put("widgetConfig", widgetConfig);

        JSONObject helpObject = new JSONObject();
        helpObject.put("show", true);
        helpObject.put("text", "？");
        helpObject.put("content", "生成的项目的文件名");
        nameUIProperty.put("help", helpObject);
        nameProperty.put("ui", nameUIProperty);
        properties.put("projectName", nameProperty);

        // 项目需要填写的参数
        List<ParamPO> paramList = templatePO.getParamList();
        if (CollectionUtils.isEmpty(paramList)) {
            return schema;
        }
        paramList.stream()
            .filter(paramPO -> StringUtils.isNotBlank(paramPO.getFieldType()))
            .filter(paramPO -> supportFieldTypeSet.contains(paramPO.getFieldType()))
            .forEach(paramPO -> {
                JSONObject paramProperty = new JSONObject();
                paramProperty.put("type", paramPO.getFieldType().toLowerCase());
                JSONObject paramUIProperty = new JSONObject();
                paramUIProperty.put("label", paramPO.getNameCN());
                paramUIProperty.put("description", paramPO.getDescription());

                JSONObject paramHelpObject = new JSONObject();
                paramHelpObject.put("show", true);
                paramHelpObject.put("text", "？");
                paramHelpObject.put("content", paramPO.getDescription());

                paramUIProperty.put("help", paramHelpObject);

                JSONObject paramWidgetConfig = new JSONObject();
                JSONObject paramCompoundConfig = new JSONObject();
                paramCompoundConfig.put("appendLabel", paramPO.getFieldType());
                paramWidgetConfig.put("compound", paramCompoundConfig);
                paramUIProperty.put("widgetConfig", paramWidgetConfig);

                paramProperty.put("ui", paramUIProperty);
                properties.put(paramPO.getNameEN(), paramProperty);
            });
        return schema;
    }
}
