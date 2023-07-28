package br.com.cwi.crescer.api.service.user;

import br.com.cwi.crescer.api.controller.dtos.request.user.CreateUserRequest;
import br.com.cwi.crescer.api.controller.dtos.response.UserResponse;
import br.com.cwi.crescer.api.domain.Item;
import br.com.cwi.crescer.api.domain.Permission;
import br.com.cwi.crescer.api.domain.User;
import br.com.cwi.crescer.api.mapper.UserMapper;
import br.com.cwi.crescer.api.repository.UserRepository;
import br.com.cwi.crescer.api.service.item.LoadDefaultItemsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateUserServiceTest {

    @InjectMocks
    private CreateUserService createUserService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @Mock
    private ValidateEmailExistsService validateEmailExistsService;

    @Mock
    private ValidateUsernameExistsService validateUsernameExistsService;

    @Mock
    private SendMailActivateAccountService sendMailActivateAccountService;

    @Mock
    private LoadDefaultItemsService loadDefaultItemsService;

    @Test
    @DisplayName("Deve criar usuário com sucesso")
    void shouldCreateUserSuccessfully() {
        // Setup
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail("test@test.com");
        request.setUsername("testuser");
        request.setPassword("password");

        User user = new User();
        user.setId(1L);
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setAtivo(false);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setExperience(0L);

        List<Item> defaultItems = new ArrayList<>();
        Item itemAvatar = new Item();
        itemAvatar.setType("AVATAR");
        itemAvatar.setImageUrl("http://example.com/avatar.png");
        defaultItems.add(itemAvatar);
        Item itemBanner = new Item();
        itemBanner.setType("CAPA");
        itemBanner.setImageUrl("http://example.com/banner.png");
        defaultItems.add(itemBanner);
        user.setItems(defaultItems);

        String token = "generated_token";
        user.setConfirmToken(token);

        List<Permission> permissions = Collections.singletonList(Permission.builder().name("BASIC").usuario(user).build());
        user.setPermissions(permissions);

        User savedUser = new User();
        savedUser.setId(1L);

        when(userMapper.toEntity(request)).thenReturn(user);
        when(loadDefaultItemsService.load()).thenReturn(defaultItems);
        when(userRepository.save(user)).thenReturn(savedUser);
        when(userMapper.toResponse(savedUser)).thenReturn(new UserResponse());

        // Execute
        UserResponse response = createUserService.create(request);

        // Verify
        verify(validateEmailExistsService, times(1)).validate(request.getEmail());
        verify(validateUsernameExistsService, times(1)).validate(request.getUsername());
        verify(userMapper, times(1)).toEntity(request);
        verify(loadDefaultItemsService, times(1)).load();
        verify(userRepository, times(1)).save(user);
        verify(sendMailActivateAccountService, times(1)).send(user.getEmail());
        verify(userMapper, times(1)).toResponse(savedUser);

        assertNotNull(response);
    }
}
