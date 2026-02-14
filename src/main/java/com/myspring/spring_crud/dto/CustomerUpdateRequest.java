package com.myspring.spring_crud.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerUpdateRequest {
    @JsonProperty("fullName")
    @Size(max = 120)
    private String fullName;

    @JsonProperty("email")
    @Email
    @Size(max = 120)
    private String email;

    @JsonProperty("phone")
    @Size(max = 120)
    private String phone;
    
}
