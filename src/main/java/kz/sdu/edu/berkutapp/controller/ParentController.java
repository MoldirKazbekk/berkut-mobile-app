package kz.sdu.edu.berkutapp.controller;

import jakarta.validation.Valid;
import kz.sdu.edu.berkutapp.model.dto.SavedLocationDTO;
import kz.sdu.edu.berkutapp.model.dto.TaskDTO;
import kz.sdu.edu.berkutapp.service.ParentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
//@PreAuthorize("hasRole(T(kz.sdu.edu.berkutapp.model.dto.UserType).PARENT.name())")
@RequestMapping("/parents")
public class ParentController {

    private final ParentService parentService;

    @PostMapping("/{parent_id}")
    public void addChild(@PathVariable("parent_id") Long parentId, @RequestParam("child_id") Long childId) {
        parentService.addChild(parentId, childId);
    }

    @PostMapping("/task/{child-id}")
    @PreAuthorize("hasRole(T(kz.sdu.edu.berkutapp.model.dto.UserType).PARENT.name())")
    public void addTask(@RequestBody @Valid TaskDTO taskDTO, @PathVariable("child-id") Long childId){
        parentService.addChildTask(taskDTO,childId);
    }
    @DeleteMapping("/task/{child-id}")
    @PreAuthorize("hasRole(T(kz.sdu.edu.berkutapp.model.dto.UserType).PARENT.name())")
    public void deleteTask(@RequestBody @Valid TaskDTO taskDTO, @PathVariable("child-id") Long childId){
        parentService.deleteChildTask(taskDTO,childId);
    }
}
