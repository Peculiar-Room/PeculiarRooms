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
    public static final RegistryObject<Block> OFFICE_LIGHT = registerBlock("office_light", () ->
            new Block(
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.WOOL)
                            .requiresCorrectToolForDrops()
                            .strength(1.5F, 6.0F)
                            .lightLevel(d->8)
                            .sound(SoundType.CHAIN)
            ));
    public static final RegistryObject<Block> OFFICE_WALL = registerBlock("office_wall", () ->
            new Block(
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.WOOL)
                            .requiresCorrectToolForDrops()
                            .strength(1.5F, 6.0F)
                            .sound(SoundType.MANGROVE_ROOTS)
            ));
    public static final RegistryObject<Block> OFFICE_CEILING = registerBlock("office_ceiling", () ->
            new Block(
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.WOOL)
                            .requiresCorrectToolForDrops()
                            .strength(1.5F, 6.0F)
                            .sound(SoundType.MANGROVE_ROOTS)
            ));
    public static final RegistryObject<Block> CARPETING = registerBlock("carpeting", () ->
            new Block(
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.WOOL)
                            .requiresCorrectToolForDrops()
                            .strength(1.5F, 6.0F)
                            .sound(SoundType.MOSS_CARPET)
            ));
    public static final RegistryObject<Block> BEDROOM_WALL = registerBlock("bedroom_wall", () ->
            new Block(
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.WOOL)
                            .requiresCorrectToolForDrops()
                            .strength(1.5F, 6.0F)
                            .sound(SoundType.FROGLIGHT)
            ));
    public static final RegistryObject<Block> BEDROOM_WALLPAPER = registerBlock("bedroom_wallpaper", () ->
            new Block(
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.WOOL)
                            .requiresCorrectToolForDrops()
                            .strength(1.5F, 6.0F)
                            .sound(SoundType.FROGLIGHT)
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