package org.warnsun.models;

import lombok.Data;

/**
 * @author song.wang
 * @create 2017/8/2 9:50
 */
@Data
public class ColumnTypes {

    private String field;//字段
    private String type;//类型
    private String allowNull;//允许空？
    private String key;//主键
    private String defaultVal;//默认值
    private String extra;//扩展描述

}
