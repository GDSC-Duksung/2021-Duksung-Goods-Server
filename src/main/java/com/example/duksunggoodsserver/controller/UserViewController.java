package com.example.duksunggoodsserver.controller;

import com.example.duksunggoodsserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/view/users")
public class UserViewController {
    private final UserService userService;

    @GetMapping("/verification")
    public String verifyUser(@RequestParam("code") String code) {
        boolean isEnabled = userService.verify(code);
        if (isEnabled)
            return "verify_success";
        else
            return "verify_fail";
    }
}
