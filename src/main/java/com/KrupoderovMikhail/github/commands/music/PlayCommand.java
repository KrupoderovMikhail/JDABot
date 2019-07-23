package com.KrupoderovMikhail.github.commands.music;

import com.KrupoderovMikhail.github.music.PlayerManager;
import com.KrupoderovMikhail.github.objects.ICommand;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class PlayCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {

        PlayerManager manager = PlayerManager.getInstance();

        manager.loadAndPlay(event.getChannel(), "https://www.youtube.com/watch?v=f4Mc-NYPHaQ");

        manager.getGuildMusicManager(event.getGuild()).player.setVolume(100);

    }

    @Override
    public String getHelp() {
        return null;
    }

    @Override
    public String getInvoke() {
        return "play";
    }
}
