package team.steelcode.ready_crossbow.mixin;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;


@Mixin(Mob.class)
public abstract class AutoChargeCrossbowMixin {

    @Inject(method = "finalizeSpawn", at = @At("RETURN"))
    private void metallics_arts$chargeCrossbowOnSpawn(
            ServerLevelAccessor level,
            DifficultyInstance difficulty,
            EntitySpawnReason spawnReason,
            @Nullable SpawnGroupData spawnGroupData,
            CallbackInfoReturnable<SpawnGroupData> cir) {

        Mob mob = (Mob) (Object) this;

        // Verificamos si es un mob que puede usar ballestas
        if (mob instanceof CrossbowAttackMob) {
            ItemStack itemstack = mob.getMainHandItem();

            // Si tiene una ballesta en la mano principal, la cargamos
            if (itemstack.getItem() instanceof CrossbowItem) {
                // Añadimos un proyectil por defecto (flecha) al componente de la ballesta
                // Esto hace que la ballesta se considere "cargada" visualmente y funcionalmente
                itemstack.set(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.of(new ItemStack(net.minecraft.world.item.Items.ARROW)));
            }
        }
    }
}