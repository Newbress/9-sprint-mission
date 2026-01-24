package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.UserService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

public class FileUserService implements UserService {
    private final Path DIRECTORY;
    private final String EXTENSION = ".ser";
    private final UserRepository userRepository;


    public FileUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.DIRECTORY = Paths.get(System.getProperty("user.dir"), "file-data-map", User.class.getSimpleName());
        if (Files.notExists(DIRECTORY)) {
            try {
                Files.createDirectories(DIRECTORY);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Path resolvePath(UUID id) {
        return DIRECTORY.resolve(id + EXTENSION);
    }

    @Override
    public User addUser(String inputUsername, String inputEmail, String inputPhone) {
        User user = new User(inputUsername, inputEmail, inputPhone);
        return userRepository.saveUser(user);
    }

    @Override
    public User findUserName(String userName) {
        return null;
    }


    @Override
    public User getUserEmail(String email) {
        return null;
    }

    @Override
    public User getUserPhone(String phone) {
        return null;
    }

    @Override
    public User fileFindID(UUID id) {
        User userNullable = null;
        Path path = resolvePath(id);
        if (Files.exists(path)) {
            try (
                    FileInputStream fis = new FileInputStream(path.toFile());
                    ObjectInputStream ois = new ObjectInputStream(fis)
            ) {
                userNullable = (User) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return Optional.ofNullable(userNullable)
                .orElseThrow(() -> new NoSuchElementException("User with id " + id + " not found"));
    }

    @Override
    public List<User> getall() {
        return userRepository.getall();
    }

    @Override
    public User editUser(User findThing, String newUsername, String newEmail, String newPhone) {
        User user = new User(newUsername, newEmail,newPhone);
        return userRepository.saveUser(user);
    }

    @Override
    public boolean delUser(UUID id) {
        return userRepository.delUser(id);
    }
}
