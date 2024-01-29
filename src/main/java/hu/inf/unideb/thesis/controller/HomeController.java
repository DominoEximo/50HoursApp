package hu.inf.unideb.thesis.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
public class HomeController {

    @RequestMapping("/index")
    @ResponseBody
    public String index() {
        return "That's pretty basic!";
    }

}
