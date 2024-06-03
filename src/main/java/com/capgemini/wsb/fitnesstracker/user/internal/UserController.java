package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Controller for handling user-related requests.
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
class UserController {
    private final UserServiceImpl userService;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    /**
     * Retrieves all users as UserDto objects.
     *
     * @return A list of UserDto objects
     */
    @GetMapping
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }
    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user to retrieve
     * @return The UserDto of the retrieved user
     * @throws UserNotFoundException if the user is not found
     */
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.getUser(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @PostMapping
    public UserDto createUser(@RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User savedUser = userService.createUser(user);
        return userMapper.toDto(savedUser);
    }
    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to delete
     * @throws UserNotFoundException if the user is not found
     */
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.getUser(id)
                .ifPresentOrElse(userRepository::delete, () -> {
                    throw new UserNotFoundException(id);
                });
    }

    /**
     * Searches for users by their email address.
     *
     * @param email the email address to search for
     * @return A list of UserDto objects matching the search criteria
     */
    @GetMapping("/search")
    public List<UserDto> searchUsersByEmail(@RequestParam String email) {
        return userService.findAllUsers()
                .stream()
                .filter(user -> user.getEmail().toLowerCase().contains(email.toLowerCase()))
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }
    /**
     * Searches for users older than the specified age.
     *
     * @param age the age to search for
     * @return A list of UserDto objects matching the search criteria
     */
    @GetMapping("/age")
    public List<UserDto> searchUsersByAge(@RequestParam int age) {
        LocalDate cutoffDate = LocalDate.now().minusYears(age);
        return userService.findAllUsers()
                .stream()
                .filter(user -> user.getBirthdate().isBefore(cutoffDate))
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }
    /**
     * Updates a user's details.
     *
     * @param id the ID of the user to update
     * @param userDto the updated user data
     * @return The UserDto of the updated user
     * @throws UserNotFoundException if the user is not found
     */
    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        User existingUser = userService.getUser(id).orElseThrow(() -> new UserNotFoundException(id));
        User updatedUser = new User(id, userDto.firstName(), userDto.lastName(), userDto.birthdate(), userDto.email());
        User savedUser = userRepository.save(updatedUser);
        return userMapper.toDto(savedUser);
    }
}
