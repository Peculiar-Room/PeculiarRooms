package com.peculiarrooms.data;

import com.peculiarrooms.PeculiarRooms;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.RegistryObject;

import java.util.HashSet;
import java.util.Set;

import static com.peculiarrooms.server.registries.PRBlockRegistry.BLOCKS;
import static com.peculiarrooms.server.registries.PRItemRegistry.ITEMS;

public class PRLangGen extends LanguageProvider {
    public PRLangGen(PackOutput output, String locale) {
        super(output, PeculiarRooms.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        Set<RegistryObject<Block>> blocks = new HashSet<>(BLOCKS.getEntries());
        Set<RegistryObject<Item>> items = new HashSet<>(ITEMS.getEntries());
//        Set<RegistryObject<SoundEvent>> sounds = new HashSet<>(SOUNDS.getEntries());
//        Set<RegistryObject<Enchantment>> enchantments = new HashSet<>(ENCHANTMENTS.getEntries());
//        Set<RegistryObject<MobEffect>> effects = new HashSet<>(EFFECTS.getEntries());
//        Set<RegistryObject<EntityType<?>>> entities = new HashSet<>(ENTITIES.getEntries());

        blocks.forEach(b ->
        {
            String name = b.get().getDescriptionId().replaceFirst("block.peculiar_rooms.", "");
            name = DataHelper.toTitleCase(correctBlockItemName(name), "_").replaceAll("Of", "of");
            add(b.get().getDescriptionId(), name);
        });

        DataHelper.takeAll(items, i -> i.get() instanceof BlockItem);
        items.forEach(i ->
        {
            String name = i.get().getDescriptionId().replaceFirst("item.peculiar_rooms.", "");
            name = DataHelper.toTitleCase(correctBlockItemName(name), "_").replaceAll("Of", "of");
            add(i.get().getDescriptionId(), name);
        });
//        entities.forEach(e -> {
//            String name = DataHelper.toTitleCase(e.getId().getPath(), "_");
//            add("entity.peculiar_rooms." + e.get().getRegistryName().getPath(), name);
//        });
    }
    public String correctBlockItemName(String name) {
        if ((!name.endsWith("_bricks"))) {
            if (name.contains("bricks")) {
                name = name.replaceFirst("bricks", "brick");
            }
        }
        if (name.contains("_fence") || name.contains("_button")) {
            if (name.contains("planks")) {
                name = name.replaceFirst("_planks", "");
            }
        }
        return name;
    }
}
