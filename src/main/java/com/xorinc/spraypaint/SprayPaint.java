package com.xorinc.spraypaint;

import java.net.URL;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.map.MapView;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class SprayPaint extends JavaPlugin implements Listener{
		
	public static AnimationConfig conf = null;
	public static SliceConfig slice = null;
    public static boolean redrawNeeded = false;
    public static SprayPaint plugin;

	
	@SuppressWarnings("deprecation")
	public void onEnable(){
		
		conf = new AnimationConfig(this);
		slice = new SliceConfig(this);

        plugin = this;

        for (short s : conf.getIds()) {

            MapView map = getServer().getMap(s);

            if (map==null) {

                continue;
            }

            map.addRenderer(conf.getAnimator(s));
        }

        for(Entry<String, List<Short>> e : slice.getImages().entrySet()){
			
			SlicingUtil.generateMaps(e.getKey(), e.getValue(), this, null);
			
		}
        getServer().getPluginManager().registerEvents(this, this);
		getCommand("sliceimage").setExecutor(new SliceCommand(this));
		
	}	
	
	public void onDisable(){
		
		slice.saveImages();
		
	}

    @EventHandler
    public void onLogin(PlayerJoinEvent ev)
    {

        Player pp = ev.getPlayer();
        if(pp != null)
        {
            FastSend sendTask;
             pp.setMetadata("SprayPaint.Render", new FixedMetadataValue(this, true));
            sendTask = new FastSend(this, 10, pp); //Right now 10 does nothing... may add back later.
            sendTask.runTaskLater(this, 2);
        }
    }


    public static boolean getMetadata(Player player, String key) {
        List<MetadataValue> values = player.getMetadata(key);
        for (MetadataValue value : values) {
            if (value.getOwningPlugin().getDescription().getName().equals(plugin.getDescription().getName())) {
                return value.asBoolean(); //value();
            }
        }
        return false;
    }

//	@SuppressWarnings("deprecation")
//	@Override
//	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args){
//		
//		MapView map = getServer().createMap(getServer().getWorlds().get(0));
//		
//		sender.sendMessage("Created map ID " + map.getId());
//		
//		return true;
//		
//		
//	}	
}
