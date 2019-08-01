package com.KrupoderovMikhail.github.commands;

import com.KrupoderovMikhail.github.Constants;
import com.KrupoderovMikhail.github.objects.ICommand;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.List;

public class WikipediaCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {

        if (args.isEmpty()) {
            event.getChannel().sendMessage("Missing arguments").queue();
            return;
        }

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://wikipedia.org/w/api.php?format=json&action=query&prop=extracts&redirects=1&exintro=&explaintext=&titles=" + args.toString()).build();

        try {
            Response response = client.newCall(request).execute();

            JSONObject object = new JSONObject(new JSONTokener(response.body().byteStream()));
            JSONObject pages = object.getJSONObject("query").getJSONObject("pages");
            String pageNumber = pages.keySet().iterator().next();

            if (pageNumber.equals("-1")) {
                event.getChannel().sendMessage("Failed to retrieve result from Wikipedia.").queue();
                return;
            }

            JSONObject page = pages.getJSONObject(pageNumber);

            EmbedBuilder builder = new EmbedBuilder();

            builder.setTitle(page.getString("title"));
            builder.setDescription(page.getString("extract").length() > 2048 ? page.getString("extract").substring(0, 2000) + "..." : page.getString("extract"));

            event.getChannel().sendMessage(builder.build()).queue();
        } catch (IOException e) {
            event.getChannel().sendMessage("Failed to retrieve results from Wikipedia.").queue();
        }
    }

    @Override
    public String getHelp() {
        return "Look up a tern on Wikipedia.\n" +
                "Usage: " + Constants.PREFIX + getInvoke() + " [term]";
    }

    @Override
    public String getInvoke() {
        return "wikipedia";
    }
}
