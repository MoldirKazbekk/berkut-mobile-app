package kz.sdu.edu.berkutapp.controller;

import kz.sdu.edu.berkutapp.model.dto.GeoData;
import kz.sdu.edu.berkutapp.service.ChildLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@PreAuthorize("hasRole(T(kz.sdu.edu.berkutapp.model.dto.UserType).CHILD.name())")
public class LocationController {
    private final ChildLocationService childLocationService;

    @PostMapping("/child-geo")
    public void processGeolocation(@RequestBody GeoData geoData) {
        childLocationService.sendLocation(geoData);
    }



}
