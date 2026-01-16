package team.steelcode.ready_crossbow.mixin;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RangedCrossbowAttackGoal;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;

@Mixin(RangedCrossbowAttackGoal.class)
public abstract class RangedCrossbowAttackGoalMixin<T extends Mob & CrossbowAttackMob> extends Goal {
    @Shadow @Final private T mob;

    @Inject(method = "tick", at = @At("HEAD"))
    private void metallics_arts$instantAttackIfCharged(CallbackInfo ci) {
        ItemStack itemstack = this.mob.getMainHandItem();

        if (itemstack.getItem() instanceof CrossbowItem && CrossbowItem.isCharged(itemstack)) {
            try {
                // Buscamos el campo 'crossbowState' por reflexión.
                // Nota: En un entorno real, podrías necesitar buscar tanto el nombre mapeado como el oficial,
                // pero buscar por tipo es mucho más seguro.
                Field stateField = null;
                for (Field f : RangedCrossbowAttackGoal.class.getDeclaredFields()) {
                    if (f.getType().isEnum() && f.getType().getName().contains("CrossbowState")) {
                        stateField = f;
                        break;
                    }
                }

                if (stateField != null) {
                    stateField.setAccessible(true);
                    Object currentState = stateField.get(this);

                    // Si el estado es el primero (UNCHARGED), lo saltamos
                    if (currentState.toString().equals("UNCHARGED")) {
                        Class<?> enumClass = stateField.getType();
                        for (Object constant : enumClass.getEnumConstants()) {
                            if (constant.toString().equals("READY_TO_ATTACK")) {
                                stateField.set(this, constant);
                                break;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                // Evitamos crashes, simplemente no forzamos el estado si algo falla
            }
        }
    }
}
