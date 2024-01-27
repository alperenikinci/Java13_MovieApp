package com.bilgeadam.repository;

import com.bilgeadam.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    // Passwordunun uzunluğu belirlediğimiz sayıdan büyük olanları getiren sorguyu yazınız.
    // Native Query ve JPQL ile yazalım, Native Query'de @Param anotasyonunu kullanalım.

    // #1 Native Query -> tablonun ismine seslendiğimiz sorgular.
    @Query(value = "SELECT * FROM tbl_user as u WHERE length(u.password)>:x ", nativeQuery = true)
    List<User> passwordLongerThan(@Param("x") Integer number);
    /*
    @Param anotasyonu number değişkenimize ("x") adını veriyor. Sonrasında query içerisinde x'e seslendiğimde aslında dışarıdan
    "number" ismi ile çağırdığım Integer değişkene sesleniyorum. @Param kullanımında parametrelerin sırası değil,query'de seslenmek için
    tanımladığımız @Param()'ın parantez içinde bulunan değişken ismi önemli oluyor.
    */

    // Native Query @Param'sız versiyon.
    @Query(value = "SELECT * FROM tbl_user as u WHERE length(u.password)>?1 ", nativeQuery = true)
    List<User> passwordLongerThanNoParam(Integer number);
    /*
    @Param yerine ?1, ?2, ?3... yaklaşımını kullanmaya karar  verirsem parametreleri aldığım sıra önem kazanıyor.
    ?1 -> İlk alınan parametrenin değerini query'e koyar.
    ?2 -> 2. alınan parametrenin değerini query'e koyar......
    Parametrenin sırasını bulup, ? + (param sıra no) şeklinde kullanırım.
     */


    // #2 JPQL -> DB'deki tablo ismine değil, Entity Class ismine (User) seslenilerek atılan sorgu.
    @Query("SELECT u FROM User AS u WHERE length(u.password)>?1 ")
    List<User> passwordLongerThanJPQL(Integer number);

    //e-mailin sonu kullanıcının girdiği değerlere göre biten emailleri listeleyiniz.
    List<User> findAllByEmailEndingWith(String value);
}
