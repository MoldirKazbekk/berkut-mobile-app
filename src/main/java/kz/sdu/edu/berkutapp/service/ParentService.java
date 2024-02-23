package kz.sdu.edu.berkutapp.service;

import jakarta.transaction.Transactional;
import kz.sdu.edu.berkutapp.model.Child;
import kz.sdu.edu.berkutapp.model.Parent;
import kz.sdu.edu.berkutapp.repository.AppUserRepository;
import kz.sdu.edu.berkutapp.repository.ChildRepository;
import kz.sdu.edu.berkutapp.repository.ParentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
@RequiredArgsConstructor
public class ParentService {
    private final ChildRepository childRepository;

    private final AppUserRepository appUserRepository;

    private final ParentRepository parentRepository;

    @Transactional
    public void addChild(Long parentId, Long childId) {
        Parent parent = parentRepository.findById(parentId).orElseThrow();
        Child child = childRepository.findById(childId).orElseThrow();
        log.info("parent - {}", parent.getAppUser().getUsername());
        log.info("child - {}", child.getAppUser().getUsername());
        child.setParent(parent);
    }
}
