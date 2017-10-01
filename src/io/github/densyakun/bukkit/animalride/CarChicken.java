package io.github.densyakun.bukkit.animalride;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftChicken;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class CarChicken implements Runnable {
	
	public CraftChicken car;
	public int a = 0;
	
	public CarChicken(CraftChicken car) {
		this.car = car;
		new Thread(this).start();
	}
	
	@Override
	public void run() {
		while (true) {
			if ((car != null) && !car.isDead() && !car.isEmpty() && car.isValid() && (car.getPassenger() != null) && (car.getPassenger() instanceof Player)) {
				if ((((Player) car.getPassenger()).getItemInHand() != null) && ((((Player) car.getPassenger()).getItemInHand().getType() == Material.SEEDS) || (((Player) car.getPassenger()).getItemInHand().getType() == Material.EGG))) {
					if (a <= 0) {
						for (int b = 0; b < ((Player) car.getPassenger()).getInventory().getSize(); b++) {
							ItemStack c = ((Player) car.getPassenger()).getInventory().getItem(b);
							if ((c != null) && (c.getType() == Material.SEEDS)) {
								if (c.getAmount() == 1) {
									((Player) car.getPassenger()).getInventory().clear(b);
								} else {
									c.setAmount(c.getAmount() - 1);
								}
								a += 600;
								break;
							}
						}
					}
					if (0 < a) {
						switch (((Player) car.getPassenger()).getItemInHand().getType()) {
						case SEEDS:
							car.setVelocity(car.getVelocity().midpoint(new Vector(car.getPassenger().getLocation().getDirection().getX() / 4 * AnimalRide.a(car.getLocation()), car.getPassenger().getLocation().getDirection().getY() / 4 * AnimalRide.a(car.getLocation()), car.getPassenger().getLocation().getDirection().getZ() / 4 * AnimalRide.a(car.getLocation()))));
							a--;
							break;
						case EGG:
							car.setVelocity(car.getVelocity().midpoint(new Vector(car.getPassenger().getLocation().getDirection().getX(), car.getPassenger().getLocation().getDirection().getY(), car.getPassenger().getLocation().getDirection().getZ())));
							a -= 2;
							break;
						default:
							break;
						}
					}
				}
				if (0 < a) {
					car.getWorld().playSound(car.getLocation(), Sound.BLOCK_SNOW_BREAK, 1, 12);
					car.getWorld().playSound(car.getLocation(), Sound.BLOCK_STONE_BREAK, 1, 12);
					car.getWorld().playSound(car.getLocation(), Sound.BLOCK_WOOD_BREAK, (float) car.getVelocity().distance(new Vector()) * 2, (float) car.getVelocity().distance(new Vector()) * 2);
					/*car.getWorld().playSound(car.getLocation(), Sound.DIG_SNOW, 1, 12);
					car.getWorld().playSound(car.getLocation(), Sound.DIG_STONE, 1, 12);
					car.getWorld().playSound(car.getLocation(), Sound.DIG_WOOD, (float) car.getVelocity().distance(new Vector()) * 2, (float) car.getVelocity().distance(new Vector()) * 2);
					car.getWorld().playSound(car.getLocation(), Sound.DIG_WOOL, (float) car.getVelocity().distance(new Vector()) * 2, (float) car.getVelocity().distance(new Vector()) * 2);*/
					a--;
				}
				try {
					Thread.sleep((long) (200 / (1 + (car.getVelocity().distance(new Vector()) * 4))));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				break;
			}
		}
	}
}
