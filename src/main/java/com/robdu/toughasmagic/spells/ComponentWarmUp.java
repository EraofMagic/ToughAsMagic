package com.robdu.toughasmagic.spells;

import com.mna.api.affinity.Affinity;
import com.mna.api.spells.ComponentApplicationResult;
import com.mna.api.spells.attributes.Attribute;
import com.mna.api.spells.attributes.AttributeValuePair;
import com.mna.api.spells.base.IModifiedSpellPart;
import com.mna.api.spells.parts.SpellEffect;
import com.mna.api.spells.targeting.SpellContext;
import com.mna.api.spells.targeting.SpellSource;
import com.mna.api.spells.targeting.SpellTarget;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import toughasnails.api.temperature.ITemperature;
import toughasnails.api.temperature.TemperatureHelper;

public class ComponentWarmUp extends SpellEffect {
    public ComponentWarmUp(ResourceLocation registryName, ResourceLocation guiIcon, AttributeValuePair... attributeValuePairs) {
        super(registryName, guiIcon, new AttributeValuePair(Attribute.MAGNITUDE, 1.0F, 1.0F, 5.0F, 1.0F, 5.0F));
    }

    @Override
    public ComponentApplicationResult ApplyEffect(SpellSource spellSource, SpellTarget spellTarget, IModifiedSpellPart<SpellEffect> iModifiedSpellPart, SpellContext spellContext) {
        if (spellTarget.getEntity() instanceof Player player) {
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
}
