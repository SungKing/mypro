package org.warnsun.dao;

import mtime.lark.db.jsd.*;
import mtime.lark.pb.data.PageInfo;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import java.util.List;
import java.util.Map;

/**
 * @author song.wang
 * @create 2017-08-15T16:15:04.511
 */
public class WarehouseDaoImpl extends BaseDao implements WarehouseDao {

    private static final String TABLE_NAME = "warehouse";
	private static final String WAREHOUSE_ID = "warehouse_id";
	private static final String CINEMA_INNER_CODE = "cinema_inner_code";
	private static final String LAST_UPDATE_TIME = "last_update_time";
	private static final String WAREHOUSE_CODE = "warehouse_code";
	private static final String NAME = "name";
	private static final String MAX_QTY = "max_qty";
	private static final String ADDRESS = "address";
	private static final String CREATER = "creater";
	private static final String CREATE_TIME = "create_time";
	private static final String UPDATER = "updater";
	private static final String UPDATER_NAME = "updater_name";
	private static final String UPDATE_TIME = "update_time";


    @Override
    public long count(WarehouseArgs args) {
        return (Long) DB()
                        .select(Shortcut.count())
                        .from(TABLE_NAME)
                        .where(this.buildFilter(args))
                        .result()
                        .value();
    }

    @Override
    public List<Warehouse> query(WarehouseArgs args, PageInfo pageInfo) {
        return DB()
                .select(Warehouse.class)
                .where(Shortcut.f())
                .page(pageInfo.getPageIndex(),pageInfo.getPageSize())
                .result()
                .all(Warehouse.class);
    }

    @Override
    public Warehouse get(WarehouseArgs args) {
            return DB()
            .select(Warehouse.class)
            .where(this.buildFilter(args))
            .page(1,1)
            .result()
            .one(Warehouse.class);
    }

    @Override
    public Warehouse getByPK(Map<String, Object> pks) {
            return DB()
            .select(Warehouse.class)
            .where(this.buildPKFilter(pks))
            .page(1,1)
            .result()
            .one(Warehouse.class);
    }

    @Deprecated
    @Override
    public int update(UpdateValues values, WarehouseArgs whereClaues) {
        return DB()
                .update(TABLE_NAME)
                .set(values)
                .where(this.buildFilter(whereClaues))
                .result()
                .getAffectedRows();
    }

    @Override
    public int delete(WarehouseArgs args) {
        return DB()
                .delete(TABLE_NAME)
                .where(this.buildFilter(args))
                .result()
                .getAffectedRows();
    }

    @Override
    public int deleteByPK(Map<String, Object> pks) {
        return DB()
                .delete(TABLE_NAME)
                .where(this.buildPKFilter(pks))
                .result()
                .getAffectedRows();
    }

    @Override
    public int insert(List<Warehouse> items) {
        return DB().insert(items).result().getAffectedRows();
    }

    @Override
    public int insert(Warehouse ele) {
        return DB().insert(ele).result().getAffectedRows();
    }

    @Override
    public boolean exist(WarehouseArgs args) {
        long count = this.count(args);
        return count>0;
    }


    /**
     * 创建过滤器
     * @param args
     * @return 返回where 条件
     */
    private Filter buildFilter(WarehouseArgs args){
        BasicFilter f = Shortcut.f();

		if (!CollectionUtils.isEmpty(args.getWarehouseIdList())){
			f.add(WAREHOUSE_ID, FilterType.IN,args.getWarehouseIdList());
		}else{
			if (args.getWarehouseIdMax()!=null)
				f.add(WAREHOUSE_ID,FilterType.LTE,args.getWarehouseIdMax());
			if (args.getWarehouseIdMin()!=null)
				f.add(WAREHOUSE_ID,FilterType.GTE,args.getWarehouseIdMin());
		}

		if (!CollectionUtils.isEmpty(args.getCinemaInnerCodeList())){
			f.add(CINEMA_INNER_CODE, FilterType.IN,args.getCinemaInnerCodeList());
		}else{
			if (args.getCinemaInnerCodeMax()!=null)
				f.add(CINEMA_INNER_CODE,FilterType.LTE,args.getCinemaInnerCodeMax());
			if (args.getCinemaInnerCodeMin()!=null)
				f.add(CINEMA_INNER_CODE,FilterType.GTE,args.getCinemaInnerCodeMin());
		}

		if (!CollectionUtils.isEmpty(args.getLastUpdateTimeList())){
			f.add(LAST_UPDATE_TIME, FilterType.IN,args.getLastUpdateTimeList());
		}else{
			if (args.getLastUpdateTimeMax()!=null)
				f.add(LAST_UPDATE_TIME,FilterType.LTE,args.getLastUpdateTimeMax());
			if (args.getLastUpdateTimeMin()!=null)
				f.add(LAST_UPDATE_TIME,FilterType.GTE,args.getLastUpdateTimeMin());
		}

		if (!CollectionUtils.isEmpty(args.getWarehouseCodeList())){
			f.add(WAREHOUSE_CODE, FilterType.IN,args.getWarehouseCodeList());
		}else{
			if (args.getWarehouseCodeMax()!=null)
				f.add(WAREHOUSE_CODE,FilterType.LTE,args.getWarehouseCodeMax());
			if (args.getWarehouseCodeMin()!=null)
				f.add(WAREHOUSE_CODE,FilterType.GTE,args.getWarehouseCodeMin());
		}

		if (!CollectionUtils.isEmpty(args.getNameList())){
			f.add(NAME, FilterType.IN,args.getNameList());
		}else{
			if (args.getNameMax()!=null)
				f.add(NAME,FilterType.LTE,args.getNameMax());
			if (args.getNameMin()!=null)
				f.add(NAME,FilterType.GTE,args.getNameMin());
		}

		if (!CollectionUtils.isEmpty(args.getMaxQtyList())){
			f.add(MAX_QTY, FilterType.IN,args.getMaxQtyList());
		}else{
			if (args.getMaxQtyMax()!=null)
				f.add(MAX_QTY,FilterType.LTE,args.getMaxQtyMax());
			if (args.getMaxQtyMin()!=null)
				f.add(MAX_QTY,FilterType.GTE,args.getMaxQtyMin());
		}

		if (!CollectionUtils.isEmpty(args.getAddressList())){
			f.add(ADDRESS, FilterType.IN,args.getAddressList());
		}else{
			if (args.getAddressMax()!=null)
				f.add(ADDRESS,FilterType.LTE,args.getAddressMax());
			if (args.getAddressMin()!=null)
				f.add(ADDRESS,FilterType.GTE,args.getAddressMin());
		}

		if (!CollectionUtils.isEmpty(args.getCreaterList())){
			f.add(CREATER, FilterType.IN,args.getCreaterList());
		}else{
			if (args.getCreaterMax()!=null)
				f.add(CREATER,FilterType.LTE,args.getCreaterMax());
			if (args.getCreaterMin()!=null)
				f.add(CREATER,FilterType.GTE,args.getCreaterMin());
		}

		if (!CollectionUtils.isEmpty(args.getCreateTimeList())){
			f.add(CREATE_TIME, FilterType.IN,args.getCreateTimeList());
		}else{
			if (args.getCreateTimeMax()!=null)
				f.add(CREATE_TIME,FilterType.LTE,args.getCreateTimeMax());
			if (args.getCreateTimeMin()!=null)
				f.add(CREATE_TIME,FilterType.GTE,args.getCreateTimeMin());
		}

		if (!CollectionUtils.isEmpty(args.getUpdaterList())){
			f.add(UPDATER, FilterType.IN,args.getUpdaterList());
		}else{
			if (args.getUpdaterMax()!=null)
				f.add(UPDATER,FilterType.LTE,args.getUpdaterMax());
			if (args.getUpdaterMin()!=null)
				f.add(UPDATER,FilterType.GTE,args.getUpdaterMin());
		}

		if (!CollectionUtils.isEmpty(args.getUpdaterNameList())){
			f.add(UPDATER_NAME, FilterType.IN,args.getUpdaterNameList());
		}else{
			if (args.getUpdaterNameMax()!=null)
				f.add(UPDATER_NAME,FilterType.LTE,args.getUpdaterNameMax());
			if (args.getUpdaterNameMin()!=null)
				f.add(UPDATER_NAME,FilterType.GTE,args.getUpdaterNameMin());
		}

		if (!CollectionUtils.isEmpty(args.getUpdateTimeList())){
			f.add(UPDATE_TIME, FilterType.IN,args.getUpdateTimeList());
		}else{
			if (args.getUpdateTimeMax()!=null)
				f.add(UPDATE_TIME,FilterType.LTE,args.getUpdateTimeMax());
			if (args.getUpdateTimeMin()!=null)
				f.add(UPDATE_TIME,FilterType.GTE,args.getUpdateTimeMin());
		}

        return f;
     }


    /**
     * 构建主键过滤器
     * @param pks 主键集
     * @return 条件
     *
    */
    private Filter buildPKFilter(Map<String,Object> pks){
        BasicFilter f = Shortcut.f();
        pks.forEach((k, v) -> {
        if (!StringUtils.isEmpty(k) && v != null) f.add(k, v);
        });
        return f;
    }
}
