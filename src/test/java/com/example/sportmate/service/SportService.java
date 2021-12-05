package com.example.sportmate.service;

import com.example.sportmate.repository.SportRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SportService {
    private final SportRepository sportRepository;

    public SportService(final SportRepository sportRepository) {
        this.sportRepository = sportRepository;
    }

    public List<String> getAllSports() {
        List<String> sports = new ArrayList<>();
        sportRepository.findAll().forEach(sport -> sports.add(sport.label()));
        return sports;
    }
}
