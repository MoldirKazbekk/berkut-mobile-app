package kz.sdu.edu.berkutapp.controller;

import kz.sdu.edu.berkutapp.model.dto.GeoData;
import kz.sdu.edu.berkutapp.service.ChildLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;


@RequiredArgsConstructor
@Controller
@PreAuthorize("hasRole(T(kz.sdu.edu.berkutapp.model.dto.UserType).CHILD.name())")
public class LocationController {
    private final ChildLocationService childLocationService;

    @MessageMapping("/geo")
    @SendTo("/child-geo")
    public GeoData processGeolocation(@Payload GeoData geoData) {
       return childLocationService.sendLocation(geoData);
    }

}
