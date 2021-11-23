import cn.edu.nju.dc.projectgeneratorserver.ProjectGeneratorServerMain;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author dc
 * @date 2019/12/16 23:23
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProjectGeneratorServerMain.class)
public class PasswordEncryptor
{

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void encode()
    {
        final String admin = this.passwordEncoder.encode("admin123");
        final String user = this.passwordEncoder.encode("editor123");
        System.err.println("admin password = " + admin);
        System.err.println("editor password = " + user);
    }
}
