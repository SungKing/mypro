import org.junit.Test;
import org.warnsun.GenerateClaz;
import org.warnsun.generate.sql.cmc.InvTem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author song.wang
 * @create 2017/7/28 15:13
 */
public class ConvertTest {
    @Test
    public void test02(){
        GenerateClaz.start(PurchaseCostDto.PurchaseCost.class,PurchaseCostDto.PurchaseCostAdjust.class,"D:\\pro\\cmc\\ddd\\mypro\\generateData\\src\\test\\java");
    }
    @Test
    public void test03(){
        InvTem.genSql("NM000000000018",346,63,1,"00000001578901",230,"20170725002327",5);
    }

    @Test
    public void test05(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
        System.out.println(formatter.format(LocalDateTime.now()));
    }



    @Test
    public void test04(){
        InvTem.genSql("NM000000000066",346,0,1,"00000001580501",230,"20170725002327",5);
        InvTem.genSql("NM000000000053",346,7,1,"00000001607500",230,"20170725002327",5);
        InvTem.genSql("NI000000000079",346,565,1,"00000001580101",230,"20170725002327",5);
        InvTem.genSql("NI000000000003",346,150,1,"00000004964800",230,"20170725002327",5);
        InvTem.genSql("NM000000000034",346,0,1,"00000001611701",230,"20170725002327",5);
        InvTem.genSql("NM000000000003",346,41,2,"00000004684200",230,"20170725002327",5);
        InvTem.genSql("NM000000000009",346,120,5,"00000004642102",230,"20170725002327",5);
    }
    @Test
    public void test06(){
        InvTem.genSql("NI000000000047",974,242,-2,"00000003476000",308,"20170725002338",5);
    }
}
