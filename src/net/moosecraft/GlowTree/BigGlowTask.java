package net.moosecraft.GlowTree;



import java.util.HashMap;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
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
        GlowTree.setBig(tTop, replace);
        GlowTree.setBig(tTop.getRelative(BlockFace.DOWN), replace);
        
        //replace the seed material with dirt when we're done
        Block seed1 = b1.getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN);
        seed1.setType(Material.DIRT);
        seed1.getRelative(BlockFace.EAST).setType(Material.DIRT);
        seed1.getRelative(BlockFace.SOUTH_EAST).setType(Material.DIRT);
        seed1.getRelative(BlockFace.SOUTH).setType(Material.DIRT);
        

	}
}