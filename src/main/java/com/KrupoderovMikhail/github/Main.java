package com.KrupoderovMikhail.github;

import com.KrupoderovMikhail.github.commands.fun.CatCommand;
import com.KrupoderovMikhail.github.secrets.Secrets;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;

public class Main {

    CommandManager commandManager = new CommandManager();
    Listener listener = new Listener(commandManager);
    private static final Logger LOGGER = LoggerFactory.getLogger(CatCommand.class);

    private Main() {
        try {
            LOGGER.info("Booting");
            new JDABuilder(AccountType.BOT)
                    .setToken(Secrets.TOKEN)
                    .setGame(Game.listening("!help"))
                    .addEventListener(listener)
                    .build().awaitReady();
            LOGGER.info("Running");
        } catch (InterruptedException | LoginException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}
