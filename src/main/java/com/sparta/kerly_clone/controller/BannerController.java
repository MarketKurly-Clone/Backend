package com.sparta.kerly_clone.controller;

import com.sparta.kerly_clone.dto.responseDto.ResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BannerController {

    @GetMapping("/banners")
    public ResponseDto getBanner() {
        Map<String, Object> responseMap = new HashMap<>();
        List<String> banners = new ArrayList<>();
        banners.add("https://img-cf.kurly.com/shop/data/main/1/pc_img_1634523002.jpg");
        banners.add("https://img-cf.kurly.com/shop/data/main/1/pc_img_1634005239.jpg");
        banners.add("https://img-cf.kurly.com/shop/data/main/1/pc_img_1634285707.jpg");
        banners.add("https://img-cf.kurly.com/shop/data/main/1/pc_img_1634171561.jpg");
        banners.add("https://img-cf.kurly.com/shop/data/main/1/pc_img_1634197973.jpg");
        banners.add("https://img-cf.kurly.com/shop/data/main/1/pc_img_1634115433.jpg");
        banners.add("https://img-cf.kurly.com/shop/data/main/1/pc_img_1628583839.jpg");
        banners.add("https://img-cf.kurly.com/shop/data/main/1/pc_img_1634107087.jpg");
        banners.add("https://img-cf.kurly.com/shop/data/main/1/pc_img_1621561009.jpg");
        banners.add("https://img-cf.kurly.com/shop/data/main/1/pc_img_1583112495.jpg");
        banners.add("https://img-cf.kurly.com/shop/data/main/1/pc_img_1633501694.jpg");
        int bannerItemCount = banners.size();
        responseMap.put("banners", banners);
        responseMap.put("bannerItemCount", bannerItemCount);

        return new ResponseDto("success", "성공적으로 조회되었습니다.", responseMap);
    }
}
