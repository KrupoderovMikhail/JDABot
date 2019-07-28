package com.KrupoderovMikhail.github.commands.fun;

import com.KrupoderovMikhail.github.Constants;
import com.KrupoderovMikhail.github.objects.ICommand;
import me.nsylke.zalgo4j.Zalgo4J;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class ZalgoCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {

        if (args.isEmpty()) {
            event.getChannel().sendMessage("Missing arguments").queue();
            return;
        }

        event.getChannel().sendMessage(Zalgo4J.zalgolize(args.toString().replaceAll("^\\[|\\]$", ""))).queue();
    }

    @Override
    public String getHelp() {
        return "Zalgolize some text.\n" +
                "Usage: `" + Constants.PREFIX + getInvoke() + " <text>`";
    }

    @Override
    public String getInvoke() {
        return "zalgo";
    }
}
