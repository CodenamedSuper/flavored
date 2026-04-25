package com.sidden.flavored.registry;

import com.sidden.flavored.Flavored;
import com.sidden.flavored.effect.HeatEffect;
import com.sidden.flavored.effect.SugarCraveEffect;
import com.sidden.flavored.effect.SugarRushEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FlavoredEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, Flavored.MOD_ID);

    public static final Holder<MobEffect> SUGAR_RUSH = MOB_EFFECTS.register("sugar_rush",
            () -> new SugarRushEffect(MobEffectCategory.NEUTRAL, 0xffeadb)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED,
                            ResourceLocation.fromNamespaceAndPath(Flavored.MOD_ID, "sugar_rush"), 0.3f,
                            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(Attributes.ATTACK_DAMAGE,
                            ResourceLocation.fromNamespaceAndPath(Flavored.MOD_ID, "sugar_rush"), 0.3f,
                            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

    public static final Holder<MobEffect> SUGAR_CRAVE = MOB_EFFECTS.register("sugar_crave",
            () -> new SugarCraveEffect(MobEffectCategory.HARMFUL, 0x4e281d));

    public static final Holder<MobEffect> HEAT = MOB_EFFECTS.register("heat",
            () -> new HeatEffect(MobEffectCategory.NEUTRAL, 0xd72710));


    public static void init(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}