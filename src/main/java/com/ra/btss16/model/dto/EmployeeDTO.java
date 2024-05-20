package com.ra.btss16.model.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmployeeDTO {
    private Integer id;

    @NotEmpty(message = "Employee name is empty")
    private String name;

    @NotEmpty(message = "Employee address is empty")
    private String address;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Date of birth is empty")
    @Past(message = "Date of birth is not valid")
    private Date dateOfBirth;

    @NotEmpty(message = "Employee phone is empty")
    private String phone;
    private MultipartFile imgFile;
}
