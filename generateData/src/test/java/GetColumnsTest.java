import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.warnsun.generate.dao.GenerateDao;
import org.warnsun.models.ColumnTypes;

import java.sql.SQLException;
import java.util.List;

/**
 * @author song.wang
 * @create 2017/8/2 10:25
 */
public class GetColumnsTest {
    @Test
    public void test01() throws SQLException {
        String url="jdbc:mysql://192.168.55.121:3306/snack_inventory";
        String user = "mxuser";
        String pwd="mxuser123456";
        List<ColumnTypes> warehouse = GenerateDao.queryColns(url, user, pwd, "warehouse");
        System.out.println(JSONObject.toJSONString(warehouse));
    }
    @Test
    public void test03(){
        String url="jdbc:mysql://192.168.55.121:3306/snack_inventory";
        String user = "mxuser";
        String pwd="mxuser123456";
        String path = "D:\\pro\\cmc\\generateData\\src\\main\\java\\org\\warnsun\\dao";
        String temPath = "D:\\pro\\cmc\\generateData\\src\\main\\resources\\templete";

        GenerateDao.start(url,user,pwd,"warehouse",path,temPath);
    }
}
