package com.KrupoderovMikhail.github;

import lombok.extern.java.Log;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

@Log
class Listener extends ListenerAdapter {

    private final CommandManager manager = new CommandManager();

    @Override
    public void onReady(ReadyEvent event) {
        log.info(String.format("Logged in as %#s\n", event.getJDA().getSelfUser()));
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User author = event.getAuthor();
        Message message = event.getMessage();
        String content = message.getContentDisplay();

        if (event.isFromType(ChannelType.TEXT)) {

            Guild guild = event.getGuild();
            TextChannel textChannel = event.getTextChannel();

            log.info(String.format("(%s)[%s]<%#s>: %s\n", guild.getName(), textChannel.getName(), author, content));
        } else if (event.isFromType(ChannelType.PRIVATE)) {
            log.info(String.format("|PRIV|<%#s>: %s\n", author, content));
        }
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        if (event.getMessage().getContentRaw().equalsIgnoreCase(Constants.PREFIX + "shutdown") &&
        event.getAuthor().getIdLong() == Constants.OWNER) {
            shutdown(event.getJDA());
            return;
        }

        if (!event.getAuthor().isBot() && !event.getMessage().isWebhookMessage() &&
        event.getMessage().getContentRaw().startsWith(Constants.PREFIX)) {
            manager.handleCommand(event);
        }
    }

    private void shutdown(JDA jda) {
        jda.shutdown();
    }
}
