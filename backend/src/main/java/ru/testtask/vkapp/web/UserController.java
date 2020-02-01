package ru.testtask.vkapp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.testtask.vkapp.business.UserService;
import ru.testtask.vkapp.dto.User;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @CrossOrigin
    @GetMapping(value = "/{code}")
    public User login(@PathVariable("code") String code) {
        return userService.getUserInfo(code);
    }
}
