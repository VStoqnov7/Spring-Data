package com.example.p02usersystem.services;

import com.example.p02usersystem.entities.Picture;
import com.example.p02usersystem.repositories.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PictureService {

    private final PictureRepository pictureRepository;

    @Autowired
    public PictureService(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    public List<Picture> getAllPictures() {
        return pictureRepository.findAll();
    }

    public Optional<Picture> getPictureById(Long id) {
        return pictureRepository.findById(id);
    }

    public Picture createPicture(Picture picture) {
        return pictureRepository.save(picture);
    }

    public void deletePicture(Long id) {
        pictureRepository.deleteById(id);
    }

}