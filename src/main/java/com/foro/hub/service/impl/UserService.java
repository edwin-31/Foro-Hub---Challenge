package com.foro.hub.service.impl;

import com.foro.hub.persistence.entity.UserEntity;
import com.foro.hub.persistence.repository.IUserRepository;
import com.foro.hub.presentation.dto.user.CreateUserDTO;
import com.foro.hub.presentation.dto.user.UserDTO;
import com.foro.hub.service.interfaces.IUserService;
import com.foro.hub.util.exception.DuplicateResourceException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    private final IUserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(IUserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(e -> new UserDTO(e.getId(), e.getName(), e.getEmail()))
                .toList();
    }

    @Override
    public Optional<UserDTO> findById(Long id) {
        return repository.findById(id)
                .map(e -> new UserDTO(e.getId(), e.getName(), e.getEmail()));
    }

    @Override
    public UserDTO save(CreateUserDTO dto) {
        UserEntity entity = UserEntity
                    .builder()
                    .name(dto.getName())
                    .email( dto.getEmail())
                    .password(dto.getPassword())
                    .build();

        if (repository.findByEmail(dto.getEmail()).isPresent()){
            throw new DuplicateResourceException("Email already exists: " + dto.getEmail());
        }

        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        UserEntity saved = repository.save(entity);

        if(saved == null) {
            throw new RuntimeException("An error occurred while saving the user.");
        }
        return new UserDTO(saved.getId(), saved.getName(), saved.getEmail());
    }

    @Override
    public Optional<UserEntity> findByEmail(String userName) {
        return repository.findByEmail(userName);
    }
}
