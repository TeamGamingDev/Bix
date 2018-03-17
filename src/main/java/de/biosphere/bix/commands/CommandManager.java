package de.biosphere.bix.commands;

import lombok.Getter;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

public class CommandManager {

    @Getter
    private ArrayList<Command> availableCommands = new ArrayList<>();

    public CommandManager() {
        Set<Class<? extends Command>> classes = new Reflections("de.biosphere.bix.commands").getSubTypesOf(Command.class);
        classes.forEach(cmdClass -> {
            try {
                registerCommand(cmdClass.newInstance());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }


    public void execute(Message message) {
        String[] arguments = message.getContentRaw().split(" ");
        //Replace the prefix of the guild later
        arguments[0] = arguments[0].replace("-", "");
        for (Command command : this.availableCommands) {
            if(command.getCategorie() == Command.Categorie.OWNER && !message.getAuthor().getId().equals("357867452807512075")){
                System.out.println(1 + "--" + command.getCommand());
                continue;
            } else if (command.getCategorie() == Command.Categorie.STAFF && !message.getMember().hasPermission(Permission.MESSAGE_MANAGE)){
                System.out.println(2 + "--" + command.getCommand());
                continue;
            }

            if(command.getCommand().equalsIgnoreCase(arguments[0])){
                command.execute(Arrays.copyOfRange(arguments, 1, arguments.length), message);
            } else {
                for (String alias : command.getAliases()) {
                    if (alias.equalsIgnoreCase(arguments[0])) {
                        command.execute(Arrays.copyOfRange(arguments, 1, arguments.length), message);
                    }
                }
            }
        }
    }

    private void registerCommand(Command command) {
        if (!availableCommands.contains(command)) {
            availableCommands.add(command);
        }
    }

}
