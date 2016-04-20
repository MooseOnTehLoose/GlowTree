package net.moosecraft.GlowTree;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class GlowTask extends BukkitRunnable {
	private final StructureGrowEvent event;
	private final Material seed;
	private Material replace = Material.LEAVES;
	HashMap<Material, GlowTree.treeType> enabledTreeList;
	
	public GlowTask(StructureGrowEvent e, Material s, HashMap<Material, GlowTree.treeType> h){
		this.event = e;
		this.seed = s;
		enabledTreeList = h;
		
	}
	
	@Override
	public void run(){

        final Location tBottom = event.getLocation();
        
        if(((GlowTree.treeType)enabledTreeList.get(seed)).equals(GlowTree.treeType.glow)){
            replace = Material.GLOWSTONE;
        } 
        
        else if(((GlowTree.treeType)enabledTreeList.get(seed)).equals(GlowTree.treeType.boom)){
            replace = Material.TNT;
        } 
        
        else if(((GlowTree.treeType)enabledTreeList.get(seed)).equals(GlowTree.treeType.pumpkin)){
            replace = Material.PUMPKIN;
        } 
        
        else if(((GlowTree.treeType)enabledTreeList.get(seed)).equals(GlowTree.treeType.melon)){
            replace = Material.MELON_BLOCK;
        } 
        
        else if(((GlowTree.treeType)enabledTreeList.get(seed)).equals(GlowTree.treeType.mob)){
            replace = Material.MOB_SPAWNER;
        } 
        
        else if(((GlowTree.treeType)enabledTreeList.get(seed)).equals(GlowTree.treeType.cake)){
            replace = Material.CAKE_BLOCK;
        }
        
        //find the start of the tree leaves
        Block tTop = GlowTree.getLeaf(tBottom.getBlock());
    	
        //go up one more if there's extra room
        if(tTop.getRelative(BlockFace.UP).getType().equals(Material.LOG)){
    		
    		tTop = tTop.getRelative(BlockFace.UP);
    	

        if(replace.equals(Material.MOB_SPAWNER)) {

        	tTop.getRelative(BlockFace.NORTH).setType(replace);
        	BlockState state = tTop.getRelative(BlockFace.NORTH).getState();
        	CreatureSpawner spawner = (CreatureSpawner)state;
            EntityType type = EntityType.ZOMBIE;
            spawner.setSpawnedType(type);
            state.update();
            
        	tTop.getRelative(BlockFace.SOUTH).setType(replace);
        	state = tTop.getRelative(BlockFace.SOUTH).getState();
        	spawner = (CreatureSpawner)state;
            type = EntityType.ZOMBIE;
            spawner.setSpawnedType(type);
            state.update();
            
        	tTop.getRelative(BlockFace.EAST).setType(replace);
        	state = tTop.getRelative(BlockFace.EAST).getState();
        	spawner = (CreatureSpawner)state;
            type = EntityType.ZOMBIE;
            spawner.setSpawnedType(type);
            state.update();
            
        	tTop.getRelative(BlockFace.WEST).setType(replace);
        	state = tTop.getRelative(BlockFace.WEST).getState();
        	spawner = (CreatureSpawner)state;
            type = EntityType.ZOMBIE;
            spawner.setSpawnedType(type);
            state.update();
        
        }//if block mobspawner
        
        //If its not a mobspawner we dont need to set mobspawner type
    	else {
            
    		tTop.getRelative(BlockFace.NORTH).setType(replace);
            tTop.getRelative(BlockFace.SOUTH).setType(replace);
            tTop.getRelative(BlockFace.EAST).setType(replace);
            tTop.getRelative(BlockFace.WEST).setType(replace);

        }//else normal replacement
        
        //replace the seed material with dirt when we're done
        tBottom.getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).setType(Material.DIRT);
    	}

	}
}