package com.robdu.toughasmagic.spells;

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
import com.mna.particles.types.movers.ParticleOrbitMover;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import toughasnails.api.temperature.ITemperature;
import toughasnails.api.temperature.TemperatureHelper;

public class ComponentCooldown extends SpellEffect {

    public ComponentCooldown(ResourceLocation registryName, ResourceLocation guiIcon) {
        super(registryName, guiIcon, new AttributeValuePair(Attribute.MAGNITUDE, 1.0F, 1.0F, 5.0F, 1.0F, 5.0F));
    }

    @Override
    public ComponentApplicationResult ApplyEffect(SpellSource spellSource, SpellTarget spellTarget, IModifiedSpellPart<SpellEffect> iModifiedSpellPart, SpellContext spellContext) {
        if (spellTarget.getEntity() instanceof Player player) {
            ITemperature playertemp = TemperatureHelper.getTemperatureData(player);

            playertemp.setLevel(playertemp.getLevel().decrement((int) iModifiedSpellPart.getValue(Attribute.MAGNITUDE)));
            return ComponentApplicationResult.SUCCESS;
        }
        return ComponentApplicationResult.FAIL;
    }

    @Override
    public Affinity getAffinity() {
        return Affinity.ICE;
    }

    @Override
    public float initialComplexity() {
        return 10f;
    }

    @Override
    public int requiredXPForRote() {
        return 400;
    }

    @Override
    public void SpawnParticles(Level world, Vec3 impact_position, Vec3 normal, int age, @Nullable LivingEntity caster, @Nullable ISpellDefinition recipe) {
        if (recipe != null && age <= 5) {
            for (int i = 0; i < 360; i++) {
                if (i % 5 == 0) {
                    double x = impact_position.x - Math.sin(Math.toRadians(i));
                    double z = impact_position.z + Math.cos(Math.toRadians(i));
                    double y = impact_position.y + (float) i / 100;
                    world.addParticle(recipe.colorParticle(new MAParticleType(ParticleInit.FROST.get()).setMover(new ParticleOrbitMover(x, 0.0d, z, 1.0, 0.1, 0.1)), caster),
                            x,
                            y,
                            z,
                            1.0d,
                            0.1d,
                            0.0d);
                }
            }
        }
    }
}
