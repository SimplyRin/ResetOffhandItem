package net.simplyrin.resetoffhanditem;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by SimplyRin on 2021/12/19.
 */
public class ResetOffhandItem extends JavaPlugin implements Listener {
	
	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		var player = event.getPlayer();
		
		// オフハンドではない
		if (event.getHand() != EquipmentSlot.OFF_HAND) {
			return;
		}
		
		if (!player.hasPermission("resetoffhanditem.use")) {
			return;
		}

		var items = event.getItemInHand();
		var inventory = player.getInventory();
		
		// 置いたブロックがなくなったら
		if ((items.getAmount() - 1) == 0) {
			// インベントリで同じブロックを持っているか判定
			for (int i = 0; i < 36; i++) {
				var item = inventory.getItem(i);
				
				// あった
				if (item != null && item.getType().equals(items.getType())) {
					inventory.setItemInOffHand(item);
					inventory.setItem(i, null);
					break;
				}
			}
		}
	}

}
