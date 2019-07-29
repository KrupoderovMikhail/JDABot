package com.KrupoderovMikhail.github.commands.music;

import com.KrupoderovMikhail.github.Constants;
import com.KrupoderovMikhail.github.music.PlayerManager;
import com.KrupoderovMikhail.github.objects.ICommand;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class GachiMusicCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {

        TextChannel channel = event.getChannel();
        PlayerManager manager = PlayerManager.getInstance();

        manager.loadAndPlay(event.getChannel(), "https://www.youtube.com/watch?v=kOCxHu_F5xo&list=PL-VMa2rh7q_ZQvmRt0dqidd9GUC-_42pG");

        manager.getGuildMusicManager(event.getGuild()).player.setVolume(100);

        event.getChannel().sendMessage("You have a good taste in music, sir :wine_glass:\n" + "https://media1.tenor.com/images/c82f2cc34aee229a08a4f39e5e3776b3/tenor.gif?itemid=13328016").queue();
    }

    @Override
    public String getHelp() {
        return "Plays a gachi song\n" +
                "Usage: `" + Constants.PREFIX + getInvoke() + "`";
    }

    @Override
    public String getInvoke() {
        return "gachi";
    }
}
