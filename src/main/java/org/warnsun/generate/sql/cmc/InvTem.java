package org.warnsun.generate.sql.cmc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 万达系统修改SQl 生成工具
 * @author song.wang
 * @create 2017/8/11 14:53
 */
public class InvTem {

    /**
     * 核心系统生成SQL
     * 时间取的是当前时间
     * @param sku   sku
     * @param cinemaInnerCode  影城内码
     * @param qty       现有库存
     * @param qtyAdjust  库存改变量
     * @param batchNo    批次号
     * @param storage_id  货架号
     * @param billCode    单据编号
     */
    public static void genSql(String sku,int cinemaInnerCode,int qty,int qtyAdjust,String batchNo,int storage_id,String billCode,int billType){
        String updateShelfInvSql = "update shelf_inv set usable_qty = usable_qty+(%d) , entity_qty = entity_qty + (%d) where sku = '%s' and cinema_inner_code = %d ;";

        String updateShelfBatchInvSql = "update shelf_batch_inv set usable_qty = usable_qty+(%d) , entity_qty = entity_qty +(%d) " +
                "where sku = '%s' and cinema_inner_code = %d and batch_no = '%s';";

        String inserShelfInvIOSql = "INSERT INTO `shelf_inv_io` (`sku`, `storage_id`, `cinema_inner_code`, `bill_type`, `bill_id`, `bill_code`, `interface_id`, `interface_name`, `usable_qty_new`, `usable_qty_old`, `usable_qty_adjust`, `locked_qty_new`, `locked_qty_old`, `locked_qty_adjust`, `entity_qty_new`, `entity_qty_old`, `entity_qty_adjust`, `oper_time`, `bill_opertype`, `original_bill_type`, `original_bill_id`, `original_bill_code`, `last_update_time`) VALUES " +
                "('%s', %d, '%d', %d, 3203, '%s', 8, 'Shelf_Adjust', %d, %d, %d, 0.0000, 0.0000, 0.0000, %d, %d, %d, '%s', 1, %d, 3203, '%s', '%s');";

        String insertShelfBatchInvIOSql = "INSERT INTO `shelf_batch_inv_io` (`sku`, `storage_id`, `cinema_inner_code`, `batch_no`, `bill_type`, `bill_id`, `bill_code`, `interface_id`, `interface_name`, `usable_qty_new`, `usable_qty_old`, `usable_qty_adjust`, `locked_qty_new`, `locked_qty_old`, `locked_qty_adjust`, `entity_qty_new`, `entity_qty_old`, `entity_qty_adjust`, `oper_time`, `bill_opertype`, `original_bill_type`, `original_bill_id`, `original_bill_code`, `last_update_time`) " +
                "VALUES ('%s', %d, '%d', '%s', %d, 0, '%s', 31, 'ShelfBatch_SplitIncrease',  %d, %d, %d, 0.0000, 0.0000, 0.0000,  %d, %d, %d, '%s', 1, 3, 0, '%s', '%s');";
        //时间选则现在的时间
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
        String time = formatter.format(LocalDateTime.now());

        //更新 货架库存
        System.out.println(String.format(updateShelfInvSql,qtyAdjust,qtyAdjust,sku,cinemaInnerCode));
        //更新 货架批次库存
        System.out.println(String.format(updateShelfBatchInvSql,qtyAdjust,qtyAdjust,sku,cinemaInnerCode,batchNo));
        //插入更新货架库存记录
        System.out.println(String.format(inserShelfInvIOSql,sku,storage_id,cinemaInnerCode,billType,billCode,qty+qtyAdjust,qty,qtyAdjust,qty+qtyAdjust,qty,qtyAdjust,time,billType,billCode,time));
        //插入更新货架批次库存记录
        System.out.println(String.format(insertShelfBatchInvIOSql,sku,storage_id,cinemaInnerCode,batchNo,billType,billCode,qty+qtyAdjust,qty,qtyAdjust,qty+qtyAdjust,qty,qtyAdjust,time,billCode,time));
    }
}
