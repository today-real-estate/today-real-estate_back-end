package com.ssafy.realestate.map.controller;

import com.ssafy.realestate.map.dto.NewsDto;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/news")
public class NewsController {

    @GetMapping
    public ResponseEntity<List<NewsDto>> newsCrawling() {
        String urlString = "https://land.naver.com/";
        String url = "https://land.naver.com/news/breaking.naver";
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements element = doc.select("div.section_headline ");
        List<NewsDto> news = new ArrayList<>();
        String imageUrl;
        String newsTitle = "";
        String newsContent = "";
        String newsLink = "";
        String writing = "";
        String date = "";
        for (Element el : element.select("li")) {
            imageUrl = el.select(".photo img[src]").attr("src");
            newsTitle = el.select("dt>a").text();
            newsLink = "https://land.naver.com" + el.select("dt>a").attr("href");
            writing = el.select("dd>span.writing").text();
            date = el.select("dd>span.date").text();
            newsContent = el.select("dd").get(0).text();
            newsContent = newsContent.replaceAll(writing, "");
            newsContent = newsContent.replaceAll(date, "");
            news.add(new NewsDto(imageUrl, newsTitle, newsContent, newsLink, writing, date));

        }
        return new ResponseEntity<List<NewsDto>>(news, HttpStatus.OK);
    }
}
