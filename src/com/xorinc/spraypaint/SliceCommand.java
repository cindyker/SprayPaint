package com.xorinc.spraypaint;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SliceCommand implements CommandExecutor {

	private SprayPaint plugin;
	
	public SliceCommand(SprayPaint plugin){
		
		this.plugin = plugin;
				
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(args.length == 0){
			
			sender.sendMessage("Please enter a URL.");
			return true;
			
		}
		
		try {
			SlicingUtil.generateMaps(args[0], null, plugin, sender);
		} catch (Exception e) {
			sender.sendMessage("Generic Exception '" + args[0] + "'");
			e.printStackTrace();
		}
		
		return true;
		
	}
	
	public static void sendCompletionMessage(List<Short> ids, CommandSender sender, String url){
		
		sender.sendMessage("Mapped image at " + url + " with ids " + ids);
		
	}

}
