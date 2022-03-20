package net.danh.pvp.Manager;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Points {

    public static int getPvPPoints(@NotNull Player p) {
        return Files.getdatafile().getInt("players." + p.getName() + ".Points");
    }

    public static void setPvPPoints(@NotNull Player p, int points) {
        Files.getdatafile().set("players." + p.getName() + ".Points", points);
        Files.savedata();
    }

    public static void addPvPPoints(@NotNull Player p, int points) {
        Files.getdatafile().set("players." + p.getName() + ".Points", getPvPPoints(p) + points);
        Files.savedata();
    }

    public static void removePvPPoints(@NotNull Player p, int points) {
        Files.getdatafile().set("players." + p.getName() + ".Points", getPvPPoints(p) - points);
        Files.savedata();
    }

}
