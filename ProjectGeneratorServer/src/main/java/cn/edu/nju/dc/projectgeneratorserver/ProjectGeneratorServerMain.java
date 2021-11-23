package cn.edu.nju.dc.projectgeneratorserver;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author dc
 * @date 2019/12/16 23:18
 */
@EnableEncryptableProperties
@SpringBootApplication
public class ProjectGeneratorServerMain {

    public static void main(String[] args) {
        SpringApplication.run(ProjectGeneratorServerMain.class, args);
    }
}
