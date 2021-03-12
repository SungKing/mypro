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
        String url="jdbc:mysql://192.168.50.134:8806/member_mtimeb";
        String user = "mtimeuser";
        String pwd="mtimeuser%^";
        //D:\pro\haiwai\scm-purchase-service\purchase-core\src\main\java\mtime\intl\ec\purchase\core
        String path = "/Users/song.wang/java/pro/mypro/mypro/src/main/resources/templete";
        String temPath = "/Users/song.wang/java/pro/mypro/mypro/src/main/resources/templete";

        GenerateDao.start(url,user,pwd,"festivalevent",path,temPath);
    }

    @Test
    public void test04(){
//        String url="jdbc:mysql://192.168.50.136:8808/mtimeaward?useUnicode=true&characterEncoding=UTF-8";
//        String user = "mtimeuser";
//        String pwd="mtimeuser!@#";

        String url="jdbc:mysql://192.168.50.136:8808/mtime?useUnicode=true&characterEncoding=UTF-8";
        String user = "mtimeuser";
        String pwd="mtimeuser!@#";
        //D:\pro\haiwai\scm-purchase-service\purchase-core\src\main\java\mtime\intl\ec\purchase\core
        String path = "/Users/song.wang/java/pro/mypro/mypro/src/main/resources/templete";
        String temPath = "/Users/song.wang/java/pro/mypro/mypro/src/main/resources/templete";

        GenerateDao.start(url,user,pwd,"image",path,temPath);
    }
}
