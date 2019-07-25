package com.KrupoderovMikhail.github;

import com.KrupoderovMikhail.github.commands.fun.CatCommand;
import com.KrupoderovMikhail.github.config.Config;
import me.duncte123.botcommons.messaging.EmbedUtils;
import me.duncte123.botcommons.web.WebUtils;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.Random;

public class Main {

    private final Random random = new Random();
    private static final Logger LOGGER = LoggerFactory.getLogger(CatCommand.class);

    private Main() throws IOException {

        Config config = new Config(new File("botconfig.json"));
        CommandManager commandManager = new CommandManager();
        Listener listener = new Listener(commandManager);

        WebUtils.setUserAgent("Dumpling JDA Bot/КислыйПельмешек#8119"); // Set your own user agent as string
        EmbedUtils.setEmbedBuilder(
                () -> new EmbedBuilder()
                        .setColor(getRandomColor())
                        .setFooter("{Dumpling}", null)
                        .setTimestamp(Instant.now())
        );

        try {
            LOGGER.info("Booting");
            new JDABuilder(AccountType.BOT)
                    .setToken(config.getString("token"))
                    .setGame(Game.listening("!help"))
                    .addEventListener(listener)
                    .build().awaitReady();
            LOGGER.info("Running");
        } catch (InterruptedException | LoginException e) {
            e.printStackTrace();
        }
    }

    private Color getRandomColor() {
        float r = random.nextFloat();
        float g = random.nextFloat();
        float b = random.nextFloat();

        return new Color(r, g, b);
    }

    public static void main(String[] args) throws IOException {
        new Main();
    }
}
