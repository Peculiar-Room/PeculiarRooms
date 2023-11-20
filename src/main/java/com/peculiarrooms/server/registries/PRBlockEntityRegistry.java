package com.peculiarrooms.server.registries;

import com.peculiarrooms.PeculiarRooms;
import com.peculiarrooms.server.blocks.blockentity.PeculiarCrystalBlockEntity;
import com.peculiarrooms.server.blocks.blockentity.PeculiarDoorBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.neoforged.neoforge.registries.RegistryObject;

public class PRBlockEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, PeculiarRooms.MODID);

    public static final RegistryObject<BlockEntityType<PeculiarDoorBlockEntity>> PECULIAR_DOOR = BLOCK_ENTITIES.register("peculiar_room", () -> BlockEntityType.Builder.of(PeculiarDoorBlockEntity::new, PRBlockRegistry.PECULIAR_DOOR.get()).build(null));
    public static final RegistryObject<BlockEntityType<PeculiarCrystalBlockEntity>> PECULIAR_CRYSTAL = BLOCK_ENTITIES.register("peculiar_crystal", () -> BlockEntityType.Builder.of(PeculiarCrystalBlockEntity::new, PRBlockRegistry.PECULIAR_CRYSTAL.get()).build(null));

}