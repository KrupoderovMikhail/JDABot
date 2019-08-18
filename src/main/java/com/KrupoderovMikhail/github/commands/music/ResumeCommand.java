package com.KrupoderovMikhail.github.commands.music;

import com.KrupoderovMikhail.github.music.GuildMusicManager;
import com.KrupoderovMikhail.github.music.PlayerManager;
import com.KrupoderovMikhail.github.objects.ICommand;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class ResumeCommand implements ICommand {

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {

        TextChannel channel = event.getChannel();
        PlayerManager playerManager = PlayerManager.getInstance();
        GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());

        if (musicManager.player.getPlayingTrack() == null) {
            channel.sendMessage("There is nothing playing").queue();
            return;
        }

        musicManager.player.setPaused(false);
        channel.sendMessage("Successfully resumed " + musicManager.player.getPlayingTrack().getInfo().title).queue();
    }

    @Override
    public String getHelp() {
        return "Resumes playing a previously paused song";
    }

    @Override
    public String getInvoke() {
        return "resume";
    }
}
