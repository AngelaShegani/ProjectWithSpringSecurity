package com.angela.codingdojo.controller;

import com.angela.codingdojo.model.Package;
import com.angela.codingdojo.service.PackageService;
import com.angela.codingdojo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class PackageController {

    @Autowired
    UserService uService;
    @Autowired
    PackageService pService;

    @GetMapping("/packages")
    public String dashboard(Principal principal, Model model) {
        if (principal.getName() != null && uService.emailExists(principal.getName())) {
            model.addAttribute("users", uService.findAll());
            model.addAttribute("packages", pService.findAllOrderBySubscribed());
            return "packages";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/packages")
    public String addPackage(Principal principal, Package aPackage) {
        if (principal.getName() != null && uService.emailExists(principal.getName()) && uService.findByEmail(principal.getName()).isAdmin()) {
            aPackage.setAvailable(false);
            pService.save(aPackage);
            return "redirect:/packages";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/packages/{id}/set_available")
    public String activatePackage(Principal principal, @PathVariable Integer id, boolean available) {
        Package aPackage = pService.findById(id);
        if (principal.getName() != null && uService.emailExists(principal.getName()) && uService.findByEmail(principal.getName()).isAdmin() && aPackage != null && aPackage.getUsers().size() == 0) {
            aPackage.setAvailable(available);
            pService.save(aPackage);
            return "redirect:/packages";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/packages/{id}")
    public String getPackage(Principal principal, Model model, @PathVariable Integer id) {
        Package aPackage = pService.findById(id);
        if (principal.getName() != null && uService.emailExists(principal.getName()) && uService.findByEmail(principal.getName()).isAdmin() && aPackage != null) {
            model.addAttribute("package", aPackage);
            return "edit_package";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/packages/{id}")
    public String postPackage(Principal principal, @PathVariable Integer id, Package aPackage) {
        Package p = pService.findById(id);
        if (principal.getName() != null && uService.emailExists(principal.getName()) && uService.findByEmail(principal.getName()).isAdmin() && p != null) {
            p.setName(aPackage.getName());
            p.setCost(aPackage.getCost());
            pService.save(p);
            return "redirect:/packages";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/packages/{id}/delete")
    public String deletePackage(Principal principal, @PathVariable Integer id) {
        Package p = pService.findById(id);
        if (principal.getName() != null && uService.emailExists(principal.getName()) && uService.findByEmail(principal.getName()).isAdmin() && p != null) {
            pService.delete(p);
            return "redirect:/packages";
        } else {
            return "redirect:/login";
        }
    }

}
