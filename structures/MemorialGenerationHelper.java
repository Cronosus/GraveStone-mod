package gravestone.structures;

import gravestone.config.GraveStoneConfig;
import gravestone.tileentity.TileEntityGSMemorial;
import java.util.Random;
import net.minecraft.world.World;

/**
 * GraveStone mod
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class MemorialGenerationHelper {

    private MemorialGenerationHelper() {
    }

    /**
     * Place memorial block
     *
     * @param component Component instance
     * @param world World object
     * @param random
     * @param x X coord
     * @param y Y coord
     * @param z Z coord
     * @param memorialMeta Memorial metadata
     * @param memorialType Memorial type
     */
    public static void placeMemorial(ComponentGraveStone component, World world, Random random, int x, int y, int z, int memorialMeta, byte memorialType) {
        component.placeBlockAtCurrentPosition(world, GraveStoneConfig.memorialID, memorialMeta, x, y, z, component.getBoundingBox());
        TileEntityGSMemorial tileEntity = (TileEntityGSMemorial) world.getBlockTileEntity(component.getXWithOffset(x, z), component.getYWithOffset(y), component.getZWithOffset(x, z));

        if (tileEntity != null) {
            tileEntity.setGraveType(memorialType);
            tileEntity.setMemorialContent(random);
        }
    }
}
