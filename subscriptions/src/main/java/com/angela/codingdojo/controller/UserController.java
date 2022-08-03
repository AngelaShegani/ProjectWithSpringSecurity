package com.angela.codingdojo.controller;

import com.angela.codingdojo.model.Package;
import com.angela.codingdojo.model.User;
import com.angela.codingdojo.util.UserValidator;
import com.angela.codingdojo.service.PackageService;
import com.angela.codingdojo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class UserController {

    @Autowired
    private UserService uService;
    @Autowired
    private UserValidator uValidator;
    @Autowired
    private PackageService pService;

    @GetMapping("/registration")
    public String register(Model model) {
        return "registration";
    }

    @PostMapping("/registration")
    public String registerPost(User user, Model model, BindingResult result) {
        uValidator.validate(user, result);
        if (result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            return register(model);
        }
        uService.registerUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid Credentials, Please try again.");
        }
        return "login";
    }

    @GetMapping("/")
    public String index(Principal principal) {
        System.out.println(principal.getName());
        if (principal.getName() != null && uService.emailExists(principal.getName())) {
            User user = uService.findByEmail(principal.getName());
            if (user.isAdmin()) {
                return "redirect:/packages";
            } else {
                return "redirect:/users/" + user.getId();
            }
        } else {
            return "redirect:/login";
        }
    }


    @GetMapping("/users/{id}")
    public String user(Principal principal, Model model, @PathVariable Integer id) {
        if (principal.getName() != null && uService.emailExists(principal.getName()) && uService.userExist(id) && uService.findByEmail(principal.getName()).getId() == id) {
            model.addAttribute("user", uService.findByEmail(principal.getName()));
            model.addAttribute("packages", pService.findAll());
            return "user";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/users/{id}/switch_package")
    public String switchPackage(Principal principal, @PathVariable Integer id, Integer packageId) {
        if (principal.getName() != null && uService.emailExists(principal.getName()) && uService.userExist(id) && uService.findByEmail(principal.getName()).getId() == id) {
            User user = uService.findByEmail(principal.getName());
            Package p = pService.findById(packageId);
            if (p != null) {
                uService.setPackage(user, p);
            }
            return "redirect:/users/" + user.getId();
        } else {
            return "redirect:/login";
        }
    }

}
