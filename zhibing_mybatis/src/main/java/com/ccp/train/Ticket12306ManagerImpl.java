package com.ccp.train;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Component
public class Ticket12306ManagerImpl {

    public DefaultHttpClient login(String userName, String password) throws Exception {

        // 创建HttpClient对象
        DefaultHttpClient httpclient = createHttpClient();

        // 获得sessionId
        getSessionId(httpclient);

        // 获得验证码
        File imgFile = getSeccodeFile(httpclient);

        checkUser(httpclient);

        loginAysnSuggest(httpclient, userName, password, null, imgFile);

        if (imgFile.exists()) {
            imgFile.delete();
        }

        return httpclient;
    }

    // 获取常用联系人列表
    public String getPassengerDTOs(HttpClient httpclient) {
        String POST_UTL_Passenger = "https://kyfw.12306.cn/otn/confirmPassenger/getPassengerDTOs";

        List<NameValuePair> parameters1 = new ArrayList<NameValuePair>();
        String responseHTML = postHttpRequestAsString(httpclient, POST_UTL_Passenger, parameters1, null);

        return responseHTML;
    }

    // 更新联系人
    public String updatePassengerDTO(HttpClient httpclient, String email) {
        String url = "https://kyfw.12306.cn/otn/passengers/edit";

        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("passenger_name", "陈建华"));
        parameters.add(new BasicNameValuePair("old_passenger_name", "陈建华"));
        parameters.add(new BasicNameValuePair("sex_code", "M"));
        parameters.add(new BasicNameValuePair("_birthDate", "2015-07-07"));
        parameters.add(new BasicNameValuePair("country_code", "CN"));
        parameters.add(new BasicNameValuePair("passenger_id_type_code", "1"));
        parameters.add(new BasicNameValuePair("old_passenger_id_type_code", "1"));
        parameters.add(new BasicNameValuePair("passenger_id_no", "440524195811155413"));
        parameters.add(new BasicNameValuePair("old_passenger_id_no", "440524195811155413"));

        parameters.add(new BasicNameValuePair("mobile_no", "13682856053"));
        parameters.add(new BasicNameValuePair("phone_no", ""));
        parameters.add(new BasicNameValuePair("email", email));
        parameters.add(new BasicNameValuePair("address", ""));
        parameters.add(new BasicNameValuePair("postalcode", ""));
        parameters.add(new BasicNameValuePair("passenger_type", "1"));
        parameters.add(new BasicNameValuePair("studentInfoDTO.province_code", "11"));
        parameters.add(new BasicNameValuePair("studentInfoDTO.school_code", ""));
        parameters.add(new BasicNameValuePair("studentInfoDTO.school_name", "简码/汉字"));
        parameters.add(new BasicNameValuePair("studentInfoDTO.department", ""));
        parameters.add(new BasicNameValuePair("studentInfoDTO.school_class", ""));
        parameters.add(new BasicNameValuePair("studentInfoDTO.student_no", ""));
        parameters.add(new BasicNameValuePair("studentInfoDTO.school_system", "1"));
        parameters.add(new BasicNameValuePair("studentInfoDTO.enter_year", "2015"));
        parameters.add(new BasicNameValuePair("studentInfoDTO.preference_card_no", ""));
        parameters.add(new BasicNameValuePair("studentInfoDTO.preference_from_station_name", "简码/汉字"));
        parameters.add(new BasicNameValuePair("studentInfoDTO.preference_from_station_code", ""));
        parameters.add(new BasicNameValuePair("studentInfoDTO.preference_to_station_name", "简码/汉字"));
        parameters.add(new BasicNameValuePair("studentInfoDTO.preference_to_station_code", ""));

        String responseHTML = postHttpRequestAsString(httpclient, url, parameters, null);

        return responseHTML;
    }

    // 删除联系人
    public String deletePassengerDTO(HttpClient httpclient, String name, String passengerIdNo) {
        String url = "https://kyfw.12306.cn/otn/passengers/delete";

        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("passenger_name", name));
        parameters.add(new BasicNameValuePair("passenger_id_type_code", "1"));
        parameters.add(new BasicNameValuePair("passenger_id_no", passengerIdNo));
        parameters.add(new BasicNameValuePair("isUserSelf", "N"));

        String responseHTML = postHttpRequestAsString(httpclient, url, parameters, null);

        return responseHTML;
    }

    // 添加联系人
    public String addPassengerDTO(HttpClient httpclient, String name, String passengerIdNo) {
        String url = "https://kyfw.12306.cn/otn/passengers/add";

        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("passenger_name", name));
        parameters.add(new BasicNameValuePair("sex_code", "M"));
        parameters.add(new BasicNameValuePair("_birthDate", ""));
        parameters.add(new BasicNameValuePair("country_code", "CN"));
        parameters.add(new BasicNameValuePair("passenger_id_type_code", "1"));
        parameters.add(new BasicNameValuePair("passenger_id_no", passengerIdNo));

        parameters.add(new BasicNameValuePair("mobile_no", ""));
        parameters.add(new BasicNameValuePair("phone_no", ""));
        parameters.add(new BasicNameValuePair("email", ""));
        parameters.add(new BasicNameValuePair("address", ""));
        parameters.add(new BasicNameValuePair("postalcode", ""));
        parameters.add(new BasicNameValuePair("passenger_type", "1"));
        parameters.add(new BasicNameValuePair("studentInfoDTO.province_code", "11"));
        parameters.add(new BasicNameValuePair("studentInfoDTO.school_code", ""));
        parameters.add(new BasicNameValuePair("studentInfoDTO.school_name", "简码/汉字"));
        parameters.add(new BasicNameValuePair("studentInfoDTO.department", ""));
        parameters.add(new BasicNameValuePair("studentInfoDTO.school_class", ""));
        parameters.add(new BasicNameValuePair("studentInfoDTO.student_no", ""));
        parameters.add(new BasicNameValuePair("studentInfoDTO.school_system", "1"));
        parameters.add(new BasicNameValuePair("studentInfoDTO.enter_year", "2015"));
        parameters.add(new BasicNameValuePair("studentInfoDTO.preference_card_no", ""));
        parameters.add(new BasicNameValuePair("studentInfoDTO.preference_from_station_name", ""));
        parameters.add(new BasicNameValuePair("studentInfoDTO.preference_from_station_code", ""));
        parameters.add(new BasicNameValuePair("studentInfoDTO.preference_to_station_name", ""));
        parameters.add(new BasicNameValuePair("studentInfoDTO.preference_to_station_code", ""));

        String responseHTML = postHttpRequestAsString(httpclient, url, parameters, null);

        return responseHTML;
    }

    // 获取未完成订单
    public String getNotDoneOrder(HttpClient httpclient) {
        String POST_UTL_Passenger = "https://kyfw.12306.cn/otn/queryOrder/queryMyOrderNoComplete";
        List<NameValuePair> parameters1 = new ArrayList<NameValuePair>();
        parameters1.add(new BasicNameValuePair("_json_att", null));
        String responseHTML = postHttpRequestAsString(httpclient, POST_UTL_Passenger, parameters1, null);

        return responseHTML;
    }

    // 获取已完成订单
    public String getDoneOrder(HttpClient httpclient) {
        String POST_UTL_Passenger = "https://kyfw.12306.cn/otn/queryOrder/queryMyOrder";

        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("queryType", "1"));
        parameters.add(new BasicNameValuePair("queryStartDate", "2015-07-01"));
        parameters.add(new BasicNameValuePair("queryEndDate", "2015-09-30"));
        parameters.add(new BasicNameValuePair("come_from_flag", "my_order"));
        parameters.add(new BasicNameValuePair("pageSize", "8"));
        parameters.add(new BasicNameValuePair("pageIndex", "0"));
        parameters.add(new BasicNameValuePair("query_where", "H"));
        parameters.add(new BasicNameValuePair("sequeue_train_name", null));
        String responseHTML = postHttpRequestAsString(httpclient, POST_UTL_Passenger, parameters, null);

        return responseHTML;
    }

    // 获取可退票订单
    public OrderDTO getRefundOrder(HttpClient httpclient) {
        String POST_UTL_Passenger = "https://kyfw.12306.cn/otn/queryOrder/queryMyOrder";

        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("queryType", "1"));
        parameters.add(new BasicNameValuePair("queryStartDate", "2015-10-02"));
        parameters.add(new BasicNameValuePair("queryEndDate", "2015-10-02"));
        parameters.add(new BasicNameValuePair("come_from_flag", "my_refund"));
        parameters.add(new BasicNameValuePair("pageSize", "8"));
        parameters.add(new BasicNameValuePair("pageIndex", "0"));
        parameters.add(new BasicNameValuePair("query_where", "G"));
        parameters.add(new BasicNameValuePair("sequeue_train_name", null));

        String responseHTML = postHttpRequestAsString(httpclient, POST_UTL_Passenger, parameters, null);
        OrderDTO orderDTO = JSON.parseObject(responseHTML, OrderDTO.class);

        return orderDTO;
    }

    // 检测用户
    private void checkUser(HttpClient httpclient) {
        String POST_UTL_LOGINACTION = "https://kyfw.12306.cn/otn/login/checkUser";
        List<NameValuePair> parameters1 = new ArrayList<NameValuePair>();
        parameters1.add(new BasicNameValuePair("_json_att", ""));
        String responseHTML = postHttpRequestAsString(httpclient, POST_UTL_LOGINACTION, parameters1, null);
        System.out.println("checkUser -> " + responseHTML);
    }

    public static String[] locationArray = new String[] {"37,41", "115,41", "186,43", "255,41", "35,116", "113,116",
                                                         "183,115", "259,117"};

    private String getArrlocation(int index) {
        if (index <= 0) {
            return null;
        }
        return locationArray[index - 1];
    }

    // 打码
    public static String getCodeResult(File file) throws IOException {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://10.218.132.231/codeBack12306.htm");

        Map<String, String> param = new HashMap<String, String>();
        param.put("agentid", " 3662263834");
        param.put("password", "5674b8aa367aa4dcca3982130ae0679c");// MD5加密后

        MultipartEntity mpEntity = new MultipartEntity(); // 文件传输
        ContentBody cbFile = new FileBody(file);
        mpEntity.addPart("myfile", cbFile);
        for (Map.Entry<String, String> entry : param.entrySet()) {
            mpEntity.addPart(entry.getKey(), new StringBody(entry.getValue()));
        }

        for (Map.Entry<String, String> entry : param.entrySet()) {
            mpEntity.addPart(entry.getKey(), new StringBody(entry.getValue()));
        }
        httpPost.setEntity(mpEntity);

        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity resEntity = response.getEntity();
        String responseStr = EntityUtils.toString(resEntity, "UTF-8");

        return responseStr.replaceAll("&quot;", "\"");
    }

    private String getCodeByString(String json) {
        JSONObject obj = JSONObject.parseObject(json);
        return obj.getJSONObject("module").getString("result");
    }

    // 登陆逻辑
    private boolean loginAysnSuggest(HttpClient httpclient,
                                     String loginUser,
                                     String loginPasswd,
                                     Map<String, String> cookieData,
                                     File imgFile) {
        String LOGIN_USERNAME = "loginUserDTO.user_name";
        String LOGIN_RANDCODE = "randCode";
        String LOGIN_RAND = "rand";
        String LOGIN_PASSWORD = "userDTO.password";

        String POST_UTL_LOGINACTION = "https://kyfw.12306.cn/otn/login/loginAysnSuggest";
        String CHECK_USER_CODE_ASY = "https://kyfw.12306.cn/otn/passcodeNew/checkRandCodeAnsyn";
        String checkcode = "";
        String[] allCodes = null;
        try {
            String codeResult = getCodeResult(imgFile);
            allCodes = getCodeByString(codeResult).split(",");
            String splitStr = "";
            if (allCodes.length > 1) {
                splitStr = ",";
            }
            for (int i = 0; i < allCodes.length; i++) {
                checkcode += getArrlocation(Integer.parseInt(allCodes[i])) + splitStr;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<NameValuePair> parametersForCode = new ArrayList<NameValuePair>();
        parametersForCode.add(new BasicNameValuePair(LOGIN_RAND, "sjrand"));
        parametersForCode.add(new BasicNameValuePair(LOGIN_RANDCODE, checkcode));
        String resultStr = postHttpRequestAsString(httpclient, CHECK_USER_CODE_ASY, parametersForCode, cookieData);

        if (resultStr.contains("TRUE")) {
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair(LOGIN_USERNAME, loginUser));
            parameters.add(new BasicNameValuePair(LOGIN_RANDCODE, checkcode));
            parameters.add(new BasicNameValuePair(LOGIN_PASSWORD, loginPasswd));

            String responseHTML = postHttpRequestAsString(httpclient, POST_UTL_LOGINACTION, parameters, cookieData);
            if (responseHTML.contains("欢迎您登录中国铁路客户服务中心网站")) {
                return true;
            }
        }

        return false;
    }

    /**
     * 返回POST请求响应字符串
     * 
     * @param httpClient
     * @param url
     * @param parameters
     * @param cookieData
     * @return
     */
    private String postHttpRequestAsString(HttpClient httpClient,
                                           String url,
                                           List<NameValuePair> parameters,
                                           Map<String, String> cookieData) {
        try {
            HttpResponse response = postHttpRequest(httpClient, url, parameters, cookieData);
            HttpEntity entity = response.getEntity();
            String responseHTML = EntityUtils.toString(entity).trim();
            return responseHTML;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * POST请求
     * 
     * @param httpclient
     * @param url
     * @param parameters
     * @param cookieData
     * @return
     */
    private HttpResponse postHttpRequest(HttpClient httpclient,
                                         String url,
                                         List<NameValuePair> parameters,
                                         Map<String, String> cookieData) {
        try {
            HttpPost post = new HttpPost(url);
            post.setHeader("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0; BOIE9;ZHCN)");
            if (cookieData != null) {
                boolean first = true;
                StringBuilder cookie = new StringBuilder();
                for (Map.Entry<String, String> me : cookieData.entrySet()) {
                    if (first) {
                        first = false;
                    } else {
                        cookie.append(";");
                    }
                    cookie.append(me.getKey() + "=" + me.getValue());
                }
                post.setHeader("Cookie", cookie.toString());
            }
            if (parameters != null) {
                UrlEncodedFormEntity uef = new UrlEncodedFormEntity(parameters, "UTF-8");
                post.setEntity(uef);
            }
            HttpResponse response = httpclient.execute(post);
            return response;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public String returnTicket(HttpClient httpclient, ReturnTicketRQ rq) {

        // _json_att:

        String POST_UTL_Passenger = "https://kyfw.12306.cn/otn/queryOrder/returnTicketAffirm";
        String url = "https://kyfw.12306.cn/otn/queryOrder/returnTicket";
        List<NameValuePair> parameters1 = new ArrayList<NameValuePair>();

        parameters1.add(new BasicNameValuePair("sequence_no", rq.getSequence_no()));
        parameters1.add(new BasicNameValuePair("batch_no", rq.getBatch_no()));
        parameters1.add(new BasicNameValuePair("coach_no", rq.getCoach_no()));
        parameters1.add(new BasicNameValuePair("seat_no", rq.getSeat_no()));
        parameters1.add(new BasicNameValuePair("start_train_date_page", rq.getStart_train_date_page()));
        parameters1.add(new BasicNameValuePair("train_code", rq.getTrain_code()));
        parameters1.add(new BasicNameValuePair("coach_name", rq.getCoach_name()));
        parameters1.add(new BasicNameValuePair("seat_name", rq.getSeat_name()));
        parameters1.add(new BasicNameValuePair("seat_type_name", rq.getSeat_type_name()));
        parameters1.add(new BasicNameValuePair("train_date", rq.getTrain_date()));
        parameters1.add(new BasicNameValuePair("from_station_name", rq.getFrom_station_name()));
        parameters1.add(new BasicNameValuePair("to_station_name", rq.getTo_station_name()));
        parameters1.add(new BasicNameValuePair("start_time", rq.getStart_time()));
        parameters1.add(new BasicNameValuePair("passenger_name", rq.getPassenger_name()));
        parameters1.add(new BasicNameValuePair("_json_att", ""));
        String responseHTML = postHttpRequestAsString(httpclient, POST_UTL_Passenger, parameters1, null);

        parameters1 = new ArrayList<NameValuePair>();
        parameters1.add(new BasicNameValuePair("_json_att", ""));

        responseHTML = postHttpRequestAsString(httpclient, url, parameters1, null);

        return responseHTML;
    }

    private static DefaultHttpClient createHttpClient() throws Exception {
        // 获得httpclient对象
        X509TrustManager tm = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        
        DefaultHttpClient httpclient = new DefaultHttpClient();
        SSLContext sslcontext = SSLContext.getInstance("TLS");
        sslcontext.init(null, new TrustManager[] {tm}, null);
        SSLSocketFactory ssf = new SSLSocketFactory(sslcontext);
        ClientConnectionManager ccm = new DefaultHttpClient().getConnectionManager();
        SchemeRegistry sr = ccm.getSchemeRegistry();
        sr.register(new Scheme("https", 443, ssf));
        HttpParams params = new BasicHttpParams();
        params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 8000);
        params.setParameter(CoreConnectionPNames.SO_TIMEOUT, 8000);
        httpclient = new DefaultHttpClient(ccm, params);
        httpclient.getParams().setParameter(HTTP.USER_AGENT, "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0; BOIE9;ZHCN)");
        return httpclient;
    }

    // 获取验证码
    private static File getSeccodeFile(HttpClient httpclient) throws Exception {
        String urlString2 = "https://kyfw.12306.cn/otn/passcodeNew/getPassCodeNew?module=login&rand=sjrand&"
                            + Math.random();
        // 获得HttpGet对象
        HttpGet httpGet2 = new HttpGet(urlString2);

        // 发送请求
        HttpResponse response2 = httpclient.execute(httpGet2);

        Header[] hrs2 = response2.getHeaders("Set-Cookie");
        System.out.println("2");
        for (Header hr : hrs2) {
            System.out.println(hr.getValue());
        }
        System.out.println("--------");

        // 输出返回值
        InputStream is = response2.getEntity().getContent();

        BufferedInputStream in = new BufferedInputStream(is);

        File tempDir = new File("D://12306");
        if (!tempDir.exists()) { // 存储目录不存在，则创建目录
            tempDir.mkdirs();
        }

        // 生成图片
        String fileName = "D://12306/" + System.currentTimeMillis() + ".png";

        // 保存的图片文件名
        File img = new File(fileName);

        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(img));

        byte[] buf = new byte[1];

        while (in.read(buf) != -1) {
            out.write(buf);
        }

        in.close();
        out.close();

        httpGet2.releaseConnection();
        return img;
    }

    /**
     * @Title: oneGoal
     * @Description: TODO
     * @author renfy
     * @date 2013-7-25 上午11:04:34
     * @param @param httpclient
     *
     */
    private static void getSessionId(HttpClient httpclient) throws Exception {
        String urlString = "https://kyfw.12306.cn/otn/leftTicket/init";
        // 获得HttpGet对象
        HttpGet httpGet = new HttpGet(urlString);
        try {
            // 发送请求
            HttpResponse response = httpclient.execute(httpGet);
            // 输出返回值
            Header[] hrs = response.getHeaders("Set-Cookie");
            System.out.println("1");
            for (Header hr : hrs) {
                System.out.println(hr.getValue());
            }
            System.out.println("--------");

            httpGet.releaseConnection();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

}
