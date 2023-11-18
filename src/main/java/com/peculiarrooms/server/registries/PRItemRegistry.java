package com.peculiarrooms.server.registries;

import com.peculiarrooms.PeculiarRooms;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.neoforged.neoforge.registries.RegistryObject;

public class PRItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PeculiarRooms.MODID);
    public static final RegistryObject<Item> PECULIAR_KEY = ITEMS.register("peculiar_key", () -> new Item(new Item.Properties()));

    public static void creativeTabBuild(CreativeModeTab.ItemDisplayParameters params, CreativeModeTab.Output output) {
        ITEMS.getEntries().forEach((i) -> {
                output.accept(i.get().asItem());
            }
        );
    }
}