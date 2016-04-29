package r3qu13m.gs;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockGeneralSolar extends ItemBlock {

	public ItemBlockGeneralSolar(int par1Id) {
		super(par1Id);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}

	@Override
	public String getItemNameIS(ItemStack par1IS) {
		return String.format("r3qu13m.block.gs.%d", par1IS.getItemDamage());
	}

	public int getMetadata(int par1Meta) {
		return par1Meta;
	}
}
