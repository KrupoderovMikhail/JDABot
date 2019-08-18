package com.KrupoderovMikhail.github.commands.music;

import com.KrupoderovMikhail.github.Constants;
import com.KrupoderovMikhail.github.music.GuildMusicManager;
import com.KrupoderovMikhail.github.music.PlayerManager;
import com.KrupoderovMikhail.github.objects.ICommand;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class VolumeCommand implements ICommand {

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {

        TextChannel channel = event.getChannel();
        PlayerManager playerManager = PlayerManager.getInstance();
        GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());

        if (musicManager.player.getPlayingTrack() == null) {
            channel.sendMessage("There is nothing playing.").queue();
            return;
        }

        if (args.isEmpty()) {
            channel.sendMessageFormat("Volume: %s", musicManager.player.getVolume()).queue();
            return;
        }

        int volume;
        try {
            volume = Integer.parseInt(args.get(0));

            if (volume < 0 || volume > 100) {
                channel.sendMessage("The volume must be in between 0 and 100.").queue();
                return;
            }
        } catch (NumberFormatException e) {
            channel.sendMessage("Could not parse number.").queue();
            return;
        }

        musicManager.player.setVolume(volume);
        channel.sendMessageFormat("Successfully set volume to %s.", musicManager.player.getVolume()).queue();
    }

    @Override
    public String getHelp() {
        return "Changes the volume of the music.\n" +
                "Usage: `" + Constants.PREFIX + getInvoke() + " <0-100>`";
    }

    @Override
    public String getInvoke() {
        return "vol";
    }
}
