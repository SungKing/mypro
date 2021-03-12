import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;

/**
 * @Description
 * @Author song.wang
 * @Date 2021-03-05
 */
public class EliteTest {

    @Test
    public void testMovie() throws IOException {
        List<MovieWork> list = new ArrayList<>();
        String url = "http://cms-prd.elite-plan.com/author/works/listjson";
        String cockie = "eLiteAuth-prd=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjEwMCwiZW52IjoiZWxpdGUtcHJkIiwiZXhwIjo2Mzc1MDkyMzYxMTU4MDAwMDAsImNsaWVudElwIjoiMTAuMTE4LjE5Mi40MiJ9.ub_-fRqmX-JdHsNEjkAHTEk8mCZxBUYJbfyMJHfmq7w; Domain=elite-plan.com; Expires=Tue, 09-Mar-2021 05:53:31 GMT; Path=/; HttpOnly";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost request = new HttpPost(url);
        request.addHeader("Content-type", "application/x-www-form-urlencoded; charset=utf-8");
        request.addHeader("Cookie", cockie);
        for (int i = 1;;i++){
            String format = "approveStatus=2&createDateStart=2020-11-06&createDateEnd=2021-03-08&pageIndex=%d&pageSize=20&limitType=1";
            String params = String.format(format, i);
            StringEntity stringEntity = new StringEntity(params, Charset.forName("UTF-8"));
            request.setEntity(stringEntity);
            CloseableHttpResponse response = null;
            response = httpclient.execute(request);
            String s = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = JSONObject.parseObject(s);
            String items1 = jsonObject.getString("items");
            if (items1!=null){
                List<MovieWork> works = JSONObject.parseArray(items1, MovieWork.class);
                list.addAll(works);
            }else{
                break;
            }
        }
        list.sort(Comparator.comparingInt(w -> w.id));
        for (MovieWork work : list) {
            System.out.println(work);
            if (work.getBiographyFileName()!=null && !work.getBiographyFileName().equals("")){
                movieDown("电影/"+work.getTitle()+"/",work.getBiographyFileName(),work.getBiographyFileId());
            }
            if (work.getOutlineFileName()!=null&& !work.getOutlineFileName().equals("")){
                movieDown("电影/"+work.getTitle()+"/",work.getOutlineFileName(),work.getOutlineFileId());
            }
            if (work.getContentFileName()!=null && !work.getContentFileName().equals("")){
                movieDown("电影/"+work.getTitle()+"/",work.getContentFileName(),work.getContentFileId());
            }
            if (!StringUtils.isEmpty(work.getSummaryFileName())){
                movieDown("电影/"+work.getTitle()+"/",work.getSummaryFileName(),work.getSummaryFileId());
            }
        }

    }

    @Test
    public void test002Online() throws IOException {
        List<MovieWork> list = new ArrayList<>();
        String url = "http://cms-prd.elite-plan.com/author/works/listjson";
        String cockie = "eLiteAuth-prd=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjEwMCwiZW52IjoiZWxpdGUtcHJkIiwiZXhwIjo2Mzc1MDkyMzYxMTU4MDAwMDAsImNsaWVudElwIjoiMTAuMTE4LjE5Mi40MiJ9.ub_-fRqmX-JdHsNEjkAHTEk8mCZxBUYJbfyMJHfmq7w; Domain=elite-plan.com; Expires=Tue, 09-Mar-2021 05:53:31 GMT; Path=/; HttpOnly";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost request = new HttpPost(url);
        request.addHeader("Content-type", "application/x-www-form-urlencoded; charset=utf-8");
        request.addHeader("Cookie", cockie);
        for (int i = 1;;i++){
            String format = "approveStatus=2&createDateStart=2020-11-06&createDateEnd=2021-03-08&pageIndex=%d&pageSize=20&limitType=2";
            String params = String.format(format, i);
            StringEntity stringEntity = new StringEntity(params, Charset.forName("UTF-8"));
            request.setEntity(stringEntity);
            CloseableHttpResponse response = null;
            response = httpclient.execute(request);
            String s = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = JSONObject.parseObject(s);
            String items1 = jsonObject.getString("items");
            if (items1!=null){
                List<MovieWork> works = JSONObject.parseArray(items1, MovieWork.class);
                list.addAll(works);
            }else{
                break;
            }
        }
        list.sort(Comparator.comparingInt(w -> w.id));
        for (MovieWork work : list) {
            System.out.println(work);
            if (work.getBiographyFileName()!=null && !work.getBiographyFileName().equals("")){
                movieDown("网剧/"+work.getTitle()+"/",work.getBiographyFileName(),work.getBiographyFileId());
            }
            if (work.getOutlineFileName()!=null&& !work.getOutlineFileName().equals("")){
                movieDown("网剧/"+work.getTitle()+"/",work.getOutlineFileName(),work.getOutlineFileId());
            }
            if (work.getContentFileName()!=null && !work.getContentFileName().equals("")){
                movieDown("网剧/"+work.getTitle()+"/",work.getContentFileName(),work.getContentFileId());
            }
            if (!StringUtils.isEmpty(work.getSummaryFileName())){
                movieDown("网剧/"+work.getTitle()+"/",work.getSummaryFileName(),work.getSummaryFileId());
            }
        }

    }

    @Setter
    @Getter
    public static class Work{
        private int id;
        private int userId;
        private String title;

        @Override
        public String toString() {
            return "Work{" +
                    "id=" + id +
                    ", userId=" + userId +
                    ", title='" + title + '\'' +
                    '}';
        }
    }

    @Setter
    @Getter
    public static class MovieWork{
        private String biographyFileId;
        private String biographyFileName;
        private String outlineFileId;
        private String outlineFileName;
        private String contentFileId;
        private String contentFileName;
        private String summaryFileId;
        private String summaryFileName;
        private int id;
        private int userId;
        private String title;

        @Override
        public String toString() {
            return "MovieWork{" +
                    "biographyFileId='" + biographyFileId + '\'' +
                    ", biographyFileName='" + biographyFileName + '\'' +
                    ", outlineFileId='" + outlineFileId + '\'' +
                    ", outlineFileName='" + outlineFileName + '\'' +
                    ", contentFileId='" + contentFileId + '\'' +
                    ", contentFileName='" + contentFileName + '\'' +
                    ", summaryFileId='" + summaryFileId + '\'' +
                    ", summaryFileName='" + summaryFileName + '\'' +
                    ", id=" + id +
                    ", userId=" + userId +
                    ", title='" + title + '\'' +
                    '}';
        }
    }


    @Test
    public void test02() throws IOException {
        String format = "http://cms-prd.elite-plan.com/author/works/getAuthorWork?workId=%d&userId=%d";
        String url = "";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = httpclient.execute(httpGet);
        String s = EntityUtils.toString(response.getEntity());

    }


    public void movieDown(String path,String fileName,String fileId){
        if (fileId.startsWith("http")){
            System.out.println(fileId);
            int i = fileId.lastIndexOf("/");
            fileId = fileId.substring(i + 1);
            System.out.println(fileId);
        }
        BasicNameValuePair pair = new BasicNameValuePair("fileName",fileName);
        String format1 = URLEncodedUtils.format(Arrays.asList(pair), "UTF-8");
        String format = "http://cms-prd.elite-plan.com/author/works/download?fileid=%s&%s";
        String url = String.format(format, fileId, format1);

        System.out.println(url);
        download(url,path,fileName);
    }

    public static String download(String url, String filepath,String fileName) {
        try {
            HttpClient client = HttpClients.createDefault();
            HttpGet httpget = new HttpGet(url);
            String cockie = "eLiteAuth-prd=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjEwMCwiZW52IjoiZWxpdGUtcHJkIiwiZXhwIjo2Mzc1MDkyMzYxMTU4MDAwMDAsImNsaWVudElwIjoiMTAuMTE4LjE5Mi40MiJ9.ub_-fRqmX-JdHsNEjkAHTEk8mCZxBUYJbfyMJHfmq7w; Domain=elite-plan.com; Expires=Tue, 09-Mar-2021 05:53:31 GMT; Path=/; HttpOnly";
            httpget.addHeader("Cookie", cockie);
            HttpResponse response = client.execute(httpget);

            HttpEntity entity = response.getEntity();
            InputStream is = entity.getContent();

            if (filepath!=null){
                filepath = filepath+fileName;
            }else{
                filepath = fileName;
            }
            File file = new File(filepath);
            if (filepath!=null){
                file.getParentFile().mkdirs();
            }
            //file.getParentFile().mkdirs();
            FileOutputStream fileout = new FileOutputStream(file);
            /**
             * 根据实际运行效果 设置缓冲区大小
             */
            byte[] buffer = new byte[1024];
            int ch = 0;
            while ((ch = is.read(buffer)) != -1) {
                fileout.write(buffer, 0, ch);
            }
            is.close();
            fileout.flush();
            fileout.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getFileName(HttpResponse response) {
        Header contentHeader = response.getFirstHeader("Content-Disposition");
        String filename = null;
        if (contentHeader != null) {
            HeaderElement[] values = contentHeader.getElements();
            if (values.length == 1) {
                NameValuePair param = values[0].getParameterByName("filename");
                if (param != null) {
                    try {
                        //filename = new String(param.getValue().toString().getBytes(), "utf-8");
                        //filename=URLDecoder.decode(param.getValue(),"utf-8");
                        filename = param.getValue();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return filename;
    }
}
