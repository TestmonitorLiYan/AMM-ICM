package org.amm.icm.system.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

public class getMethodTest {
	public void doGetTestOne() {
		// 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		// 创建Get请求
		HttpGet httpGet = new HttpGet("http://localhost:12345/doGetControllerOne");
 
		// 响应模型
		CloseableHttpResponse response = null;
		try {
			// 由客户端执行(发送)Get请求
			response = httpClient.execute(httpGet);
			// 从响应模型中获取响应实体
			HttpEntity responseEntity = response.getEntity();
			System.out.println("响应状态为:" + response.getStatusLine());
			if (responseEntity != null) {
				System.out.println("响应内容长度为:" + responseEntity.getContentLength());
				System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 释放资源
				if (httpClient != null) {
					httpClient.close();
				}
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		 
		for(int i=0;i<=1000000000;i++) {
		Random random = new Random(System.currentTimeMillis());
		int num=random.nextInt(1000000000);
		// 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		// 创建Post请求
		HttpOptions httpPost=new HttpOptions("http://121.40.74.3:5001/api/votes/20191015");
		HttpPost httpPost1 = new HttpPost("http://121.40.74.3:5001/api/votes/20191015");
		 Map<String, Object> paramsMap = new HashMap<>();
		 paramsMap.put("worksID", "13410");
		 paramsMap.put("openID", num);
		 paramsMap.put("nickname", "qian");
		 paramsMap.put("count", "1");
	     String jsonString = JSONObject.toJSONString(paramsMap);
	     RequestConfig requestConfig = RequestConfig.custom()
	                .setConnectTimeout(10000)
	                .setConnectionRequestTimeout(10000)
	                .setSocketTimeout(10000)
	                .build();
	     httpPost1.setConfig(requestConfig);
        // 将请求实体设置到httpPost对象中
	     StringEntity entity = new StringEntity(jsonString, ContentType.APPLICATION_JSON);
	       httpPost1.setEntity(entity);
		// 响应模型
        httpPost1.setHeader("Accept", "application/json, text/plain, */*");
		httpPost1.setHeader("Content-Type","application/json;charset=UTF-8");
		httpPost1.setHeader("Origin","http://event.zhuan-dao.cn");
		httpPost1.setHeader("Referer","http://event.zhuan-dao.cn/works/13410?from=groupmessage&isappinstalled=0");
		httpPost1.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.121 Safari/537.36");
		httpPost1.setHeader("HTTP_CLIENT_IP", "121.40.74.121");
		httpPost1.setHeader("HTTP_X_FORWARDED_FOR", "121.40.74.121");
		httpPost1.setHeader("X-Forwarded-For", "121.40.74.121");
		
		httpPost.setHeader("Access-Control-Request-Headers", "content-type");
		httpPost.setHeader("Access-Control-Request-Method","POST");
		httpPost.setHeader("Origin","http://event.zhuan-dao.cn");
		httpPost.setHeader("HTTP_CLIENT_IP", "121.40.74.121");
		httpPost.setHeader("HTTP_X_FORWARDED_FOR", "121.40.74.121");
		httpPost.setHeader("X-Forwarded-For", "121.40.74.121");
		httpPost.setHeader("Referer","http://event.zhuan-dao.cn/works/13410?from=groupmessage&isappinstalled=0");
		httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.121 Safari/537.36");
		CloseableHttpResponse response = null;
		CloseableHttpResponse response1 = null;
		try {
			// 由客户端执行(发送)Post请求
			response = httpClient.execute(httpPost);
			response1 = httpClient.execute(httpPost1);
			// 从响应模型中获取响应实体
			HttpEntity responseEntity = response.getEntity();
			HttpEntity responseEntity1 = response1.getEntity();
			System.out.println("响应状态为:" + response.getStatusLine());
			System.out.println("----"+response.toString());
			if (responseEntity != null) {
				System.out.println("响应内容长度为:" + responseEntity.getContentLength());
				System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
			}
			System.out.println("1响应状态为:" + response1.getStatusLine());
			System.out.println("----"+response1.toString());
			if (responseEntity1 != null) {
				System.out.println("1响应内容长度为:" + responseEntity1.getContentLength());
				System.out.println("1响应内容为:" + EntityUtils.toString(responseEntity1));
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 释放资源
				if (httpClient != null) {
					httpClient.close();
				}
				if (response != null) {
					response.close();
				}
				if (response1 != null) {
					response1.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		}
	}
}
