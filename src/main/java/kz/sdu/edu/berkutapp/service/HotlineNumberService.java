package kz.sdu.edu.berkutapp.service;

import kz.sdu.edu.berkutapp.model.AppUser;
import kz.sdu.edu.berkutapp.model.HotlineNumber;
import kz.sdu.edu.berkutapp.model.dto.NumberDTO;
import kz.sdu.edu.berkutapp.model.dto.UserType;
import kz.sdu.edu.berkutapp.repository.AppUserRepository;
import kz.sdu.edu.berkutapp.repository.HotlineNumberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HotlineNumberService {
    private final HotlineNumberRepository hotlineNumberRepository;

    private final AppUserRepository appUserRepository;

    @Transactional(readOnly = true)
    public List<NumberDTO> getListOfNumbers(Long childId) {
        List<NumberDTO> numberDTOS = new ArrayList<>();

        // Retrieve parent numbers
        List<AppUser> parents = appUserRepository.getParentsByChildId(childId);
        parents.forEach(parent -> numberDTOS.add(new NumberDTO(parent.getPhoneNumber(), parent.getUsername())));
        log.info("only parents number " + parents.size());
        // Retrieve child numbers
        appUserRepository.findById(childId).orElseThrow()
                .getHotlineNumbers().forEach(number -> numberDTOS.add(new NumberDTO(number)));
        log.info("Child with id {} has hotline numbers: {}", childId, hotlineNumberRepository.findNumbersByChildId(childId).size());
        return numberDTOS;
    }

    @Transactional
    public void addNumber(Long childId, NumberDTO numberDTO) {
        AppUser appUser = appUserRepository.findById(childId).orElseThrow();
        log.info("child id : " + appUser.getUsername());
        if (appUser.getRole() == UserType.CHILD) {
            appUser.addHotlineNumber(new HotlineNumber(numberDTO));
        }
    }

    @Transactional
    public void addNumberToAllChildren(NumberDTO numberDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long parentId = Long.valueOf((String) authentication.getPrincipal());
        AppUser parent = appUserRepository.findById(parentId).orElseThrow();
        for (AppUser child : parent.getChildren()) {
            child.addHotlineNumber(new HotlineNumber(numberDTO));
        }
    }

    @Transactional
    public void deleteNumber(Long childId, NumberDTO numberDTO) {
        hotlineNumberRepository.deleteByPhoneNumberAndNameAndChild_Id(numberDTO.getPhoneNumber(),
                numberDTO.getName(), childId);
    }
}
