package com.murggar.SkillCraftHub.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.murggar.SkillCraftHub.Entities.UsersEntity;
import com.murggar.SkillCraftHub.Services.UserServices;

@Controller
public class TemplaController {

    private UserServices userServices;

    public TemplaController(UserServices userServices) {
        this.userServices = userServices;
    }

    // @GetMapping("/courses")
    // public ModelAndView courses() {
    // ModelAndView mv = new ModelAndView();
    // mv.setViewName("courses");
    // mv.addObject("modules", modulesServices.getAll());

    // mv.addObject("link", modulesServices.getAll().get(0).getImageUrl());
    // return mv;
    // }

    @GetMapping("/")
    public ModelAndView home() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");

        return mv;
    }

    @GetMapping("/logout_success")
    public ModelAndView logout(@ModelAttribute UsersEntity user) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("logout");
        return mv;
    }

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView mv = new ModelAndView();
        UsersEntity uEntity = new UsersEntity();

        mv.addObject("user", uEntity);
        mv.setViewName("login");
        return mv;
    }

    @PostMapping("/perform_login")
    public ModelAndView home(@ModelAttribute UsersEntity user, BindingResult bindingResult) {
        ModelAndView mv = new ModelAndView();

        if (bindingResult.hasErrors()) {

            List<String> errorMessage = bindingResult.getFieldErrors().stream().map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());

            String errorMessageString = errorMessage.toString();
            throw new RuntimeException(errorMessageString);
        }

        userServices.loginUser(user);

        return mv;
    }

    @GetMapping("/create_an_account")
    public ModelAndView getRegister() {
        ModelAndView mv = new ModelAndView();

        UsersEntity uEntity = new UsersEntity();

        mv.addObject("user", uEntity);
        mv.setViewName("register");
        return mv;
    }

    @PostMapping("/register")
    public ModelAndView register(@ModelAttribute UsersEntity user, BindingResult bindingResult) {
        ModelAndView mv = new ModelAndView();
        user.setRole("USER");

        if (bindingResult.hasErrors()) {

            List<String> errorMessage = bindingResult.getFieldErrors().stream().map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());

            String errorMessageString = errorMessage.toString();
            throw new RuntimeException(errorMessageString);
        }

        userServices.registerUser(user);

        mv.setViewName("login");
        return mv;
    }

    @GetMapping("/payment")
    public ModelAndView payment() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("payment");
        return mv;
    }

    @GetMapping("/update")
    public ModelAndView updateRole() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        userServices.updateUser(username);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("courses");
        return mv;
    }
}
