package org.L2X9.EventCore.AntiLag;

import org.L2X9.EventCore.Main;
import org.L2X9.EventCore.Utils;
import org.L2X9.EventCore.VilationUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class LightingLag implements Listener {
	VilationUtils breakMan = new VilationUtils();

	@EventHandler
	public void delay(BlockBreakEvent event) {
		Player player = event.getPlayer();
		int x = event.getBlock().getLocation().getBlockX();
		int y = event.getBlock().getLocation().getBlockY();
		int z = event.getBlock().getLocation().getBlockZ();
		if (Utils.getTps() <= 17) {
			if (event.getBlock().getWorld().getBlockAt(new Location(event.getBlock().getWorld(), x, y - 1, z))
					.getType() == Material.AIR) {
				breakMan.addVls(player, 1);
				new BukkitRunnable() {

					@Override
					public void run() {
						if (breakMan.vlMapContainsPlayer(player)) {
							if (breakMan.getVls(player) > 4) {
								breakMan.removeVl(player, breakMan.getVls(player) - 4);
							}
							if (breakMan.getVls(player) < 2) {
								breakMan.resetVls(player);

							}

						}

					}

				}.runTaskLater(Main.getPlugin(), 100);
				if (breakMan.getVls(player) == 10) {
					event.setCancelled(true);
					Utils.sendMessage(player, Utils.getPrefix() + "&6Please slow down or you will be kicked");
				}
				if (breakMan.getVls(player) >= 20) {
					Utils.kickPlayer(player, Utils.getPrefix() + "&6LightLag has been disabled due to lag sorry ):");
					Utils.sendOpMessgge(Utils.getPrefix() + "&c" + player.getName() + " &r&6Has been kicked for lightlag");
					Utils.println(Utils.getPrefix() + "&c " + player.getName() + " &r&6Has been kicked for lightlag");
					breakMan.resetVls(player);
				}
			}
		}
	}

	@EventHandler
	public void delay(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		int x = event.getBlock().getLocation().getBlockX();
		int y = event.getBlock().getLocation().getBlockY();
		int z = event.getBlock().getLocation().getBlockZ();
		if (Utils.getTps() <= 17) {
			if (event.getBlock().getWorld().getBlockAt(new Location(event.getBlock().getWorld(), x, y - 1, z))
					.getType() == Material.AIR) {
				breakMan.addVls(player, 1);
				new BukkitRunnable() {

					@Override
					public void run() {
						if (breakMan.vlMapContainsPlayer(player)) {
							if (breakMan.getVls(player) > 4) {
								breakMan.removeVl(player, breakMan.getVls(player) - 4);
							}
							if (breakMan.getVls(player) < 2) {
								breakMan.resetVls(player);

							}

						}

					}

				}.runTaskLater(Main.getPlugin(), 30);
				if (breakMan.getVls(player) == 10) {
					event.setCancelled(true);
					Utils.kickPlayer(player, Utils.getPrefix() + "&6LightLag has been disabled due to lag sorry ):");
				}
				if (breakMan.getVls(player) >= 20) {
					Utils.sendOpMessgge(Utils.getPrefix() + "&c " + player.getName() + " &r&6Has been kicked for lightlag");
					Utils.println(Utils.getPrefix() + "&c" + player.getName() + " &r&6Has been kicked for lightlag");
					breakMan.resetVls(player);
				}
			}
		}
	}
}