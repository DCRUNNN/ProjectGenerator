package cn.edu.nju.dc.projectgeneratorserver.constant;

/**
 * @author dc
 * @date 2019/10/6 2:47
 */
public enum LogType {
    BUSINESS {
        @Override
        public String getValue() {
            return "business";
        }
    },

    ERROR {
        @Override
        public String getValue() {
            return "error";
        }
    };

    public abstract String getValue();
}
