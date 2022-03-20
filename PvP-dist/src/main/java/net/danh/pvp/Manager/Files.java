package net.danh.pvp.Manager;

import net.danh.pvp.PvP;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import preponderous.ponder.minecraft.bukkit.nms.NMSAssistant;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Files {


    public final static char COLOR_CHAR = ChatColor.COLOR_CHAR;
    private static File configFile, languageFile, dataFile;
    private static FileConfiguration config, language, data;

    public static void createfiles() {
        configFile = new File(PvP.getInstance().getDataFolder(), "config.yml");
        languageFile = new File(PvP.getInstance().getDataFolder(), "language.yml");
        dataFile = new File(PvP.getInstance().getDataFolder(), "data.yml");

        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!languageFile.exists()) {
            languageFile.getParentFile().mkdirs();
            try {
                languageFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!dataFile.exists()) {
            dataFile.getParentFile().mkdirs();
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        config = new YamlConfiguration();
        language = new YamlConfiguration();
        data = new YamlConfiguration();

        try {
            config.load(configFile);
            language.load(languageFile);
            data.load(dataFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static FileConfiguration getconfigfile() {
        return config;
    }

    public static FileConfiguration getlanguagefile() {
        return language;
    }


    public static FileConfiguration getdatafile() {
        return data;
    }


    public static void reloadfiles() {
        checkFiles();
        config = YamlConfiguration.loadConfiguration(configFile);
        language = YamlConfiguration.loadConfiguration(languageFile);
        data = YamlConfiguration.loadConfiguration(dataFile);
    }

    public static void saveconfig() {
        try {
            config.save(configFile);
        } catch (IOException ignored) {
        }
    }

    public static void savelanguage() {
        try {
            language.save(languageFile);
        } catch (IOException ignored) {
        }
    }


    public static void savedata() {
        try {
            data.save(dataFile);
        } catch (IOException ignored) {
        }
    }

    public static void checkFiles() {
        checkConfigFile();
        checkLanguageFile();
    }

    private static void checkConfigFile() {
        if (!getconfigfile().contains("CONFIG_VERSION")) {
            getconfigfile().set("CONFIG_VERSION", PvP.getInstance().getDescription().getVersion());
        }
        if (!getconfigfile().contains("DEBUG")) {
            getconfigfile().set("DEBUG", false);
        }
        if (!getconfigfile().contains("PVP.DEFAULT_POINTS")) {
            getconfigfile().set("PVP.DEFAULT_POINTS", 10 + "#Points will give on first join");
        }
        if (!getconfigfile().contains("PVP.COOLDOWN_TIMES")) {
            getconfigfile().set("PVP.COOLDOWN_TIMES", 300 +  "#Times to do command /PvP again");
        }
        if (!getconfigfile().contains("PVP.POINTS_DEAD")) {
            getconfigfile().set("PVP.POINTS_DEAD", 1 + "#Amount PvP Points will lose when dead to protect PvP Status off");
        }
        if (!getconfigfile().contains("PVP.POINTS_TOGGLE")) {
            getconfigfile().set("PVP.POINTS_TOGGLE", 1 + "#Amount PvP will lose when do command /PvP");
        }
        if (!getconfigfile().contains("PVP.STATUS_ON")) {
            getconfigfile().set("PVP.STATUS_ON", "On");
        }
        if (!getconfigfile().contains("PVP.STATUS_OFF")) {
            getconfigfile().set("PVP.STATUS_OFF", "Off");
        }
        if (!getconfigfile().contains("PVP.FIRST_TIME_PROTECT")){
            getconfigfile().set("PVP.FIRST_TIME_PROTECT", 300);
        }
        saveconfig();
    }

    private static void checkLanguageFile() {
        if (!getlanguagefile().contains("LANGUAGE_VERSION")) {
            getlanguagefile().set("LANGUAGE_VERSION", PvP.getInstance().getDescription().getVersion());
        }
        if (!getlanguagefile().contains("PVP.COOLDOWN_TIMES")) {
            getlanguagefile().set("PVP.COOLDOWN_TIMES", "&aYou can use command /PvP in %time%s");
        }
        if (!getlanguagefile().contains("PVP.NOT_ENOUGH_POINTS")) {
            getlanguagefile().set("PVP.NOT_ENOUGH_POINTS", "&cYou do not have enough PvP Points! You need at least %amount% points");
        }
        if (!getlanguagefile().contains("PVP.TOGGLE_MESSAGE")) {
            getlanguagefile().set("PVP.TOGGLE_MESSAGE", "&aPvP Status: %status%");
        }
        if (!getlanguagefile().contains("PVP.USER_HELP")) {
            ArrayList<String> help = new ArrayList<>();
            help.add("&a/PvP -&e Toggle PvP");
            getlanguagefile().set("PVP.USER_HELP", help);
        }
        if (!getlanguagefile().contains("PVP.ADMIN_HELP")) {
            ArrayList<String> help = new ArrayList<>();
            help.add("&a/PvPAdmin points <set/add/remove> <player> <amount> - &3Add/set/remove PvP Points");
            help.add("&a/PvPAdmin status <player> <true/false> - &3Set PvP Status");
            help.add("&a/PvPAdmin reload - &3Reload files");
            getlanguagefile().set("PVP.ADMIN_HELP", help);
        }
        if (!getlanguagefile().contains("PVP.PROTECT_PVP_TIMES")) {
            getlanguagefile().set("PVP.PROTECT_PVP_TIMES", "&aProtect PvP in %time%s");
        }
        savelanguage();
    }


    // Colorizes message with preset colorcodes (&) and if using 1.16+, applies hex values via "&#hexvalue"
    public static String convert(String input) {

        input = ChatColor.translateAlternateColorCodes('&', input);
        NMSAssistant nms = new NMSAssistant();
        if (nms.isVersionGreaterThan(15)) {
            input = translateHexColorCodes("&#", "", input);
        }

        return input;
    }

    private static @NotNull String translateHexColorCodes(String startTag, String endTag, String message) {

        final Pattern hexPattern = Pattern.compile(startTag + "([A-Fa-f0-9]{6})" + endTag);
        Matcher matcher = hexPattern.matcher(message);
        StringBuffer buffer = new StringBuffer(message.length() + 4 * 8);

        while (matcher.find()) {

            String group = matcher.group(1);
            matcher.appendReplacement(buffer, COLOR_CHAR + "x" + COLOR_CHAR + group.charAt(0) + COLOR_CHAR + group.charAt(1) + COLOR_CHAR
                    + group.charAt(2) + COLOR_CHAR + group.charAt(3) + COLOR_CHAR + group.charAt(4) + COLOR_CHAR + group.charAt(5));

        }

        return matcher.appendTail(buffer).toString();
    }
}
