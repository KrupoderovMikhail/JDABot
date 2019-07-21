package com.KrupoderovMikhail.github.commands;

import com.KrupoderovMikhail.github.objects.ICommand;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DogCommand implements ICommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(DogCommand.class);

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        OkHttpClient http = new OkHttpClient();
        Request request = new Request.Builder().url("https://dog.ceo/api/breeds/image/random").build();

        try {
            Response response = http.newCall(request).execute();

            event.getChannel().sendMessage(new JSONObject(response.body().string()).get("message").toString()).queue();
        } catch (Exception e) {
            LOGGER.error("Dog API has either been updated or is down for maintenance.", e);
            event.getChannel().sendMessage("Failed to make a request.").queue();
        }
    }

    @Override
    public String getHelp() {
        return "Shows you a random dog";
    }

    @Override
    public String getInvoke() {
        return "dog";
    }
}
