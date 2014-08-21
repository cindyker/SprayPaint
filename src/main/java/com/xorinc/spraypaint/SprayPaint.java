package com.xorinc.spraypaint;

import java.net.URL;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

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
    public static Random rr;
    public static boolean ImagesLoaded = false;
	
	@SuppressWarnings("deprecation")
	public void onEnable(){
		


        plugin = this;

        ImageLoad LoadTask;
        LoadTask = new ImageLoad(this); //Right now 10 does nothing... may add back later.
        LoadTask.runTaskAsynchronously(this);

        rr = new Random();


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
        if(pp != null && ImagesLoaded)
        {
            FastSend sendTask;
             pp.setMetadata("SprayPaint.Render", new FixedMetadataValue(this, true));
            sendTask = new FastSend(this, 10, pp); //Right now 10 does nothing... may add back later.


            sendTask.runTaskLater(this,rr.nextInt(250)+1);
        }
        else
        {
            if(!ImagesLoaded)
                this.getLogger().info("[SprayPaint] Player did not get images sent, they were still loading. ");
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
