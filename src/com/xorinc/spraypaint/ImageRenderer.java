package com.xorinc.spraypaint;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

public class ImageRenderer extends MapRenderer {

	private byte[][] colors = new byte[128][128];
	
	public ImageRenderer(byte[][] data){
		
		colors = data;
		
	}
	
	@Override
	public void render(MapView view, MapCanvas canvas, Player player) {
		
		view.setCenterX(Integer.MAX_VALUE);
		view.setCenterZ(Integer.MAX_VALUE);
		
		for(int i = 0; i < 128; i++)
			for(int j = 0; j < 128; j++){				
				canvas.setPixel(i, j, getColor(i, j));				
			}

	}
	
	private byte getColor(int x, int z){
		
		if(colors.length < x || colors[x].length < z)
			return (byte) 0;
		
		return colors[x][z];
		
	}

}
