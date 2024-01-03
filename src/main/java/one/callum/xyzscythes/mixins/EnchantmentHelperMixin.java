package one.callum.xyzscythes.mixins;

import com.mojang.logging.LogUtils;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.SweepingEdgeEnchantment;
import one.callum.xyzscythes.item.custom.ScytheItem;
import one.callum.xyzscythes.util.ModTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.world.item.enchantment.EnchantmentHelper.getEnchantmentLevel;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {

    @Inject(method = "getSweepingDamageRatio", at = @At("RETURN"), cancellable = true)
    private static void getSweepingDamageRatio(LivingEntity pEntity, CallbackInfoReturnable<Float> cir) {
        if (pEntity.getMainHandItem().is(ModTags.ITEMS.scythes))  {
            Tier tier = ((ScytheItem) pEntity.getMainHandItem().getItem()).getTier();
            int i = 0;
            if (tier == Tiers.IRON) i = 1;
            else if (tier == Tiers.DIAMOND) i = 2;
            else if (tier == Tiers.NETHERITE) i = 3;

            cir.setReturnValue(SweepingEdgeEnchantment.getSweepingDamageRatio(i));
        } else cir.cancel();
    }
}
