package br.com.cwi.crescer.api.repository;

import br.com.cwi.crescer.api.domain.Friendship;
import br.com.cwi.crescer.api.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Friendship a WHERE a.usuario = :usuarioPrincipal AND a.friend = :usuarioFriend AND a.status = 'ACEITA'")
    boolean existsFriendship(@Param("usuarioPrincipal") User usuarioPrincipal, @Param("usuarioFriend") User usuarioFriend);

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Friendship a WHERE a.usuario = :usuarioPrincipal AND a.friend = :usuarioFriend AND a.status = 'PENDENTE'")
    boolean existsRequestFriendship(@Param("usuarioPrincipal") User usuarioPrincipal, @Param("usuarioFriend") User usuarioFriend);

    @Query("SELECT a FROM Friendship a WHERE a.friend.id = :userId AND a.status = 'PENDENTE'")
    List<Friendship> findByUserIdWithAmizadePendente(@Param("userId") Long userId);


    @Modifying
    @Query("DELETE FROM Friendship f WHERE (f.usuario.id = :userId AND f.friend.id = :friendId) OR (f.usuario.id = :friendId AND f.friend.id = :userId)")
    void deleteFriendship(@Param("userId") Long userId , @Param("friendId") Long friendId);

    @Query("SELECT f FROM Friendship f WHERE f.friend.id = :userId AND f.status = 'ACEITA' AND (f.usuario.username = :search OR f.usuario.email = :search)")
    Page<Friendship> findByUserIdWithFiltro(@Param("userId") Long userId, Pageable pageable, String search);

    @Query("SELECT f FROM Friendship f WHERE f.friend.id = :userId AND f.status = 'ACEITA'")
    Page<Friendship> findByUserId(@Param("userId") Long userId, Pageable pageable);

}
