package com.sparta.kerly_clone.dto;

import lombok.Data;

@Data
public class ProductDto {

    private int display;
    private Item[] items;

    @Data
    static class Item {
        public String title;
        public String image;
        public int lprice;
        public String category1;
        public String category2;
    }
}
