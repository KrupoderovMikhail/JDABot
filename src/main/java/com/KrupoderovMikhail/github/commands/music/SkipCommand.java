package com.KrupoderovMikhail.github.commands.music;

import com.KrupoderovMikhail.github.music.GuildMusicManager;
import com.KrupoderovMikhail.github.music.PlayerManager;
import com.KrupoderovMikhail.github.music.TrackScheduler;
import com.KrupoderovMikhail.github.objects.ICommand;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class SkipCommand implements ICommand {

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {

        TextChannel channel = event.getChannel();
        PlayerManager playerManager = PlayerManager.getInstance();
        GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
        TrackScheduler scheduler = musicManager.scheduler;
        AudioPlayer player = musicManager.player;

        if (player.getPlayingTrack() == null) {
            channel.sendMessage("The player isn't playing anything").queue();
            return;
        }

        try {
            scheduler.nextTrack();
            channel.sendMessage("Skipping the current track").queue();
        } catch (IllegalStateException e) {
            scheduler.nextTrack();
        }
    }

    @Override
    public String getHelp() {
        return "Skips the current song";
    }

    @Override
    public String getInvoke() {
        return "skip";
    }
}
