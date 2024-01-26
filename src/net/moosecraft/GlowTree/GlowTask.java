package net.moosecraft.GlowTree;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class GlowTask extends BukkitRunnable {
	private final StructureGrowEvent event;
	private final Material seed;
	HashMap<Material, GlowTree.treeType> enabledTreeList;
	
	private Material[] trees = {
			Material.ACACIA_LOG,
			Material.BIRCH_LOG,
			Material.CHERRY_LOG,
			Material.DARK_OAK_LOG,
			Material.JUNGLE_LOG,
			Material.MANGROVE_LOG,
			Material.OAK_LOG,
			Material.SPRUCE_LOG,
			Material.STRIPPED_ACACIA_LOG,
			Material.STRIPPED_BIRCH_LOG,
			Material.STRIPPED_CHERRY_LOG,
			Material.STRIPPED_DARK_OAK_LOG,
			Material.STRIPPED_JUNGLE_LOG,
			Material.STRIPPED_MANGROVE_LOG,
			Material.STRIPPED_OAK_LOG,
			Material.STRIPPED_SPRUCE_LOG };
    
	private List<Material> treeList = Arrays.asList(trees);
	
	public GlowTask(StructureGrowEvent e, Material s){
		this.event = e;
		this.seed = s;
		enabledTreeList = GlowTree.enabledTreeList;
		
	}
	
	@Override
	public void run(){

        final Location tBottom = event.getLocation();
        Material replace = GlowTree.seedList.get(GlowTree.enabledTreeList.get(seed));
        
        //find the start of the tree leaves
        Block tTop = GlowTree.getLeaf(tBottom.getBlock());
    	
        //go up one more if there's extra room
        if(treeList.contains(tTop.getRelative(BlockFace.UP).getType()) ){
    		
    		tTop = tTop.getRelative(BlockFace.UP);
        }

          
    	tTop.getRelative(BlockFace.NORTH).setType(replace);
        tTop.getRelative(BlockFace.SOUTH).setType(replace);
        tTop.getRelative(BlockFace.EAST).setType(replace);
        tTop.getRelative(BlockFace.WEST).setType(replace);
        
        //replace the seed material with dirt when we're done
        tBottom.getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).setType(Material.DIRT);

	}
}