package com.xorinc.spraypaint;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class SliceConfig {

	private static final String CONFIG_NAME = "slices.yml";
	
	private Map<String, List<Short>> urls = new HashMap<String, List<Short>>();
	
	private SprayPaint plugin;
	
	public SliceConfig(SprayPaint plugin){
		
		this.plugin = plugin;
		
		loadImages();
	}
	
	public void loadImages(){
		
		if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }
    	
        File f = new File(plugin.getDataFolder(), CONFIG_NAME);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().severe("Unable to create " + CONFIG_NAME + "! No config options were loaded!");
                return;
            }
        }
        
        YamlConfiguration conf = new YamlConfiguration();
        conf.options().header(plugin.getDescription().getName() +  " configuration" + System.getProperty("line.separator"));
        conf.options().pathSeparator('\\');
        
        try {
            conf.load(f);
        } catch (Exception e) {
            plugin.getLogger().severe("==================== " + plugin.getDescription().getName() + " ====================");
            plugin.getLogger().severe("Unable to load " + CONFIG_NAME);
            plugin.getLogger().severe("Check your config for formatting issues!");
            plugin.getLogger().severe("No config options were loaded!");
            plugin.getLogger().severe("Error: " + e.getMessage());
            plugin.getLogger().severe("====================================================");
            return;
        }
        
        
        if(!conf.isConfigurationSection("images"))
        	conf.createSection("images");
        	
        ConfigurationSection im = conf.getConfigurationSection("images");
        
        System.out.println(im.getKeys(false));
        System.out.println(im.getKeys(true));
        
        for(String s : im.getKeys(false)){
        	
        	try {
        		
				String url = s;
				List<Integer> raw = im.getIntegerList(s);
				
				List<Short> ids = new ArrayList<Short>();
				
				for(int i : raw){
					ids.add((short) i);
				}
				
				urls.put(url, ids);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
        	
        }
        
        System.out.println(urls);
        
        try {
            conf.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}
	
	public void saveImages(){
		
		if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }
    	
        File f = new File(plugin.getDataFolder(), CONFIG_NAME);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().severe("Unable to create " + CONFIG_NAME + "! No config options were loaded!");
                return;
            }
        }
        
        YamlConfiguration conf = new YamlConfiguration();
        conf.options().pathSeparator('\\');
        conf.options().header(plugin.getDescription().getName() +  " configuration" + System.getProperty("line.separator"));
        
        try {
            conf.load(f);
        } catch (Exception e) {
            plugin.getLogger().severe("==================== " + plugin.getDescription().getName() + " ====================");
            plugin.getLogger().severe("Unable to load " + CONFIG_NAME);
            plugin.getLogger().severe("Check your config for formatting issues!");
            plugin.getLogger().severe("No config options were loaded!");
            plugin.getLogger().severe("Error: " + e.getMessage());
            plugin.getLogger().severe("====================================================");
            return;
        }
        
        
        if(!conf.isConfigurationSection("images"))
        	conf.createSection("images");
        	
        ConfigurationSection im = conf.getConfigurationSection("images");
        
        for(Entry<String, List<Short>> e : urls.entrySet()){
        	
        	List<Integer> ids = new ArrayList<Integer>();
        	
        	for(short s : e.getValue())
        		ids.add((int) s);
        	
        	im.set(e.getKey().toString(), ids);
        	
        }
        
        try {
            conf.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
	
	}
        
	public Map<String, List<Short>> getImages(){
		
		return Collections.unmodifiableMap(urls);
		
	}
	
	public void addImage(String url, List<Short> ids){
		
		urls.put(url, ids);
	
		saveImages();
		
	}
	
}
