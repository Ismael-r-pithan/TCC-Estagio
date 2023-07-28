package br.com.cwi.crescer.api.controller.dtos.response;
import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class FriendshipResponse {
    private Long idRequest;
    private Long idFriend;
    private String usernameFriend;
    private String imageProfileFriend;
    private Long experienceFriend;
}
