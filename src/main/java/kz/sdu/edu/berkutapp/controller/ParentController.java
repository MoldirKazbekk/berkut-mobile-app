package kz.sdu.edu.berkutapp.controller;

import kz.sdu.edu.berkutapp.model.dto.SavedLocationDTO;
import kz.sdu.edu.berkutapp.service.ParentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping
    public void addLocation(@RequestBody SavedLocationDTO savedLocationDTO) {
        parentService.addSavedLocation(savedLocationDTO);
    }
}
