package com.robdu.toughasmagic.spells;

import com.mna.api.spells.parts.SpellEffect;
import com.mna.api.tools.RLoc;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "tam", bus = Mod.EventBusSubscriber.Bus.MOD)
public class SpellRegistry {

    public static SpellEffect WARMUP;
    public static SpellEffect COOLDOWN;

    @SubscribeEvent
    public static void registerComponents(final RegistryEvent.Register<SpellEffect> event) {
        event.getRegistry().register(SpellRegistry.WARMUP);
        event.getRegistry().register(SpellRegistry.COOLDOWN);
    }

    static {
        SpellRegistry.WARMUP = new ComponentWarmUp(new ResourceLocation("tam", "components/warmup"), new ResourceLocation("tam", "textures/components/warmup.png"));
        SpellRegistry.COOLDOWN = new ComponentCooldown(new ResourceLocation("tam", "components/cooldown"), new ResourceLocation("tam", "textures/components/cooldown.png"));
    }
}
