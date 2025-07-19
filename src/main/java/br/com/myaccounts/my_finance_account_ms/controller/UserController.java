package br.com.myaccounts.my_finance_account_ms.controller;

import br.com.myaccounts.my_finance_account_ms.controller.doc.UserControllerDoc;
import br.com.myaccounts.my_finance_account_ms.dto.*;
import br.com.myaccounts.my_finance_account_ms.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController implements UserControllerDoc {

    private final UserService userService;

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable UUID id) {
        log.debug("GET /api/users/{}", id);
        UserDTO user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @Override
    @GetMapping("/{id}/with-categories")
    public ResponseEntity<UserWithCategoriesDTO> getUserWithCategories(@PathVariable UUID id) {
        log.debug("GET /api/users/{}/with-categories", id);
        UserWithCategoriesDTO user = userService.findByIdWithCategories(id);
        return ResponseEntity.ok(user);
    }

    @Override
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody CreateUserDTO dto) {
        log.debug("POST /api/users");
        UserDTO user = userService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable UUID id, @Valid @RequestBody UpdateUserDTO dto) {
        log.debug("PUT /api/users/{}", id);
        UserDTO user = userService.update(id, dto);
        return ResponseEntity.ok(user);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        log.debug("DELETE /api/users/{}", id);
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        log.debug("GET /api/users");
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @Override
    @GetMapping("/count")
    public ResponseEntity<Long> getUserCount() {
        log.debug("GET /api/users/count");
        long count = userService.getUserCount();
        return ResponseEntity.ok(count);
    }

    @Override
    @PutMapping("/{id}/last-access")
    public ResponseEntity<Void> updateLastAccess(@PathVariable UUID id) {
        log.debug("PUT /api/users/{}/last-access", id);
        userService.updateLastAccess(id);
        return ResponseEntity.ok().build();
    }
}
