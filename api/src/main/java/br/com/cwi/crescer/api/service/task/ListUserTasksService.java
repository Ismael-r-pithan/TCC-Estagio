package br.com.cwi.crescer.api.service.task;

import br.com.cwi.crescer.api.controller.dtos.response.TaskResponse;
import br.com.cwi.crescer.api.domain.User;
import br.com.cwi.crescer.api.domain.enums.Category;
import br.com.cwi.crescer.api.domain.enums.Priority;
import br.com.cwi.crescer.api.mapper.TaskMapper;
import br.com.cwi.crescer.api.repository.FriendshipRepository;
import br.com.cwi.crescer.api.repository.TaskRepository;
import br.com.cwi.crescer.api.service.user.GetUserAuthenticatedService;
import br.com.cwi.crescer.api.service.user.GetUserByIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ListUserTasksService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private GetUserAuthenticatedService getUserAuthenticatedService;

    @Autowired
    private GetUserByIdService getUserByIdService;

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private TaskMapper taskMapper;

    public Page<TaskResponse> list(Long userId,
                                   LocalDate date,
                                   Category category,
                                   Priority priority,
                                   Pageable pageable) {

        User me = getUserAuthenticatedService.get();
        User user = getUserByIdService.get(userId);

        if (me.equals(user)) {
            Page<TaskResponse> page = taskRepository.findByUserIdAndFilteredFields(userId, category, priority, pageable).map(taskMapper::toResponse);
            if (Objects.nonNull(date)) return filterByDate(page, date);
            return page;
        } else if (friendshipRepository.existsFriendship(me, user)) {
            Page<TaskResponse> page = taskRepository.findByUserIdAndFilteredFieldsAndFriendVisibility(userId, category, priority, pageable).map(taskMapper::toResponse);
            if (Objects.nonNull(date)) return filterByDate(page, date);
            return page;
        } else {
            Page<TaskResponse> page = taskRepository.findByUserIdAndFilteredFieldsAndPublicVisibility(userId, category, priority, pageable).map(taskMapper::toResponse);
            if (Objects.nonNull(date)) return filterByDate(page, date);
            return page;
        }
    }

    private Page<TaskResponse> filterByDate(Page<TaskResponse> page, LocalDate filterDate) {
        List<TaskResponse> filteredList = page.getContent().stream()
                .filter(taskResponse -> taskResponse.getScheduledDate().equals(filterDate))
                .collect(Collectors.toList());

        return  new PageImpl<>(filteredList, page.getPageable(), page.getTotalElements());
    }
}
