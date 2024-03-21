package kz.sdu.edu.berkutapp.controller;

import kz.sdu.edu.berkutapp.model.SavedLocation;
import kz.sdu.edu.berkutapp.model.dto.GeoData;
import kz.sdu.edu.berkutapp.model.dto.SavedLocationDTO;
import kz.sdu.edu.berkutapp.service.ChildLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@RequiredArgsConstructor
@RestController
@PreAuthorize("hasRole(T(kz.sdu.edu.berkutapp.model.dto.UserType).CHILD.name())")
public class LocationController {
    private final ChildLocationService childLocationService;

    @PostMapping("/child-geo")
    public void processGeolocation(@RequestBody GeoData geoData) {
        childLocationService.sendLocation(geoData);
    }
    @GetMapping("/SavedLocation/{child-id}")
    public ResponseEntity<List<SavedLocationDTO>> getListOfSavedLocations(@PathVariable("child-id") Long childId) {
        return ResponseEntity.ok(childLocationService.getNearestSavedLocation(childId));
    }
}

