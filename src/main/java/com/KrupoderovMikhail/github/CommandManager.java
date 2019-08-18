package com.KrupoderovMikhail.github;

import com.KrupoderovMikhail.github.commands.StrawpollCommand;
import com.KrupoderovMikhail.github.commands.UrbandictionaryCommand;
import com.KrupoderovMikhail.github.commands.WikipediaCommand;
import com.KrupoderovMikhail.github.commands.admin.SetPrefixCommand;
import com.KrupoderovMikhail.github.commands.core.HelpCommand;
import com.KrupoderovMikhail.github.commands.core.ServerInfoCommand;
import com.KrupoderovMikhail.github.commands.core.UptimeCommand;
import com.KrupoderovMikhail.github.commands.core.UserInfoCommand;
import com.KrupoderovMikhail.github.commands.fun.*;
import com.KrupoderovMikhail.github.commands.moderation.BanCommand;
import com.KrupoderovMikhail.github.commands.moderation.KickCommand;
import com.KrupoderovMikhail.github.commands.moderation.PurgeCommand;
import com.KrupoderovMikhail.github.commands.moderation.UnbanCommand;
import com.KrupoderovMikhail.github.commands.music.*;
import com.KrupoderovMikhail.github.commands.owner.EvalCommand;
import com.KrupoderovMikhail.github.config.Config;
import com.KrupoderovMikhail.github.objects.ICommand;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Pattern;

public class CommandManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandManager.class);
    private final Map<String, ICommand> commands = new HashMap<>();

    CommandManager() {

        if (Config.getInstance().getBoolean("loadcommands")) {
            LOGGER.info("Loading commands");

            addCommand(new PingCommand());
            addCommand(new HelpCommand(this));
            addCommand(new CatCommand());
            addCommand(new DogCommand());
            addCommand(new MemeCommand());
            addCommand(new KickCommand());
            addCommand(new BanCommand());
            addCommand(new UnbanCommand());
            addCommand(new SetPrefixCommand());
            addCommand(new UserInfoCommand());
            addCommand(new ServerInfoCommand());
            addCommand(new UptimeCommand());
            addCommand(new PurgeCommand());
            addCommand(new BunnyCommand());
            addCommand(new RollCommand());
            addCommand(new RandomCommand());
            addCommand(new FlipCommand());
            addCommand(new ZalgoCommand());
            addCommand(new ReverseCommand());
            addCommand(new QuoteCommand());
            addCommand(new FacesCommand());
            addCommand(new ChooseCommand());

            addCommand(new UrbandictionaryCommand());
            addCommand(new WikipediaCommand());

            addCommand(new StrawpollCommand());

            // Music command
            addCommand(new JoinCommand());
            addCommand(new LeaveCommand());
            addCommand(new PlayCommand());
            addCommand(new StopCommand());
            addCommand(new QueueCommand());
            addCommand(new SkipCommand());
            addCommand(new NowPlayingCommand());
            addCommand(new VolumeCommand());
            addCommand(new PauseCommand());
            addCommand(new ResumeCommand());
            addCommand(new GachiMusicCommand());

            addCommand(new EvalCommand());
        }
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
        final String prefix = Constants.PREFIXES.get(event.getGuild().getIdLong());

        final String[] split = event.getMessage().getContentRaw().replaceFirst(
                "(?i)" + Pattern.quote(prefix), "").split("\\s+");

        final String invoke = split[0].toLowerCase();

        if (commands.containsKey(invoke)) {
            final List<String> args = Arrays.asList(split).subList(1, split.length);

            event.getChannel().sendTyping().queue();
            commands.get(invoke).handle(args, event);
        }
    }
}
