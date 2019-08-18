package com.KrupoderovMikhail.github.commands.moderation;

import com.KrupoderovMikhail.github.Constants;
import com.KrupoderovMikhail.github.objects.ICommand;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class PurgeCommand implements ICommand {

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {

        TextChannel channel = event.getChannel();
        Member member = event.getMember();
        Member selfMember = event.getGuild().getSelfMember();

        if (!member.hasPermission(Permission.MESSAGE_MANAGE)) {
            channel.sendMessage("You need the `Manage Messages` permission to use this command").queue();
            return;
        }

        if (!selfMember.hasPermission(Permission.MESSAGE_MANAGE)) {
            channel.sendMessage("I need the `Manage Messages` permission for this command").queue();
            return;
        }

        if (args.isEmpty()) {
            channel.sendMessage("Correct usage is: `" + Constants.PREFIX + getInvoke() + " <amount>`").queue();
            return;
        }

        int amount;
        String arg = args.get(0);

        try {
            amount = Integer.parseInt(arg);
        } catch (NumberFormatException ignored) {
            channel.sendMessageFormat("`%s` is not a valid number", arg).queue();
            return;
        }

        if (amount < 2 || amount > 100) {
            channel.sendMessageFormat("Amount must be at least 2 and at most 100").queue();
            return;
        }

        channel.getIterableHistory()
                .takeAsync(amount)
                .thenApplyAsync((messages) -> {
                    List<Message> goodMessages = messages.stream()
                            .filter((m) -> m.getCreationTime().isBefore(
                                    OffsetDateTime.now().plus(2, ChronoUnit.WEEKS)
                            ))
                            .collect(Collectors.toList());

                    channel.purgeMessages(goodMessages);


                    return goodMessages.size();
                })
                .whenCompleteAsync((count, thr) -> channel.sendMessageFormat("Deleted `%d` messages", count).queue(
                        (message) -> message.delete().queueAfter(10, TimeUnit.SECONDS)
                )).exceptionally((thr) -> {
            String cause = "";

            if (thr.getCause() != null) {
                cause = " caused by: " + thr.getCause().getMessage();
            }

            channel.sendMessageFormat("Error: %s%s", thr.getMessage(), cause).queue();
            return 0;
        });
    }

    @Override
    public String getHelp() {
        return "Clears the chat with the specified amount of messages.\n" +
                "Usage: `" + Constants.PREFIX + getInvoke() + " <amount>`";
    }

    @Override
    public String getInvoke() {
        return "purge";
    }
}
