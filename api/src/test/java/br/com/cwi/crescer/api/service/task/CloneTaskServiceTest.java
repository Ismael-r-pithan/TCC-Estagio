package br.com.cwi.crescer.api.service.task;

import br.com.cwi.crescer.api.domain.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class CloneTaskServiceTest {

    @InjectMocks
    private CloneTaskService cloneTaskService;

    @Test
    @DisplayName("Deve clonar a tarefa corretamente")
    void shouldCloneTaskSuccessfully() {
        // given
        Task task = new Task();
        task.setId(1L);
        task.setDescription("Tarefa original");

        // when
        Task clone = cloneTaskService.clone(task);

        // then
        assertThat(clone).isNotNull();
        assertThat(clone.getId()).isNull();
        assertThat(clone.getDescription()).isEqualTo(task.getDescription());
    }

}

