package com.KrupoderovMikhail.github.commands.fun;

import com.KrupoderovMikhail.github.Constants;
import com.KrupoderovMikhail.github.objects.ICommand;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;
import java.util.Random;

public class RandomCommand implements ICommand {

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {

        if (args.size() == 1) {
            int min;

            try {
                min = Integer.parseInt(args.get(0));
            } catch (NumberFormatException e) {
                min = 0;
            }

            event.getChannel().sendMessage(Integer.toString(new Random().ints(0, min).iterator().nextInt())).queue();
        } else if (args.size() == 2) {
            int min;
            int max;

            try {
                min = Integer.parseInt(args.get(0));
                max = Integer.parseInt(args.get(1));
            } catch (NumberFormatException e) {
                min = 0;
                max = Integer.MAX_VALUE;
            }

            event.getChannel().sendMessage(Integer.toString(new Random().ints(min, max).iterator().nextInt())).queue();
        } else {
            event.getChannel().sendMessage(Integer.toString(new Random().ints(0, Integer.MAX_VALUE).iterator().nextInt())).queue();
        }
    }

    @Override
    public String getHelp() {
        return "Returns a random number.\n" +
                "Usage: `" + Constants.PREFIX + getInvoke() + " [min] / [min] [max]`";
    }

    @Override
    public String getInvoke() {
        return "random";
    }
}
