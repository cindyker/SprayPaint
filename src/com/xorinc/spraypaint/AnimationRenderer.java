package com.xorinc.spraypaint;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

public class AnimationRenderer extends MapRenderer {

	private byte[][][] images;
	
	private int finishedImages = 0, checksum;
	
	private List<Integer> intervals;
			
	private int currentImage = 0, timer = 0, currentInverval = 0;
		
	public AnimationRenderer(List<String> urls, List<Integer> intervals, final SprayPaint plugin){
		
		images = new byte[urls.size()][128][128];
		
		checksum = urls.size();
		
		this.intervals = intervals;
		
		int count = 0;
		
		for(final String s : urls){
			
			final int index = count;
						
			new BukkitRunnable(){

				@Override
				public void run() {
					
					BufferedImage buffer = null;
					
					try {
						//URL url = new URL(s);
						//buffer = ImageIO.read(url.openStream());
                        plugin.getLogger().info("Opening File: " + plugin.getDataFolder()+"/"+s);
                        buffer = ImageIO.read(new File( plugin.getDataFolder()+"/"+s));
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					final BufferedImage image = buffer;
					
					new BukkitRunnable(){

						@Override
						public void run() {
													
							if(image != null){
								
								BufferedImage resizedImage = MapPalette.resizeImage(image);
								
								images[index] = ColorUtil.fromImage(resizedImage);
								
								finishedImages++;

                                SprayPaint.redrawNeeded = true;

                                for(Player pp:SprayPaint.plugin.getServer().getOnlinePlayers())
                                {
                                    pp.setMetadata("SprayPaint.animation",new FixedMetadataValue(plugin,true));
                                }
								
							}

							
						}			
						
					}.runTask(plugin);
				}
					
			}.runTaskAsynchronously(plugin);
			
			count++;
			
		}
		
	}
	
	@Override
	public void render(MapView view, MapCanvas canvas, Player player) {
		
		view.setCenterX(Integer.MAX_VALUE);
		view.setCenterZ(Integer.MAX_VALUE);
		
		if(finishedImages != checksum)
			return;
		
		if(timer == currentInverval){
			
			timer = 0;
			currentImage = nextIndex(currentImage);
			currentInverval = intervals.get(currentImage);
			
			for(int i = 0; i < 128; i++)
				for(int j = 0; j < 128; j++){				
					canvas.setPixel(i, j, getColor(currentImage, i, j));				
				}
			
		}
		
		timer++;

        if (SprayPaint.getMetadata(player,"SprayPaint.animation") ) {
            // do the redrawing work
            player.removeMetadata("SprayPaint.animation",SprayPaint.plugin);
            player.sendMap(view);
        }


    }
	
	private byte getColor(int i, int x, int z){
		
		if(images[i].length < x || images[i][x].length < z)
			return (byte) 0;
		
		return images[i][x][z];
		
	}
	
	private int nextIndex(int i){
		
		i++;
		
		if(i > checksum - 1)
			i = 0;
			
		return i;
	}

}
