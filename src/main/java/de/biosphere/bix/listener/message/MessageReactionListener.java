package de.biosphere.bix.listener.message;

import com.vdurmont.emoji.EmojiManager;
import de.biosphere.bix.Bix;
import de.biosphere.bix.commands.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageReaction;
import net.dv8tion.jda.core.events.message.priv.react.GenericPrivateMessageReactionEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;

/**
 * @author Biosphere
 * @date 17.03.18
 */
public class MessageReactionListener extends ListenerAdapter {

    @Override
    public void onGenericPrivateMessageReaction(GenericPrivateMessageReactionEvent event) {
        super.onGenericPrivateMessageReaction(event);
        if (event.getUser().isBot()) {
            return;
        }
        if (event.getReactionEmote() == null) {
            return;
        }
        event.getChannel().getMessageById(event.getMessageId()).queue(message -> {
            if (!message.getEmbeds().isEmpty() && message.getAuthor() == message.getJDA().getSelfUser()) {
                String emote = getReaction(event.getReactionEmote());
                if (emote.equalsIgnoreCase("wastebasket")) {
                    message.delete().queue();
                    return;
                }
                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setColor(Color.GREEN);
                for (Command.Categorie categorie : Command.Categorie.values()) {
                    if (categorie.getEmote().equalsIgnoreCase(emote) && !categorie.equals(Command.Categorie.OWNER)) {
                        embedBuilder.setTitle(":question: " + categorie.getName() + " Commands");
                        for (Command command : Bix.getInstance().getCommandManager().getAvailableCommands()) {
                            if (command.getCategorie() == categorie) {
                                embedBuilder.appendDescription("**-" + command.getCommand() + "**\n" + command.getDescription() + "\n\n");
                            }
                        }
                        message.editMessage(embedBuilder.build()).queue();
                        break;
                    }
                }
            }
        });
    }

    private String getReaction(MessageReaction.ReactionEmote emote) {
        try {
            return EmojiManager.getByUnicode(emote.getName()).getAliases().get(0);
        } catch (Exception e) {
            return emote.getName();
        }
    }

}
