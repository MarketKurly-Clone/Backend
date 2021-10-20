package com.sparta.kerly_clone.dto.requestDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

@NoArgsConstructor
@Getter
public class ProductRequestDto {
    private String title;
    private Long price;
    private String description;
    private String category1;
    private String category2;
    private String image;

    public ProductRequestDto(JSONObject itemJson) {
        this.title = itemJson.getString("title");
        this.price = itemJson.getLong("lprice");
        this.description = itemJson.getString("category3") +itemJson.getString("mallName")+itemJson.getString("maker");
        this.category1="";
        this.category2="";
        this.image = itemJson.getString("image");
    }
}
