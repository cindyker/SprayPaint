package com.xorinc.spraypaint;

import net.minecraft.server.v1_7_R1.Items;
import net.minecraft.server.v1_7_R1.WorldMap;

import org.bukkit.World;
import org.bukkit.craftbukkit.v1_7_R1.CraftWorld;
import org.bukkit.map.MapView;

public class NMSUtil {

	
	public static MapView createMap(short id, World w){
		
	    net.minecraft.server.v1_7_R1.ItemStack stack = new net.minecraft.server.v1_7_R1.ItemStack(Items.MAP, 1, id);
	    
	    WorldMap worldmap = Items.MAP.getSavedMap(stack, ((CraftWorld)w).getHandle());
	    
	    return worldmap.mapView;
		
	}
	
	
}
