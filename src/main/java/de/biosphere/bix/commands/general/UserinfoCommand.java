package de.biosphere.bix.commands.general;

import de.biosphere.bix.commands.Command;
import de.biosphere.bix.utils.StringUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;

import java.time.format.DateTimeFormatter;

/**
 * @author Biosphere
 * @date 17.03.18
 */
public class UserinfoCommand extends Command {

    public UserinfoCommand() {
        super("userinfo", "Show some information about a user", Categorie.GENERAL);
    }

    @Override
    public void execute(String[] args, Message message) {
        Member member = message.getMentionedMembers().isEmpty() ? message.getMember() : message.getMentionedMembers().get(0);

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setThumbnail(member.getUser().getAvatarUrl());
        embedBuilder.addField("Name", StringUtils.replaceCharacter(member.getUser().getName()), true);
        embedBuilder.addField("Nickname", member.getNickname() == null ? StringUtils.replaceCharacter(member.getUser().getName()) : StringUtils.replaceCharacter(member.getNickname()), true);
        embedBuilder.addField("Status", member.getOnlineStatus().getKey(), true);
        embedBuilder.addField("Game", member.getGame() != null ? member.getGame().getName() : "---", true);
        embedBuilder.addField("Roles", String.valueOf(member.getRoles().size()), true);
        embedBuilder.addField("Join date", member.getJoinDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")), true);

        message.getTextChannel().sendMessage(embedBuilder.build()).queue();
    }
}
