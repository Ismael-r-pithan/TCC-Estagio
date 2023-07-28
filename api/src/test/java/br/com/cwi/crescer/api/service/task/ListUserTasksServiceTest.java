package br.com.cwi.crescer.api.service.task;

import br.com.cwi.crescer.api.controller.dtos.response.TaskResponse;
import br.com.cwi.crescer.api.domain.Task;
import br.com.cwi.crescer.api.domain.User;
import br.com.cwi.crescer.api.domain.enums.Category;
import br.com.cwi.crescer.api.domain.enums.Priority;
import br.com.cwi.crescer.api.mapper.TaskMapper;
import br.com.cwi.crescer.api.repository.FriendshipRepository;
import br.com.cwi.crescer.api.repository.TaskRepository;
import br.com.cwi.crescer.api.service.user.GetUserAuthenticatedService;
import br.com.cwi.crescer.api.service.user.GetUserByIdService;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListUserTasksServiceTest {

    @InjectMocks
    private ListUserTasksService listUserTasksService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private GetUserAuthenticatedService getUserAuthenticatedService;

    @Mock
    private GetUserByIdService getUserByIdService;

    @Mock
    private FriendshipRepository friendshipRepository;

    @Mock
    private TaskMapper taskMapper;

    @Test
    @DisplayName("Deve listar tarefas do usuário autenticado")
    void deveListarTarefasDoUsuarioAutenticado() {
        // Cenário
        Long userId = 1L;
        LocalDate date = null;
        Category category = null;
        Priority priority = null;
        Pageable pageable = PageRequest.of(0, 10);

        User me = new User();
        me.setId(1L);

        User user = new User();
        user.setId(userId);

        List<Task> tasks = new ArrayList<>();
        Task task = new Task();
        task.setId(1L);
        task.setAutor(me);
        tasks.add(task);

        Page<Task> page = new PageImpl<>(tasks);

        when(getUserAuthenticatedService.get()).thenReturn(me);
        when(getUserByIdService.get(userId)).thenReturn(user);
        when(taskRepository.findByUserIdAndFilteredFields(userId, category, priority, pageable)).thenReturn(page);
        when(taskMapper.toResponse(task)).thenReturn(new TaskResponse());

        // Execução
        Page<TaskResponse> result = listUserTasksService.list(userId, date, category, priority, pageable);

        // Verificação
        verify(getUserAuthenticatedService).get();
        verify(getUserByIdService).get(userId);
        verify(taskRepository).findByUserIdAndFilteredFields(userId, category, priority, pageable);
        verify(taskMapper).toResponse(task);

        assertEquals(1, result.getTotalElements());
    }

}