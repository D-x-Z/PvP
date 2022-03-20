package net.danh.pvp.Manager;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Status {

    public static boolean getPvPStatus(@NotNull Player p) {
        return Files.getdatafile().getBoolean("players." + p.getName() + ".Status");
    }

    public static void TogglePvP(Player p, boolean status) {
        if (Points.getPvPPoints(p) >= Files.getconfigfile().getInt("PVP.POINTS_TOGGLE")) {
            Files.getdatafile().set("players." + p.getName() + ".Status", status);
            Points.removePvPPoints(p, Files.getconfigfile().getInt("PVP.POINTS_TOGGLE"));
        } else {
            p.sendMessage(Files.convert(Objects.requireNonNull(Files.getlanguagefile().getString("PVP.NOT_ENOUGH_POINTS"))
                    .replaceAll("%amount%", String.valueOf(Files.getconfigfile().getInt("PVP.POINTS_TOGGLE")))));
        }
        Files.savedata();
    }
}
