package net.moosecraft.GlowTree;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class BigGlowTask extends BukkitRunnable {
	private final StructureGrowEvent event;
	private final Material seed;
	HashMap<Material, GlowTree.treeType> enabledTreeList;
	
	public BigGlowTask(StructureGrowEvent e, Material s){
		this.event = e;
		this.seed = s;
		enabledTreeList = GlowTree.enabledTreeList;
		
	}
	
	@Override
	public void run(){

        final Location tBottom = event.getLocation();
        Material replace = GlowTree.seedList.get(GlowTree.enabledTreeList.get(seed));
        
        //get block at position 1
        Block b1 = GlowTree.getB1(tBottom.getBlock());
        
        //find the top of the tree
        Block tTop = GlowTree.getBigLeaf(b1);
        Block seed1 = b1.getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN);
        
        setBig(tTop, seed1, replace);
        setBig(tTop.getRelative(BlockFace.DOWN), seed1, replace);
        
        //replace the seed material with dirt when we're done

        seed1.setType(Material.DIRT);
        seed1.getRelative(BlockFace.EAST).setType(Material.DIRT);
        seed1.getRelative(BlockFace.SOUTH_EAST).setType(Material.DIRT);
        seed1.getRelative(BlockFace.SOUTH).setType(Material.DIRT);

	}
	
    public static void setBig(Block b,Block s, Material type){
    	ArrayList<Block> trees = new ArrayList<Block>(
        		Arrays.asList(b.getRelative(BlockFace.WEST),
        					  b.getRelative(BlockFace.NORTH),
        					  b.getRelative(BlockFace.NORTH_EAST),
        					  b.getRelative(BlockFace.EAST).getRelative(BlockFace.EAST),
        					  b.getRelative(BlockFace.EAST).getRelative(BlockFace.SOUTH_EAST),
        					  b.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH_EAST),
        					  b.getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH),
        					  b.getRelative(BlockFace.SOUTH_WEST)));
    	
    	if(type.equals(Material.MOB_SPAWNER)) {
        	
        	CreatureSpawner seedSpawner = (CreatureSpawner)s.getState();
    		EntityType mtype = seedSpawner.getSpawnedType();
        	
    	
        	for (Block bX : trees){
        		bX.setType(Material.MOB_SPAWNER);
        		CreatureSpawner spawner = (CreatureSpawner)bX.getState();
        		spawner.setSpawnedType(mtype);
        		spawner.update();
        	}

        }//if block mobspawner
    	else {
    		for (Block bX : trees){
    			bX.setType(type);
    		}
    	}
    }
}