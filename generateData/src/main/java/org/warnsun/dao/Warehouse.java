package org.warnsun.dao;

import lombok.Data;
import mtime.lark.db.jsd.NameStyle;
import java.time.LocalDateTime;
import mtime.lark.db.jsd.annotation.JsdTable;


@Data
@JsdTable(nameStyle = NameStyle.LOWER)
public class Warehouse {
	private int warehouseId;
	private String cinemaInnerCode;
	private LocalDateTime lastUpdateTime;
	private String warehouseCode;
	private String name;
	private int maxQty;
	private String address;
	private int creater;
	private LocalDateTime createTime;
	private int updater;
	private String updaterName;
	private LocalDateTime updateTime;
}