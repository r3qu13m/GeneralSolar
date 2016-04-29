package r3qu13m.gs;

import java.util.List;
import java.util.Vector;

import ic2.api.Direction;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileSourceEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.network.INetworkDataProvider;
import ic2.api.network.INetworkUpdateListener;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenDesert;
import net.minecraftforge.common.MinecraftForge;

public class TileEntityGeneralSolar extends TileEntity
		implements IEnergySource, INetworkDataProvider, INetworkUpdateListener {

	public static boolean isSunVisible(World var0, int var1, int var2, int var3) {
		return var0.isDaytime() && !var0.provider.hasNoSky && var0.canBlockSeeTheSky(var1, var2, var3)
				&& (var0.getWorldChunkManager().getBiomeGenAt(var1, var3) instanceof BiomeGenDesert
						|| !var0.isRaining() && !var0.isThundering());
	}

	private boolean addedToEnergyNet;
	private boolean loaded;
	private int type = 0;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	private boolean sunIsVisible;

	@Override
	public boolean emitsEnergyTo(TileEntity var1, Direction var2) {
		return true;
	}

	private int getLightValue() {
		int x = xCoord;
		int y = yCoord;
		int z = zCoord;
		int lv = 0;
		if (this.sunIsVisible) {
			return 15;
		}
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				for (int k = -1; k <= 1; k++) {
					lv = Math.max(worldObj.getBlockLightValue(x + i, y + j, z + j), lv);
				}
			}
		}
		return lv;
	}

	@Override
	public int getMaxEnergyOutput() {
		switch (this.type) {
		case 0:
			return 8;
		case 1:
			return 15;
		case 2:
			return 30;
		}
		return 0;
	}

	@Override
	public List getNetworkedFields() {
		Vector var1 = new Vector(2);
		return var1;
	}

	@Override
	public void invalidate() {
		if (this.loaded) {
			this.onUnloaded();
		}
		super.invalidate();
	}

	@Override
	public boolean isAddedToEnergyNet() {
		return this.addedToEnergyNet;
	}

	@Override
	public void onChunkUnload() {
		if (this.loaded) {
			this.onUnloaded();
		}
		super.onChunkUnload();
	}

	public void onLoaded() {
		this.loaded = true;
		MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
		this.addedToEnergyNet = true;
	}

	@Override
	public void onNetworkUpdate(String var1) {

	}

	public void onUnloaded() {
		if (this.addedToEnergyNet) {
			MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
			this.addedToEnergyNet = false;
		}
		this.loaded = false;
	}

	@Override
	public void readFromNBT(NBTTagCompound var1) {
		super.readFromNBT(var1);
		this.type = var1.getInteger("type");
	}

	public int sendEnergy(int var1) {
		EnergyTileSourceEvent var2 = new EnergyTileSourceEvent(this, var1);
		MinecraftForge.EVENT_BUS.post(var2);
		return var2.amount;
	}

	@Override
	public void updateEntity() {
		if (this.addedToEnergyNet) {
			int lv = getLightValue();
			switch (this.type) {
			case 0:
				lv -= 7;
				break;
			case 1:
				break;
			case 2:
				lv *= 2;
				break;
			}
			if (lv < 0) {
				lv = 0;
			}
			sendEnergy(lv);
		}
		updateSunVisibility();
	}

	public void updateSunVisibility() {
		this.sunIsVisible = isSunVisible(this.worldObj, this.xCoord, this.yCoord + 1, this.zCoord);
	}

	@Override
	public void validate() {
		super.validate();
		if (!this.loaded) {
			this.onLoaded();
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound var1) {
		var1.setInteger("type", this.type);
		super.writeToNBT(var1);
	}
}
