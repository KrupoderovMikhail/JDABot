package com.KrupoderovMikhail.github.commands.fun;

import com.KrupoderovMikhail.github.Constants;
import com.KrupoderovMikhail.github.objects.ICommand;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;
import java.util.Random;

public class QuoteCommand implements ICommand {

    /* Information was taken from the site - https://www.brainyquote.com/top_100_quotes
     * Other quotes will be added later.
     * */
    private String[] responses = new String[]{
            " I love you the more in that I believe you had liked me for my own sake and for nothing else. - John Keats ",
            "But man is not made for defeat. A man can be destroyed but not defeated. - Ernest Hemingway",
            "When you reach the end of your rope, tie a knot in it and hang on - Franklin D. Roosevelt",
            "There is nothing permanent except change. - Heraclitus",
            "You cannot shake hands with a clenched fist. - Indira Gandhi",
            "Let us sacrifice our today so that our children can have a better tomorrow. - A. P. J. Abdul Kalam",
            "It is better to be feared than loved, if you cannot be both. - Niccolo Machiavelli",
            "The most difficult thing is the decision to act, the rest is merely tenacity. The fears are paper tigers. You can do anything you decide to do. You can act to change and control your life; and the procedure, the process is its own reward. - Amelia Earhart",
            "Do not mind anything that anyone tells you about anyone else. Judge everyone and everything for yourself. - Henry James",
            "Learning never exhausts the mind. - Leonardo da Vinci",
            "There is no charm equal to tenderness of heart. - Jane Austen",
            "All that we see or seem is but a dream within a dream. - Edgar Allan Poe",
            "Lord, make me an instrument of thy peace. Where there is hatred, let me sow love. - Francis of Assisi",
            "The only journey is the one within. - Rainer Maria Rilke",
            "Good judgment comes from experience, and a lot of that comes from bad judgment. - Will Rogers",
            "Think in the morning. Act in the noon. Eat in the evening. Sleep in the night. - William Blake",
            "Life without love is like a tree without blossoms or fruit. - Khalil Gibran",
            "No act of kindness, no matter how small, is ever wasted. - Aesop",
            "Love cures people - both the ones who give it and the ones who receive it. - Karl A. Menninger",
            "Work like you don't need the money. Love like you've never been hurt. Dance like nobody's watching. - Satchel Paige",
            "It is far better to be alone, than to be in bad company. - George Washington",
    };

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {

        String[] parts = responses[new Random().nextInt(responses.length)].split("-");

        event.getChannel().sendMessage(parts[0] + " - **" + parts[1] + "**").queue();
    }

    @Override
    public String getHelp() {
        return "Returns a quote.\n" +
                "Usage: `" + Constants.PREFIX + getInvoke() + "`";
    }

    @Override
    public String getInvoke() {
        return "quote";
    }
}
