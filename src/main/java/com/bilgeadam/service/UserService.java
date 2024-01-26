package com.bilgeadam.service;

import com.bilgeadam.entity.User;
import com.bilgeadam.repository.UserRepository;
import com.bilgeadam.utility.ICrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements ICrudService<User,Long> {

    private final UserRepository userRepository;

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public Iterable<User> saveAll(Iterable<User> t) {
        return null;
    }

    @Override
    public User deleteById(Long aLong) {
        return null;
    }

    @Override
    public Optional<User> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    public User register(String name, String surname, String email,String password,String rePassword){
        User registeredUser = User.builder()
                .name(name)
                .surname(surname)
                .email(email)
                .password(password)
                .rePassword(rePassword)
                .build();
        // " " -> isBlank = true, " " isEmpty = false
        if (!password.equals(rePassword) || password.isBlank()  ){
            throw new RuntimeException("Sifreler ayni degildir.");
            /*
            Exception -> Checked -> Compile error. Derleme hatası.
            RuntimeException -> Unchecked -> Runtime error. Çalışma zamanı hatası. -> Program çalışırken gerçekleşir.
             */
        } else {
            return userRepository.save(registeredUser);
        }
    }
}
