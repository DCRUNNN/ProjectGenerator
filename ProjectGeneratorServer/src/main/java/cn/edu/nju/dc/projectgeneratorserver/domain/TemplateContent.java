package cn.edu.nju.dc.projectgeneratorserver.domain;

import java.util.List;
import lombok.Data;

/**
 * @author dc
 * @date 2021/12/12 17:07
 */
@Data
public class TemplateContent {

    private int id;

    private String name;

    // fileType 1=文件夹, 2=文本文件
    private int fileType;

    private String language;

    private String content;

    private List<TemplateContent> children;

    public boolean isTextFile() {
        return this.fileType == 2;
    }

    public boolean isDirectory() {
        return this.fileType == 1;
    }
}
