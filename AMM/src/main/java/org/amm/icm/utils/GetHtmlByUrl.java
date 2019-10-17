package org.amm.icm.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class GetHtmlByUrl {
	public static void main(String[] args) {
		try {
            //创建一个URL实例
            URL url = new URL("https://www.baidu.com/s?wd=ERROR%3A%20%20%27%E5%9C%A8%20publicId%20%E5%92%8C%20systemId%20%E4%B9%8B%E9%97%B4%E9%9C%80%E8%A6%81%E6%9C%89%E7%A9%BA%E6%A0%BC%E3%80%82%27&rsv_spt=1&rsv_iqid=0xbbaebe2900006746&issp=1&f=8&rsv_bp=1&rsv_idx=2&ie=utf-8&rqlang=cn&tn=baiduhome_pg&rsv_enter=0&rsv_dl=tb&inputT=1705&rsv_t=26298YUbIk84vNzvnPakutvY7OKwidE%2BABtQM89bLs3Tia4R%2BwBNBibKuIO65%2Ftb8T7Z&oq=html%25E8%25BD%25ACPDF&rsv_pq=ae78b82e0012e2ac&rsv_sug3=130&rsv_sug1=61&rsv_sug7=100&rsv_n=2&prefixsug=%2526gt%253BRROR%253A%2520%2520%2526%252339%253B%25E5%259C%25A8%2520publicId%2520%25E5%2592%258C%2520systemId%2520%25E4%25B9%258B%25E9%2597%25B4%25E9%259C%2580%25E8%25A6%2581%25E6%259C%2589%25E7%25A9%25BA%25E6%25A0%25BC%25E3%2580%2582%2526%252339%253B&rsp=0&rsv_sug4=2209&rsv_sug=1");

            try {
                //通过URL的openStrean方法获取URL对象所表示的自愿字节输入流
                InputStream is = url.openStream();
                InputStreamReader isr = new InputStreamReader(is,"UTF-8");

                //为字符输入流添加缓冲
                BufferedReader br = new BufferedReader(isr);
                String data = br.readLine();//读取数据
                String htmlCode="";
                while (data!=null){//循环读取数据
                    System.out.println(data);//输出数据
                    htmlCode=htmlCode+data;
                    data = br.readLine();
                }
                HtmlCodeToPdf.htmlCodeToPdf(htmlCode, "D:\\itextFile\\first\\htmlcode.pdf");
                br.close();
                isr.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
