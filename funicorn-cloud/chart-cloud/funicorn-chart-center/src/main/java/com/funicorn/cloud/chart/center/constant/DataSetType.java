package com.funicorn.cloud.chart.center.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Aimme
 * @since 2020/11/16 11:06
 */

@SuppressWarnings("unused")
public enum DataSetType {

    /**数据库查询*/
    SQL("SQL",true),

    /**json字符串*/
    JSON("JSON",true),

    /**HTTP接口*/
    HTTP("HTTP",true),

    /**CSV文件*/
    CSV("CSV",false),

    /**EXCEL文件*/
    EXCEL("EXCEL",false),
    ;

    public static List<Map<String, String>> typeList = new ArrayList<>();

    static {
        DataSetType[] dataSetTypes = DataSetType.values();
        for (DataSetType dataSetType:dataSetTypes) {
            if (dataSetType.getIsShow()){
                Map<String, String> typeMap = new HashMap<>();
                typeMap.put("key",dataSetType.getType());
                typeMap.put("name",dataSetType.getType());
                typeList.add(typeMap);
            }
        }
    }

    DataSetType(String type, Boolean isShow) {
        this.type = type;
        this.isShow = isShow;
    }

    private final String type;

    private final Boolean isShow;

    public String getType() {
        return type;
    }

    public Boolean getIsShow() {
        return isShow;
    }
}
