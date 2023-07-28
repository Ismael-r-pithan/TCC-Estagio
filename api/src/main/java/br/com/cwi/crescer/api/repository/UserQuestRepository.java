package br.com.cwi.crescer.api.repository;

import br.com.cwi.crescer.api.domain.UserQuest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface UserQuestRepository extends JpaRepository<UserQuest, Long> {

    @Query("SELECT u FROM UserQuest u WHERE u.user.id = :userId AND u.quest.id = :questId")
    Optional<UserQuest> findByUserIdAndQuestId(Long userId, Long questId);

    @Query("SELECT u FROM UserQuest u WHERE u.user.id = :userId")
    Page<UserQuest> findQuestsByUserId(Pageable pageable, Long userId);

    @Query("SELECT COUNT(t) FROM UserQuest t JOIN t.user u WHERE u.id = :userId AND t.quest.id = :questId AND t.completedAt = :now")
    Long userQuestAvailable(Long userId, Long questId, LocalDate now);

}
