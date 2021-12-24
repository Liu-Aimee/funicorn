package com.funicorn.cloud.system.center.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author Aimee
 * @since 2021/10/15 10:54
 */
@Data
public class MultiDictItemDTO {

    /**
     * 字典类型
     */
    @NotBlank(message = "字典类型不能为空")
    private String dictType;

    /**
     * 字典项数组
     * */
    @NotEmpty(message = "字典项数组不能为空")
    private List<Item> items;

    @Data
    public static class Item {
        /**
         * 值
         */
        @NotEmpty(message = "字典项值不能为空")
        private String dictValue;

        /**
         * 标签
         */
        @NotEmpty(message = "字典项标签不能为空")
        private String dictLabel;
    }
}
