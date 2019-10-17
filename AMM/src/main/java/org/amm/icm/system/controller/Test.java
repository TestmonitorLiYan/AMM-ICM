package org.amm.icm.system.controller;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;

public class Test {
	public static void main(String args []) {
		HttpHeaders requestHeaders = new HttpHeaders();
		Random random = new Random(System.currentTimeMillis());
		String ip = (random.nextInt(255) + 1) + "." + (random.nextInt(255) + 1) + "." + (random.nextInt(255) + 1) + "."
                + (random.nextInt(255) + 1);
		System.out.println("======="+ip);
		ip="121.40.74.3";
		requestHeaders.add("X-Forwarded-For", ip);
        requestHeaders.add("HTTP_X_FORWARDED_FOR", ip);
        requestHeaders.add("HTTP_CLIENT_IP", ip);
        requestHeaders.setHost(new InetSocketAddress("121.40.74.2", 5001));
        requestHeaders.add("Accept", "application/json, text/plain, */*");
        requestHeaders.add("Content-Type", "application/json;charset=UTF-8");
        requestHeaders.add("Origin", "http://event.zhuan-dao.cn");
        requestHeaders.add("Access-Control-Allow-Headers", "content-type");
        requestHeaders.add("Access-Control-Allow-Methods", "POST");
        requestHeaders.add("Access-Control-Allow-Origin", "**");
        requestHeaders.add("Access-Control-Max-Age", "3628800");
        requestHeaders.add("Referer", "http://event.zhuan-dao.cn/works/13410?from=groupmessage&isappinstalled=0");
        //HttpEntity
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://121.40.74.3:5001/api/votes/20191015";
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("worksID", "13410");
        jsonObj.put("openID", "1");
        jsonObj.put("nickname", "qian");
        jsonObj.put("count", "1");
        HttpEntity<String> entity = new HttpEntity<>(jsonObj.toString(), requestHeaders);
        ResponseEntity<JSONObject> exchange = restTemplate.exchange(url,
                                          HttpMethod.POST, entity, JSONObject.class);
	}
}
