package com.example.service;

import com.example.models.User;
import com.example.models.dto.LoginDTO;
import com.example.models.dto.RegistrationDTO;
import com.example.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;

    @Autowired
    public UserService(ModelMapper modelMapper, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    public void register(RegistrationDTO dto) {
        User user = this.modelMapper.map(dto, User.class);

        this.userRepository.save(user);
    }

    public Optional<User> login(LoginDTO loginDTO) {
        return this.userRepository
                .findByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword());

        // check password if hashed
        // marked user as logged if data is correct
    }
}
