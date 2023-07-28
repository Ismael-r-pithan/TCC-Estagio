package br.com.cwi.crescer.api.repository;

import br.com.cwi.crescer.api.domain.Task;
import br.com.cwi.crescer.api.domain.User;
import br.com.cwi.crescer.api.domain.enums.Category;
import br.com.cwi.crescer.api.domain.enums.Priority;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    boolean existsByAutorAndScheduledDateAndStartTimeLessThanEqualAndEndTimeGreaterThanEqualAndFinishedDateIsNull(User autor,
                                                                                                                  LocalDate scheduledDate,
                                                                                                                  LocalTime startTime,
                                                                                                                  LocalTime endTime);
    @Query("SELECT COUNT(t) FROM Task t JOIN t.autor u WHERE u.id = :userId AND t.category = :category AND t.scheduledDate = :now")
    Long countTasksByCategoryAndScheduledDate(Long userId, Category category, LocalDate now);

    @Query("SELECT t.category FROM Task t JOIN t.autor u " +
            "WHERE u.id = :userId AND t.scheduledDate = :now group by t.category")
    List<String> findCategoryByUserIdAndScheduledDate(@Param("userId") Long userId, @Param("now") LocalDate now);


    @Query("SELECT t.scheduledDate FROM Task t WHERE t.autor.id = :userId AND t.scheduledDate BETWEEN date_trunc('week', current_date) AND :lastDayOfWeek GROUP BY t.scheduledDate")
    List<LocalDate> countTasksInCurrentWeek(@Param("userId") Long userId, LocalDate lastDayOfWeek);



    @Query("SELECT t.scheduledDate FROM Task t WHERE t.autor.id = :userId AND t.scheduledDate BETWEEN date_trunc('month', current_date) AND :lastDayOfMonth GROUP BY t.scheduledDate")
    List<LocalDate> findScheduleDateInCurrentMonth(@Param("userId") Long userId, LocalDate lastDayOfMonth);


    @Query("SELECT t FROM Task t WHERE t.autor.id = :userId " +
            "AND ( t.category = :category OR :category IS NULL  ) " +
            "AND ( t.priority = :priority OR :priority IS NULL  )")
    Page<Task> findByUserIdAndFilteredFields(Long userId,
                                             Category category,
                                             Priority priority,
                                             Pageable pageable);

    @Query("SELECT t FROM Task t WHERE t.autor.id = :userId " +
            "AND ( t.category = :category OR :category IS NULL  ) " +
            "AND ( t.priority = :priority OR :priority IS NULL  ) " +
            "AND ( t.visibility = 'AMIGOS' OR t.visibility = 'PUBLICO')")
    Page<Task> findByUserIdAndFilteredFieldsAndFriendVisibility(Long userId,
                                                                Category category,
                                                                Priority priority,
                                                                Pageable pageable);

    @Query("SELECT t FROM Task t WHERE t.autor.id = :userId " +
            "AND ( t.category = :category OR :category IS NULL  ) " +
            "AND ( t.priority = :priority OR :priority IS NULL  ) " +
            "AND ( t.visibility = 'PUBLICO')")
    Page<Task> findByUserIdAndFilteredFieldsAndPublicVisibility(Long userId,
                                                                Category category,
                                                                Priority priority,
                                                                Pageable pageable);
}
