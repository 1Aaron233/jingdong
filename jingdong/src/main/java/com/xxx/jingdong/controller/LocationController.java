package com.xxx.jingdong.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1")
public class LocationController {

    @GetMapping("/location")
    public String location(){
        return "location/location";
    }

}
