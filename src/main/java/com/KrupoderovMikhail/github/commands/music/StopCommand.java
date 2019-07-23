package com.KrupoderovMikhail.github.commands.music;

import com.KrupoderovMikhail.github.music.GuildMusicManager;
import com.KrupoderovMikhail.github.music.PlayerManager;
import com.KrupoderovMikhail.github.objects.ICommand;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class StopCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {

        PlayerManager playerManager = PlayerManager.getInstance();
        GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());

        musicManager.scheduler.getQueue().clear();
        musicManager.player.stopTrack();
        musicManager.player.setPaused(false);

        event.getChannel().sendMessage("Stopping the player and clearing the queue").queue();
    }

    @Override
    public String getHelp() {
        return "Stops the music player";
    }

    @Override
    public String getInvoke() {
        return "stop";
    }
}
