package kz.sdu.edu.berkutapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import kz.sdu.edu.berkutapp.model.dto.NumberDTO;
import kz.sdu.edu.berkutapp.service.HotlineNumberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/hotline-numbers")
public class HotlineNumbersController {
    private final HotlineNumberService hotlineNumberService;

    @GetMapping("/{child-id}")
    public ResponseEntity<List<NumberDTO>> getNumbers(@PathVariable("child-id") Long childId) {
        return ResponseEntity.ok(hotlineNumberService.getListOfNumbers(childId));
    }

    @PostMapping("/{child-id}")
    @PreAuthorize("hasRole(T(kz.sdu.edu.berkutapp.model.dto.UserType).PARENT.name())")
    @Operation(summary = "only PARENT can add a hotline phone number to his specific child")
    public void addHotlineNumber(@RequestBody @Valid NumberDTO numberDTO,
                                 @PathVariable("child-id") Long childId) {
        hotlineNumberService.addNumber(childId, numberDTO);
    }

    @PostMapping
    @PreAuthorize("hasRole(T(kz.sdu.edu.berkutapp.model.dto.UserType).PARENT.name())")
    @Operation(summary = "only PARENT can add a hotline phone number to all his children")
    public void addHotlineNumberToAllChildren(@RequestBody @Valid NumberDTO numberDTO) {
        hotlineNumberService.addNumberToAllChildren(numberDTO);
    }

    @DeleteMapping("/{child-id}")
    @PreAuthorize("hasRole(T(kz.sdu.edu.berkutapp.model.dto.UserType).PARENT.name())")
    @Operation(summary = "only PARENT can delete a hotline phone number of his specific child")
    public void deleteHotlineNumber(@RequestBody @Valid NumberDTO numberDTO,
                                    @PathVariable("child-id") Long childId) {
        hotlineNumberService.deleteNumber(childId, numberDTO);
    }
}


