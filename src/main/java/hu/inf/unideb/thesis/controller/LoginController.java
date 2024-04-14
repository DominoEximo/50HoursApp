package hu.inf.unideb.thesis.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

/**
 * Controller for handling logging in and out.
 */
@RestController("/login")
@CrossOrigin
@RequestScope
public class LoginController {

    /**
     * Endpoint to delegate username and password to Spring Security
     * @param username the name of the user
     * @param password the user's password
     * @return on successful login it redirects to a designated page
     */
    @PostMapping("/perform_login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password) {

        return "redirect:/index";
    }

    /**
     * Endpoint to delegate request to Spring Security for logging out
     * @return on successful logout it redirects to a designated page
     */
    @GetMapping("/perform_logout")
    public String logout() {

        return "redirect:/index";
    }
}
