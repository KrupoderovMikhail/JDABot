package com.KrupoderovMikhail.github.commands;

import com.KrupoderovMikhail.github.Constants;
import com.KrupoderovMikhail.github.objects.ICommand;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import okhttp3.*;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.List;

public class StrawpollCommand implements ICommand {

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {

        if (args.isEmpty()) {
            event.getChannel().sendMessage("Missing arguments").queue();
            return;
        }

        boolean multi = false;

        if (args.get(0).equals("-m")) {
            multi = true;
            args.remove(0);
        }

        String[] arguments = args.toString()
                .replaceAll("]", "")
                .replaceAll("\\[", "")
                .replaceAll(",", "")
                .split("\\|");

        JSONObject object = new JSONObject();
        object.put("title", arguments[0]);
        object.put("options", arguments[1].split(";"));
        object.put("multi", multi);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://www.strawpoll.me/api/v2/polls").post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString())).build();

        try {
            Response response = client.newCall(request).execute();

            JSONObject poll = new JSONObject(new JSONTokener(response.body().byteStream()));

            event.getChannel().sendMessageFormat("Your Strawpoll has been successfully created. <https://strawpoll.me/%s>", Integer.toString(poll.getInt("id"))).queue();
        } catch (IOException | ArrayIndexOutOfBoundsException | UnsupportedOperationException e) {
            e.printStackTrace();
            event.getChannel().sendMessage("Failed to create poll on Strawpoll.").queue();
        }
    }

    @Override
    public String getHelp() {
        return "Create a strawpoll vote.\n" +
                "Usage: \n`" + Constants.PREFIX + getInvoke() + " [question] | [choice]; [choice]; [choice]`,\n`" +
                Constants.PREFIX + getInvoke() + "-m [question] | [choice]; [choice]; [choice]`";
    }

    @Override
    public String getInvoke() {
        return "strawpoll";
    }
}