package com.caci.gaia_leave.shared_tools.component;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.text.ParseException;


@Component
@RequiredArgsConstructor
public class AfterStartUp {
    private final JavaMailSender mailSender;

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() throws ParseException {

    }

}
