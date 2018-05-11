package org.springframework.security.samples.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author:longming
 * @Description:
 * @Date:Created in 11:42 2018/5/10
 * @Modified By:
 */
@Controller
public class LoginController {
    @RequestMapping(value = "/login")
    public String Hello(){
        return "login";
    }
}
