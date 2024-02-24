package kz.sdu.edu.berkutapp.service;

import jakarta.transaction.Transactional;
import kz.sdu.edu.berkutapp.model.AppUser;
import kz.sdu.edu.berkutapp.model.dto.UserType;
import kz.sdu.edu.berkutapp.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ParentService {

    private final AppUserRepository appUserRepository;

    @Transactional
    public void addChild(Long parentId, Long childId) {
        AppUser parent = appUserRepository.findById(parentId).orElseThrow();
        AppUser child = appUserRepository.findById(childId).orElseThrow();
        if (child.getRole() == UserType.CHILD && parent.getRole() == UserType.PARENT) {
            parent.getChildren().add(child);
            appUserRepository.save(parent);
        }
    }
}
