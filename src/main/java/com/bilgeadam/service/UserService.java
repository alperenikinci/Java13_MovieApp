package com.bilgeadam.service;

import com.bilgeadam.dto.request.LoginRequestDto;
import com.bilgeadam.dto.request.RegisterRequestDto;
import com.bilgeadam.dto.response.LoginResponseDto;
import com.bilgeadam.dto.response.RegisterResponseDto;
import com.bilgeadam.entity.User;
import com.bilgeadam.mapper.UserMapper;
import com.bilgeadam.repository.UserRepository;
import com.bilgeadam.utility.EStatus;
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
    public User deleteById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            user.get().setStatus(EStatus.INACTIVE);
            return userRepository.save(user.get());
        } else {
            throw new NullPointerException("Böyle bir kullanıcı bulunamadı.");
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            return user;
        }else{
            throw new NullPointerException("Böyle bir kullanıcı yok");
        }

    }

    @Override
    public List<User> findAll() {
        List<User> userList = userRepository.findAll();
        if(userList.isEmpty()){
            throw new NullPointerException("Liste boş");
        }
        return userList;
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

    public User login(String email, String password) {
        Optional<User> user = userRepository.findByEmailAndPassword(email,password);
        if(user.isEmpty()){
            throw  new RuntimeException("Böyle bir kullanıcı bulunamadı...");
        }
        return user.get();
    }

    public RegisterResponseDto registerDto(RegisterRequestDto dto) {

        User user = User.builder()
                .name(dto.getName())
                .surname(dto.getSurname())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .rePassword(dto.getRePassword())
                .build();
        if (!user.getPassword().equals(user.getRePassword()) || user.getPassword().isBlank()  ){
            throw new RuntimeException("Sifreler ayni degildir.");
        }
        userRepository.save(user);
        //RequestDto -> User -> ResponseDto

        return RegisterResponseDto.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .status(user.getStatus())  //User'ın içini Request dto ile, Response'un içini oluşturduğumuz user'ın değerleriyle doldurmalıyız.
                .build();
    }

    public LoginResponseDto loginDto(LoginRequestDto dto) {
        Optional<User> optionalUser = userRepository.findByEmailAndPassword(dto.getEmail(),dto.getPassword());

        if(optionalUser.isEmpty()){
            throw new RuntimeException("Email veya Şifre hatalıdır.");
        }

//        User user = optionalUser.get();

        return LoginResponseDto.builder()
                .email(optionalUser.get().getEmail())
//                .email(user.getEmail())
                .build();
    }

    public LoginResponseDto loginMapper(LoginRequestDto dto) {

        Optional<User> optionalUser = userRepository.findByEmailAndPassword(dto.getEmail(),dto.getPassword());

        if(optionalUser.isEmpty()){
            throw new RuntimeException("Email veya Şifre hatalıdır.");
        }
        return UserMapper.INSTANCE.fromUserToLoginResponseDto(optionalUser.get());
    }


    public RegisterResponseDto registerMapper(RegisterRequestDto dto) {
        User user = UserMapper.INSTANCE.fromRegisterRequestDtoToUser(dto);
        if (!user.getPassword().equals(user.getRePassword()) || user.getPassword().isBlank()  ){
            throw new RuntimeException("Sifreler ayni degildir.");
        }
        userRepository.save(user);
        return UserMapper.INSTANCE.fromUserToRegisterResponseDto(user);
    }
}
