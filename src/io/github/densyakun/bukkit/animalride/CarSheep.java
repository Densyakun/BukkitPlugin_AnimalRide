package io.github.densyakun.bukkit.animalride;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftSheep;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class CarSheep implements Runnable {
	
	public CraftSheep car;
	public int a = 0;
	
	public CarSheep(CraftSheep car) {
		this.car = car;
		new Thread(this).start();
	}
	
	@Override
	public void run() {
		while (true) {
			if ((car != null) && !car.isDead() && !car.isEmpty() && car.isValid() && (car.getPassenger() != null) && (car.getPassenger() instanceof Player)) {
				if ((((Player) car.getPassenger()).getInventory().getItemInMainHand() != null) && (((Player) car.getPassenger()).getInventory().getItemInMainHand().getType() == Material.WHEAT)) {
					if (a <= 0) {
						for (int b = 0; b < ((Player) car.getPassenger()).getInventory().getSize(); b++) {
							ItemStack c = ((Player) car.getPassenger()).getInventory().getItem(b);
							if (c != null) {
								if (c.getType() == Material.WHEAT) {
									if (c.getAmount() == 1) {
										((Player) car.getPassenger()).getInventory().clear(b);
									} else {
										c.setAmount(c.getAmount() - 1);
									}
									a += 240;
									break;
								}
							}
						}
					}
					if (0 < a) {
						switch (((Player) car.getPassenger()).getInventory().getItemInMainHand().getType()) {
						case WHEAT:
							if (car.isOnGround()) {
								car.setVelocity(car.getVelocity().midpoint(new Vector(car.getPassenger().getLocation().getDirection().getX() * AnimalRide.a(car.getLocation()), car.getPassenger().getLocation().getDirection().getY() * 2  * AnimalRide.a(car.getLocation()), car.getPassenger().getLocation().getDirection().getZ() * AnimalRide.a(car.getLocation()))));
							} else {
								car.setVelocity(car.getVelocity().midpoint(new Vector(car.getPassenger().getLocation().getDirection().getX() * AnimalRide.a(car.getLocation()), 0, car.getPassenger().getLocation().getDirection().getZ() * AnimalRide.a(car.getLocation()))));
							}
							a--;
							break;
						default:
							break;
						}
					}
				}
				if (0 < a) {
					car.getWorld().playSound(car.getLocation(), Sound.BLOCK_WOOD_BREAK, (float) car.getVelocity().distance(new Vector()), (float) car.getVelocity().distance(new Vector()));
					/*car.getWorld().playSound(car.getLocation(), Sound.DIG_WOOD, (float) car.getVelocity().distance(new Vector()), (float) car.getVelocity().distance(new Vector()));
					car.getWorld().playSound(car.getLocation(), Sound.DIG_WOOL, (float) car.getVelocity().distance(new Vector()), (float) car.getVelocity().distance(new Vector()));*/
					a--;
				}
				try {
					Thread.sleep((long) (250 / (1 + (car.getVelocity().distance(new Vector()) * 4))));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				break;
			}
		}
	}
}
