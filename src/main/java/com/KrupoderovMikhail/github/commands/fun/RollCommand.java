package com.KrupoderovMikhail.github.commands.fun;

import com.KrupoderovMikhail.github.objects.ICommand;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RollCommand implements ICommand {

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {

        if (args.isEmpty()) {
            event.getChannel().sendMessage("Please provide some arguments.").queue();

            return;
        }

        String[] values = args.get(0).split("d");

        if (values.length != 2) {
            event.getChannel().sendMessage("Incorrect usage.").queue();
            return;
        }

        int dices;
        int sides;

        try {
            dices = Integer.parseInt(values[0]);
            sides = Integer.parseInt(values[1]);
        } catch (NumberFormatException e) {
            dices = 2;
            sides = 6;
        }

        if (dices == 0 || dices > 100) {
            event.getChannel().sendMessage("Dices must be in between 0 and 100.").queue();
            return;
        }

        if (sides == 0 || sides > 1000) {
            event.getChannel().sendMessage("Sides must be in between 0 and 1000.").queue();
        }

        int[] rolls = new int[dices];

        for (int i = 0; i < dices; i++) {
            rolls[i] = (new Random().ints(1, sides).iterator().nextInt());
        }

        event.getChannel().sendMessageFormat("You rolled: %s. Total: %s", IntStream.of(rolls).mapToObj(Integer::toString).collect(Collectors.joining(", ")), IntStream.of(rolls).sum()).queue();
    }

    @Override
    public String getHelp() {
        return "Role some dice";
    }

    @Override
    public String getInvoke() {
        return "roll";
    }
}
