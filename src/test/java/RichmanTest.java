import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Stream;

/**
 * @Description:
 * @author: wei.cui
 * @Date: 2021-01-12
 */
public class RichmanTest{

    @Test
    public void articleImportTaskIds() throws InterruptedException {
        //String url = "http://front-gateway.mtime.com/richman/myPocket.api";
        String url = "http://front-gateway.mtime.com/richman/addAuction.api?toolCardId=14&timeLimited=2&startPrice=50&fixPrice=80000";
        String cockie = "_mu_=D68C48CBC60BCB311CE602BCFF04A436; _mi_=ffd94f27f63d8ec7c43c8f644e11dab1";
        List<Integer> list = new ArrayList<>();

        ExecutorService executorService = new ThreadPoolExecutor(100, 100,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());
        for (int i = 0; i < 100 ; i++) {
            executorService.submit(() -> {
                CloseableHttpClient httpclient = HttpClients.createDefault();
                HttpPost request = new HttpPost(url);
                request.addHeader("Cookie", cockie);
                CloseableHttpResponse response = null;
                try {
                    response = httpclient.execute(request);
                    System.out.println(response.getStatusLine());
                    HttpEntity entity1 = response.getEntity();
                    printJsonln(EntityUtils.toString(entity1));
                } catch (IOException e) {
                } finally {
                    try {
                        response.close();
                    } catch (IOException e) {
                    }
                }
            });
        }
        executorService.awaitTermination(1, TimeUnit.MINUTES);
        printJsonln("end");
    }

    private void printJsonln(String end) {
        System.out.println(end);
    }
}
