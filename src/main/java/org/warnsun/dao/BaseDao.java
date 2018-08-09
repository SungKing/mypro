package org.warnsun.dao;

import mtime.lark.db.jsd.DatabaseFactory;
import mtime.lark.db.jsd.Query;

/**
 * @author song.wang
 * @create 2017/8/15 9:46
 */
public class BaseDao {

    public Query DB(){
        return DatabaseFactory.open("name");//假的
    }
}
