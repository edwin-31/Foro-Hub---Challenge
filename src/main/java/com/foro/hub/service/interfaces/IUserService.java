package com.foro.hub.service.interfaces;

import com.foro.hub.persistence.entity.UserEntity;
import com.foro.hub.presentation.dto.user.CreateUserDTO;
import com.foro.hub.presentation.dto.user.UserDTO;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<UserDTO> findAll();
    Optional<UserDTO> findById(Long id);
    Optional<UserEntity> findByEmail(String email);
    UserDTO save(CreateUserDTO dto);
}
