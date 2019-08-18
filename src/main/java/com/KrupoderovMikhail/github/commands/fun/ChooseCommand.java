package com.KrupoderovMikhail.github.commands.fun;

import com.KrupoderovMikhail.github.Constants;
import com.KrupoderovMikhail.github.objects.ICommand;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;
import java.util.Random;

public class ChooseCommand implements ICommand {

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {

        if (args.isEmpty()) {
            event.getChannel().sendMessage("Missing arguments").queue();
            return;
        }

        String[] input = args.toString().split(", ");
        event.getChannel().sendMessage(input[new Random().nextInt(input.length)]).queue();
    }

    @Override
    public String getHelp() {
        return "Choose an option from a list.\n" +
                "Usage: `" + Constants.PREFIX + getInvoke() + " [option, option, option]`";
    }

    @Override
    public String getInvoke() {
        return "choose";
    }
}
