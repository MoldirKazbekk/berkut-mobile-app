package kz.sdu.edu.berkutapp.service;

import jakarta.transaction.Transactional;
import kz.sdu.edu.berkutapp.model.AppUser;
import kz.sdu.edu.berkutapp.model.ChildTask;
import kz.sdu.edu.berkutapp.model.dto.TaskDTO;
import kz.sdu.edu.berkutapp.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {
    private final AppUserRepository appUserRepository;

    @Transactional
    public void addChildTask(TaskDTO taskDTO, Long childId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser parent = appUserRepository.findById(Long.valueOf((String) authentication.getPrincipal())).orElseThrow();
        for (AppUser child : parent.getChildren()) {
            if (child.getId().equals(childId)) {
                child.addChildTask(new ChildTask(taskDTO));
                return;
            }
        }
    }

    @Transactional
    public void deleteChildTask(TaskDTO taskDTO, Long childId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser parent = appUserRepository.findById(Long.valueOf((String) authentication.getPrincipal())).orElseThrow();
        for (AppUser child : parent.getChildren()) {
            if (child.getId().equals(childId)) {
                child.getChildTasks()
                        .removeIf(cht -> cht.getName().equals(String.valueOf(taskDTO.getName())) &&
                                cht.getDescription().equals(String.valueOf(taskDTO.getDescription())));
            }
        }
    }
}
