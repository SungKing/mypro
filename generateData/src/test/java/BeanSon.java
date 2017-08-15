import lombok.Data;

import java.math.BigDecimal;

/**
 * @author song.wang
 * @create 2017/7/28 15:12
 */
@Data
public class BeanSon {

    private String name;
    private BigDecimal haha;

    @Data
    public static class BeanSon2{
        private String name;
        private BigDecimal haha;
    }
}
