package com.peculiarrooms.server.registries;

import com.peculiarrooms.PeculiarRooms;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.neoforged.neoforge.registries.RegistryObject;

public class PRItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PeculiarRooms.MODID);

    public static final RegistryObject<Item> PECULIAR_DOOR = ITEMS.register("peculiar_door", () -> new DoubleHighBlockItem(PRBlockRegistry.PECULIAR_DOOR.get(), new Item.Properties()));
}