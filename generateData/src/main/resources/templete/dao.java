
import mtime.lark.pb.data.PageInfo;
import mtime.lark.db.jsd.UpdateValues;
import java.util.List;

/**
 *
 * @author {author}
 * @create {time}
 */
public interface {TableName}Dao{
    /**
     * 对符合条件的结果进行统计
     * @param args 请求参数
     * @return 计数结果
     */
    long count({TableName}Args args);
    /**
     * 查询
     * @param args 请求参数
     * @param pageInfo 分页参数
     * @return 结果集
     */
    List<{TableName}> query({TableName}Args args,PageInfo pageInfo);
    /**
     * 返回查询到的第一个结果
     * @param args 请求参数
     * @return
     */
    {TableName} get({TableName}Args args);
    /**
     * 通过主键来获取结果
     *
     * @return
     */
    {TableName} getByPK({pkArgs});
    /**
     * 更新数据  这个方法不好，因为现实中的更新是比较复杂的
     * 这个最好自己写一些，或者以后我再想更好的更新
     * @param values 要更新的数据
     * @param whereClaues 限制条件
     * @return 影响行数
     */
    @Deprecated
    int update(UpdateValues values,{TableName}Args whereClaues);
    /**
     * 按照指定条件删除
     * @param args
     * @return 影响行数
     */
    int delete({TableName}Args args);
    /**
     * 按照主键删除
     *
     * @return 影响行数
     */
    int deleteByPK({pkArgs});
    /**
     * 批量插入
     * @param items 集合
     * @return 影响行数
     */
    int insert(List<{TableName}> items);
    /**
     * 插入单条记录
     * @param ele
     * @return 影响行数
     */
    int insert({TableName} ele);

    /**
     * 判断相应的记录是否存在
     * @param args 参数
     * @return true -->存在； false --> 不存在
     */
    boolean exist({TableName}Args args);

}