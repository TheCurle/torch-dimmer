package uk.gemwire.dimmer;

import net.fabricmc.api.ModInitializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TrapDoorBlock;
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

            StateDefinition<?, ?> openDef = (StateDefinition<?, ?>) stateDef.get(Blocks.OAK_TRAPDOOR);

            openDef.getPossibleStates().forEach((state) -> {
                try {
                    if (state.getValue(TrapDoorBlock.OPEN))
                        lightEmission.set(state, 15);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });

            stateDef.set(Blocks.OAK_TRAPDOOR, openDef);

            LogManager.getLogger("dimmer").info("Oak Trapdoor Luminance fixed.");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
