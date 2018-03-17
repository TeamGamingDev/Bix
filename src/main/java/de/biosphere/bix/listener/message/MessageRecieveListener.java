package de.biosphere.bix.listener.message;

import ai.api.AIServiceException;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import de.biosphere.bix.Bix;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDAInfo;
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
        if(!PermissionUtil.checkPermission(event.getChannel(), event.getGuild().getSelfMember(), Permission.MESSAGE_WRITE)){
            return;
        }
        if (!PermissionUtil.checkPermission(event.getChannel(), event.getGuild().getSelfMember(), Permission.MESSAGE_HISTORY)) {
            return;
        }
        if(event.getMessage().getContentRaw().equalsIgnoreCase(event.getGuild().getSelfMember().getAsMention())){
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setAuthor("Bix", "https://github.com/biixbot/Bix", event.getJDA().getSelfUser().getAvatarUrl());
            embedBuilder.setDescription("Bix is a non-mainstream Discord Bot with various commands and features!");
            embedBuilder.addField("Creator", "[Niklas#0928](https://discord.gg/3By2ZPC)", false);
            embedBuilder.addField("Invite", "[Invite Bix](https://discordapp.com/oauth2/authorize?client_id=361027969570832384&scope=bot&permissions=1031007319&response_type=code&redirect_uri=https://discord.gg/3By2ZPC)", false);
            embedBuilder.addField("Github", "[Repository](https://github.com/biixbot/Bix)", false);
            embedBuilder.addField("Features", "- Unlimited reaction channel switching\n -Musicsystem\n -Levelsystem\nMore features comming soon!", false);
            embedBuilder.addField("JDA-Version", JDAInfo.VERSION, false);
            embedBuilder.addField("Commands", "-help", false);
            embedBuilder.setFooter("@" + event.getAuthor().getName() + "#" + event.getAuthor().getDiscriminator(), event.getAuthor().getEffectiveAvatarUrl());

            event.getChannel().sendMessage(embedBuilder.build()).queue();
            return;
        } else if(event.getMessage().getContentRaw().startsWith(event.getGuild().getSelfMember().getAsMention()) && Bix.getInstance().getAiDataService() != null){
            try {
                AIRequest request = new AIRequest(event.getMessage().getContentRaw().replace(event.getGuild().getSelfMember().getAsMention(), ""));
                AIResponse response = Bix.getInstance().getAiDataService().request(request);

                if (response.getStatus().getCode() == 200) {
                    if (response.getResult().getFulfillment().getSpeech().trim().isEmpty()) {
                        event.getChannel().sendMessage("Leider kann ich nicht verstehen, was du von mir möchtest.").queue();
                    } else {
                        event.getChannel().sendMessage(response.getResult().getFulfillment().getSpeech().replace("@everyone", "@ everyone").replace("@here", "@ here")).queue();
                    }
                }
            } catch (AIServiceException e) {
                e.printStackTrace();
            }
        }
        if(event.getMessage().getContentRaw().startsWith("-")){
            Bix.getInstance().getCommandManager().execute(event.getMessage());
        }
    }

    @Override
    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
        super.onPrivateMessageReceived(event);
        if (event.getAuthor().isBot()) {
            return;
        }
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.GREEN);
        embedBuilder.setDescription("Private messages are disabled\n[Bix Discord](https://discord.gg/3By2ZPC)");
        event.getChannel().sendMessage(embedBuilder.build()).queue();
    }
}
