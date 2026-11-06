package net.tobias.neo_prism.event;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tobias.neo_prism.Neo_Prism;
import net.tobias.neo_prism.effects.ModEffects;

@Mod.EventBusSubscriber(modid = Neo_Prism.MOD_ID, value = Dist.CLIENT)
public class ModClientEvents {
    private static final String PARALYSIS_LOCK_YAW_KEY = "neo_prism_paralysis_lock_yaw";
    private static final String PARALYSIS_LOCK_PITCH_KEY = "neo_prism_paralysis_lock_pitch";

    @SubscribeEvent
    public static void applyParalysisFovModifier(ViewportEvent.ComputeFov event) {
        Player player = Minecraft.getInstance().player;
        if (player != null && player.hasEffect(ModEffects.PARALYSIS.get())) {
            event.setFOV(event.getFOV() * 0.85D);
        }
    }

    @SubscribeEvent
    public static void lockParalysisCamera(ViewportEvent.ComputeCameraAngles event) {
        Player player = Minecraft.getInstance().player;
        if (player == null || !player.hasEffect(ModEffects.PARALYSIS.get())) {
            return;
        }

        CompoundTag persistentData = player.getPersistentData();
        if (!persistentData.contains(PARALYSIS_LOCK_YAW_KEY) || !persistentData.contains(PARALYSIS_LOCK_PITCH_KEY)) {
            return;
        }

        event.setYaw(persistentData.getFloat(PARALYSIS_LOCK_YAW_KEY));
        event.setPitch(persistentData.getFloat(PARALYSIS_LOCK_PITCH_KEY));
    }
}
