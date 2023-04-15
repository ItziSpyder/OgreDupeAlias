package io.github.itzispyder.ogredupealias.commands.commands;

import io.github.itzispyder.ogredupealias.commands.CmdExHandler;
import io.github.itzispyder.ogredupealias.data.Config;
import io.github.itzispyder.ogredupealias.data.ConfigDataType;
import io.github.itzispyder.ogredupealias.utils.ArrayUtils;
import io.github.itzispyder.ogredupealias.utils.Text;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

import static io.github.itzispyder.ogredupealias.OgreDupeAlias.prefix;

public class ConfigCommand implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            final String path = args[1];
            final ConfigDataType<?> type = ConfigDataType.valueOf(args[2]);

            switch (args[0]) {
                case "set" -> {
                    List<String> bd = new ArrayList<>();
                    for (int i = 3; i < args.length; i ++) bd.add(args[i]);
                    String value = Text.color(String.join(" ",bd));

                    FileConfiguration config = Config.get();
                    config.set(path,ConfigDataType.parse(value,type));
                    Config.save(config);
                    sender.sendMessage(Text.builder("&3Config path '&b" + path + "&3' has updated: \n&3Value: &7" + ConfigDataType.parse(value,type)).color().prefix().build());
                }
                case "get" -> {
                    sender.sendMessage(Text.builder("&3Config path '&b" + path + "&3' has the following data: \n&3Value: &7" + ConfigDataType.parseConfig(path,type)).color().prefix().build());
                }
            }
        }
        catch (Exception ex) {
            CmdExHandler handler = new CmdExHandler(ex,command);
            sender.sendMessage(handler.getHelp());
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> list = new ArrayList<>();

        switch (args.length) {
            case 1 -> {
                list.add("get");
                list.add("set");
            }
            case 2 -> {
                list.addAll(Config.getSections());
            }
            case 3 -> {
                list.addAll(ArrayUtils.toNewList(ConfigDataType.values(), type -> type.name().toLowerCase()));
            }
            case 4 -> {
                switch (args[0]) {
                    case "set" -> {
                        switch (args[2]) {
                            case "location" -> {
                                if (!args[3].contains(",")) list.addAll(ArrayUtils.toNewList(Bukkit.getWorlds(),w -> w.getName() + ","));
                                else if (args[3].charAt(args[3].length() - 1) != ',' && args[3].split(",").length <= 3) {
                                    list.add(args[3] + ",");
                                }
                            }
                            case "byte[]", "string[]" -> {
                                if (args[3].length() >= 1 && args[3].charAt(args[3].length() - 1) != ',') {
                                    list.add(args[3] + ",");
                                }
                            }
                            default -> {
                                list.add("<value>");
                            }
                        }
                    }
                }
            }
        }

        list.removeIf(s -> !s.toLowerCase().contains(args[args.length - 1].toLowerCase()));
        return list;
    }
}
