import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.junit.Test;

import java.time.Duration;
import java.util.Map;

/**
 * @Description
 * @Author song.wang
 * @Date 2021-03-24
 */
public class CaffeineTest {
    @Test
    public void test01() throws InterruptedException {

        Cache<Object, Object> cache = Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofSeconds(60))
                .expireAfterAccess(Duration.ofSeconds(60))
                .maximumSize(1000)
                .softValues()
                .build();
        for (int i = 0;i<50;i++){
            cache.put(i,i+"sssss");
        }
        System.out.println(cache.estimatedSize());
        System.out.println(cache.asMap().size());
        cache.asMap().forEach((k,v)-> System.out.println(k+"="+v));
        System.gc();
        Thread.sleep(1000);
        System.gc();
        Thread.sleep(1000);
        System.gc();
        Thread.sleep(1000);
        System.gc();
        Thread.sleep(1000);
        System.gc();
        Thread.sleep(1000);
        System.gc();
//        System.out.println(cache.estimatedSize());
//        System.out.println(cache.asMap().size());
        for (Map.Entry<Object, Object> objectEntry : cache.asMap().entrySet()) {
            System.out.println(objectEntry);
        }
        cache.asMap().forEach((k,v)-> System.out.println(k+"="+v));
        Thread.sleep(1000);
        System.out.println(cache.stats());
        System.out.println(cache.estimatedSize());


    }
}
