package gravestone.tileentity;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

/**
 * GraveStone mod
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public abstract class GSSpawner {

    protected TileEntity tileEntity;
    protected int delay;
    protected Entity spawnedMob;

    public GSSpawner(TileEntity tileEntity, int delay) {
        this.tileEntity = tileEntity;
        this.delay = delay;
    }

    /**
     * Update entity state.
     */
    public void updateEntity() {
        if (canSpawnMobs(tileEntity.worldObj) && tileEntity.worldObj.difficultySetting != 0 && anyPlayerInRange()) {
            if (tileEntity.worldObj.isRemote) {
                clientUpdateLogic();
            } else {
                serverUpdateLogic();
            }
        }
    }

    /**
     * Sets the delay before a new spawn.
     */
    protected void updateDelay() {
        delay = getMinDelay() + tileEntity.worldObj.rand.nextInt(getMaxDelay() - getMinDelay());
    }

    protected void setMinDelay() {
        delay = getMinDelay();
    }

    protected int getNearbyMobsCount() {
        return tileEntity.worldObj.getEntitiesWithinAABB(this.spawnedMob.getClass(), AxisAlignedBB.getAABBPool().getAABB(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord,
                tileEntity.xCoord + 1, tileEntity.yCoord + 1, tileEntity.zCoord + 1).expand(1.0D, 4.0D, getSpawnRange() * 2)).size();
    }

    protected boolean anyPlayerInRange() {
        return tileEntity.worldObj.getClosestPlayer(tileEntity.xCoord + 0.5D, tileEntity.yCoord + 0.5D, tileEntity.zCoord + 0.5D, getPlayerRange()) != null;
    }

    abstract protected boolean canSpawnMobs(World world);

    abstract protected int getPlayerRange();

    abstract protected int getSpawnRange();

    abstract protected int getMinDelay();

    abstract protected int getMaxDelay();

    abstract protected Entity getMob();

    abstract protected void clientUpdateLogic();

    abstract protected void serverUpdateLogic();
}
