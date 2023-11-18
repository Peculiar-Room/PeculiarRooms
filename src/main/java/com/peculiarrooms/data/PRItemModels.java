package com.peculiarrooms.data;

import com.peculiarrooms.PeculiarRooms;
import com.peculiarrooms.server.registries.PRItemRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.neoforged.neoforge.registries.RegistryObject;

import java.util.HashSet;
import java.util.Set;

import static com.peculiarrooms.server.registries.PRItemRegistry.ITEMS;

public class PRItemModels extends ItemModelProvider {
    public PRItemModels(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, PeculiarRooms.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(PRItemRegistry.PECULIAR_KEY.getId().getPath());
        Set<RegistryObject<Item>> items = new HashSet<>(ITEMS.getEntries());
        DataHelper.takeAll(items, i -> i.get() instanceof BlockItem).forEach(this::blockItem);
    }
    private ItemModelBuilder simpleItem(String path) {
        return withExistingParent(path,
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(PeculiarRooms.MODID,"item/" + path));
    }
    private ItemModelBuilder handheldItem(String path) {
        return withExistingParent(path,
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(PeculiarRooms.MODID,"item/" + path));
    }

    private void blockItem(RegistryObject<Item> i) {
        String name = ForgeRegistries.ITEMS.getKey(i.get()).getPath();
        getBuilder(name).parent(new ModelFile.UncheckedModelFile(prefix("block/" + name)));
    }

    private ResourceLocation prefix(String s) {
        return new ResourceLocation(PeculiarRooms.MODID, s);
    }

}
