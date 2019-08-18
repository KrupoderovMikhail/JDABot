package com.KrupoderovMikhail.github.commands;

import com.KrupoderovMikhail.github.Constants;
import com.KrupoderovMikhail.github.objects.ICommand;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class UrbandictionaryCommand implements ICommand {

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {

        String url;

        if (args.isEmpty()) {
            url = "http://api.urbandictionary.com/v0/random";
        } else {
            url = "http://api.urbandictionary.com/v0/define?term=" + args.toString();
        }

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        try {
            Response response = client.newCall(request).execute();

            JSONArray list = new JSONObject(new JSONTokener(response.body().byteStream())).getJSONArray("list");
            JSONObject term = list.getJSONObject(0);

            EmbedBuilder builder = new EmbedBuilder();

            builder.setTitle(term.getString("word"), term.getString("permalink"));
            builder.setDescription(term.getString("definition").replaceAll("\\[", "").replaceAll("]", ""));
            builder.setColor(Color.GREEN);
            builder.addField("Example", term.getString("example").replaceAll("\\[", "").replaceAll("]", ""), false);
            builder.addField("Rating", ":arrow_up: `" + term.getInt("thumbs_up") + "` :arrow_down: `" + term.getInt("thumbs_down") + "`", false);

            event.getChannel().sendMessage(builder.build()).queue();
        } catch (IOException e) {
            event.getChannel().sendMessage("Failed to retrieve result from Urban Dictionary.").queue();
        }
    }

    @Override
    public String getHelp() {
        return "Grabs the definition from Urban Dictionary for the specified word.\n" +
                "Usage: " + Constants.PREFIX + getInvoke() + " [query]";
    }

    @Override
    public String getInvoke() {
        return "urbandictionary";
    }
}
