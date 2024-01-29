package com.bilgeadam.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
@AllArgsConstructor
public enum ErrorType {
    INTERNAL_ERROR_SERVER(5100,"Sunucu Hatası",HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST(4100,"Parametre Hatası",HttpStatus.BAD_REQUEST),

    USER_NOT_FOUND(4101,"Boyle bir kullanıcı bulunamadı",HttpStatus.NOT_FOUND),
    LOGIN_ERROR(4102 ,"Kullanıcı adı veya şifre hatalıdır!!!!",HttpStatus.BAD_REQUEST),

    MOVIE_NOT_FOUND(4201,"Boyle bir film  bulunamadı",HttpStatus.NOT_FOUND)


    ;
   private  int code;
    private String message;
    HttpStatus httpStatus;
}