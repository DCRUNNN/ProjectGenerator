import cn.edu.nju.dc.projectgeneratorserver.ProjectGeneratorServerMain;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author dc
 * @date 2019/12/16 23:25
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProjectGeneratorServerMain.class)
public class JasyptStringEncryptor
{

    @Autowired()
    private StringEncryptor stringEncryptor;

    @Test
    public void encode()
    {
        final String name = stringEncryptor.encrypt("root");
        final String password = stringEncryptor.encrypt("mysql");

        System.err.println("name = " + name);
        System.err.println("password = " + password);
    }
}
