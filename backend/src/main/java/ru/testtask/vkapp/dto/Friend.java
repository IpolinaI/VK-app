package ru.testtask.vkapp.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Friend {
    private String firstName;
    private String lastName;
}
