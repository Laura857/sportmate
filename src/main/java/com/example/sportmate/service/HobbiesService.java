package com.example.sportmate.service;

import com.example.sportmate.entity.Hobbies;
import com.example.sportmate.entity.Sport;
import com.example.sportmate.repository.HobbiesRepository;
import com.example.sportmate.repository.SportRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.StreamSupport.stream;

@Service
@AllArgsConstructor
public class HobbiesService {
    private final HobbiesRepository hobbiesRepository;


    public List<String> getUserHobbies(final Integer userId) {
        return hobbiesRepository.findUserHobbies(userId).stream()
                .map(Hobbies::label)
                .toList();
    }

}
