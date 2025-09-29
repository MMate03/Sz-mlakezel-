package com.example.szamlakezelo.controller;

import com.google.code.kaptcha.Producer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
public class CaptchaController {

    private final Producer captchaProducer;

    public CaptchaController(Producer captchaProducer) {
        this.captchaProducer = captchaProducer;
    }

    @GetMapping("/captcha.jpg")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("image/jpeg");

        String captchaText = captchaProducer.createText();

        request.getSession().setAttribute("captcha", captchaText);

        BufferedImage image = captchaProducer.createImage(captchaText);

        ImageIO.write(image, "jpg", response.getOutputStream());
    }
}
