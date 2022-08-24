package com.example.sportmate.service;

import com.example.sportmate.entity.Hobbies;
import com.example.sportmate.exception.NotFoundException;
import com.example.sportmate.record.hobbies.UpdateUserHobbiesDto;
import com.example.sportmate.repository.HobbiesRepository;
import com.example.sportmate.repository.UserHobbiesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.sportmate.enumeration.ErrorMessageEnum.HOBBIES_NOT_FOUND;

@Service
@AllArgsConstructor
public class HobbiesService {
    private final HobbiesRepository hobbiesRepository;
    private final UserHobbiesRepository userHobbiesRepository;

    public List<String> getUserHobbies(final Integer userId) {
        return hobbiesRepository.findUserHobbies(userId).stream()
                .map(Hobbies::getLabel)
                .toList();
    }

   @Transactional
   public void updateUserHobbies(final Integer userId, final UpdateUserHobbiesDto updateUserHobbiesDto) {
        userHobbiesRepository.deleteAllByUserId(userId);
        updateUserHobbiesDto.hobbies().forEach(oneHobbies -> {
            final Hobbies hobbiesFound = hobbiesRepository.findByLabel(oneHobbies)
                    .orElseThrow(() -> new NotFoundException(HOBBIES_NOT_FOUND.getMessage()));
            userHobbiesRepository.save(userId, hobbiesFound.getId());
        });
    }
}
