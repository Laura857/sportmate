package com.example.sportmate.service;

import com.example.sportmate.entity.Level;
import com.example.sportmate.repository.LevelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.StreamSupport.stream;

@Service
@AllArgsConstructor
public class LevelService {
    private final LevelRepository levelRepository;

    public List<String> getAllLevels() {
        return stream(levelRepository.findAll().spliterator(), false)
                .map(Level::label)
                .toList();
    }
}
