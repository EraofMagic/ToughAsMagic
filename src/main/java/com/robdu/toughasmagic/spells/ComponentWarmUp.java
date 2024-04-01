package com.robdu.toughasmagic.spells;

import com.github.wolfiewaffle.bon.capability.temperature.BodyTemp;
import com.github.wolfiewaffle.bon.capability.temperature.IBodyTemp;
import com.github.wolfiewaffle.bon.event.player.PlayerTickEventHandler;
import com.mna.api.affinity.Affinity;
import com.mna.api.particles.MAParticleType;
import com.mna.api.particles.ParticleInit;
import com.mna.api.spells.ComponentApplicationResult;
import com.mna.api.spells.attributes.Attribute;
import com.mna.api.spells.attributes.AttributeValuePair;
import com.mna.api.spells.base.IModifiedSpellPart;
import com.mna.api.spells.base.ISpellDefinition;
import com.mna.api.spells.parts.SpellEffect;
import com.mna.api.spells.targeting.SpellContext;
import com.mna.api.spells.targeting.SpellSource;
import com.mna.api.spells.targeting.SpellTarget;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.ModList;
import org.apache.commons.lang3.ObjectUtils;
import org.jetbrains.annotations.Nullable;
import toughasnails.api.temperature.ITemperature;
import toughasnails.api.temperature.TemperatureHelper;

public class ComponentWarmUp extends SpellEffect {
    public ComponentWarmUp(ResourceLocation registryName, ResourceLocation guiIcon) {
        super(registryName, guiIcon, new AttributeValuePair(Attribute.MAGNITUDE, 1.0F, 1.0F, 5.0F, 1.0F, 5.0F), new AttributeValuePair(Attribute.PRECISION, 0.0f, 8.0f, 1.0f, 1.0f, 10.0f));
    }

    @Override
    public ComponentApplicationResult ApplyEffect(SpellSource spellSource, SpellTarget spellTarget, IModifiedSpellPart<SpellEffect> iModifiedSpellPart, SpellContext spellContext) {
        if (spellTarget.getEntity() instanceof Player player) {
            //TODO: Copy this over to ComponentCoolDown and modify accordingly
            if (ModList.get().isLoaded("bon")) {
                LazyOptional<IBodyTemp> playertemp = player.getCapability(BodyTemp.INSTANCE, null);
                playertemp.ifPresent(data -> {
                     float currentTemp = data.getTemp();
                     if ((int) iModifiedSpellPart.getValue(Attribute.PRECISION) > 0.0f) {
                         data.setTemp(currentTemp + 20 * (int) iModifiedSpellPart.getValue(Attribute.MAGNITUDE));
                         if (data.getTemp() > 80.0f) {
                             data.setTemp(80.0f);
                         }
                     } else {
                         data.setTemp(currentTemp + 20 * (int) iModifiedSpellPart.getValue(Attribute.MAGNITUDE));
                     }
                });
            }

            ITemperature playertemp = TemperatureHelper.getTemperatureData(player);
            playertemp.setLevel(playertemp.getLevel().increment((int) iModifiedSpellPart.getValue(Attribute.MAGNITUDE)));
            return ComponentApplicationResult.SUCCESS;
        }
        return ComponentApplicationResult.FAIL;
    }

    @Override
    public Affinity getAffinity() {
        return Affinity.FIRE;
    }

    @Override
    public float initialComplexity() {
        return 10.0f;
    }

    @Override
    public int requiredXPForRote() {
        return 400;
    }

    @Override
    public void SpawnParticles(Level world, Vec3 impact_position, Vec3 normal, int age, @Nullable LivingEntity caster, @Nullable ISpellDefinition recipe) {
        if (recipe != null && age <= 5) {
            for (int i = 0; i < 360; i++) {
                if (i % 2.5 == 0) {
                    world.addParticle(recipe.colorParticle(new MAParticleType(ParticleInit.FLAME_ORBIT.get()), caster),
                            impact_position.x - Math.sin(Math.toRadians(i)),
                            impact_position.y + (double) i / 100,
                            impact_position.z + Math.cos(Math.toRadians(i)),
                            1.0d,
                            0.1d,
                            0.0d);
                }
            }
        }
    }
}
