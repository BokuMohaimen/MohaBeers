package beers.mohabeers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;

import java.util.Random;

public final class MohaBeers extends JavaPlugin implements Listener {

    Random r = new Random();
    private VaultManager vault;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("起動しました");
        Bukkit.getPluginManager().registerEvents(this, this);
        vault = new VaultManager(this);
    }

    @Override
    public Logger getSLF4JLogger() {
        return null;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("停止しました");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("mam10honeychest.op")) {
            sender.sendMessage("§4§lYou don't have enough permission.");
            return false;
        }
        if (command.getName().equalsIgnoreCase("mb")) {
            if (args.length == 0) {
                sender.sendMessage("/mb help");
                return true;
            }
            if (args.length >= 2) {
                sender.sendMessage("/mb help");
                return true;
            }
            if (args[0].equalsIgnoreCase("god")) {
                Player p = (Player) sender;
                ItemStack honeyChest = new ItemStack(Material.GLASS_BOTTLE);
                ItemMeta meta = honeyChest.getItemMeta();
                meta.setDisplayName("§6§lゴッドビール");
                honeyChest.setItemMeta(meta);
                p.getInventory().addItem(honeyChest);
                sender.sendMessage("ゴッドビールを購入しました");
                return true;
            }
        }
        return true;
    }
    @EventHandler
    public void onDrink(PlayerInteractEvent e) {
        if(e.getAction() == Action.RIGHT_CLICK_AIR){
            if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("§6§lゴッドビール")) {
                if (vault.getBalance(e.getPlayer().getUniqueId()) < 10000000){
                    e.getPlayer().sendMessage("§7お金が足りません");
                    return;
                }
                    if (r.nextInt(10) == 0){
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mtitle &e&lゴッドビールで1億当選！ " + e.getPlayer().getName() + " 1 5 1");
                    e.getPlayer().sendMessage("§a§l$100,000,000円を獲得！");
                    e.getPlayer().spawnParticle(Particle.HEART,e.getPlayer().getLocation().getX()+3,e.getPlayer().getLocation().getY()+3,e.getPlayer().getLocation().getZ()+3,20);
                    vault.withdraw(e.getPlayer().getUniqueId(), 10000000);
                    vault.deposit(e.getPlayer().getUniqueId(), 100000000);
                    return;
                }
                if (r.nextInt(10) != 0){
                    e.getPlayer().sendMessage("§7外れました");
                    e.getPlayer().spawnParticle(Particle.VILLAGER_ANGRY, e.getPlayer().getLocation().getX(),e.getPlayer().getLocation().getY(),e.getPlayer().getLocation().getZ(),20);
                    vault.withdraw(e.getPlayer().getUniqueId(), 10000000);
                }
            }
        }
    }
}
