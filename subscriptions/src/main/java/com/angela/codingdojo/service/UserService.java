package com.angela.codingdojo.service;

import com.angela.codingdojo.model.Package;
import com.angela.codingdojo.model.User;
import com.angela.codingdojo.repositories.UserRepository;
import com.angela.codingdojo.util.Role;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository uRepository;
    @Autowired
    private PackageService pService;

    public User registerUser(User user) {
        user.setPassword(hashPassword(user.getPassword()));
        if (uRepository.count() == 0) {
            user = addRole(user, Role.ADMIN);
        } else {
            user = addRole(user, Role.USER);
            setPackage(user, pService.findById(1));
        }
        return uRepository.save(user);
    }

    public User setLastSignIn(User user) {
        user.setLastSignIn(new Date());
        return uRepository.save(user);
    }

    public User setPackage(User user, Package aPackage) {
        user.setCurrentPackage(aPackage);
        user.getCurrentPackage();
        return uRepository.save(user);
    }

    public User addRole(User user, Role role) {
        if (!user.getRoles().contains(role)) {
            user.getRoles().clear();
            user.getRoles().add(role);
            return uRepository.save(user);
        } else {
            return user;
        }
    }

    public User removeRole(User user, Role role) {
        if (user.getRoles().contains(role)) {
            user.getRoles().remove(role);
            return uRepository.save(user);
        } else {
            return user;
        }
    }

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public void deleteUser(User user) {
        uRepository.delete(user);
    }

    public User findByUsername(String username) {
        Optional<User> user = uRepository.findByUsername(username);
        if (user.isPresent()) {
            return user.get();
        } else {
            return null;
        }
    }

    public User findByEmail(String email) {
        return uRepository.findByEmail(email).orElse(null);
    }

    public User findById(Integer id) {
        Optional<User> u = uRepository.findById(id);
        if (u.isPresent()) {
            return u.get();
        } else {
            return null;
        }
    }

    public boolean userExist(Integer id) {
        return uRepository.findById(id).isPresent();
    }

    public boolean emailExists(String email) {
        return uRepository.findByEmail(email).isPresent();
    }

    public boolean usernameExists(String userName) {
        return uRepository.findByUsername(userName).isPresent();
    }

    public Iterable<User> findAll() {
        return uRepository.findAll();
    }

    public boolean authenticateUser(String email, String password) {
        User user = findByEmail(email);
        if (user == null) {
            return false;
        } else {
            if (BCrypt.checkpw(password, user.getPassword())) {
                return true;
            } else {
                return false;
            }
        }
    }
}
