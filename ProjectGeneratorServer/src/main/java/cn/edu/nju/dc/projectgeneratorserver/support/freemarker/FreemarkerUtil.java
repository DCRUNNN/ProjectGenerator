package cn.edu.nju.dc.projectgeneratorserver.support.freemarker;

import cn.edu.nju.dc.projectgeneratorserver.support.exception.ServiceException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

/**
 * @author dc
 * @date 2021/12/12 20:38
 */
public class FreemarkerUtil {

    private Configuration freemarkerConfig;

    private FreemarkerUtil() {
        this.freemarkerConfig = new Configuration(Configuration.VERSION_2_3_26);
        this.freemarkerConfig.setLocale(Locale.CHINA);
        this.freemarkerConfig.setDefaultEncoding(StandardCharsets.UTF_8.name());
        this.freemarkerConfig.setTemplateLoader(new SimpleStringTemplateLoader()); // 直接渲染字符串
        this.freemarkerConfig.setLocalizedLookup(false); // 否则渲染出来的内容会带有locale后缀
    }

    public static FreemarkerUtil getInstance() {
        return SingletonHolder.instance;
    }

    public String renderInMemory(String template, Map<String, Object> attrMap) {
        if (StringUtils.isEmpty(template)) {
            return StringUtils.EMPTY;
        }
        final StringWriter writer = new StringWriter();
        try {
            Template freemarkerTemplate = this.freemarkerConfig.getTemplate(template);
            freemarkerTemplate.process(attrMap, writer);
        }
        catch (TemplateException | IOException ex) {
            throw new ServiceException("[FreemarkerUtil] renderInMemory fail", ex);
        }
        return writer.toString();
    }

    // SingletonHolder 静态内部类实现单例
    private static class SingletonHolder {
        private static FreemarkerUtil instance = new FreemarkerUtil();
    }
}
