package com.example.sportmate.service;

import com.example.sportmate.entity.Level;
import com.example.sportmate.entity.Sport;
import com.example.sportmate.exception.NotFoundException;
import com.example.sportmate.record.authentification.signing.SportDto;
import com.example.sportmate.repository.LevelRepository;
import com.example.sportmate.repository.SportRepository;
import com.example.sportmate.repository.UserFavoriteSportRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.sportmate.enumeration.ErrorMessageEnum.LEVEL_NOT_FOUND;
import static com.example.sportmate.enumeration.ErrorMessageEnum.SPORT_NOT_FOUND;
import static java.util.stream.StreamSupport.stream;

@Service
@AllArgsConstructor
public class SportService {
    private final SportRepository sportRepository;
    private final LevelRepository levelRepository;
    private final UserFavoriteSportRepository userFavoriteSportRepository;

    public List<String> getAllSports() {
        return stream(sportRepository.findAll().spliterator(), false)
                .map(Sport::getLabel)
                .toList();
    }

    @Transactional
    public List<SportDto> getUserSports(final Integer userId) {
        return userFavoriteSportRepository.findUserSports(userId).stream()
                .map(userFavoriteSport -> {
                    final Sport sport = sportRepository.findById(userFavoriteSport.getUserSportId().getSportId())
                            .orElseThrow(() -> new NotFoundException(SPORT_NOT_FOUND.getMessage()));
                    final Level level = levelRepository.findById(userFavoriteSport.getLevelId())
                            .orElseThrow(() -> new NotFoundException(LEVEL_NOT_FOUND.getMessage()));
                    return new SportDto(sport.getLabel(), level.getLabel());
                })
                .toList();
    }

}
