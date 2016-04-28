package net.moosecraft.GlowTree;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.Listener;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.EventHandler;


public class GlowTree extends JavaPlugin implements Listener {
    
    public HashMap<Material, treeType> enabledTreeList = new HashMap<Material, treeType>();
    private final GlowTree plugin = this;
    public enum treeType {

        glow,boom,pumpkin,melon,mob,cake,lantern

    }
    

    @Override
	public void onEnable(){
		
		//initialize GlowTree
        getLogger().info("GlowTree has been enabled");
        getServer().getPluginManager().registerEvents(this, this);
        saveDefaultConfig();

        for(treeType type : treeType.values()){

            if(getConfig().getBoolean(type.name())){
                
            	Material cost = Material.getMaterial(getConfig().getString(type.toString() + "block" ));
            	
            	enabledTreeList.put(cost, type);
                
                //notify server of tree types and costs for each type
                getLogger().info(type.toString() + " uses: " + cost.toString());
                
            }
        }
    }
    
    @Override
    public void onDisable(){
        
    	getLogger().info("GlowTree has been disabled");
    	
    } 
    
	//Start at the bottom of the tree and recurse up until you find leaves around the tree center
    public static Block getLeaf(Block start){
        
    	Material a = Material.AIR;
        Material l = Material.LEAVES;
        Material n = start.getRelative(BlockFace.NORTH).getType();
        Material s = start.getRelative(BlockFace.SOUTH).getType();
        Material e = start.getRelative(BlockFace.EAST).getType();
        Material w = start.getRelative(BlockFace.WEST).getType();
        
        if(n.equals(l) || s.equals(l) || e.equals(l) || w.equals(l) || start.getRelative(BlockFace.UP).getType().equals(a)) {
            return start;
        } 
       
        else {
            return getLeaf(start.getRelative(BlockFace.UP));
        }
        
    }

        
    @EventHandler
    public void onInteract(StructureGrowEvent event){
            
    	Material seed = event.getLocation().getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getType();
    
    	if(enabledTreeList.containsKey(seed)){
    		
    		new GlowTask(event, seed, enabledTreeList).runTaskLater(this.plugin, 1200);

    	}
    }// onInteract

	
}//class GlowTree


