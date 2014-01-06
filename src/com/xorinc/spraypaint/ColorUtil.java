package com.xorinc.spraypaint;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Color;

public class ColorUtil {
	
	private static Map<Color, Byte> base = new HashMap<Color, Byte>();
	private static Map<Color, Byte> colors = new HashMap<Color, Byte>();
	
	static{
		
		base.put(Color.fromRGB(127, 178, 56), (byte) 1);
		base.put(Color.fromRGB(247, 233, 163), (byte) 2);
		base.put(Color.fromRGB(167, 167, 167), (byte) 3);
		base.put(Color.fromRGB(255, 0, 0), (byte) 4);
		base.put(Color.fromRGB(160, 160, 255), (byte) 5);
		base.put(Color.fromRGB(167, 167, 167), (byte) 6);
		base.put(Color.fromRGB(0, 124, 0), (byte) 7);
		base.put(Color.fromRGB(255, 255, 255), (byte) 8);
		base.put(Color.fromRGB(164, 168, 184), (byte) 9);
		base.put(Color.fromRGB(183, 106, 47), (byte) 10);
		base.put(Color.fromRGB(112, 112, 112), (byte) 11);
		base.put(Color.fromRGB(64, 64, 255), (byte) 12);
		base.put(Color.fromRGB(104, 83, 50), (byte) 13);
		base.put(Color.fromRGB(255, 252, 245), (byte) 14);
		base.put(Color.fromRGB(216, 127, 51), (byte) 15);
		base.put(Color.fromRGB(178, 76, 216), (byte) 16);
		base.put(Color.fromRGB(102, 153, 216), (byte) 17);
		base.put(Color.fromRGB(229, 229, 51), (byte) 18);
		base.put(Color.fromRGB(127, 204, 25), (byte) 19);
		base.put(Color.fromRGB(242, 127, 165), (byte) 20);
		base.put(Color.fromRGB(76, 76, 76), (byte) 21);
		base.put(Color.fromRGB(153, 153, 153), (byte) 22);
		base.put(Color.fromRGB(76, 127, 153), (byte) 23);
		base.put(Color.fromRGB(127, 63, 178), (byte) 24);
		base.put(Color.fromRGB(51, 76, 178), (byte) 25);
		base.put(Color.fromRGB(102, 76, 51), (byte) 26);
		base.put(Color.fromRGB(102, 127, 51), (byte) 27);
		base.put(Color.fromRGB(153, 51, 51), (byte) 28);
		base.put(Color.fromRGB(25, 25, 25), (byte) 29);
		base.put(Color.fromRGB(250, 238, 77), (byte) 30);
		base.put(Color.fromRGB(92, 219, 213), (byte) 31);
		base.put(Color.fromRGB(74, 128, 255), (byte) 32);
		base.put(Color.fromRGB(0, 217, 58), (byte) 33);
		base.put(Color.fromRGB(21, 20, 31), (byte) 34);
		base.put(Color.fromRGB(112, 2, 0), (byte) 35);

		for(Entry<Color, Byte> e : base.entrySet()){
			
			Color color = e.getKey();
			Color aC = Color.fromRGB(color.getRed() * 180 / 255, color.getGreen() * 180 / 255, color.getBlue() * 180 / 255);
			Color bC = Color.fromRGB(color.getRed() * 220 / 255, color.getGreen() * 220 / 255, color.getBlue() * 220 / 255);
			Color cC = Color.fromRGB(color.getRed() * 255 / 255, color.getGreen() * 225 / 255, color.getBlue() * 225 / 255);
			Color dC = Color.fromRGB(color.getRed() * 135 / 255, color.getGreen() * 135 / 255, color.getBlue() * 135 / 255);
			
			byte bite = e.getValue();
			byte aB = (byte) (bite*4 + 0);
			byte bB = (byte) (bite*4 + 1);
			byte cB = (byte) (bite*4 + 2);
			byte dB = (byte) (bite*4 + 3);
			
			colors.put(aC, aB);
			colors.put(bC, bB);
			colors.put(cC, cB);
			colors.put(dC, dB);
			
		}
		
	}
	
	public static byte fromColor(Color color){
		
		double dist = 1000;
		Color c = null;
		
		for(Color col : colors.keySet()){		
			double d = distance(color, col);
			
			if(d < dist){
				
				dist = d;
				c = col;
			}
		}
		
		return colors.get(c);
		
	}
	
	public static double distance(Color a, Color b){
		
		return Math.sqrt(Math.pow(a.getRed() - b.getRed(), 2) + Math.pow(a.getGreen() - b.getGreen(), 2) + Math.pow(a.getBlue() - b.getBlue(), 2));
		
	}
	
	public static byte[][] fromImage(BufferedImage im){
		
		byte[][] bytes = new byte[128][128];
		
		for(int i = 0; i < 128 && i < im.getWidth(); i++)
			for(int j = 0; j < 128 && j < im.getHeight(); j++){
				
				int rgb = im.getRGB(i, j);
				
				int alpha = (rgb >> 24) & 0xff;
				
				if(alpha == 0){
					bytes[i][j] = 0;
				}
				else{
					bytes[i][j] = fromColor(Color.fromRGB((rgb >> 16) & 0xff, (rgb >> 8) & 0xff, (rgb >> 0	) & 0xff));
				}		
			}
		
		return bytes;
	}
}
