package net.danh.pvp.Commands;

import net.danh.Manager.Data;
import net.danh.Manager.Storage;
import net.danh.pvp.Manager.Cooldown;
import net.danh.pvp.Manager.Files;
import net.danh.pvp.Manager.Points;
import net.danh.pvp.Manager.Status;
import net.danh.pvp.PvP;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class PvPCommmands implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (label.equalsIgnoreCase("pvp")) {
            if (sender instanceof Player) {
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("off")) {
                        if (Storage.getStorage(((Player) sender).getPlayer(), Data.getconfigfile().getString("Item_Toggle_PvP")) > Data.getconfigfile().getInt("Req_Log_To_PvP")) {
                            int timeLeft = Cooldown.getCooldown(((Player) sender).getUniqueId());
                            if (timeLeft == 0) {
                                Cooldown.setCooldown(((Player) sender).getUniqueId(), Files.getconfigfile().getInt("PVP.COOLDOWN_TIMES"));
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        int timeLeft = Cooldown.getCooldown(((Player) sender).getUniqueId());
                                        Cooldown.setCooldown(((Player) sender).getUniqueId(), --timeLeft);
                                        if (timeLeft == 0) {
                                            cancel();
                                        }
                                    }
                                }.runTaskTimer(PvP.getInstance(), 20, 20);

                            } else {
                                //Hasn't expired yet, shows how many seconds left until it does
                                sender.sendMessage(Files.convert(Objects.requireNonNull(Files.getlanguagefile().getString("PVP.COOLDOWN_TIMES")).replaceAll("%time%", String.valueOf(timeLeft))));
                                return true;
                            }
                            Status.TogglePvP(Objects.requireNonNull(((Player) sender).getPlayer()), false);
                            sender.sendMessage(Files.convert(Objects.requireNonNull(Files.getlanguagefile().getString("PVP.TOGGLE_MESSAGE"))
                                    .replaceAll("%status%", Objects.requireNonNull(Files.getconfigfile().getString("PVP.STATUS_OFF")))));
                            return true;
                        } else {
                            sender.sendMessage(Data.convert(Data.getlanguagefile().getString("DO_NOT_ENOUGH_LOG")
                                    .replaceAll("%amount%", String.valueOf(Data.getconfigfile().getInt("Req_Log_To_PvP")))));
                        }
                    } else {
                        if (args[0].equalsIgnoreCase("on")) {
                            Status.TogglePvP(Objects.requireNonNull(((Player) sender).getPlayer()), true);
                            sender.sendMessage(Files.convert(Objects.requireNonNull(Files.getlanguagefile().getString("PVP.TOGGLE_MESSAGE"))
                                    .replaceAll("%status%", Objects.requireNonNull(Files.getconfigfile().getString("PVP.STATUS_ON")))));
                            return true;
                        }
                    }
                }
            }
            return true;
        }
        if (label.equalsIgnoreCase("pvpadmin")) {
            if (sender.hasPermission("pvp.admin")) {
                if (args.length == 0) {
                    for (String user : Files.getlanguagefile().getStringList("PVP.ADMIN_HELP")) {
                        sender.sendMessage(Files.convert(user));
                    }
                    return true;
                }
                if (args[0].equalsIgnoreCase("points")) {
                    if (args.length == 4) {
                        if (Bukkit.getPlayer(args[2]) != null) {
                            if (args[1].equalsIgnoreCase("set")) {
                                Points.setPvPPoints(Objects.requireNonNull(Bukkit.getPlayer(args[2])), Integer.parseInt(args[3]));
                                sender.sendMessage(Files.convert("&aDone"));
                            }
                            if (args[1].equalsIgnoreCase("add")) {
                                Points.addPvPPoints(Objects.requireNonNull(Bukkit.getPlayer(args[2])), Integer.parseInt(args[3]));
                                sender.sendMessage(Files.convert("&aDone"));
                            }
                            if (args[1].equalsIgnoreCase("remove")) {
                                Points.removePvPPoints(Objects.requireNonNull(Bukkit.getPlayer(args[2])), Integer.parseInt(args[3]));
                                sender.sendMessage(Files.convert("&aDone"));
                            }
                        }
                    }
                }
                if (args[0].equalsIgnoreCase("status")) {
                    if (args.length == 3) {
                        if (Bukkit.getPlayer(args[1]) != null) {
                            Status.TogglePvP(Bukkit.getPlayer(args[1]), Boolean.getBoolean(args[2]));
                            if (Status.getPvPStatus(Objects.requireNonNull(Bukkit.getPlayer(args[1])))) {
                                sender.sendMessage(Files.convert(Objects.requireNonNull(Files.getlanguagefile().getString("PVP.TOGGLE_MESSAGE" + " &7(" + Objects.requireNonNull(Bukkit.getPlayer(args[1])).getName() + ")"))
                                        .replaceAll("%status%", Objects.requireNonNull(Files.getconfigfile().getString("PVP.STATUS_ON")))));
                            } else {
                                sender.sendMessage(Files.convert(Objects.requireNonNull(Files.getlanguagefile().getString("PVP.TOGGLE_MESSAGE" + " &7(" + Objects.requireNonNull(Bukkit.getPlayer(args[1])).getName() + ")"))
                                        .replaceAll("%status%", Objects.requireNonNull(Files.getconfigfile().getString("PVP.STATUS_OFF")))));
                            }
                        }
                    }
                    return true;
                }
                if (args[0].equalsIgnoreCase("reload")) {
                    if (args.length == 1) {
                        Files.reloadfiles();
                        sender.sendMessage(Files.convert("&aDone"));
                    }
                    return true;
                }
            }
            return true;
        }
        return false;
    }
}
