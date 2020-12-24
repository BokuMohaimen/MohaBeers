package beers.mohabeers;

import org.bukkit.plugin.java.JavaPlugin;

public final class MohaBeers extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("起動しました");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("停止しました");
    }
}
