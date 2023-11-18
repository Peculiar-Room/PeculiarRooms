package com.peculiarrooms.data;

import com.peculiarrooms.PeculiarRooms;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.neoforged.neoforge.registries.RegistryObject;

import java.util.HashSet;
import java.util.Set;

import static com.peculiarrooms.server.registries.PRItemRegistry.ITEMS;

public class PRItemModelGen extends ItemModelProvider {
    public PRItemModelGen(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, PeculiarRooms.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        Set<RegistryObject<Item>> items = new HashSet<>(ITEMS.getEntries());

        DataHelper.takeAll(items, i -> i.get() instanceof BlockItem).forEach(this::blockItem);
        DataHelper.takeAll(items, i -> i.get() instanceof BlockItem && ((BlockItem) i.get()).getBlock() instanceof WallBlock).forEach(this::wallBlockItem);
        DataHelper.takeAll(items, i -> i.get() instanceof BlockItem && ((BlockItem) i.get()).getBlock() instanceof FenceBlock).forEach(this::fenceBlockItem);
        DataHelper.takeAll(items, i -> i.get() instanceof BlockItem && ((BlockItem) i.get()).getBlock() instanceof DoorBlock).forEach(this::generatedItem);
        DataHelper.takeAll(items, i -> i.get() instanceof BlockItem && ((BlockItem) i.get()).getBlock() instanceof BushBlock && !(((BlockItem) i.get()).getBlock() instanceof DoublePlantBlock)).forEach(this::blockGeneratedItem);
        DataHelper.takeAll(items, i -> i.get() instanceof BlockItem && ((BlockItem) i.get()).getBlock() instanceof DoublePlantBlock).forEach(this::generatedItem);
        DataHelper.takeAll(items, i -> i.get() instanceof SignItem).forEach(this::generatedItem);
        //withExistingParent(EXAMPLE_MOB_BABABA.get().getRegistryName().getPath(), mcLoc("item/template_spawn_egg"));
        items.forEach(this::generatedItem);
    }
    private static final ResourceLocation GENERATED = new ResourceLocation("item/generated");
    private static final ResourceLocation HANDHELD = new ResourceLocation("item/handheld");

    private void handheldItem(RegistryObject<Item> i) {
        String name = ForgeRegistries.ITEMS.getKey(i.get()).getPath();
        withExistingParent(name, HANDHELD).texture("layer0", prefix("item/" + name));
    }

    private ResourceLocation prefix(String s) {
        return new ResourceLocation(PeculiarRooms.MODID, s);
    }

    private void generatedItem(RegistryObject<Item> i) {
        String name = ForgeRegistries.ITEMS.getKey(i.get()).getPath();
        withExistingParent(name, GENERATED).texture("layer0", prefix("item/" + name));
    }

    private void blockGeneratedItem(RegistryObject<Item> i) {
        String name = ForgeRegistries.ITEMS.getKey(i.get()).getPath();
        withExistingParent(name, GENERATED).texture("layer0", prefix("block/" + name));
    }

    private void blockItem(RegistryObject<Item> i) {
        String name = ForgeRegistries.ITEMS.getKey(i.get()).getPath();
        getBuilder(name).parent(new ModelFile.UncheckedModelFile(prefix("block/" + name)));
    }

    private void wallBlockItem(RegistryObject<Item> i) {
        String name = ForgeRegistries.ITEMS.getKey(i.get()).getPath();
        String baseName = name.substring(0, name.length() - 5);
        wallInventory(name, prefix("block/" + baseName));
    }
    private void fenceBlockItem(RegistryObject<Item> i) {
        String name = ForgeRegistries.ITEMS.getKey(i.get()).getPath();
        String baseName = name.substring(0, name.length() - 5);
        fenceInventory(name, prefix("block/" + baseName));
    }
}
