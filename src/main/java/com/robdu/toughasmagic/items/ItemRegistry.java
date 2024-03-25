package com.robdu.toughasmagic.items;

import com.robdu.toughasmagic.ToughAsMagic;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ToughAsMagic.MOD_ID);

    public static final RegistryObject<DebugItem> DEBUGITEM = ITEMS.register("debugitem", () -> new DebugItem(new Item.Properties()));
}
