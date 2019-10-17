package org.amm.icm.system.controller;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class test1 {
	public static void main(String[] args) {
		HttpHeaders requestHeaders = new HttpHeaders();
		/*List<String> list=new ArrayList<String>();
		list.add("content-type");
		list.add("X-Forwarded-For");
		list.add("HTTP_X_FORWARDED_FOR");
		list.add("HTTP_CLIENT_IP");
		requestHeaders.setHost(new InetSocketAddress("121.40.74.2", 5001));
		requestHeaders.add("X-Forwarded-For", "121.40.74.3");
        requestHeaders.add("HTTP_X_FORWARDED_FOR", "121.40.74.3");
        requestHeaders.add("HTTP_CLIENT_IP", "121.40.74.3");
		//requestHeaders.setHost(new InetSocketAddress("http://event.zhuan-dao.cn", 5001));
		requestHeaders.setAccessControlRequestHeaders(list);*/
		requestHeaders.setAccessControlRequestMethod(HttpMethod.POST);
		requestHeaders.setOrigin("180.167.176.170");
		requestHeaders.add("Referer", "http://event.zhuan-dao.cn/works/13410?from=groupmessage&isappinstalled=0");
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> entity1 = new HttpEntity<>(requestHeaders);
		String url = "http://121.40.74.3:5001/api/votes/20191015";
		restTemplate.optionsForAllow(url, entity1);
	}
}
