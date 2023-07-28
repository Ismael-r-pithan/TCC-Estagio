package br.com.cwi.crescer.api.controller.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class UserResponse {

    private Long id;
    private String username;
    private int money;
    private Long experience;
    private String imageProfile;
    private String bannerUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
