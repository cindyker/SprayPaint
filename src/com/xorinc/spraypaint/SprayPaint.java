package com.xorinc.spraypaint;

import java.net.URL;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.map.MapView;
import org.bukkit.plugin.java.JavaPlugin;

public class SprayPaint extends JavaPlugin{
		
	public static AnimationConfig conf = null;
	public static SliceConfig slice = null;
	
	@SuppressWarnings("deprecation")
	public void onEnable(){
		
		conf = new AnimationConfig(this);
		slice = new SliceConfig(this);
		
		for(short s : conf.getIds()){
			
			MapView map = getServer().getMap(s);
			
			if(map == null){
				continue;
			}
			
			map.addRenderer(conf.getAnimator(s));
		}
		
		for(Entry<URL, List<Short>> e : slice.getImages().entrySet()){
			
			SlicingUtil.generateMaps(e.getKey(), e.getValue(), this, null);
			
		}
		
		getCommand("sliceimage").setExecutor(new SliceCommand(this));
		
	}	
	
	public void onDisable(){
		
		slice.saveImages();
		
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
