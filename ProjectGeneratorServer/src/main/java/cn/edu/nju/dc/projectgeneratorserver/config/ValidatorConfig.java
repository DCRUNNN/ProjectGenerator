package cn.edu.nju.dc.projectgeneratorserver.config;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 *  @author dc
 *  @date 2019/12/17 23:00
 * 参数校验 https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/#section-constraint-violation-methods
 */
@Configuration
public class ValidatorConfig {

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        final MethodValidationPostProcessor postProcessor = new MethodValidationPostProcessor();
        // 设置 validator 模式为快速失败返回
        postProcessor.setValidator(this.validatorFailFast());
        return postProcessor;
        // 默认是普通模式，会返回所有的验证不通过信息集合
        // return new MethodValidationPostProcessor();
    }

    @Bean
    public Validator validatorFailFast() {
        final ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
            .configure()
            .addProperty("hibernate.validator.fail_fast", "true")
            .buildValidatorFactory();
        return validatorFactory.getValidator();
    }
}