package com.example.szamlakezelo.config;


import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class CaptchaConfig {
    @Bean
    public DefaultKaptcha captchaProducer() {
        Properties props = new Properties();
        props.setProperty("kaptcha.image.width", "200");
        props.setProperty("kaptcha.image.height", "50");
        props.setProperty("kaptcha.textproducer.font.size", "40");
        props.setProperty("kaptcha.textproducer.char.length", "5");
        props.setProperty("kaptcha.textproducer.char.string", "ABCDEFGHJKMNPQRSTUVWXYZ23456789");
        props.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");
        Config config = new Config(props);
        DefaultKaptcha kaptcha = new DefaultKaptcha();
        kaptcha.setConfig(config);
        return kaptcha;
    }
}
