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
        String url="jdbc:mysql://192.168.50.56:3306/scm_purchase";
        String user = "mtimeuser";
        String pwd="mtimeuser0301";
        List<ColumnTypes> warehouse = GenerateDao.queryColns(url, user, pwd, "fee_detail");
        System.out.println(JSONObject.toJSONString(warehouse));
    }
    @Test
    public void test03(){
        String url="jdbc:mysql://192.168.50.56:3306/scm_purchase";
        String user = "mtimeuser";
        String pwd="mtimeuser0301";
        //D:\pro\haiwai\scm-purchase-service\purchase-core\src\main\java\mtime\intl\ec\purchase\core
        String path = "D:\\pro\\cmc\\ddd\\mypro\\generateData\\src\\main\\java\\mtime\\intl\\ec\\purchase\\core\\dao";
        String temPath = "D:\\pro\\cmc\\ddd\\mypro\\generateData\\src\\main\\resources\\templete";

        GenerateDao.start(url,user,pwd,"fee_detail",path,temPath);
    }
}
