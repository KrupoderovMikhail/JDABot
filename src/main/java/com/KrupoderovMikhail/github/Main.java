package com.KrupoderovMikhail.github;

import lombok.extern.java.Log;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;

import javax.security.auth.login.LoginException;

@Log
public class Main {

    private Main() {
        try {
            log.info("Booting...");
            new JDABuilder(AccountType.BOT)
                    .setToken(Secrets.TOKEN)
                    .setAudioEnabled(false)
                    .setGame(Game.streaming("JDA", "https://www.twitch.tv/santamas"))
                    .addEventListener(new Listener())
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
