package com.olafski.fastleafdecay;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by Olaf on 29-12-2015.
 */
public class FldHandler {
    static Random rng = new Random();

    private static int baseDecayTime = FldConfiguration.minimumDecayTime;
    private static int randomizationTime = FldConfiguration.maximumDecayTime - FldConfiguration.minimumDecayTime;

    public static void handleLeafDecay(World worldObj, BlockPos pos, Block block)
    {
        if (block.getLocalizedName().startsWith("tile.ore.berries")) { // Ignore Natura oreberries
            return;
        }

        worldObj.scheduleUpdate(pos, block, baseDecayTime + rng.nextInt(randomizationTime));

        return;
    }
}
