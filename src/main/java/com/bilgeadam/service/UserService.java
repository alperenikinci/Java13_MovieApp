package com.bilgeadam.service;

import com.bilgeadam.dto.request.LoginRequestDto;
import com.bilgeadam.dto.request.RegisterRequestDto;
import com.bilgeadam.dto.request.UserUpdateRequestDto;
import com.bilgeadam.dto.response.LoginResponseDto;
import com.bilgeadam.dto.response.RegisterResponseDto;
import com.bilgeadam.entity.User;
import com.bilgeadam.mapper.UserMapper;
import com.bilgeadam.repository.UserRepository;
import com.bilgeadam.utility.EStatus;
import com.bilgeadam.utility.EUserType;
import com.bilgeadam.utility.ICrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements ICrudService<User, Long> {

    private final UserRepository userRepository;

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }

    // Güncellemeye başka parametre gelirse ne olacak?
    // Kullanıcı aşağıdaki parametrelerden birisini JSON body'de hiç girmezse ne olacak?
    public User updateDto(UserUpdateRequestDto dto){
        Optional<User> optionalUser = userRepository.findById(dto.getId());
        if(optionalUser.isPresent()){
            optionalUser.get().setName(dto.getName());
            optionalUser.get().setSurname(dto.getSurname());
            optionalUser.get().setEmail(dto.getEmail());
            optionalUser.get().setPhone(dto.getPhone());
            return userRepository.save(optionalUser.get());
        } else {
            throw new NullPointerException("Kullanıcı bulunamadı...");
        }
    }

    public User updateMapper(UserUpdateRequestDto dto){
        Optional<User> optionalUser = userRepository.findById(dto.getId());
        if(optionalUser.isEmpty()){
            throw new NullPointerException("Kullanıcı bulunamadı...");
        }
        UserMapper.INSTANCE.updateUserFromDto(dto,optionalUser.get());
        return userRepository.save(optionalUser.get());
    }

    @Override
    public Iterable<User> saveAll(Iterable<User> t) {
        return null;
    }

    @Override
    public User deleteById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            user.get().setStatus(EStatus.INACTIVE);
            return userRepository.save(user.get());
        } else {
            throw new NullPointerException("Böyle bir kullanıcı bulunamadı.");
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user;
        } else {
            throw new NullPointerException("Böyle bir kullanıcı yok");
        }

    }

    @Override
    public List<User> findAll() {
        List<User> userList = userRepository.findAll();
        if (userList.isEmpty()) {
            throw new NullPointerException("Liste boş");
        }
        return userList;
    }

    public User register(String name, String surname, String email, String password, String rePassword) {
        User registeredUser = User.builder()
                .name(name)
                .surname(surname)
                .email(email)
                .password(password)
                .rePassword(rePassword)
                .build();
        // " " -> isBlank = true, " " isEmpty = false
        if (!password.equals(rePassword) || password.isBlank()) {
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
        Optional<User> user = userRepository.findByEmailAndPassword(email, password);
        if (user.isEmpty()) {
            throw new RuntimeException("Böyle bir kullanıcı bulunamadı...");
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
        if (!user.getPassword().equals(user.getRePassword()) || user.getPassword().isBlank()) {
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
        Optional<User> optionalUser = userRepository.findByEmailAndPassword(dto.getEmail(), dto.getPassword());

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Email veya Şifre hatalıdır.");
        }

//        User user = optionalUser.get();

        return LoginResponseDto.builder()
                .email(optionalUser.get().getEmail())
//                .email(user.getEmail())
                .build();
    }

    public LoginResponseDto loginMapper(LoginRequestDto dto) {

        Optional<User> optionalUser = userRepository.findByEmailAndPassword(dto.getEmail(), dto.getPassword());

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Email veya Şifre hatalıdır.");
        }
        return UserMapper.INSTANCE.fromUserToLoginResponseDto(optionalUser.get());
    }


    public RegisterResponseDto registerMapper(RegisterRequestDto dto) {
        User user = UserMapper.INSTANCE.fromRegisterRequestDtoToUser(dto);

        /* Burada else if yapısı kurarak ba.admin@email.com'u tekrar edebilir bir yapıya getirdim.
         * Ancak bunun dışında alan bütün e-mailler unique olmak zorunda kaldı. Sonrasındaysa şifre kontrolümü
         * her iki durum(admin maili ya da user maili) için de yine değerlendiriyorum. Akış şu şekilde;
         * admin e-maili mi? True/False? -> true ise ->> şifrelerin uyuşma durumunu kontrol et.
         * admin e-maili mi? True/False? -> false ise ->> girilen email sistemde kayıtlı mı?
         *   True ->> Hata fırlat
         *   False ->> şifrelerin uyuşma durumunu kontrol et.
         * */
        if (dto.getEmail().equalsIgnoreCase("ba.admin@email.com")) {
            user.setStatus(EStatus.ACTIVE);
            user.setUserType(EUserType.ADMIN);
        } else if (!userRepository.findAllByEmailContainingIgnoreCase(dto.getEmail()).isEmpty()) {
            throw new RuntimeException("Girdiğiniz e-mail kullanılmaktadır.");
        }
        if (!user.getPassword().equals(user.getRePassword()) || user.getPassword().isBlank()) {
            throw new RuntimeException("Sifreler ayni degildir.");
        }

        userRepository.save(user);
        return UserMapper.INSTANCE.fromUserToRegisterResponseDto(user);
    }

    public List<User> findAllByOrderByName() {
        return userRepository.findAllByOrderByName();
    }

    public Boolean existsByNameContainsIgnoreCase(String name) {
        return userRepository.existsByNameContainsIgnoreCase(name);
    }

    public List<User> findAllByNameContainingIgnoreCase(String value) {
        return userRepository.findAllByNameContainingIgnoreCase(value);
    }

    public List<User> findByEmailIgnoreCase(String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }

    public Optional<User> findOptionalByEmailIgnoreCase(String email) {
        return userRepository.findOptionalByEmailIgnoreCase(email);
    }

    public List<User> findAllByEmailContainingIgnoreCase(String value) {
        return userRepository.findAllByEmailContainingIgnoreCase(value);
    }

    public List<User> passwordLongerThan(Integer number) {
        return userRepository.passwordLongerThan(number);
    }

    public List<User> passwordLongerThanNoParam(Integer number) {
        return userRepository.passwordLongerThanNoParam(number);
    }

    public List<User> passwordLongerThanJPQL(Integer number) {
        return userRepository.passwordLongerThanJPQL(number);
    }

    public List<User> findAllByEmailEndingWith(String value) {
        return userRepository.findAllByEmailEndingWith(value);
    }

}
