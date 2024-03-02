package kz.sdu.edu.berkutapp.service;

import kz.sdu.edu.berkutapp.model.AppUser;
import kz.sdu.edu.berkutapp.model.ChildLocation;
import kz.sdu.edu.berkutapp.model.dto.GeoData;
import kz.sdu.edu.berkutapp.model.dto.UserType;
import kz.sdu.edu.berkutapp.repository.AppUserRepository;
import kz.sdu.edu.berkutapp.repository.ChildLocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChildLocationService {

    private final AppUserRepository appUserRepository;

    private final SimpMessagingTemplate messagingTemplate;

    private final ChildLocationRepository childLocationRepository;

    @Transactional
    public void sendLocation(GeoData geoData) {
        geoData = saveLocation(geoData);
        List<AppUser> parents = appUserRepository.getParentsByChildId(Long.valueOf(geoData.getUserId()));
        log.info("number of parents: {}", parents.size());
        for (AppUser parent : parents) {
            log.info("sending to parent: {}", parent.getUsername());
            messagingTemplate.convertAndSendToUser(
                    parent.getId().toString(),
                    "/user/child-geo",
                    geoData);
        }
    }

    public GeoData saveLocation(GeoData geoData) {
        AppUser child = appUserRepository.findById(geoData.getUserId()).orElseThrow();
        if (child.getRole() == UserType.CHILD) {
            var childLocation = new ChildLocation(geoData);
            childLocation.setChild(child);
            childLocationRepository.save(childLocation);
            geoData.setUsername(child.getUsername());
        }
        return geoData;
    }
}
