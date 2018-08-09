package org.warnsun.dao;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
@Data
public class WarehouseArgs {
	//warehouseId
	private List<Integer> warehouseIdList;
	private Integer warehouseIdMax;
	private Integer warehouseIdMin;
	//cinemaInnerCode
	private List<String> cinemaInnerCodeList;
	private String cinemaInnerCodeMax;
	private String cinemaInnerCodeMin;
	//lastUpdateTime
	private List<LocalDateTime> lastUpdateTimeList;
	private LocalDateTime lastUpdateTimeMax;
	private LocalDateTime lastUpdateTimeMin;
	//warehouseCode
	private List<String> warehouseCodeList;
	private String warehouseCodeMax;
	private String warehouseCodeMin;
	//name
	private List<String> nameList;
	private String nameMax;
	private String nameMin;
	//maxQty
	private List<Integer> maxQtyList;
	private Integer maxQtyMax;
	private Integer maxQtyMin;
	//address
	private List<String> addressList;
	private String addressMax;
	private String addressMin;
	//creater
	private List<Integer> createrList;
	private Integer createrMax;
	private Integer createrMin;
	//createTime
	private List<LocalDateTime> createTimeList;
	private LocalDateTime createTimeMax;
	private LocalDateTime createTimeMin;
	//updater
	private List<Integer> updaterList;
	private Integer updaterMax;
	private Integer updaterMin;
	//updaterName
	private List<String> updaterNameList;
	private String updaterNameMax;
	private String updaterNameMin;
	//updateTime
	private List<LocalDateTime> updateTimeList;
	private LocalDateTime updateTimeMax;
	private LocalDateTime updateTimeMin;
}