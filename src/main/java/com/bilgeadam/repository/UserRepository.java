package com.bilgeadam.repository;

import com.bilgeadam.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndPassword(String email, String password);

    //Kullanıcıları ismine göre sıralayan bir metot yazınız.
    List<User> findAllByOrderByName(); //Burada DTO hazırlayıp da dönebiliriz.

    //Kullanıcının girdiği bir isimle veritabanındaki bir ismin var olup olmadığını karşılaştırınız.
    Boolean existsByNameContainsIgnoreCase(String name); //existsByName de doğru kabul edilebilir. Soru biraz ucu açık olmuş.

    //Kullanıcının isim sorgulaması için girdiği harf veya kelimeye göre veritabanında sorgu yapan bir metot yazınız.
    List<User> findAllByNameContainingIgnoreCase(String value);

    //Kullanıcının girdiği email'e göre veritabanında sorgu yapan bir metot yazınız.
    // # 1
    List<User> findByEmailIgnoreCase(String email); //e-mail unique değilse. //findAllBy demesek bile liste eşlemesi yapabildiğimizi görüyoruz.

    // # 2
    Optional<User> findOptionalByEmailIgnoreCase(String email); //e-mail unique ise. Unique olmaması durumunda 500 hatası döner.

    // # 3
    List<User> findAllByEmailContainingIgnoreCase(String value); //e-mail unique değilse ve tam mail adresi girmeden arama yapabilmek istiyorsam.
}
