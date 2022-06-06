package uk.gemwire.dimmer;

import net.fabricmc.api.ModInitializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.StateDefinition;
import org.apache.logging.log4j.LogManager;

import java.lang.reflect.Field;

public class DimmerMain implements ModInitializer {
    @Override
    public void onInitialize() {
        try {
            Field stateDef = Block.class.getDeclaredField("field_10647");
            stateDef.setAccessible(true);

            Field lightEmission = BlockBehaviour.BlockStateBase.class.getDeclaredField("field_23167");
            lightEmission.setAccessible(true);

            StateDefinition<?, ?> standingDef = (StateDefinition<?, ?>) stateDef.get(Blocks.REDSTONE_TORCH);
            StateDefinition<?, ?> wallDef = (StateDefinition<?, ?>) stateDef.get(Blocks.REDSTONE_WALL_TORCH);

            standingDef.getPossibleStates().forEach((state) -> {
                try {
                    lightEmission.set(state, 0);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });

            wallDef.getPossibleStates().forEach((state) -> {
                try {
                    lightEmission.set(state, 0);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });

            stateDef.set(Blocks.REDSTONE_TORCH, standingDef);
            stateDef.set(Blocks.REDSTONE_WALL_TORCH, wallDef);

            LogManager.getLogger("dimmer").info("Restone Torch Luminance fixed.");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
