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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import toughasnails.api.temperature.ITemperature;
import toughasnails.api.temperature.TemperatureHelper;

public class ComponentWarmUp extends SpellEffect {

    private static double x = 0;
    private static double y = 0;
    private static double z = 0;

    public ComponentWarmUp(ResourceLocation registryName, ResourceLocation guiIcon, AttributeValuePair... attributeValuePairs) {
        super(registryName, guiIcon, new AttributeValuePair(Attribute.MAGNITUDE, 1.0F, 1.0F, 5.0F, 1.0F, 5.0F));
    }

    @Override
    public ComponentApplicationResult ApplyEffect(SpellSource spellSource, SpellTarget spellTarget, IModifiedSpellPart<SpellEffect> iModifiedSpellPart, SpellContext spellContext) {
        if (spellTarget.getEntity() instanceof Player player) {

            x = player.getX();
            y = player.getY();
            z = player.getZ();

            ITemperature playertemp = TemperatureHelper.getTemperatureData(player);
            playertemp.setLevel(playertemp.getLevel().increment((int)iModifiedSpellPart.getValue(Attribute.MAGNITUDE)));
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
    public boolean canBeOnRandomStaff() {
        return super.canBeOnRandomStaff();
    }

    @Override
    public void SpawnParticles(Level world, Vec3 impact_position, Vec3 normal, int age, @Nullable LivingEntity caster, @Nullable ISpellDefinition recipe) {
        for(int i = 0; i < 360; i++){
            if(i % 2.5 == 0 && age <= 5) {
                world.addParticle(recipe.colorParticle(new MAParticleType(ParticleInit.FLAME.get()),caster),
                        x+-Math.sin(i*Math.PI/180)*1,
                        y + (float)i / 100,
                        z+Math.cos(i*Math.PI/180)*1,
                        0.0d,
                        0.1d,
                        0.0d);
            }
        }

    }
}
