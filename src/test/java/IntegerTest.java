import org.junit.Test;

import java.lang.reflect.Field;

/**
 * @Description
 * @Author song.wang
 * @Date 2021-04-01
 */
public class IntegerTest {

    @Test
    public void test01() throws NoSuchFieldException, IllegalAccessException {

        Class<Integer> clz = Integer.class;
        Field digitOnes = clz.getDeclaredField("DigitOnes");
        digitOnes.setAccessible(true);
        updateChinese((char [])digitOnes.get(Integer.class));
        Field digitTens = clz.getDeclaredField("DigitTens");
        digitTens.setAccessible(true);
        updateChinese((char [])digitTens.get(Integer.class));
        Field digits = clz.getDeclaredField("digits");
        digits.setAccessible(true);
        updateChinese((char [])digits.get(Integer.class));

        int i = 12345678;
        System.out.println(i);
    }

    @Test
    public void test022(){
        String a  = "2130706434\t3232235521\t80\t\t\t1621232211\t\t\t\t456.456\t123.123\t01\t\t10\t\t\t1070123\t02\tsha@mtime.com\t100049\t2130706434\t1621232211\twww.mtime.com\t\t\t\t\t\t\t\tsha莎莎\t\t\t\t";
        String[] split = a.split("\t");
        for(int i = 0;i<split.length;i++){
            String sd = "没有  ";
            if (split[i].length()>0){
                sd = split[i];
            }
            System.out.println(i+"============="+sd);
        }
    }

    private void updateChinese(char[] aaa) {
        for (int i = 0;i<aaa.length;i++) {
            switch (aaa[i]){
                case '0':
                    aaa[i] = '零';
                    break;
                case '1':
                    aaa[i] = '壹';
                    break;
                case '2':
                    aaa[i] = '贰';
                    break;
                case '3':
                    aaa[i] = '叁';
                    break;
                case '4':
                    aaa[i] = '肆';
                    break;
                case '5':
                    aaa[i] = '伍';
                    break;
                case '6':
                    aaa[i] = '陆';
                    break;
                case '7':
                    aaa[i] = '柒';
                    break;
                case '8':
                    aaa[i] = '扒';
                    break;
                case '9':
                    aaa[i] = '玖';
                    break;

            }
        }
    }
}
