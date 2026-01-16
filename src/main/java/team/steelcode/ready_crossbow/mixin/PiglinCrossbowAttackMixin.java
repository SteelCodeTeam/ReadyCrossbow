package team.steelcode.ready_crossbow.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.CrossbowAttack;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;

@Mixin(CrossbowAttack.class)
public abstract class PiglinCrossbowAttackMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    private void metallics_arts$piglinInstantAttack(ServerLevel level, Mob entity, long gameTime, CallbackInfo ci) {
        // Los Piglins son los principales usuarios de este Behavior
        if (entity instanceof Piglin piglin) {
            ItemStack itemstack = piglin.getMainHandItem();

            if (itemstack.getItem() instanceof CrossbowItem && CrossbowItem.isCharged(itemstack)) {
                try {
                    Field stateField = null;
                    for (Field f : CrossbowAttack.class.getDeclaredFields()) {
                        if (f.getType().isEnum() && f.getType().getName().contains("CrossbowState")) {
                            stateField = f;
                            break;
                        }
                    }

                    if (stateField != null) {
                        stateField.setAccessible(true);
                        Object currentState = stateField.get(this);

                        if (currentState != null && currentState.toString().equals("UNCHARGED")) {
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
                    // Silencioso
                }
            }
        }
    }
}
