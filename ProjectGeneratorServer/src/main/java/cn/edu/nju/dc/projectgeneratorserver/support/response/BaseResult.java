package cn.edu.nju.dc.projectgeneratorserver.support.response;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dc
 * @date 2019/12/17 23:11
 *
 * 后端给前端的统一返回体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResult implements Serializable {

    private static final long serialVersionUID = 1108845534729207971L;

    private Integer returnCode;

    private String message;

    private Object data;

    private BaseResult(final Builder builder) {
        this.returnCode = builder.returnCode;
        this.message = builder.message;
        this.data = builder.data;
    }

    public static class Builder {

        private Integer returnCode;

        private String message;

        private Object data;

        public Builder(final Integer returnCode) {
            this.returnCode = returnCode;
        }

        public Builder message(final String message) {
            this.message = message;
            return this;
        }

        public Builder data(final Object data) {
            this.data = data;
            return this;
        }

        public BaseResult build() {
            return new BaseResult(this);
        }
    }
}
