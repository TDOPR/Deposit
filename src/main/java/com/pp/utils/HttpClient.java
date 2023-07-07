package com.pp.utils;

import com.alibaba.fastjson.JSON;
import com.pp.exception.HttpRequestException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.collections4.MapUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class HttpClient {

    private OkHttpClient httpClient;

    private AtomicInteger numRequestFaild = new AtomicInteger(0);

    static final MediaType JSON_TYPE = MediaType.parse("application/json");

    private HttpClient() {
		/*OkHttpClient.Builder builder = new OkHttpClient.Builder()
				.connectionPool(new ConnectionPool(200, 10, TimeUnit.SECONDS)).connectTimeout(3, TimeUnit.SECONDS)
				.readTimeout(5, TimeUnit.SECONDS);*/
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectionPool(new ConnectionPool(200, 10, TimeUnit.SECONDS)).connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS);
        httpClient = builder.build();
    }

    public static HttpClient getInstance() {
        return new HttpClient();
    }

    public String doGet(String url, Map<String, String> params) {
        Request.Builder reqBuild = new Request.Builder();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        if (MapUtils.isNotEmpty(params)) {
            params.forEach((k, v) -> {
                urlBuilder.addQueryParameter(k, v);
            });
        }
        reqBuild.url(urlBuilder.build());
        reqBuild.addHeader("TRON-PRO-API-KEY", "c15b86be-e273-462e-a2b3-3ff4fe713dc1");
        reqBuild.addHeader("Content-Type", "application/json");

        Response response = null;
        try {
            response = httpClient.newCall(reqBuild.build()).execute();
        } catch (IOException e) {
            if (e instanceof SocketTimeoutException) {
                numRequestFaild.getAndIncrement();
            }
            log.error("doGet http执行异常，url={1}, e:{2}" + url, e.getMessage());
            throw new HttpRequestException("http执行异常，url=" + url, e);
        }
        if (response.isSuccessful()) {
            try {
                reset();
                String result = response.body().string();
                //log.info("doGet 请求返回结果:{}", result);
                return result;
            } catch (IOException e) {
                log.error("doGet http结果解析异常，url={1}, e:{2}" + url, e);
                throw new HttpRequestException("http结果解析异常", e);
            }
        } else {
            int statusCode = response.code();
            try {
                String result = response.body().string();
                log.info("doGet 请求返回结果:{}", result);
                return result;
            } catch (IOException e) {
                log.error("doGet http结果解析异常，url={1}, e:{2}" + url, e);
                throw new HttpRequestException("http结果解析异常", e);
            }
            /*getRatelimitInfo(response);
            log.error("doGet 响应码不为200，返回响应码：" + statusCode + "，url:" + urlBuilder.build());
            throw new HttpRequestException("响应码不为200，返回响应码：" + statusCode + "，url：" + urlBuilder.build());*/
        }
    }

    public String doPost(String url, Map<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();
        if (MapUtils.isNotEmpty(params)) {
            params.forEach((k, v) -> {
                builder.add(k, v);
            });
        }

        Request.Builder reqBuilder = new Request.Builder().url(url);
        if (MapUtils.isNotEmpty(params)) {
            reqBuilder.post(builder.build());
        }

        Response response = null;
        try {
            response = httpClient.newCall(reqBuilder.build()).execute();
        } catch (IOException e) {
            if (e instanceof SocketTimeoutException) {
                numRequestFaild.getAndIncrement();
            }
            log.error("doPost http执行异常，url={1}, e:{2}" + url, e.getMessage());
            throw new HttpRequestException("http执行异常，url=" + url, e);
        }
        if (response.isSuccessful()) {
            try {
                reset();
                String result = response.body().string();
                log.info("doPost 请求返回结果:{}", result);
                return result;
            } catch (IOException e) {
                log.error("doPost http结果解析异常，url={1}, e:{2}" + url, e);
                throw new HttpRequestException("http结果解析异常", e);
            }
        } else {
            int statusCode = response.code();
            getRatelimitInfo(response);
            log.error("doPost 响应码不为200，返回响应码：" + statusCode + "，url：" + reqBuilder.build());
            throw new HttpRequestException("响应码不为200，返回响应码：" + statusCode + "，url：" + reqBuilder.build());
        }
    }

    public String doPostJson(String url, Map<String, String> params) {
        RequestBody body = RequestBody.create(JSON_TYPE, JSON.toJSONString(params));
        Request request = new Request.Builder().url(url).post(body).build();
        Response response = null;
        try {
            response = httpClient.newCall(request).execute();
        } catch (IOException e) {
            throw new HttpRequestException("doPostJson http执行异常，url=" + url, e);
        }
        if (response.isSuccessful()) {
            try {
                String result = response.body().string();
                log.info("doPostJson 请求返回结果:{}", result);
                return result;
            } catch (IOException e) {
                throw new HttpRequestException("doPostJson http结果解析异常", e);
            }
        } else {
            int statusCode = response.code();
            getRatelimitInfo(response);
            log.error("doPostJson 响应码不为200，返回响应码：" + statusCode + "，url：" + request);
            throw new HttpRequestException("响应码不为200，返回响应码：" + statusCode + "，url：" + request);
        }
    }


    public String doPost(String url, String params) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost(new URI(url));
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-Type", "application/json");
            String charSet = "UTF-8";
            StringEntity entity = new StringEntity(params, charSet);
            httpPost.setEntity(entity);
            response = httpclient.execute(httpPost);
        } catch (IOException | URISyntaxException e) {
            throw new HttpRequestException("doPostJson http执行异常，url=" + url, e);
        }
        StatusLine status = response.getStatusLine();
        int state = status.getStatusCode();
        if (state == HttpStatus.SC_OK) {
            try {
                HttpEntity responseEntity = response.getEntity();
                String result = EntityUtils.toString(responseEntity);
                log.info("doPostJson 请求返回结果:{}", result);
                return result;
            } catch (IOException e) {
                throw new HttpRequestException("doPostJson http结果解析异常", e);
            } finally {
                if (response != null) {
                    try {
                        response.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    httpclient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            log.error("doPostJson 响应码不为200，返回响应码：{},url:{},参数：{}",state, url,params);
            throw new HttpRequestException("响应码不为200，返回响应码：" + state + "，url：" + url);
        }
    }

    private void reset() {
        numRequestFaild.set(0);
    }

    public int getRequestFaildTotal() {
        return numRequestFaild.get();
    }


    public void getRatelimitInfo(Response response) {

        String rateLimtLimt = response.header("ratelimit-limit");//单轮请求数上限，单位：次数
        String rateLimtinterval = response.header("ratelimit-interval");//：请求数重置的时间间隔，单位：毫秒
        String rateLimtRemaining = response.header("ratelimit-remaining");//：本轮剩余可用请求数，单位：次数
        String rateLimtReset = response.header("ratelimit-reset");//：请求数上限重置时间，单位：毫秒

        log.info("火币接口访问次数限制信息, 单轮请求数上限:{}, 请求数重置的时间间隔:{}, 本轮剩余可用请求数:{},请求数上限重置时间:{}",
                rateLimtLimt, rateLimtinterval, rateLimtRemaining, rateLimtReset);
    }

}
