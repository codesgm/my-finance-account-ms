package br.com.myaccounts.my_finance_account_ms.service;

import br.com.myaccounts.my_finance_account_ms.dto.*;
import br.com.myaccounts.my_finance_account_ms.entity.User;
import br.com.myaccounts.my_finance_account_ms.exception.UserNotFoundException;
import br.com.myaccounts.my_finance_account_ms.exception.ValidationException;
import br.com.myaccounts.my_finance_account_ms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserDTO findById(UUID id) {
        log.debug("Finding user by ID: {}", id);
        User user = validateUserExists(id);
        return new UserDTO(user);
    }

    @Transactional(readOnly = true)
    public UserWithCategoriesDTO findByIdWithCategories(UUID id) {
        log.debug("Finding user with categories by ID: {}", id);
        User user = userRepository.findByIdWithCategories(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return new UserWithCategoriesDTO(user);
    }

    @Transactional(readOnly = true)
    public UserDTO findByEmail(String email) {
        log.debug("Finding user by email: {}", email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
        return new UserDTO(user);
    }

    public UserDTO create(CreateUserDTO dto) {
        log.debug("Creating new user with email: {}", dto.getEmail());
        
        if (!dto.isValid()) {
            throw new ValidationException("Invalid user data provided");
        }

        if (existsByEmail(dto.getEmail())) {
            throw new ValidationException("User with email " + dto.getEmail() + " already exists");
        }

        try {
            User user = dto.toEntity();
            User savedUser = userRepository.save(user);
            log.info("User created successfully with ID: {}", savedUser.getId());
            return new UserDTO(savedUser);
        } catch (DataIntegrityViolationException e) {
            log.error("Data integrity violation while creating user", e);
            throw new ValidationException("User with this email already exists");
        }
    }

    public UserDTO update(UUID id, UpdateUserDTO dto) {
        log.debug("Updating user with ID: {}", id);
        
        if (!dto.isValid()) {
            throw new ValidationException("Invalid user data provided");
        }

        User user = validateUserExists(id);

        // Check email uniqueness if email is being updated
        if (dto.getEmail() != null && !dto.getEmail().equals(user.getEmail())) {
            if (existsByEmail(dto.getEmail())) {
                throw new ValidationException("User with email " + dto.getEmail() + " already exists");
            }
        }

        try {
            if (dto.getName() != null) {
                user.setName(dto.getName());
            }
            if (dto.getEmail() != null) {
                user.setEmail(dto.getEmail());
            }

            User savedUser = userRepository.save(user);
            log.info("User updated successfully with ID: {}", savedUser.getId());
            return new UserDTO(savedUser);
        } catch (DataIntegrityViolationException e) {
            log.error("Data integrity violation while updating user", e);
            throw new ValidationException("User with this email already exists");
        }
    }

    public void delete(UUID id) {
        log.debug("Deleting user with ID: {}", id);
        User user = validateUserExists(id);
        user.delete(); // Soft delete
        userRepository.save(user);
        log.info("User soft deleted successfully with ID: {}", id);
    }

    public void updateLastAccess(UUID id) {
        log.debug("Updating last access for user with ID: {}", id);
        User user = validateUserExists(id);
        user.updateLastAccess();
        userRepository.save(user);
        log.debug("Last access updated for user with ID: {}", id);
    }

    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public boolean existsById(UUID id) {
        return userRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    public User validateUserExists(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        log.debug("Getting all users");
        return userRepository.findAll().stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public long getUserCount() {
        return userRepository.count();
    }
}
