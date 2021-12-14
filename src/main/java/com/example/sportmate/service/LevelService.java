package com.example.sportmate.service;

import com.example.sportmate.repository.LevelRepository;
import com.example.sportmate.repository.SportRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LevelService {
    private final LevelRepository levelRepository;

    public LevelService(final LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }

    public List<String> getAllLevels() {
        List<String> levels = new ArrayList<>();
        levelRepository.findAll().forEach(level -> levels.add(level.label()));
        return levels;
    }
}
