package br.com.cwi.crescer.api.service.factories;

import br.com.cwi.crescer.api.domain.Task;
import br.com.cwi.crescer.api.domain.User;

public class TaskFactory {

    public static Task get(User user) {
        Task task = new Task();
        task.setAutor(user);
        task.setRecurring(false);
        return task;
    }

    public static Task getRecurring(User user) {
        Task task = new Task();
        task.setAutor(user);
        task.setRecurring(true);
        return task;
    }
}
