package kz.sdu.edu.berkutapp.controller;

import jakarta.validation.Valid;
import kz.sdu.edu.berkutapp.model.dto.TaskDTO;
import kz.sdu.edu.berkutapp.service.ParentService;
import kz.sdu.edu.berkutapp.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole(T(kz.sdu.edu.berkutapp.model.dto.UserType).PARENT.name())")
@RequestMapping("/parents")
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/task/{child-id}")
    @PreAuthorize("hasRole(T(kz.sdu.edu.berkutapp.model.dto.UserType).PARENT.name())")
    public void addTask(@RequestBody @Valid TaskDTO taskDTO, @PathVariable("child-id") Long childId) {
        taskService.addChildTask(taskDTO, childId);
    }

    @DeleteMapping("/task/{child-id}")
    @PreAuthorize("hasRole(T(kz.sdu.edu.berkutapp.model.dto.UserType).PARENT.name())")
    public void deleteTask(@RequestBody @Valid TaskDTO taskDTO, @PathVariable("child-id") Long childId) {
        taskService.deleteChildTask(taskDTO, childId);
    }
}
