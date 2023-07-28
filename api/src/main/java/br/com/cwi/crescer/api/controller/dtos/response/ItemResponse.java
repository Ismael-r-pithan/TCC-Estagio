package br.com.cwi.crescer.api.controller.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemResponse {
    private Long id;

    private String name;

    private String description;

    private String type;

    private int price;

    private String imageUrl;
}
