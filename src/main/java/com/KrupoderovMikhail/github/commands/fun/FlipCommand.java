package com.KrupoderovMikhail.github.commands.fun;

import com.KrupoderovMikhail.github.objects.ICommand;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class FlipCommand implements ICommand {

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {

        String flip = ThreadLocalRandom.current().nextBoolean() ? "Heads" : "Tails";
        event.getChannel().sendMessage(flip).queue();
    }

    @Override
    public String getHelp() {
        return "Flips a coin - head or tails?";
    }

    @Override
    public String getInvoke() {
        return "flip";
    }
}
