package kz.sdu.edu.berkutapp.controller;

import kz.sdu.edu.berkutapp.model.dto.GeoData;
import kz.sdu.edu.berkutapp.service.ChildLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
//@PreAuthorize("hasRole(T(kz.sdu.edu.berkutapp.model.dto.UserType).CHILD.name())")
public class ChildLocationController {
    private final ChildLocationService childLocationService;

    @PostMapping("/geo")
    public void processGeolocation(@RequestBody GeoData geoData) {
        childLocationService.sendLocation(geoData);
    }

}
