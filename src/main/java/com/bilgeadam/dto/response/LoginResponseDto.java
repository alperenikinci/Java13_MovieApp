package com.bilgeadam.dto.response;

import com.bilgeadam.entity.User;
import com.bilgeadam.utility.EStatus;
import com.bilgeadam.utility.EUserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDto {
    String email;
    String password;
    EStatus status;
    EUserType userType;
}
