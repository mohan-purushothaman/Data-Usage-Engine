/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.verizon.du.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import org.verizon.du.core.BaseConfig;

/**
 *
 * @author Mohan Purushothaman
 */
public class TestDataGenerator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        long totalRecords = 5000000;
        int customerCount = 20000;

        if (args.length > 0) {
            totalRecords = Long.parseLong(args[0]);
            if (args.length > 1) {
                customerCount = Integer.parseInt(args[1]);
            }
        }
        Random r = new Random();
        String[] customerIds = new String[customerCount];
        int index = 0;

        try (RandomAccessFile r1 = new RandomAccessFile("D:\\test\\insert.sql", "rw")) {

            for (int j = 34567890 - customerCount; j < 34567890; j++) {
                customerIds[index++] = String.valueOf(j);
                r1.writeBytes("INSERT INTO CUST_DETAILS VALUES ('" + j + "','" + bc[r.nextInt(bc.length)]
                        + "','a@b.com','9790887567');\nINSERT INTO USAGE_INFO(CUSTID) VALUES('" + j + "');\n");
            }
        }
        DateFormat df = new SimpleDateFormat(BaseConfig.DATE_FORMAT);
        try (RandomAccessFile r1 = new RandomAccessFile("D:\\test\\input.su", "rw")) {
            for (int i = 0; i < totalRecords; i++) {
                Date date = new Date();
                date.setTime(date.getTime() - r.nextInt(85400000));
                Date eDate = new Date(date.getTime() + r.nextInt(6000));
                r1.writeBytes(customerIds[r.nextInt(customerCount)] + BaseConfig.SPLIT_STRING + "DeviceId" + BaseConfig.SPLIT_STRING
                        + w[r.nextInt(w.length)] + BaseConfig.SPLIT_STRING +
                        (r.nextInt(9876543)+5)+BaseConfig.SPLIT_STRING 
                        +df.format(date) + BaseConfig.SPLIT_STRING + df.format(eDate) + "\n");
            }
        }

    }
    private static final String[] w = {"www.google.com", "www.gmail.com", "www.verizon.com", "www.facebook.com", "www.cricinfo.com",
        "booking.com", "blogspot.com", "blogger.com", "pixnet.net", "wordpress.com", "craigslist.org", "outbrain.com", "imdb.com", "kat.cr", "amazon.co.jp", "amazon.de", "rakuten.co.jp", "amazon.co.uk", "amazon.in", "jd.com", "amazon.com", "alibaba.com", "mail.ru", "live.com", "wikipedia.org", "diply.com", "reddit.com", "dropbox.com", "googleusercontent.com", "imgur.com", "tianya.cn", "gmw.cn", "cnn.com", "xinhuanet.com", "nytimes.com", "bbc.co.uk", "onclickads.net", "googleadservices.com", "adcash.com", "popads.net", "ebay.com", "ebay.de", "ebay.co.uk", "taobao.com", "aliexpress.com", "paypal.com", "instagram.com", "yahoo.com", "qq.com", "sina.com.cn", "yahoo.co.jp", "msn.com", "sohu.com", "go.com", "Naver.com", "stackoverflow.com", "tmall.com", "flipkart.com", "google.com", "baidu.com", "google.co.in", "google.co.jp", "yandex.ru", "bing.com", "google.de", "google.co.uk", "ask.com", "soso.com", "google.co.id", "google.com.au", "google.co.kr", "google.pl", "sogou.com", "pinterest.com", "tumblr.com", "facebook.com", "twitter.com", "linkedin.com", "weibo.com", "vk.com", "ok.ru", "microsoft.com", "github.com", "espn.go.com", "netflix.com", "apple.com", "t.co", "youtube.com"};

    private static final String[] bc = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28"};
}
