package de.biosphere.bix;

import de.biosphere.bix.commands.CommandManager;
import de.biosphere.bix.listener.other.ReadyListener;
import io.sentry.Sentry;
import lombok.Getter;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Biosphere
 * @date 17.03.18
 */
@Getter
public class Bix {

    @Getter
    private static Bix instance;
    private final List<JDA> shardList;
    private final CommandManager commandManager;
    private final Logger logger = LoggerFactory.getLogger("de.biosphere.bix");


    private Bix(){
        instance = this;

        shardList = new ArrayList<>();
        commandManager = new CommandManager();

        JDABuilder jda = new JDABuilder(AccountType.BOT)
                .setToken(System.getenv("DISCORD-TOKEN"))
                .setGame(Game.of(Game.GameType.DEFAULT, "Booting... | fee-hosting.com"))
                .addEventListener(new ReadyListener())
                .setAutoReconnect(true)
                .setEnableShutdownHook(true);

        int shards = System.getenv("BOT-SHARDS") == null ? 1 : Integer.valueOf(System.getenv("BOT-SHARDS"));

        for (int i = 0; i < shards; i++) {
            try {
                shardList.add(jda.useSharding(i, shards).buildBlocking());
                Thread.sleep(5000);
            } catch (InterruptedException | LoginException e) {
                e.printStackTrace();
            }
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> shardList.forEach(JDA::shutdown)));
    }

    public static void main(String... args){
        if(System.getenv("DISCORD-TOKEN") == null){
            System.err.println("Please set the token for the bot in the DISCORD-TOKEN Environment Variable!");
            return;
        }
        if (System.getenv("SENTRY_DSN") != null || System.getProperty("sentry.properties") != null) {
            Sentry.init();
        }
        new Bix();
    }

}
