package cn.edu.nju.dc.projectgeneratorserver.support.freemarker;

import static org.junit.Assert.assertEquals;

import com.google.common.collect.Maps;
import java.util.Map;
import org.junit.Test;

/**
 * @author dc
 * @date 2021/12/12 20:55
 */
public class FreemarkerUtilTest {

    @Test
    public void renderInMemory() throws Exception {
        String template = "哈哈 is ${projectName}";
        Map<String, Object> attrMap = Maps.newHashMap();
        attrMap.put("projectName", "generator");
        String renderResult = FreemarkerUtil.getInstance().renderInMemory(template, attrMap);
        assertEquals("哈哈 is generator", renderResult);
    }
}