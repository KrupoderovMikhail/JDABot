package com.KrupoderovMikhail.github;

import lombok.extern.java.Log;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;

import javax.security.auth.login.LoginException;

@Log
public class Main {

    private Main() {
        try {
            log.info("Booting...");
            new JDABuilder(AccountType.CLIENT)
                    .setToken(Secrets.TOKEN)
                    .setAudioEnabled(false)
                    .build().awaitReady();
            log.info("Running...");
        } catch (InterruptedException | LoginException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new Main();
    }
}
