

import mtime.lark.pb.data.PageInfo;
import mtime.lark.db.jsd.*;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import java.util.List;
import java.util.Map;

/**
 * @author {author}
 * @create {time}
 */
public class {TableName}DaoImpl extends BaseDao implements {TableName}Dao {

    private static final String TABLE_NAME = "{table_name}";
    {field}

    @Override
    public long count({TableName}Args args) {
        return (Long) DB()
                        .select(Shortcut.count())
                        .from(TABLE_NAME)
                        .where(this.buildFilter(args))
                        .result()
                        .value();
    }

    @Override
    public List<{TableName}> query({TableName}Args args, PageInfo pageInfo) {
        return DB()
                .select({TableName}.class)
                .where(Shortcut.f())
                .page(pageInfo.getPageIndex(),pageInfo.getPageSize())
                .result()
                .all({TableName}.class);
    }

    @Override
    public {TableName} get({TableName}Args args) {
            return DB()
            .select({TableName}.class)
            .where(this.buildFilter(args))
            .page(1,1)
            .result()
            .one({TableName}.class);
    }

    @Override
    public {TableName} getByPK(Map<String, Object> pks) {
            return DB()
            .select({TableName}.class)
            .where(this.buildPKFilter(pks))
            .page(1,1)
            .result()
            .one({TableName}.class);
    }

    @Deprecated
    @Override
    public int update(UpdateValues values, {TableName}Args whereClaues) {
        return DB()
                .update(TABLE_NAME)
                .set(values)
                .where(this.buildFilter(whereClaues))
                .result()
                .getAffectedRows();
    }

    @Override
    public int delete({TableName}Args args) {
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
    public int insert(List<{TableName}> items) {
        return DB().insert(items).result().getAffectedRows();
    }

    @Override
    public int insert({TableName} ele) {
        return DB().insert(ele).result().getAffectedRows();
    }

    @Override
    public boolean exist({TableName}Args args) {
        long count = this.count(args);
        return count>0;
    }


    /**
     * 创建过滤器
     * @param args
     * @return 返回where 条件
     */
    private Filter buildFilter({TableName}Args args){
        BasicFilter f = Shortcut.f();
        {for_filter}
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
