package net.danh.pvp.Manager;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FirstJoin {

    public static boolean isFirstJoin(@NotNull Player p) {
        return !Files.getdatafile().getBoolean("players." + p.getName() + ".First_Join");
    }

    public static void setFirstJoin(@NotNull Player p, boolean hmm) {
        Files.getdatafile().set("players." + p.getName() + ".First_Join", hmm);
    }
}
