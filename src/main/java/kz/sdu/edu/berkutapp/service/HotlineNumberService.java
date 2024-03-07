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

    public List<NumberDTO> getListOfNumbers(Long childId) {
        List<NumberDTO> numberDTOS = new ArrayList<>();
        List<AppUser> parents = appUserRepository.getParentsByChildId(childId);
        parents.forEach(parent -> numberDTOS.add(new NumberDTO(parent.getPhoneNumber(), parent.getUsername())));
        AppUser child = appUserRepository.findById(childId).orElseThrow();
        log.info("child by id {} has hotline numbers: {}", childId, child.getHotlineNumbers().size());
        child.getHotlineNumbers().forEach(number -> numberDTOS.add(new NumberDTO(number)));
        return numberDTOS;
    }

    @Transactional
    public void addNumber(Long childId, NumberDTO numberDTO) {
        HotlineNumber hotlineNumber = new HotlineNumber(numberDTO);
        AppUser appUser = appUserRepository.findById(childId).orElseThrow();
        if (appUser.getRole() == UserType.CHILD) {
            hotlineNumber.setChild(appUser);
            hotlineNumberRepository.save(hotlineNumber);
        }
    }

    @Transactional
    public void addNumberToAllChildren(NumberDTO numberDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        log.info(authentication);
        AppUser parent = appUserRepository.findById((Long) authentication.getPrincipal()).orElseThrow();
        for (AppUser child : parent.getChildren()) {
            child.getHotlineNumbers().add(new HotlineNumber(numberDTO));
        }
        appUserRepository.save(parent);
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
