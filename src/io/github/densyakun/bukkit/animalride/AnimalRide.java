package io.github.densyakun.bukkit.animalride;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftChicken;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftOcelot;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftSheep;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class AnimalRide extends JavaPlugin implements Listener {
	
	public static ClassLoader ClassLoader;
	public static String Bukkit_Version;
	public static String a;
	
	@Override
	public void onLoad() {
		ClassLoader = getClassLoader();
		String packageName = getServer().getClass().getPackage().getName();
		Bukkit_Version = packageName.substring(packageName.lastIndexOf('.') + 1);
	}
	
	@Override
	public void onEnable() {
		System.out.println("バージョン: " + getServer().getClass().getPackage().getName());
		
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void PlayerInteractEntity(PlayerInteractEntityEvent e) {
		Player p = e.getPlayer();
		
		//TODO 1.9
		//ItemStack item = p.getInventory().getItemInMainHand();
		ItemStack item = p.getInventory().getItemInHand();
		if (item == null || (item != null && item.getType() == Material.AIR)) {
			if ((e.getRightClicked() instanceof CraftChicken) && ((CraftChicken) e.getRightClicked()).isAdult()) {
				e.getRightClicked().setPassenger(e.getPlayer());
				new CarChicken((CraftChicken) e.getRightClicked());
			} else if ((e.getRightClicked() instanceof CraftSheep) && ((CraftSheep) e.getRightClicked()).isAdult()) {
				e.getRightClicked().setPassenger(e.getPlayer());
				new CarSheep((CraftSheep) e.getRightClicked());
			} else if ((e.getRightClicked() instanceof CraftOcelot) && ((CraftOcelot) e.getRightClicked()).isAdult()) {
				e.getRightClicked().setPassenger(e.getPlayer());
				new CarOcelot((CraftOcelot) e.getRightClicked());
			}
		}
	}
	
	public static double a(Location loc) {
		if ((loc.getBlock() != null) && (loc.getBlock().getType() != Material.AIR)) {
			return 1;
		}
		for (int a = 1; a <= 3; a++) {
			for (int b = -a; b <= a; b++) {
				for (int c = -a; c <= a; c++) {
					if ((loc.getWorld().getBlockAt((int) (loc.getX() + b), (int) loc.getY(), (int) (loc.getZ() + c)) != null) && (loc.getWorld().getBlockAt((int) (loc.getX() + b), (int) loc.getY(), (int) (loc.getZ() + c)).getType() != Material.AIR)) {
						return 1 + loc.distance(new Location(loc.getWorld(), (double) (loc.getX() + b + 0.5), (double) loc.getY() + 0.5, (double) (loc.getZ() + c + 0.5)));
					}
				}
			}
		}
		return 3;
	}
}
