package de.biosphere.bix.listener.guild;

import de.biosphere.bix.Bix;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.io.IOException;

/**
 * @author Biosphere
 * @date 19.03.18
 */
public class GuildJoinListener extends ListenerAdapter {

    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        super.onGuildJoin(event);
        int server = 0;
        for (JDA jda : Bix.getInstance().getShardList()) {
            server += jda.getGuilds().size();
        }
        if (Bix.getInstance().getBotlistSpaceClient() != null) {
            try {
                Bix.getInstance().getBotlistSpaceClient().postStats(server);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
