package com.KrupoderovMikhail.github.commands.music;

import com.KrupoderovMikhail.github.Constants;
import com.KrupoderovMikhail.github.music.PlayerManager;
import com.KrupoderovMikhail.github.objects.ICommand;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class PlayCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {

        TextChannel channel = event.getChannel();

        if (args.isEmpty()) {
            channel.sendMessage("Please provide some arguments").queue();

            return;
        }

        String input = String.join(" ", args);

        if (!isURL(input) && input.startsWith("vtsearch:")) {
            // Use the youtube api for search instead, making a lot of requests with "vtsearch:" will get you blocked
            channel.sendMessage("Please provide a valid youtube, soundcloud or bandcamp link").queue();

            return;
        }

        PlayerManager manager = PlayerManager.getInstance();

        manager.loadAndPlay(event.getChannel(), input);

        manager.getGuildMusicManager(event.getGuild()).player.setVolume(100);
    }

    private boolean isURL(String input) {
        try {
            new URL(input);
            return true;
        } catch (MalformedURLException ignored) {
            return false;
        }
    }

    @Override
    public String getHelp() {
        return "Plays a song\n" +
                "Usage: `" + Constants.PREFIX + getInvoke() + " <song url>`";
    }

    @Override
    public String getInvoke() {
        return "play";
    }
}
