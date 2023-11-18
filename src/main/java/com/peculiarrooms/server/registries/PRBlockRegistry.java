package com.peculiarrooms.server.registries;


import com.peculiarrooms.PeculiarRooms;
import com.peculiarrooms.server.blocks.PeculiarDoorBlock;
import com.peculiarrooms.server.blocks.blockentity.PeculiarDoorBlockEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.neoforged.neoforge.registries.RegistryObject;

import java.util.function.Supplier;


public class PRBlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, PeculiarRooms.MODID);

    public static final RegistryObject<PeculiarDoorBlock> PECULIAR_DOOR = registerBlock("peculiar_door", () ->
            new PeculiarDoorBlock(BlockBehaviour.Properties.of()
            .instrument(NoteBlockInstrument.BASS)
            .strength(3.0F)
            .noOcclusion()
            .ignitedByLava()
            .pushReaction(PushReaction.DESTROY),
                    BlockSetType.WARPED
    ));

    public static final RegistryObject<Block> PECULIAR_CRYTAL = registerBlock("peculiar_crystal", () ->
            new Block(
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.DIRT)
                            .requiresCorrectToolForDrops()
                            .strength(1.5F, 6.0F)
            ));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return PRItemRegistry.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties()));
    }
}