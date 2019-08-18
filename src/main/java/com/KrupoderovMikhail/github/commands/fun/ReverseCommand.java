package com.KrupoderovMikhail.github.commands.fun;

import com.KrupoderovMikhail.github.Constants;
import com.KrupoderovMikhail.github.objects.ICommand;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class ReverseCommand implements ICommand {

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {

        if (args.isEmpty()) {
            event.getChannel().sendMessage("Missing arguments").queue();
            return;
        }

        StringBuilder builder = new StringBuilder(args.toString().replaceAll("^\\[|\\]$", ""));
        event.getChannel().sendMessage(builder.reverse().toString()).queue();
    }

    @Override
    public String getHelp() {
        return "Reverses the inputted message.\n" +
                "Usage: `" + Constants.PREFIX + getInvoke() + " <message>`";
    }

    @Override
    public String getInvoke() {
        return "reverse";
    }
}
