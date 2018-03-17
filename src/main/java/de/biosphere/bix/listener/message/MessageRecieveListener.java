package de.biosphere.bix.listener.message;

import de.biosphere.bix.Bix;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.utils.PermissionUtil;

import java.awt.*;

/**
 * @author Biosphere
 * @date 17.03.18
 */
public class MessageRecieveListener extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        super.onGuildMessageReceived(event);
        if(event.getAuthor().isBot()){
            return;
        }
        System.out.println(event.getMessage().getContentRaw());


        if(!PermissionUtil.checkPermission(event.getChannel(), event.getGuild().getSelfMember(), Permission.MESSAGE_WRITE)){
            return;
        }

        if (!PermissionUtil.checkPermission(event.getChannel(), event.getGuild().getSelfMember(), Permission.MESSAGE_HISTORY)) {
            return;
        }
        if(event.getMessage().getContentRaw().startsWith("-")){
            Bix.getInstance().getCommandManager().execute(event.getMessage());
        }
    }

    @Override
    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
        super.onPrivateMessageReceived(event);
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.GREEN);
        embedBuilder.setDescription("Private messages are disabled");
        event.getChannel().sendMessage(embedBuilder.build()).queue();
        event.getChannel().sendMessage("discord.gg/3By2ZPC").queue();
    }
}
