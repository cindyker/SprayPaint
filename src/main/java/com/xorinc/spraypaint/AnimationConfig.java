package com.xorinc.spraypaint;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class AnimationConfig {

	private static final String CONFIG_NAME = "animations.yml";
	
	private Map<Short, List<String>> urls = Collections.synchronizedMap(new HashMap<Short, List<String>>());
	private Map<Short, List<Integer>> intervals = Collections.synchronizedMap(new HashMap<Short, List<Integer>>());
	
	private SprayPaint plugin;
	
	public AnimationConfig(SprayPaint plugin){
		
		this.plugin = plugin;
		
		loadSettings();
	}
	
	public void loadSettings(){
		
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
        conf.options().pathSeparator('.');
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
        
        
        if(!conf.isConfigurationSection("animations"))
        	conf.createSection("animations");
        	
        ConfigurationSection anim = conf.getConfigurationSection("animations");
        
        for(String s : anim.getKeys(false)){
        	
        	if(!isShort(s))
        		continue;
        	
        	short sh = Short.parseShort(s);
        	
        	List<String> urls = anim.getConfigurationSection(s).getStringList("urls");
        	List<Integer> intervals = anim.getConfigurationSection(s).getIntegerList("intervals");
        	
        	this.urls.put(sh, urls);
        	this.intervals.put(sh, intervals);
        	
        }
        
        try {
            conf.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}
	
	private boolean isShort(String s){
		
		try{			
			Short.parseShort(s);			
		}
		catch(NumberFormatException e){
			return false;
		}
		
		return true;
		
	}
	
	public AnimationRenderer getAnimator(short id){
		
		if(!urls.containsKey(id) || !intervals.containsKey(id))
			return null;
			
		return new AnimationRenderer(urls.get(id), intervals.get(id), plugin);
		
	}
	
	public Set<Short> getIds(){
		
		return urls.keySet();
		
	}
}
