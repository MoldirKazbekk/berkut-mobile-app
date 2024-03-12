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
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class HotlineNumberService {
    private final HotlineNumberRepository hotlineNumberRepository;

    private final AppUserRepository appUserRepository;

    public List<NumberDTO> getListOfNumbers(Long childId) {
        List<NumberDTO> numberDTOS = new ArrayList<>();
        List<AppUser> parents = appUserRepository.getParentsByChildId(childId);
        parents.forEach(parent -> numberDTOS.add(new NumberDTO(parent.getPhoneNumber(), parent.getUsername())));
        AppUser child = appUserRepository.findBiId(childId).orElseThrow();
        log.info("child by id {} has hotline numbers: {}", childId, child.getHotlineNumbers().size());
        child.getHotlineNumbers().forEach(number -> numberDTOS.add(new NumberDTO(number)));
        return numberDTOS;
    }

    @Transactional
    public void addNumber(Long childId, NumberDTO numberDTO) {
        log.info("in add child");
        HotlineNumber hotlineNumber = new HotlineNumber(numberDTO);
        AppUser appUser = appUserRepository.findById(childId).orElseThrow();
        log.info("child id : "+appUser.getUsername());
        if (appUser.getRole() == UserType.CHILD) {
            hotlineNumber.setChild(appUser);
            hotlineNumberRepository.save(hotlineNumber);
        }
    }

    @Transactional
    public void addNumberToAllChildren(NumberDTO numberDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long parentId = Long.valueOf((String) authentication.getPrincipal());

        log.info(String.valueOf(parentId));
        AppUser parent = appUserRepository.findById(parentId).orElseThrow();
        for (AppUser child : parent.getChildren()) {
            HotlineNumber hotlineNumber = new HotlineNumber(numberDTO);
            hotlineNumber.setChild(child);
            hotlineNumberRepository.save(hotlineNumber);
        }

    }

    @Transactional
    public void deleteNumber(Long childId, NumberDTO numberDTO) {
        // 1-st way
        hotlineNumberRepository.deleteByPhoneNumberAndNameAndChild_Id(numberDTO.getPhoneNumber(),
                numberDTO.getName(), childId);
        //2-nd way
        AppUser appUser = appUserRepository.findById(childId).orElseThrow();
        appUser.getHotlineNumbers()
                .removeIf(ht -> ht.getPhoneNumber().equals(numberDTO.getPhoneNumber()) &&
                        ht.getName().equals(numberDTO.getName()));
        //todo - question: do I need to save explicitly?
        appUserRepository.save(appUser);
    }
}
