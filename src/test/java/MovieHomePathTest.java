import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;

/**
 * @Description
 * @Author song.wang
 * @Date 2021-05-08
 */
public class MovieHomePathTest {
    @Test
    public void test01(){

        CloseableHttpClient httpclient = HttpClients.createDefault();
        String url = "";
        HttpPost request = new HttpPost(url);
        String cockie = "";
        request.addHeader("Cookie", cockie);
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(request);
            System.out.println(response.getStatusLine());
            HttpEntity entity1 = response.getEntity();
            System.out.println(EntityUtils.toString(entity1));
        } catch (IOException e) {
        } finally {
            try {
                response.close();
            } catch (IOException e) {
            }
        }
    }
}
