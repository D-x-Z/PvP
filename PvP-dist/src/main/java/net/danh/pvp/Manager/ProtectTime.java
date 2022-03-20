package net.danh.pvp.Manager;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ProtectTime {

    public static int getProtectTimes(@NotNull Player p) {
        return Files.getdatafile().getInt("players." + p.getName() + ".Protect_Time");
    }

    public static void setProtectTimes(@NotNull Player p, int times) {
        Files.getdatafile().set("players." + p.getName() + ".Protect_Time", times);
        Files.savedata();
    }
}
