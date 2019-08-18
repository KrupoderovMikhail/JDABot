package com.KrupoderovMikhail.github;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Listener extends ListenerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(Listener.class);
    private final CommandManager manager;

    Listener(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void onReady(ReadyEvent event) {
        LOGGER.info(String.format("Logged in as %#s\n", event.getJDA().getSelfUser()));
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User author = event.getAuthor();
        Message message = event.getMessage();
        String content = message.getContentDisplay();

        if (event.isFromType(ChannelType.TEXT)) {

            Guild guild = event.getGuild();
            TextChannel textChannel = event.getTextChannel();

            LOGGER.info(String.format("(%s)[%s]<%#s>: %s", guild.getName(), textChannel.getName(), author, content));
        } else if (event.isFromType(ChannelType.PRIVATE)) {
            LOGGER.info(String.format("|PRIV|<%#s>: %s", author, content));
        }
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        String raw = event.getMessage().getContentRaw();

        if (raw.equalsIgnoreCase(Constants.PREFIX + "shutdown") &&
                event.getAuthor().getIdLong() == Constants.OWNER) {
            shutdown(event.getJDA());
            return;
        }

        String prefix = Constants.PREFIXES.computeIfAbsent(event.getGuild().getIdLong(), (l) -> Constants.PREFIX);

        if (!event.getAuthor().isBot() && !event.getMessage().isWebhookMessage() && raw.startsWith(prefix)) {
            manager.handleCommand(event);
        }
    }

    private void shutdown(JDA jda) {
        jda.shutdown();
        System.exit(0);
    }
}
