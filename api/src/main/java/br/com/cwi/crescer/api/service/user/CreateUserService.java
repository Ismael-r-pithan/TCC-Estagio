package br.com.cwi.crescer.api.service.user;

import br.com.cwi.crescer.api.controller.dtos.request.user.CreateUserRequest;
import br.com.cwi.crescer.api.controller.dtos.response.UserResponse;

import br.com.cwi.crescer.api.domain.Item;
import br.com.cwi.crescer.api.domain.Permission;
import br.com.cwi.crescer.api.domain.User;
import br.com.cwi.crescer.api.mapper.UserMapper;
import br.com.cwi.crescer.api.repository.UserRepository;
import br.com.cwi.crescer.api.service.item.LoadDefaultItemsService;
import br.com.cwi.crescer.api.shared.GenerateToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CreateUserService {
    @Autowired
    private UserRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ValidateEmailExistsService validateEmailExistsService;

    @Autowired
    private ValidateUsernameExistsService validateUsernameExistsService;

    @Autowired
    private SendMailActivateAccountService sendMailActivateAccountService;

    @Autowired
    private LoadDefaultItemsService loadDefaultItemsService;


    @Transactional
    public UserResponse create(CreateUserRequest request) {

        validateEmailExistsService.validate(request.getEmail());
        validateUsernameExistsService.validate(request.getUsername());

        User user = userMapper.toEntity(request);
        user.setAtivo(false);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setExperience(0L);

        List<Item> defaultItems = loadDefaultItemsService.load();
        user.setItems(defaultItems);

        Optional<Item> defaultAvatar = user.getItems().stream().filter(u -> u.getType().equals("AVATAR")).findFirst();
        Optional<Item> defaultBanner = user.getItems().stream().filter(u -> u.getType().equals("CAPA")).findFirst();

        if (defaultAvatar.isPresent() && defaultBanner.isPresent()) {
            user.setImageProfile(defaultAvatar.get().getImageUrl());
            user.setBannerUrl(defaultBanner.get().getImageUrl());
        }


        String token = GenerateToken.generate();

        user.setConfirmToken(token);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setPermissions(Collections.singletonList(Permission.builder().name("BASIC").usuario(user).build()));

        usuarioRepository.save(user);

        sendMailActivateAccountService.send(user.getEmail());

        return userMapper.toResponse(user);
    }
}
