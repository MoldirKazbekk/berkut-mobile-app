package kz.sdu.edu.berkutapp.service;

import jakarta.transaction.Transactional;
import kz.sdu.edu.berkutapp.model.AppUser;
import kz.sdu.edu.berkutapp.model.SavedLocation;
import kz.sdu.edu.berkutapp.model.dto.QRInfo;
import kz.sdu.edu.berkutapp.model.dto.SavedLocationDTO;
import kz.sdu.edu.berkutapp.model.dto.UserType;
import kz.sdu.edu.berkutapp.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ParentService {
    private final SimpMessagingTemplate messagingTemplate;

    private final AppUserRepository appUserRepository;

    @Transactional
    public void addChild(Long parentId, Long childId) {
        AppUser parent = appUserRepository.findById(parentId).orElseThrow();
        AppUser child = appUserRepository.findById(childId).orElseThrow();
        if (child.getRole() == UserType.CHILD && parent.getRole() == UserType.PARENT) {
            parent.getChildren().add(child);
            // /user/child-id/qr-info subscribe while rendering QR-code
            messagingTemplate.convertAndSendToUser(child.getId().toString(), "/qr-info", new QRInfo(parent));
        }
    }

    @Transactional
    public void addSavedLocation(SavedLocationDTO savedLocationDTO) {
        AppUser parent = appUserRepository.findById(savedLocationDTO.getParentId()).orElseThrow();
        parent.addSavedLocation(new SavedLocation(savedLocationDTO));
    }
}
