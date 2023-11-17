package com.peculiarrooms.server.registries;


import com.peculiarrooms.PeculiarRooms;
import com.peculiarrooms.server.blocks.PeculiarDoorBlock;
import com.peculiarrooms.server.blocks.blockentity.PeculiarDoorBlockEntity;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.neoforged.neoforge.registries.RegistryObject;


public class PRBlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, PeculiarRooms.MODID);

    public static final RegistryObject<PeculiarDoorBlock> PECULIAR_DOOR = BLOCKS.register("peculiar_door", () ->
            new PeculiarDoorBlock(BlockBehaviour.Properties.of()
            .instrument(NoteBlockInstrument.BASS)
            .strength(3.0F)
            .noOcclusion()
            .ignitedByLava()
            .pushReaction(PushReaction.DESTROY),
                    BlockSetType.WARPED
    ));

}