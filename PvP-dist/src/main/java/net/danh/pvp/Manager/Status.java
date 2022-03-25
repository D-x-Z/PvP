package net.danh.pvp.Manager;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Status {

    public static boolean getPvPStatus(@NotNull Player p) {
        return Files.getdatafile().getBoolean("players." + p.getName() + ".Status");
    }

    public static void TogglePvP(@NotNull Player p, boolean status) {
        Files.getdatafile().set("players." + p.getName() + ".Status", status);
        Points.removePvPPoints(p, Files.getconfigfile().getInt("PVP.POINTS_TOGGLE"));
        Files.savedata();
    }
}
