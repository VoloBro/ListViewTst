package com.example.vovap.listviewtst;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testOne() throws Exception {
        Document doc = Jsoup.connect("https://zn.ua/").get();
        Elements newsHeadlines = doc.select("li.news_item ");

        List<String> titles = new ArrayList<>();
        for (Element e: newsHeadlines){
            String url = e.select("a.news_link").attr("href");
            String headline = e.select("span.news_title").text();
            titles.add(headline);
        }


    }
}