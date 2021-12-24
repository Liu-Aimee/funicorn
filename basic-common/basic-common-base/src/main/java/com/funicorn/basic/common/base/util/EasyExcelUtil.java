package com.funicorn.basic.common.base.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.AbstractColumnWidthStyleStrategy;
import com.alibaba.excel.write.style.row.AbstractRowHeightStyleStrategy;
import org.apache.poi.ss.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Aimee
 * @since 2021/7/20 9:44
 */
@SuppressWarnings("unused")
public class EasyExcelUtil {

    /**
     * excel导出
     * @param <T> 泛型
     * @param resultList 结果集
     * @param filename 文件名称
     * @param response 返回流
     * @param clazz 泛型
     * @throws IOException 异常
     * */
    public static <T> void writeExcel(List<T> resultList, String filename, HttpServletResponse response, Class<T> clazz) throws IOException {
        //通知浏览器以附件的形式下载处理，设置返回头要注意文件名有中文
        // 这里URLEncoder.encode可以防止中文乱码
        filename = URLEncoder.encode(filename, "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + filename + ExcelTypeEnum.XLSX.getValue());
        response.addHeader("filename", filename + ExcelTypeEnum.XLSX.getValue());
        response.addHeader("Access-Control-Expose-Headers","filename");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("UTF-8");

        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        headWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());

        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        contentWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);
        contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
        contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);

        EasyExcel.write(response.getOutputStream(), clazz)
                .needHead(true)
                .registerWriteHandler(new CustomCellWriteHeightHandler())
                .registerWriteHandler(horizontalCellStyleStrategy)
                .registerWriteHandler(new CustomCellWriteHandler())
                .sheet("sheet1")
                .doWrite(resultList);
    }

    /**
     * excel导出
     * @param <T> 泛型
     * @param head 自定义表头
     * @param resultList 结果集
     * @param filename 文件名称
     * @param response 返回流
     * @param clazz 泛型
     * @throws IOException 异常
     * */
    public static <T> void writeExcelWithHead(List<T> resultList,List<List<String>> head, String filename, HttpServletResponse response, Class<T> clazz)
            throws IOException {
        //通知浏览器以附件的形式下载处理，设置返回头要注意文件名有中文
        // 这里URLEncoder.encode可以防止中文乱码
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = getHorizontalCellStyleStrategy(filename, response);

        EasyExcel.write(response.getOutputStream(), clazz)
                .needHead(true)
                .head(head)
                .registerWriteHandler(new CustomCellWriteHeightHandler())
                .registerWriteHandler(horizontalCellStyleStrategy)
                .registerWriteHandler(new CustomCellWriteHandler())
                .sheet("sheet1")
                .doWrite(resultList);
    }

    private static HorizontalCellStyleStrategy getHorizontalCellStyleStrategy(String filename, HttpServletResponse response) throws UnsupportedEncodingException {
        filename = URLEncoder.encode(filename, "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + filename + ExcelTypeEnum.XLSX.getValue());
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("filename", filename + ExcelTypeEnum.XLSX.getValue());
        response.addHeader("Access-Control-Expose-Headers","filename");
        response.setCharacterEncoding("UTF-8");

        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        headWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());

        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        contentWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);
        contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
        contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
        return new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
    }

    /**
     * 全局设置行高
     * @author Aimee
     * @since 2021/12/06
     * */
    public static class CustomCellWriteHeightHandler extends AbstractRowHeightStyleStrategy {
        /**
         * 默认高度
         */
        private static final Integer DEFAULT_HEIGHT = 300;

        @Override
        protected void setHeadColumnHeight(Row row, int relativeRowIndex) {
            Iterator<Cell> cellIterator = row.cellIterator();
            if (!cellIterator.hasNext()) {
                return;
            }
            row.setHeight((short) (2 * DEFAULT_HEIGHT));
        }

        @Override
        protected void setContentColumnHeight(Row row, int relativeRowIndex) {
            Iterator<Cell> cellIterator = row.cellIterator();
            if (!cellIterator.hasNext()) {
                return;
            }
            row.setHeight((short) (2 * DEFAULT_HEIGHT));
        }
    }


    /**
     * 表头最大宽度
     * */
    private static final Integer MAX_COLUMN_WIDTH = 255;

    /**
     * 内部类
     * 设置导出时表头列宽自适应
     * */
    public static class CustomCellWriteHandler extends AbstractColumnWidthStyleStrategy {

        private final Map<Integer, Map<Integer, Integer>> CACHE = new HashMap<>(16);

        @Override
        protected void setColumnWidth(WriteSheetHolder writeSheetHolder, List<CellData> list, Cell cell, Head head, Integer integer, Boolean isHead) {
            boolean needSetWidth = isHead || (list != null && !list.isEmpty());
            if (needSetWidth) {
                Map<Integer, Integer> maxColumnWidthMap = CACHE.computeIfAbsent(writeSheetHolder.getSheetNo(), k -> new HashMap<>(16));
                Integer columnWidth = this.dataLength(list.get(0), cell, isHead);
                if (columnWidth >= 0) {
                    if (columnWidth > MAX_COLUMN_WIDTH) {
                        columnWidth = MAX_COLUMN_WIDTH;
                    }
                    Integer maxColumnWidth = maxColumnWidthMap.get(cell.getColumnIndex());
                    if (maxColumnWidth == null || columnWidth > maxColumnWidth) {
                        maxColumnWidthMap.put(cell.getColumnIndex(), columnWidth);
                        writeSheetHolder.getSheet().setColumnWidth(cell.getColumnIndex(), columnWidth * 256);
                    }
                }
            }
        }

        /**
         * 计算字符长度
         * @param cellData cellData
         * @param cell cell
         * @param isHead isHead
         * @return Integer
         * */
        private Integer dataLength(CellData<?> cellData, Cell cell, Boolean isHead) {
            if (isHead) {
                //限制最长为20，最短为12
                return Math.min(Math.max(cell.getStringCellValue().getBytes().length, 12),20);
            } else {
                CellDataTypeEnum type = cellData.getType();
                if (type == null) {
                    return -1;
                } else {
                    switch (type) {
                        case STRING:
                            return cellData.getStringValue().getBytes().length;
                        case BOOLEAN:
                            return cellData.getBooleanValue().toString().getBytes().length;
                        case NUMBER:
                            return cellData.getNumberValue().toString().getBytes().length;
                        default:
                            return -1;
                    }
                }
            }
        }
    }
}
