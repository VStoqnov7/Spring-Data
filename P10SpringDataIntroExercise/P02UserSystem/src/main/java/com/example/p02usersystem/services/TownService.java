package com.example.p02usersystem.services;

import com.example.p02usersystem.entities.Town;
import com.example.p02usersystem.repositories.TownRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TownService {

    private final TownRepository townRepository;

    @Autowired
    public TownService(TownRepository townRepository) {
        this.townRepository = townRepository;
    }

    public List<Town> getAllTowns() {
        return townRepository.findAll();
    }

    public Optional<Town> getTownById(Long id) {
        return townRepository.findById(id);
    }

    public Town createTown(Town town) {
        return townRepository.save(town);
    }

    public void deleteTown(Long id) {
        townRepository.deleteById(id);
    }
}
