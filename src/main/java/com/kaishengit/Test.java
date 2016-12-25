package com.kaishengit;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by 刘忠伟 on 2016/12/23.
 */
public class Test {

    public static void main(String[] args) throws IOException{






       /*for(int j = 2;j<10;j++){//循环页数
            //解析document
            Document document = Jsoup.connect("http://www.mm131.com/xinggan/list_6_"+j+".html")
                    .cookie("is_click","1")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.75 Safari/537.36")
                    .get();

            Elements elements = document.select(".main dl dd a");//解析document确定位置标签唯一
            for(int i= 0;i<elements.size();i++){
                String url = elements.get(i).attr("href");
                System.out.println(url);
                //再解析document
                Document documents = Jsoup.connect(url)
                        .cookie("is_click","1")
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.75 Safari/537.36")
                        .get();

                Element elementss = documents.select(".content .content-pic a img").first();//再解析确定需要标签
                String src = elementss.attr("src");
                System.out.println(src);

                CloseableHttpClient httpClient = HttpClients.createDefault();
                HttpGet httpGet = new HttpGet(src);
                HttpResponse response = httpClient.execute(httpGet);
                if(response.getStatusLine().getStatusCode() == 200){
                    //响应输入流，拿到此响应的东西
                    InputStream inputStream = response.getEntity().getContent();
                    //文件名
                    String imgname = src.substring(src.lastIndexOf("/"));
                    FileOutputStream outputStream = new FileOutputStream("D:/扒图/mm131/"+imgname);
                    IOUtils.copy(inputStream,outputStream);

                    inputStream.close();
                    outputStream.flush();
                    outputStream.close();
                } else {
                    System.out.print("ERROR:"+ response.getStatusLine().getStatusCode());
                }



            }


        }
*/

      /*  for(int i= 391337;i<391360;i++) {
            Document documents = Jsoup.connect("http://www.ivsky.com/bizhi/emi_takei_v17532/pic_" + i + ".html#al_tit")
                    .cookie("is_click", "1")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.75 Safari/537.36")
                    .get();

            Element elementss = documents.select("#imgis").first();//再解析确定需要标签
            String src = elementss.attr("src");
            System.out.println(src);

            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(src);
            HttpResponse response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                //响应输入流，拿到此响应的东西
                InputStream inputStream = response.getEntity().getContent();
                //文件名
                String imgname = src.substring(src.lastIndexOf("/"));
                FileOutputStream outputStream = new FileOutputStream("D:/扒图/ivsky" + imgname);
                IOUtils.copy(inputStream, outputStream);

                inputStream.close();
                outputStream.flush();
                outputStream.close();
            } else {
                System.out.print("ERROR:" + response.getStatusLine().getStatusCode());
            }

        }*/


    }
}
