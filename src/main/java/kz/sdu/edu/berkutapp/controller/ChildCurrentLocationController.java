package kz.sdu.edu.berkutapp.controller;

import kz.sdu.edu.berkutapp.model.dto.GeoData;
import kz.sdu.edu.berkutapp.model.dto.SavedLocationDTO;
import kz.sdu.edu.berkutapp.service.ChildLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/child-geo")
@PreAuthorize("hasRole(T(kz.sdu.edu.berkutapp.model.dto.UserType).CHILD.name())")
public class ChildCurrentLocationController {
    private final ChildLocationService childLocationService;

    @PostMapping
    public void processGeolocation(@RequestBody GeoData geoData) {
        childLocationService.sendLocation(geoData);
    }
    @GetMapping("/saved-locations/{child-id}")
    public ResponseEntity<List<SavedLocationDTO>> getListOfSavedLocations(@PathVariable("child-id") Long childId,
                                                                          @RequestParam(required = false, name = "amount") Integer amount) {
        return ResponseEntity.ok(childLocationService.getNearestSavedLocation(childId, amount));
    }
}

