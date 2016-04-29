package r3qu13m.gs;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import ic2.api.Items;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.Configuration;

@Mod(name = "GeneralSolar", version = "0.0.1", modid = "generalsolar", dependencies = "after:IC2")
public class GeneralSolar {
	@Mod.Instance("generalsolar")
	public static GeneralSolar instance;

	public Block blockGS;

	public int id_GS_blk;

	public GeneralSolar() {
		instance = this;
	}

	@Mod.Init
	public void init(FMLInitializationEvent event) {
		blockGS = new BlockGeneralSolar(id_GS_blk);
		MinecraftForgeClient.preloadTexture("/r3qu13m/assets/block_generator.png");
		blockGS.setCreativeTab(CreativeTabs.tabRedstone);
		blockGS.setHardness(5.0F);
		GameRegistry.registerTileEntity(TileEntityGeneralSolar.class, TileEntityGeneralSolar.class.getCanonicalName());
		
		GameRegistry.registerBlock(blockGS, ItemBlockGeneralSolar.class, "r3qu13m.block.gs");
		
		LanguageRegistry.instance().addNameForObject(new ItemStack(blockGS, 1, 0), "en_US", "General Solar Mk1");
		LanguageRegistry.instance().addNameForObject(new ItemStack(blockGS, 1, 1), "en_US", "General Solar Mk2");
		LanguageRegistry.instance().addNameForObject(new ItemStack(blockGS, 1, 2), "en_US", "General Solar Mk3");

		GameRegistry.addRecipe(new ItemStack(blockGS, 1, 0),
				new Object[] { "XXX", "XYX", "XXX", 'X', Items.getItem("solarPanel"), 'Y', Item.lightStoneDust });
		GameRegistry.addRecipe(new ItemStack(blockGS, 1, 1),
				new Object[] { "ZZZ", "XYX", "ZZZ", 'X', new ItemStack(blockGS, 1, 0), 'Y', Items.getItem("electronicCircuit"), 'Z', Block.glass });
		GameRegistry.addRecipe(new ItemStack(blockGS, 1, 2),
				new Object[] { "ZZZ", "XYX", "ZZZ", 'X', new ItemStack(blockGS, 1, 1), 'Y', Items.getItem("advancedCircuit"), 'Z', Items.getItem("reinforcedGlass") });
	}

	@Mod.PreInit
	public void preinit(FMLPreInitializationEvent event) {
		Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());
		id_GS_blk = cfg.get("blocks", "GeneralSolarID", 3028).getInt();
		cfg.save();
	}
}
