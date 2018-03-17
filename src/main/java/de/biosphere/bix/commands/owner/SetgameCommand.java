package de.biosphere.bix.commands.owner;

import de.biosphere.bix.Bix;
import de.biosphere.bix.commands.Command;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.utils.PermissionUtil;

/**
 * @author Biosphere
 * @date 17.03.18
 */
public class SetgameCommand extends Command {

    public SetgameCommand() {
        super("setgame", "Change the status of the bot", Categorie.OWNER);
    }

    @Override
    public void execute(String[] args, Message message) {
        Bix.getInstance().getShardList().forEach(jda -> jda.getPresence().setGame(Game.of(Game.GameType.DEFAULT, String.join(" ", args).trim())));

        if (PermissionUtil.checkPermission(message.getTextChannel(), message.getGuild().getSelfMember(), Permission.MESSAGE_MANAGE)) {
            message.delete().queue();
        }
    }
}
