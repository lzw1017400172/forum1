package com.kaishengit;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by loveoh on 2016/12/23.
 */
public class Test2 {
    public static void main(String[] args) throws IOException {


        for (int j = 4; j < 10; j++) {

            Document document = Jsoup.connect("http://www.mm131.com/xinggan/list_6_"+j+".html")
                    .cookie("is_click", "1")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36")
                    .get();

            //Elements elements = document.select("#msy .t >a");
            Elements elements = document.select(".main .list-left  dd >a");
            //Elements elements = document.select(".zm-item-answer .zm-editable-content img");
            System.out.println(elements);
            for (int i = 0; i < elements.size(); i++) {
                Element element = elements.get(i);
                String href = element.attr("href");


                Document bigDocument1 = Jsoup.connect(href)
                        .cookie("is_click", "1")
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36")
                        .get();
                Element bigElement1 = bigDocument1.select(".content .content-page span").first();
                int length = Integer.parseInt(bigElement1.text().substring(1,3));


                href = href.substring(0,href.lastIndexOf("."));

                System.out.println(href);
                for (int k = 2; k < length; k++) {
                    href=href+"_" + k + ".html";

                    Document bigDocument = Jsoup.connect(href)
                            .cookie("is_click", "1")
                            .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36")
                            .get();
                    Element bigElement = bigDocument.select(".content .content-pic img").first();
                    //Element bigElement = bigDocument.select("#item-tip").first();
                    String imgsrc = bigElement.attr("src");
                    System.out.println(imgsrc);

                    CloseableHttpClient httpClient = HttpClients.createDefault();
                    HttpGet httpGet = new HttpGet(imgsrc);
                    HttpResponse response = httpClient.execute(httpGet);
                    if (response.getStatusLine().getStatusCode() == 200) {
                        InputStream inputStream = response.getEntity().getContent();
                        String uuid = UUID.randomUUID().toString();
                        String imageName = uuid +".jpg";
                        OutputStream outputStream = new FileOutputStream("D:/扒图/mm131/" +imageName);
                        href = href.substring(0,href.lastIndexOf("_"));
                        IOUtils.copy(inputStream, outputStream);
                        outputStream.flush();
                        outputStream.close();
                        inputStream.close();
                    } else {
                        System.out.println("error = " + response.getStatusLine().getStatusCode());
                    }
                }
            }
        }
    }
}