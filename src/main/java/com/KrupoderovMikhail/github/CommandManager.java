package com.KrupoderovMikhail.github;

import com.KrupoderovMikhail.github.commands.*;
import com.KrupoderovMikhail.github.commands.fun.CatCommand;
import com.KrupoderovMikhail.github.commands.fun.DogCommand;
import com.KrupoderovMikhail.github.commands.fun.MemeCommand;
import com.KrupoderovMikhail.github.commands.fun.PingCommand;
import com.KrupoderovMikhail.github.commands.moderation.BanCommand;
import com.KrupoderovMikhail.github.commands.moderation.KickCommand;
import com.KrupoderovMikhail.github.commands.moderation.UnbanCommand;
import com.KrupoderovMikhail.github.objects.ICommand;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.regex.Pattern;

public class CommandManager {

    private final Map<String, ICommand> commands = new HashMap<>();

    CommandManager() {
        addCommand(new PingCommand());
        addCommand(new HelpCommand(this));
        addCommand(new CatCommand());
        addCommand(new DogCommand());
        addCommand(new MemeCommand());
        addCommand(new KickCommand());
        addCommand(new BanCommand());
        addCommand(new UnbanCommand());
    }

    private void addCommand(ICommand command) {
        if (!commands.containsKey(command.getInvoke())) {
            commands.put(command.getInvoke(), command);
        }
    }

    public Collection<ICommand> getCommands() {
        return commands.values();
    }

    public ICommand getCommand(@NotNull String name) {
        return commands.get(name);
    }

    void handleCommand(GuildMessageReceivedEvent event) {
        final String[] split = event.getMessage().getContentRaw().replaceFirst(
                "(?i)" + Pattern.quote(Constants.PREFIX), "").split("\\s+");
        final String invoke = split[0].toLowerCase();

        if (commands.containsKey(invoke)) {
            final List<String> args = Arrays.asList(split).subList(1, split.length);

            event.getChannel().sendTyping().queue();
            commands.get(invoke).handle(args, event);
        }
    }
}
