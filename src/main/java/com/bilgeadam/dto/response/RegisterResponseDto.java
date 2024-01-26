package com.bilgeadam.dto.response;

import com.bilgeadam.utility.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterResponseDto {
    String name;
    String surname;
    String email;
    EStatus status;
}
