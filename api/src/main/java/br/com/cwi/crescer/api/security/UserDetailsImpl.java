package br.com.cwi.crescer.api.security;

import java.util.List;
import java.util.stream.Collectors;
import br.com.cwi.crescer.api.domain.User;
import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDetailsImpl implements UserDetails {

    private static final String PREFIXO_PERMISSAO_SPRING = "ROLE_";

    private Long id;
    private String username;
    private String password;
    private String imageProfile;
    private String confirmToken;
    private Boolean ativo;
    private List<SimpleGrantedAuthority> permissions;

    public UserDetailsImpl(User usuario) {
        this.id = usuario.getId();
        this.password = usuario.getPassword();
        this.ativo = usuario.getAtivo();
        this.permissions = convertePermissoes(usuario);
        this.username = usuario.getEmail();
        this.imageProfile = usuario.getImageProfile();
        this.confirmToken = usuario.getConfirmToken();
    }

    private List<SimpleGrantedAuthority> convertePermissoes(User usuario) {
        return usuario.getPermissions().stream()
                .map(permissao -> new SimpleGrantedAuthority(PREFIXO_PERMISSAO_SPRING + permissao.getName()))
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    @Override
    public List<SimpleGrantedAuthority> getAuthorities() {
        return this.permissions;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.ativo;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.ativo;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.ativo;
    }

    @Override
    public boolean isEnabled() {
        return this.ativo;
    }
}
