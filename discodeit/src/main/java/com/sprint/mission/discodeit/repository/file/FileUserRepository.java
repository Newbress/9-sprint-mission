package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class FileUserRepository implements UserRepository {
    private final Path DIRECTORY;
    private final String EXTENSION = ".ser";
    private Path resolvePath(UUID id) {
        return DIRECTORY.resolve(id + EXTENSION);
    }
    private final Path nameIndexPath;
    private final Path infoIndexPath;

    public FileUserRepository() {
        this.DIRECTORY = Paths.get(System.getProperty("user.dir"),"file-data-map", User.class.getSimpleName());
        if (Files.notExists(DIRECTORY)) {
            try {
                Files.createDirectories(DIRECTORY);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        this.nameIndexPath = this.DIRECTORY.resolve("user_index.map");
        this.infoIndexPath = this.DIRECTORY.resolve("infoIndexmap");
    }

    private void nameIndexMap(String userName, UUID id) {
        Map<String, UUID> indexMap = new HashMap<>();
        if(Files.exists(nameIndexPath)) {
            try(FileInputStream fis = new FileInputStream(nameIndexPath.toFile());
                ObjectInputStream ois = new ObjectInputStream(fis)) {
                Object readObj = ois.readObject();
                if(readObj instanceof Map) {
                    indexMap = (Map<String, UUID>) readObj;
                }
            }catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        indexMap.put(userName, id);

        try(FileOutputStream fos = new FileOutputStream(nameIndexPath.toFile());
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(indexMap);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void infoIndexMap(String key, UUID id){
        Map<String, UUID> indexMap = new HashMap<>();
        if(Files.exists(infoIndexPath)){
            try(FileInputStream fis = new FileInputStream(infoIndexPath.toFile());
                ObjectInputStream ois = new ObjectInputStream(fis)){
                Object readObj = ois.readObject();
                if(readObj instanceof Map){
                    indexMap = (Map<String, UUID>) readObj;
                }
            }catch (IOException | ClassNotFoundException e){
                throw new RuntimeException(e);
            }
        }

        indexMap.put(key, id);

        try(FileOutputStream fos = new FileOutputStream(infoIndexPath.toFile());
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(indexMap);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private UUID findUserIdByName(String userName) {
        if(Files.exists(nameIndexPath)) {
            try(FileInputStream fis = new FileInputStream(nameIndexPath.toFile());
                ObjectInputStream ois = new ObjectInputStream(fis)){
                Map<String, UUID> indexMap = (Map<String, UUID>) ois.readObject();
                return indexMap.get(userName);
            }catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    private  UUID findUserIdByInfo(String key){
        if(Files.exists(infoIndexPath)){
            try(FileInputStream fis = new FileInputStream(infoIndexPath.toFile());
                ObjectInputStream ois = new ObjectInputStream(fis)){
                Map<String, UUID> indexMap = (Map<String, UUID>) ois.readObject();
                return indexMap.get(key);
            }catch (IOException |ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    @Override
    public User findByContactInfo(String input){
        String key;
        if (input.contains("@")) {
            key = "EMAIL:" + input;
        }else {
            key = "PHONE:" + input;
        }

        UUID id = findUserIdByInfo(key);

        if (id == null) {
            return null; // findById를 호출하지 말고, 그냥 null을 들고 돌아가라!
        }

        return findById(id);
    }


    @Override
    public User saveUser(User user) {
        Path path = resolvePath(user.getId());
        try(FileOutputStream fos = new FileOutputStream(path.toFile());
        ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(user);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
        nameIndexMap(user.getUserName(), user.getId());
        infoIndexMap("EMAIL:" + user.getEmail(), user.getId());
        infoIndexMap("PHONE:" + user.getPhone(), user.getId());
        return user;
    }

    @Override
    public User findUserName(String userName) {
        UUID id = findUserIdByName(userName);
        if(id == null) {
            throw new RuntimeException("찾을 수 없는 이름 : " + userName);
        }
        Path path = resolvePath(id);
        if(Files.exists(path)){
            try(FileInputStream fis = new FileInputStream(path.toFile());
                ObjectInputStream ois = new ObjectInputStream(fis)) {
                return (User) ois.readObject();

            }catch (IOException | ClassNotFoundException e){
                throw new RuntimeException(e);
            }
        }
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
    public User findById(UUID id) {
        Path path = resolvePath(id);
        if(Files.exists(path)){
            try(FileInputStream fis = new FileInputStream(path.toFile());
                ObjectInputStream ois = new ObjectInputStream(fis)) {
                return (User)ois.readObject();
            }catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        throw new NoSuchElementException("파일이 존재하지 않습니다 ID: " + id);
    }


    @Override
    public List<User> getall() {
        try{
            return Files.list(DIRECTORY)
                    .filter(path -> path.toString().endsWith(EXTENSION))
                    .map(path -> {
                        try (FileInputStream fis = new FileInputStream(path.toFile());
                             ObjectInputStream ois = new ObjectInputStream(fis)) {
                            return (User) ois.readObject();
                        }catch (IOException | ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .toList();
        }catch (IOException e ) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User editUser(User findThing, String newUsername, String newEmail, String newPhone) {
        User userNullable =null;
        Path path = resolvePath(findThing.getId());
        if(Files.exists(path)) {
            try(FileInputStream fis = new FileInputStream(path.toFile());
                ObjectInputStream ois = new ObjectInputStream(fis)  ){
                userNullable = (User) ois.readObject();
            }catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        User user = Optional.ofNullable(userNullable)
                .orElseThrow(() -> new NoSuchElementException("User with id " + findThing.getId() + " not found"));
        user.update(newUsername, newEmail, newPhone);

        try(FileOutputStream fos = new FileOutputStream(path.toFile());
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(user);
        }catch (IOException e){
            throw new RuntimeException(e);
        }

        return user;
    }

    @Override
    public boolean delUser(UUID id) {
        Path path = resolvePath(id);
        if(Files.notExists(path)) {
            throw new NoSuchElementException("User with id" + id + " not found");
        }
        try{
            Files.delete(path);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        return false;
    }
}
