package ru.testtask.vkapp.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class User {
    private String photoUrl;
    private List<Friend> friends;
}
