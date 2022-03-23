package com.example.sportmate.service;

import com.example.sportmate.entity.Sport;
import com.example.sportmate.repository.SportRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.StreamSupport.stream;

@Service
@AllArgsConstructor
public class SportService {
    private final SportRepository sportRepository;

    public List<String> getAllSports() {
        return stream(sportRepository.findAll().spliterator(), false)
                .map(Sport::label)
                .toList();
    }
}
