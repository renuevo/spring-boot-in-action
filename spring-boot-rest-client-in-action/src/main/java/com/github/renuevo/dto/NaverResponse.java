package com.github.renuevo.dto;

import lombok.Data;

import java.util.List;

@Data
public class NaverResponse {
    private String lastBuildDate;
    private int total;
    private int start;
    private int display;
    private List<Item> items;

    @Data
    public static class Item{
        private String title;
        private String link;
        private String description;
        private String bloggername;
        private String bloggerlink;
        private String postdate;
    }
}
