package com.funicorn.basic.gateway.api.enums;

import com.funicorn.basic.common.base.util.CheckUtil;
import com.funicorn.basic.common.base.util.JsonUtil;
import com.funicorn.basic.gateway.api.model.Filter;
import com.funicorn.basic.gateway.api.util.Validator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Aimee
 * @since 2022/3/17 11:13
 */
@SuppressWarnings({"unchecked","unused"})
public enum FilterType implements Validator<Filter> {

    /**
     * AddRequestHeader 校验
     * */
    AddRequestHeader {
        @Override
        public void validate(Filter filter) {
            Map<String, String> valueMap = JsonUtil.json2Object(filter.getValue(),Map.class);
            normalCheck(valueMap,"name","value");
        }
    },

    /**
     * AddRequestParameter 校验
     * */
    AddRequestParameter {
        @Override
        public void validate(Filter filter) {
            Map<String, String> valueMap = JsonUtil.json2Object(filter.getValue(),Map.class);
            normalCheck(valueMap,"name","value");
        }
    },

    /**
     * AddResponseHeader 校验
     * */
    AddResponseHeader {
        @Override
        public void validate(Filter filter) {
            Map<String, String> valueMap = JsonUtil.json2Object(filter.getValue(),Map.class);
            normalCheck(valueMap,"name","value");
        }
    },

    /**
     * PrefixPath 校验
     * */
    PrefixPath {
        @Override
        public void validate(Filter filter) {
            Map<String, String> valueMap = JsonUtil.json2Object(filter.getValue(),Map.class);
            normalCheck(valueMap,"prefix");
        }
    },

    /**
     * RedirectTo 校验
     * */
    RedirectTo {
        @Override
        public void validate(Filter filter) {
            Map<String, String> valueMap = JsonUtil.json2Object(filter.getValue(),Map.class);
            normalCheck(valueMap,"status","url");
            Assert.isTrue(STATUS_CODE_300.contains(valueMap.get("status")),"非300类HTTP状态码");
            Assert.isTrue(CheckUtil.isHttpOrHttps(valueMap.get("url")),"重定向地址不合法");
        }
    },

    /**
     * RemoveRequestHeader 校验
     * */
    RemoveRequestHeader {
        @Override
        public void validate(Filter filter) {
            Map<String, String> valueMap = JsonUtil.json2Object(filter.getValue(),Map.class);
            normalCheck(valueMap,"name");
        }
    },

    /**
     * RemoveResponseHeader 校验
     * */
    RemoveResponseHeader {
        @Override
        public void validate(Filter filter) {
            Map<String, String> valueMap = JsonUtil.json2Object(filter.getValue(),Map.class);
            normalCheck(valueMap,"name");
        }
    },

    /**
     * RewritePath 校验
     * */
    RewritePath {
        @Override
        public void validate(Filter filter) {
            Map<String, String> valueMap = JsonUtil.json2Object(filter.getValue(),Map.class);
            normalCheck(valueMap,"regexp","replacement");
        }
    },

    /**
     * RewriteResponseHeader 校验
     * */
    RewriteResponseHeader {
        @Override
        public void validate(Filter filter) {
            Map<String, String> valueMap = JsonUtil.json2Object(filter.getValue(),Map.class);
            normalCheck(valueMap,"name","regexp","replacement");
        }
    },

    /**
     * SetPath 校验
     * */
    SetPath {
        @Override
        public void validate(Filter filter) {
            Map<String, String> valueMap = JsonUtil.json2Object(filter.getValue(),Map.class);
            normalCheck(valueMap,"template");
        }
    },

    /**
     * SetResponseHeader 校验
     * */
    SetResponseHeader {
        @Override
        public void validate(Filter filter) {
            Map<String, String> valueMap = JsonUtil.json2Object(filter.getValue(),Map.class);
            normalCheck(valueMap,"name","value");
        }
    },

    /**
     * SetStatus 校验
     * */
    SetStatus {
        @Override
        public void validate(Filter filter) {
            Map<String, String> valueMap = JsonUtil.json2Object(filter.getValue(),Map.class);
            normalCheck(valueMap,"status");
            int status;
            try {
                status = Integer.parseInt(valueMap.get("status"));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("状态码不合法");
            }
            Assert.isTrue(Arrays.stream(HttpStatus.values()).map(HttpStatus::value).collect(Collectors.toList()).contains(status),"状态码不合法");
        }
    },

    /**
     * StripPrefix 校验
     * */
    StripPrefix {
        @Override
        public void validate(Filter filter) {
            Map<String, String> valueMap = JsonUtil.json2Object(filter.getValue(),Map.class);
            normalCheck(valueMap,"parts");
        }
    },

    ;

    private static final List<String> STATUS_CODE_300 = Arrays.asList("300","301","302","303","304","305","305");

    /**
     * 判断是否存在枚举类
     * @param type type
     * @return boolean
     * */
    public static boolean hasType(String type) {
        return Arrays.stream(FilterType.values()).map(FilterType::name).collect(Collectors.toList()).contains(type);
    }

    /**
     * normalCheck
     * @param valueMap valueMap
     * @param keys keys
     * */
    private static void normalCheck(Map<String, String> valueMap, String... keys){
        for (String key : keys) {
            Assert.isTrue(valueMap != null && !valueMap.isEmpty() && valueMap.containsKey(key) && StringUtils.isNotBlank(valueMap.get(key)),
                    "["+ key + "] 不能为空");
        }
    }
}
