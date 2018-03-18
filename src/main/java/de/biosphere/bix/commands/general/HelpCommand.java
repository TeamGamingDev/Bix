package de.biosphere.bix.commands.general;

import com.vdurmont.emoji.EmojiManager;
import de.biosphere.bix.commands.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;

import java.awt.*;
import java.util.Arrays;

/**
 * @author Biosphere
 * @date 17.03.18
 */
public class HelpCommand extends Command {

    public HelpCommand() {
        super("help", "View this message", Categorie.GENERAL);
    }

    @Override
    public void execute(String[] args, Message message) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle(":question: Help");
        embedBuilder.setColor(Color.GREEN);
        embedBuilder.appendDescription("Click the corresponding emote for more infos.\n");
        Arrays.stream(Categorie.values()).filter(categorie -> !categorie.equals(Categorie.OWNER)).map(categorie -> "**" + (categorie.ordinal() + 1) + " - " + categorie.getName() + " Commands**\n" + categorie.getDescription() + "\n").forEach(embedBuilder::appendDescription);
        message.getTextChannel().sendMessage(getEmbed(message.getGuild(), message.getAuthor()).setDescription("I've sent you a DM with all my commands!").build()).queue();
        message.getMember().getUser().openPrivateChannel().queue(success -> success.sendMessage(embedBuilder.build()).queue(dmessage -> {
            Arrays.stream(Categorie.values()).filter(categorie -> !categorie.equals(Categorie.OWNER)).forEach(categorie -> dmessage.addReaction(EmojiManager.getForAlias(categorie.getEmote()).getUnicode()).queue());
            dmessage.addReaction(EmojiManager.getForAlias("wastebasket").getUnicode()).queue();
        }));
    }
}
