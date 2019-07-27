package com.KrupoderovMikhail.github.commands.fun;

import com.KrupoderovMikhail.github.objects.ICommand;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class BunnyCommand implements ICommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(BunnyCommand.class);

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {

        String type = Math.random() <= 0.25 ? "gif" : "poster";

        OkHttpClient http = new OkHttpClient();
        Request request = new Request.Builder().url(String.format("https://api.bunnies.io/v2/loop/random/?media=%s", type)).build();

        try {
            Response response = http.newCall(request).execute();

            event.getChannel().sendMessage(new JSONObject(response.body().string()).getJSONObject("media").get(type).toString()).queue();
        } catch (IOException e) {
            LOGGER.error("Bunny API has either been updated or is down for maintenance.");
            event.getChannel().sendMessage("Failed to make a request.").queue();
        }
    }

    @Override
    public String getHelp() {
        return "Gives you random bunny picture.";
    }

    @Override
    public String getInvoke() {
        return "bunny";
    }
}
