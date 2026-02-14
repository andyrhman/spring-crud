package com.myspring.spring_crud.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerRequest {
    @JsonProperty("fullName")
    @NotBlank
    @Size(max = 120)
    String fullName;

    @JsonProperty("email")
    @NotBlank
    @Email
    String email;

    @JsonProperty("phone")
    @Size(max = 20)
    String phone;
}
