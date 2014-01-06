package com.xorinc.spraypaint;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import org.bukkit.command.CommandSender;
import org.bukkit.map.MapView;
import org.bukkit.scheduler.BukkitRunnable;

public class SlicingUtil {

	public static void generateMaps(final URL url, final List<Short> ids, final SprayPaint plugin, final CommandSender sender){
		
		new BukkitRunnable(){

			@Override
			public void run() {
				
				BufferedImage buffer = null;
				
				try {
					buffer = ImageIO.read(url.openStream());
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				final BufferedImage image = buffer;
				
				new BukkitRunnable(){

					@SuppressWarnings("deprecation")
					@Override
					public void run() {
												
						if(image != null){
							
							BufferedImage[][] images = slice(image);
														
							if(ids == null){
								
								List<Short> newIds = new ArrayList<Short>();
								
								for(int i = 0; i < images.length; i++)
									for(int j = 0; j < images[i].length; j++){
														
										MapView map = plugin.getServer().createMap(plugin.getServer().getWorlds().get(0));
										
										map.addRenderer(new ImageRenderer(ColorUtil.fromImage(images[i][j])));
										
										newIds.add(map.getId());
										
									}
								
								SliceCommand.sendCompletionMessage(newIds, sender, url);
								
								SprayPaint.slice.addImage(url, newIds);
							
							}
							
							else{
								
								Iterator<Short> iter = ids.iterator();
								
								for(int i = 0; i < images.length; i++)
									for(int j = 0; j < images[i].length; j++){
										
										if(!iter.hasNext())
											break;
										
										short next = iter.next();
										
										MapView map = plugin.getServer().getMap(next);
										
										map.addRenderer(new ImageRenderer(ColorUtil.fromImage(images[i][j])));
										
									}
							}
							
						}
						
					}			
					
				}.runTask(plugin);
			}
				
		}.runTaskAsynchronously(plugin);
		
		
		
	}
	
	public static BufferedImage[][] slice(BufferedImage im){
		
		int width = im.getWidth(), height = im.getHeight();
		
		int widthParts = width / 128 + (width % 128 == 0 ? 0 : 1);
		int heightParts = height / 128 + (height % 128 == 0 ? 0 : 1);
		
		BufferedImage[][] buffer = new BufferedImage[widthParts][heightParts];
		
		for(int i = 0; i < widthParts; i++)
			for(int j = 0; j < heightParts; j++){
				
				int x = i * 128;
				int y = j * 128;
				
				int w = width % 128 != 0 && i == widthParts - 1 ? width % 128 : 128;
				int h = height % 128 != 0 && j == heightParts - 1 ? height % 128 : 128; 
				
				System.out.println("w = " + w + " h = " + h);
				
				buffer[i][j] = im.getSubimage(x, y, w, h);
				
			}
		
		return buffer;
	}
	
}
