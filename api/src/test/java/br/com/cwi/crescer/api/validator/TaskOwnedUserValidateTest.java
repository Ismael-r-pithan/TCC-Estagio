package br.com.cwi.crescer.api.validator;

import br.com.cwi.crescer.api.domain.Task;
import br.com.cwi.crescer.api.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertThrows;



@ExtendWith(MockitoExtension.class)
class TaskOwnedUserValidateTest {

    @InjectMocks
    private TaskOwnedUserValidate taskOwnedUserValidate;

    @Mock
    private User user;

    @Mock
    private Task task;

    @Test
    @DisplayName("Deve permitir que um usuário que é dono da tarefa acesse a tarefa")
    void shouldAllowOwnerToAccessTask() {

        User user = User.builder().id(1L).build();
        Task task = Task.builder().id(1L).autor(user).build();


        taskOwnedUserValidate.validate(user, task);
    }

    @Test
    @DisplayName("Deve lançar uma exceção se um usuário que não é dono da tarefa tentar acessá-la")
    void shouldThrowExceptionIfNonOwnerTriesToAccessTask() {
        User fulano = User.builder().id(1L).build();
        User ciclano = User.builder().id(2L).build();
        Task task = Task.builder().id(1L).autor(fulano).build();


        assertThrows(ResponseStatusException.class, () -> {
            taskOwnedUserValidate.validate(ciclano, task);
        });
    }
}
