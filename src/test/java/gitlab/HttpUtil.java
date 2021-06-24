package gitlab;

import lombok.Getter;
import lombok.Setter;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author song.wang
 * @Date 2021-05-31
 */
@Getter
@Setter
public class HttpUtil {
    public Map<String,String> cookie = new HashMap<>();
    CloseableHttpClient httpclient = HttpClients.createDefault();
    String authenticity_token = "";

    public static void main(String[] args) {
        HttpUtil httpUtil = new HttpUtil();
        //请求 cookie
        //httpUtil.doGet("http://gitlab.wandatech-dev.com",null);
        //302
        MyResponse myResponse = httpUtil.doGet("http://gitlab.wandatech-dev.com/users/sign_in", null);
        for (String s : myResponse.getCookie()) {
            httpUtil.dealCookie(s);
        }
        String entity = myResponse.getEntity();
        int i = entity.indexOf("name=\"csrf-token\"");
        int start = entity.indexOf("content=\"", i);
        int end = entity.indexOf("\"", start + "content=\"".length());
        String substring = entity.substring(start + "content=\"".length(), end);
        httpUtil.setAuthenticity_token(substring);
        //登录
        ArrayList<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("utf8","✓"));
        pairs.add(new BasicNameValuePair("authenticity_token",httpUtil.getAuthenticity_token()));
        pairs.add(new BasicNameValuePair("user[login]","song.wang@mtime.com"));
        pairs.add(new BasicNameValuePair("user[password]","wangsong"));
        pairs.add(new BasicNameValuePair("user[remember_me]","0"));
        MyResponse myResponse2 = httpUtil.doPostForm("http://gitlab.wandatech-dev.com/users/sign_in", httpUtil.transCookie(), pairs);
        for (String s : myResponse2.getCookie()) {
            httpUtil.dealCookie(s);
        }
        MyResponse myResponse1 = httpUtil.doGet("http://gitlab.wandatech-dev.com/mtime/common/icp/icp-admin-service/pipelines.json?scope=all&page=1", httpUtil.transCookie());
        System.out.println(myResponse1.entity);
    }

    public MyResponse doPostForm(String url,String cookie,ArrayList<NameValuePair> pairs){
        MyResponse myResponse = new MyResponse();
        HttpPost request = new HttpPost(url);
        request.addHeader("Cookie", cookie);
        request.setEntity(new UrlEncodedFormEntity(pairs, StandardCharsets.UTF_8));
        CloseableHttpResponse response = null;
        try {

            response = httpclient.execute(request);
            myResponse.setStatus(response.getStatusLine());
            HttpEntity entity1 = response.getEntity();
            myResponse.setEntity(EntityUtils.toString(entity1));
            Header[] allHeaders = response.getAllHeaders();
            for (Header header : allHeaders) {
                if (header.getName().equals("Set-Cookie")){
                    myResponse.getCookie().add(header.getValue());
                }
            }
        } catch (IOException e) {
        } finally {
            try {
                response.close();
            } catch (IOException e) {
            }
        }

        return myResponse;
    }

    public MyResponse doSome(String url,String cookie){
        MyResponse myResponse = new MyResponse();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost request = new HttpPost(url);
        request.addHeader("Cookie", cookie);
        ArrayList<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("",""));
        request.setEntity(new UrlEncodedFormEntity(pairs, StandardCharsets.UTF_8));
        CloseableHttpResponse response = null;
        try {

            response = httpclient.execute(request);
            System.out.println(response.getStatusLine());
            HttpEntity entity1 = response.getEntity();

            Header[] allHeaders = response.getAllHeaders();
            for (Header header : allHeaders) {
                System.out.println(header);
            }
            printJsonln(EntityUtils.toString(entity1));
        } catch (IOException e) {
        } finally {
            try {
                response.close();
            } catch (IOException e) {
            }
        }
        return myResponse;
    }

    public MyResponse doGet(String url,String cookie){
        MyResponse myResponse = new MyResponse();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Cookie",cookie);
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpGet);

            myResponse.setStatus(response.getStatusLine());
            myResponse.setEntity(EntityUtils.toString(response.getEntity()));
            println("请求",url,"返回结果状态：",response.getStatusLine().toString());
            for (Header header : response.getAllHeaders()) {
                if (header.getName().equals("Set-Cookie")){
                    myResponse.getCookie().add(header.getValue());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (response!=null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return myResponse;
    }


    public void printJsonln(String end) {
        System.out.println(end);
    }

    public void printEntity(HttpEntity entity){
        System.out.println("开始打印entity");
        String s = null;
        try {
            s = EntityUtils.toString(entity);
        } catch (IOException e) {
            System.out.println("无法转换为string");
        }
        System.out.println(s);
        System.out.println("打印entity结束");
    }

    public void printHeaders(Header[] headers){
        System.out.println("开始打印header");
        for (Header header : headers) {
            System.out.println(header);
        }
        System.out.println("打印header结束");
    }

    public void dealCookie(String cookieStr){
        if (cookieStr!=null){
            String[] split = cookieStr.split("[;=]");
            cookie.put(split[0].trim(),split[1].trim());
        }
    }

    public String transCookie(){
        StringBuilder sb = new StringBuilder();
        if (cookie.size()>0)
            cookie.forEach((k,v)->sb.append(k).append("=").append(v).append(";"));
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

    public void println(String... ss){

        for (String s : ss) {
            System.out.print(s);
            System.out.print(" ");
        }
        System.out.println();
    }

    @Setter
    @Getter
    public static class MyResponse{
        private String entity;
        private StatusLine status;
        private ArrayList<String> cookie = new ArrayList<>();//setCookie
    }
}
