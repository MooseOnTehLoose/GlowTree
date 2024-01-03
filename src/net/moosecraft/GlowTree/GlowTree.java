package net.moosecraft.GlowTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.Listener;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.EventHandler;


public class GlowTree extends JavaPlugin implements Listener {
    
	public enum treeType { glow,boom,pumpkin,melon,mob,cake,lantern }
	
    public static HashMap<Material, treeType> enabledTreeList = new HashMap<Material, treeType>();
    
    public static HashMap<treeType, Material> seedList = new HashMap<treeType, Material>() {
		
    	private static final long serialVersionUID = 1L;

	{
		put(treeType.glow, Material.GLOWSTONE);
    	put(treeType.boom, Material.TNT);
    	put(treeType.cake, Material.CAKE_BLOCK);
    	put(treeType.melon, Material.MELON);
    	put(treeType.mob, Material.MOB_SPAWNER);
    	put(treeType.pumpkin, Material.PUMPKIN);
    	put(treeType.lantern, Material.SEA_LANTERN);
    	
    }
	};
    
    public static ArrayList<TreeType> growSmall = new ArrayList<TreeType>(
    		Arrays.asList(TreeType.BIG_TREE, TreeType.BIRCH, TreeType.COCOA_TREE,  
    	    			  TreeType.REDWOOD, TreeType.SMALL_JUNGLE, TreeType.SWAMP, 
    	    			  TreeType.TALL_BIRCH, TreeType.TALL_REDWOOD, TreeType.TREE));
    
    public static ArrayList<TreeType> growBig = new ArrayList<TreeType>(
    	    Arrays.asList(TreeType.JUNGLE, TreeType.MEGA_REDWOOD, TreeType.DARK_OAK));
    
    
    
    private final GlowTree plugin = this;

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
    
	//Start at the bottom of the tree and recurse up until you find leaves around the tree center
    public static Block getAcaciaLeaf(Block start){
        
    	//if logs keep going up
    	if (start.getRelative(BlockFace.UP).getType().equals(Material.LOG_2)){
        		return getAcaciaLeaf(start.getRelative(BlockFace.UP));
        }
        
    	//if you hit air look up and around for more logs
    	else if (start.getRelative(BlockFace.UP).getType().equals(Material.AIR) || start.getRelative(BlockFace.UP).getType().equals(Material.LEAVES_2) ){
        	
        	//go up 1 block	
        	Block up1 = start.getRelative(BlockFace.UP);
        		
        	//if up 1 and north is a log go up 	
        	if (up1.getRelative(BlockFace.NORTH).getType().equals(Material.LOG_2)){		
        		return getAcaciaLeaf(up1.getRelative(BlockFace.NORTH));
        	}
        	
        	else if (up1.getRelative(BlockFace.SOUTH).getType().equals(Material.LOG_2)){
        		return getAcaciaLeaf(up1.getRelative(BlockFace.SOUTH));
        	}
        	
        	else if (up1.getRelative(BlockFace.EAST).getType().equals(Material.LOG_2)){	
        		return getAcaciaLeaf(up1.getRelative(BlockFace.EAST));
        	}
        	
        	else if (up1.getRelative(BlockFace.WEST).getType().equals(Material.LOG_2)){
        			
        		return getAcaciaLeaf(up1.getRelative(BlockFace.WEST));
        	}
        	
        	else if (up1.getType().equals(Material.LEAVES_2)){
 
        		return up1;
        	}
        	
        	return start;
        }	
    	else if (start.getRelative(BlockFace.UP).getType().equals(Material.LEAVES_2)){
    		return start.getRelative(BlockFace.UP);
    	}
    	return start;
    	
	}
    
	//Start at the bottom of the tree and recurse up until you find leaves around the tree center
    public static Block getBigLeaf(Block start){
    	Block bUp = start.getRelative(BlockFace.UP);
    	if (bUp.getType().equals(Material.AIR)){
    		return start.getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN);
   
    	}
    	else {
    		return getBigLeaf(bUp);
    	}
    }
    
    public static Block getB1(Block b){
    	Block bE = b.getRelative(BlockFace.EAST);
    	Block bS = b.getRelative(BlockFace.SOUTH);
    	Block bW = b.getRelative(BlockFace.WEST);
    	Block bN = b.getRelative(BlockFace.NORTH);
    	Block bSE = b.getRelative(BlockFace.SOUTH_EAST);
    	Block bSW = b.getRelative(BlockFace.SOUTH_WEST);
    	Block bNW = b.getRelative(BlockFace.NORTH_WEST);
    	//Block bNE = b.getRelative(BlockFace.NORTH_EAST);
    	
    	Material bt = b.getType();
    	Material bEt = bE.getType();
    	Material bSt = bS.getType();
    	Material bWt = bW.getType();
    	Material bNt = bN.getType();
    	Material bSEt = bSE.getType();
    	Material bSWt = bSW.getType();
    	Material bNWt = bNW.getType();
    	//Material bNEt = bNE.getType();

    	if (bEt.equals(bt) && bSt.equals(bt) && bSEt.equals(bt)){
    		return b;
    	}
    	else if (bSt.equals(bt) && bWt.equals(bt) && bSWt.equals(bt)){
    		return bW;
    	}
    	else if (bWt.equals(bt) && bNt.equals(bt) && bNWt.equals(bt)){
    		return bNW;
    	}
    	else {
    		return bN;
    	}
    
    } 
    

    
    @EventHandler
    public void onInteract(StructureGrowEvent event){
        TreeType thisTree = event.getSpecies();
        
        
        Material seed = Material.DIRT;
        seed = event.getLocation().getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getType();
        
        //acacia special call method
        if ( thisTree.equals(TreeType.ACACIA) && enabledTreeList.containsKey(seed)){

        	new AcaciaGlowTask(event, seed).runTaskLater(this.plugin, 1200);

        }//if acacia
        //call normal grow method
        else if (growSmall.contains(thisTree) && enabledTreeList.containsKey(seed)){

        	new GlowTask(event, seed).runTaskLater(this.plugin, 1200);

        }//if normal 
        //call special 4block grow method
        else if (growBig.contains(thisTree) && enabledTreeList.containsKey(seed)){
        	Block sB = getB1(event.getLocation().getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN));
        	Material sB1 = sB.getType();
        	Material sB2 = sB.getRelative(BlockFace.EAST).getType();
            Material sB3 = sB.getRelative(BlockFace.SOUTH_EAST).getType();
            Material sB4 = sB.getRelative(BlockFace.SOUTH).getType();
        	
            if (sB1.equals(seed) && sB2.equals(seed) && sB3.equals(seed) && sB4.equals(seed) ){

              	new BigGlowTask(event, seed).runTaskLater(this.plugin, 1200);
     	
            }   
       }//if big
    }// onInteract
}//class GlowTree


