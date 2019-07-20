package com.KrupoderovMikhail.github;

import lombok.extern.java.Log;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

@Log
class Listener extends ListenerAdapter {

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
}
