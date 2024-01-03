package net.moosecraft.GlowTree;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class AcaciaGlowTask extends BukkitRunnable {
	private final StructureGrowEvent event;
	private final Material seed;
	HashMap<Material, GlowTree.treeType> enabledTreeList;
	
	public AcaciaGlowTask(StructureGrowEvent e, Material s){
		this.event = e;
		this.seed = s;
		enabledTreeList = GlowTree.enabledTreeList;
		
	}
	
	@Override
	public void run(){

        final Location tBottom = event.getLocation();
        Material replace = GlowTree.seedList.get(GlowTree.enabledTreeList.get(seed));
        
        //find the start of the tree leaves
        Block tTop = GlowTree.getAcaciaLeaf(tBottom.getBlock());
    	if (tTop.getRelative(BlockFace.UP).getType().equals(Material.AIR)){
    		tTop = tTop.getRelative(BlockFace.DOWN);
    	}


        tTop.getRelative(BlockFace.NORTH).setType(replace);
        tTop.getRelative(BlockFace.SOUTH).setType(replace);
        tTop.getRelative(BlockFace.EAST).setType(replace);
        tTop.getRelative(BlockFace.WEST).setType(replace);


        
        //replace the seed material with dirt when we're done
        tBottom.getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).setType(Material.DIRT);

	}
}
