package com.xorinc.spraypaint;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

/**
 * Created by cindy on 7/13/14.
 * Borrowed from de.craftlancer.imagemaps
 * http://dev.bukkit.org/bukkit-plugins/imagemaps/
 */
public class FastSend extends BukkitRunnable  {

        private final SprayPaint plugin;
        private final int mapsPerRun;
        private final Player p;

        public FastSend(SprayPaint plugin, int mapsPerSend, Player player)
        {
            this.plugin = plugin;
            this.mapsPerRun = mapsPerSend;
            this.p = player;
        }

        public void run()
        {

            //add MapsPerSend back? hmmm not right now.

            SprayPaint.plugin.getLogger().info("Running load! for player ");

            for(Map.Entry<String, List<Short>> e : SprayPaint.slice.getImages().entrySet()){

                for(Short x: e.getValue())
                {
                  //  for (Player p : this.plugin.getServer().getOnlinePlayers())
                  //  {
                            p.sendMap(this.plugin.getServer().getMap(x));
               //     }
                }
            }
        }


    }

