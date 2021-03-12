import org.junit.Test;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author song.wang
 * @Date 2021-03-08
 */
public class ExecutorServiceTest {

    @Test
    public void test01() throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 10, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(5));
        for (int i = 1;i<=50;i++){
            final int a = i;
            executor.execute(()->{
                System.out.println(Thread.currentThread().getName()+"===================="+a);
            });
        }

        Thread.sleep(100000);
        System.out.println("===========");
    }
}
