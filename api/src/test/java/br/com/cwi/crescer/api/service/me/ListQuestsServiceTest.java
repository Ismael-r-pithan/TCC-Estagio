package br.com.cwi.crescer.api.service.me;

import br.com.cwi.crescer.api.controller.dtos.response.UserQuestResponse;
import br.com.cwi.crescer.api.domain.User;
import br.com.cwi.crescer.api.domain.UserQuest;
import br.com.cwi.crescer.api.mapper.UserQuestMapper;
import br.com.cwi.crescer.api.repository.UserQuestRepository;
import br.com.cwi.crescer.api.service.user.GetUserAuthenticatedService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ListQuestsServiceTest {

    @InjectMocks
    private ListQuestsService tested;

    @Mock
    private UserQuestRepository userQuestRepository;

    @Mock
    private UserQuestMapper userQuestMapper;

    @Mock
    private GetUserAuthenticatedService getUserAuthenticatedService;

    @Test
    @DisplayName("Deve retornar a lista de quests do usuário")
    void shouldReturnListOfUserQuests() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        Pageable pageable = PageRequest.of(0, 10);
        List<UserQuest> userQuests = Arrays.asList(new UserQuest(), new UserQuest());
        Page<UserQuest> page = new PageImpl<>(userQuests);
        given(getUserAuthenticatedService.get()).willReturn(user);
        given(userQuestRepository.findQuestsByUserId(pageable, userId)).willReturn(page);
        given(userQuestMapper.toResponse(any(UserQuest.class))).willReturn(new UserQuestResponse());

        // When
        Page<UserQuestResponse> result = tested.list(pageable);

        // Then
        verify(getUserAuthenticatedService).get();
        verify(userQuestRepository).findQuestsByUserId(pageable, userId);
        verify(userQuestMapper, times(userQuests.size())).toResponse(any(UserQuest.class));
        assertEquals(userQuests.size(), result.getContent().size());
    }
}
