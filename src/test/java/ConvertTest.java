import org.junit.Test;
import org.warnsun.GenerateClaz;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author song.wang
 * @create 2017/7/28 15:13
 */
public class ConvertTest {
    @Test
    public void test02(){
        //GenerateClaz.start(PurchaseCostDto.PurchaseCost.class,PurchaseCostDto.PurchaseCostAdjust.class,"D:\\pro\\cmc\\ddd\\mypro\\generateData\\src\\test\\java");
    }

    @Test
    public void test05(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
        System.out.println(formatter.format(LocalDateTime.now()));
    }

    @Test
    public void test06(){
        //GenerateClaz.start(UserSyncVo.class, UserSyncVo.class,"/Users/song.wang/java/pro/mypro/mypro/src/test/java");
    }

    @Test
    public void test07(){
        String a = "🀄️";
        System.out.println(a.length());
        System.out.println(a);
    }



}
