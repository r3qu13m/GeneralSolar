package r3qu13m.gs;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockGeneralSolar extends Block {
	public BlockGeneralSolar(int id) {
		super(id, 0, Material.iron);

	}

	@Override
	public TileEntity createTileEntity(World world, int meta) {
		TileEntityGeneralSolar te = new TileEntityGeneralSolar();
		te.setType(meta);
		return te;
	}

	public int damageDropped(int meta) {
		return meta;
	}

	@Override
	public String getBlockName() {
		return "r3qu13m.block.gs";
	}

	public int getBlockTextureFromSideAndMetadata(int side, int meta) {
		return meta;
	}

	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) {
		par3List.add(new ItemStack(par1, 1, 0));
		par3List.add(new ItemStack(par1, 1, 1));
		par3List.add(new ItemStack(par1, 1, 2));
	}

	@Override
	public String getTextureFile() {
		return "/r3qu13m/assets/block_generator.png";
	}

	@Override
	public boolean hasTileEntity(int meta) {
		return true;
	}

	public int quantityDropped(Random par1Random) {
		return 1;
	}
}
