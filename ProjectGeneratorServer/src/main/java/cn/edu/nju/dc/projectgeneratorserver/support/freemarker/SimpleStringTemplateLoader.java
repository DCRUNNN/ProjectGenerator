package cn.edu.nju.dc.projectgeneratorserver.support.freemarker;

import freemarker.cache.TemplateLoader;
import java.io.Reader;
import java.io.StringReader;

/**
 * @author dc
 * @date 2021/12/12 21:03
 *
 * Freemarker TemplateLoader，实现字符串渲染
 */
public class SimpleStringTemplateLoader implements TemplateLoader {

    @Override
    public Object findTemplateSource(String name) {
        return name;
    }

    @Override
    public long getLastModified(Object templateSource) {
        return System.currentTimeMillis();
    }

    @Override
    public Reader getReader(Object templateSource, String encoding) {
        return new StringReader((String)templateSource);
    }

    @Override
    public void closeTemplateSource(Object templateSource) {
        // ignore
    }
}
