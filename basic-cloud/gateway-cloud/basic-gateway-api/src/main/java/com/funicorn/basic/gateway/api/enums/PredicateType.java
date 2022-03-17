package com.funicorn.basic.gateway.api.enums;

import com.funicorn.basic.common.base.util.JsonUtil;
import com.funicorn.basic.gateway.api.model.Predicate;
import com.funicorn.basic.gateway.api.util.Validator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.util.Assert;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Aimee
 * @since 2022/3/16 11:20
 */
@SuppressWarnings({"unchecked","unused"})
public enum PredicateType implements Validator<Predicate> {

    /**
     * After校验
     * */
    After {
        @Override
        public void validate(Predicate predicate) {
            Map<String, String> valueMap = JsonUtil.json2Object(predicate.getValue(),Map.class);
            normalCheck(valueMap,"datetime");
            String dateTimeStr = valueMap.get("datetime");
            try {
                ZonedDateTime.parse(dateTimeStr);
            } catch (Exception e) {
                throw new IllegalArgumentException("时间格式不正确");
            }
        }
    },

    /**
     * Before校验
     * */
    Before {
        @Override
        public void validate(Predicate predicate) {
            Map<String, String> valueMap = JsonUtil.json2Object(predicate.getValue(),Map.class);
            normalCheck(valueMap,"datetime");
            String dateTimeStr = valueMap.get("datetime");
            try {
                ZonedDateTime.parse(dateTimeStr);
            } catch (Exception e) {
                throw new IllegalArgumentException("时间格式不正确");
            }
        }
    },

    /**
     * Between校验
     * */
    Between {
        @Override
        public void validate(Predicate predicate) {
            Map<String, String> valueMap = JsonUtil.json2Object(predicate.getValue(),Map.class);
            normalCheck(valueMap,"datetime1","datetime2");
            for (String value : valueMap.values()) {
                try {
                    ZonedDateTime.parse(value);
                } catch (Exception e) {
                    throw new IllegalArgumentException("时间格式不正确");
                }
            }
        }
    },

    /**
     * Path校验
     * */
    Path {
        @Override
        public void validate(Predicate predicate) {
            Map<String, String> valueMap = JsonUtil.json2Object(predicate.getValue(),Map.class);
            normalCheck(valueMap,"pattern");
        }
    },

    /**
     * Path校验
     * */
    Host {
        @Override
        public void validate(Predicate predicate) {
            Map<String, String> valueMap = JsonUtil.json2Object(predicate.getValue(),Map.class);
            normalCheck(valueMap,"pattern");
        }
    },

    /**
     * Cookie校验
     * */
    Cookie {
        @Override
        public void validate(Predicate predicate) {
            Map<String, String> valueMap = JsonUtil.json2Object(predicate.getValue(),Map.class);
            normalCheck(valueMap,"name","regexp");
        }
    },

    /**
     * Header校验
     * */
    Header {
        @Override
        public void validate(Predicate predicate) {
            Map<String, String> valueMap = JsonUtil.json2Object(predicate.getValue(),Map.class);
            normalCheck(valueMap,"header","regexp");
        }
    },

    /**
     * Query校验
     * */
    Query {
        @Override
        public void validate(Predicate predicate) {
            Map<String, String> valueMap = JsonUtil.json2Object(predicate.getValue(),Map.class);
            normalCheck(valueMap,"param","regexp");
        }
    },

    /**
     * Method校验
     * */
    Method {
        @Override
        public void validate(Predicate predicate) {
            Map<String, String> valueMap = JsonUtil.json2Object(predicate.getValue(),Map.class);
            normalCheck(valueMap,"method");
            String[] predicates = valueMap.get("method").split(",");
            for (String s : predicates) {
                Assert.isTrue(Arrays.stream(HttpMethod.values()).map(HttpMethod::name).collect(Collectors.toList()).contains(s),
                        "不支持的请求方式[" + s + "]");
            }
        }
    },

    /**
     * RemoteAddr校验
     * */
    RemoteAddr {
        @Override
        public void validate(Predicate predicate) {
            Map<String, String> valueMap = JsonUtil.json2Object(predicate.getValue(),Map.class);
            normalCheck(valueMap,"source");
            String[] values = valueMap.get("source").split(",");
            for (String s : values) {
                String[] ipAddressCidrPrefix = s.split("/", 2);
                Assert.isTrue(ipAddressCidrPrefix.length == 2, "参数无效");
                int cidrPrefix = Integer.parseInt(ipAddressCidrPrefix[1]);
                Assert.isTrue(cidrPrefix>=0 && cidrPrefix <=32, "IPv4要求子网前缀的范围为[0,32]");
            }
        }
    },
    ;

    /**
     * 判断是否存在枚举类
     * @param type type
     * @return boolean
     * */
    public static boolean hasType(String type) {
        return Arrays.stream(PredicateType.values()).map(PredicateType::name).collect(Collectors.toList()).contains(type);
    }


    /**
     * normalCheck
     * @param valueMap valueMap
     * @param keys keys
     * */
    private static void normalCheck(Map<String, String> valueMap,String... keys){
        for (String key : keys) {
            Assert.isTrue(valueMap != null && !valueMap.isEmpty() && valueMap.containsKey(key) && StringUtils.isNotBlank(valueMap.get(key)),
                    "["+ key + "] 不能为空");
        }
    }
}
