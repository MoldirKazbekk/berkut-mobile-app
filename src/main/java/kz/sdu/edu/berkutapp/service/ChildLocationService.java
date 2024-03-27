package kz.sdu.edu.berkutapp.service;

import kz.sdu.edu.berkutapp.model.AppUser;
import kz.sdu.edu.berkutapp.model.ChildLocation;
import kz.sdu.edu.berkutapp.model.dto.GeoData;
import kz.sdu.edu.berkutapp.model.dto.SavedLocationDTO;
import kz.sdu.edu.berkutapp.model.dto.UserType;
import kz.sdu.edu.berkutapp.repository.AppUserRepository;
import kz.sdu.edu.berkutapp.repository.ChildLocationRepository;
import kz.sdu.edu.berkutapp.repository.SavedLocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChildLocationService {

    private final AppUserRepository appUserRepository;
    private final SavedLocationRepository savedLocationRepository;

    private final SimpMessagingTemplate messagingTemplate;
    private  final  ChildLocationRepository childLocationRepository;

    @Transactional
    public void sendLocation(GeoData geoData) {
        geoData = saveLocation(geoData);
        List<AppUser> parents = appUserRepository.getParentsByChildId(geoData.getUserId());
        log.info("number of parents: {}", parents.size());
        for (AppUser parent : parents) {
            log.info("sending to parent: {}", parent.getUsername());
            messagingTemplate.convertAndSendToUser(parent.getId().toString(), "/geo-data", geoData);
        }
    }

    public GeoData saveLocation(GeoData geoData) {
        AppUser child = appUserRepository.findById(geoData.getUserId()).orElseThrow();
        if (child.getRole() == UserType.CHILD) {
            child.addChildLocation(new ChildLocation(geoData));
            geoData.setUsername(child.getUsername());
        }
        return geoData;
    }
        public List<SavedLocationDTO> getNearestSavedLocation(Long childId, Integer amount){

            List<SavedLocationDTO> savedLocationDTOS = new ArrayList<>();
            // list of parents
            List<AppUser> parents = appUserRepository.getParentsByChildId(childId);
            //get all lists of saved locs from all parents
            for (AppUser parent : parents) {
                savedLocationRepository.findByParentId(parent.getId())
                        .forEach(item->savedLocationDTOS.add(new SavedLocationDTO(item)));
            }
            //last child location
            ChildLocation lastChildLocation = childLocationRepository.findByTimeDesc(childId);
            //no duplicates
            List<SavedLocationDTO> savedLocationDTOList = new ArrayList<>(new HashSet<>(savedLocationDTOS));
            //compare and sort
            savedLocationDTOList.sort(Comparator.comparingDouble(savedLocation ->
                    calculateDistance(lastChildLocation.getLatitude(), lastChildLocation.getLongitude(),
                            savedLocation.getLatitude(), savedLocation.getLongitude())));
            return savedLocationDTOList.subList(0, Math.min(amount, savedLocationDTOList.size()));

        }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the earth in km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // Distance in km
    }
}
