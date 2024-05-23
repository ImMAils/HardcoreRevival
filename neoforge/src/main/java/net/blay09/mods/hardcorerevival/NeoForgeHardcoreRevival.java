package net.blay09.mods.hardcorerevival;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.client.BalmClient;
import net.blay09.mods.balm.neoforge.NeoForgeLoadContext;
import net.blay09.mods.balm.neoforge.provider.NeoForgeBalmProviders;
import net.blay09.mods.hardcorerevival.capability.HardcoreRevivalData;
import net.blay09.mods.hardcorerevival.capability.HardcoreRevivalDataImpl;
import net.blay09.mods.hardcorerevival.client.HardcoreRevivalClient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.DistExecutor;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@Mod(HardcoreRevival.MOD_ID)
public class NeoForgeHardcoreRevival {

    private final EntityCapability<HardcoreRevivalData, Void> hardcoreRevivalDataCapability = EntityCapability.createVoid(new ResourceLocation(HardcoreRevival.MOD_ID,
            "entity_data"), HardcoreRevivalData.class);

    public NeoForgeHardcoreRevival(IEventBus eventBus) {
        final var context = new NeoForgeLoadContext(eventBus);
        Balm.initialize(HardcoreRevival.MOD_ID, context, HardcoreRevival::initialize);
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> BalmClient.initialize(HardcoreRevival.MOD_ID, context, HardcoreRevivalClient::initialize));

        eventBus.addListener(this::registerCapabilities);

        NeoForgeBalmProviders providers = (NeoForgeBalmProviders) Balm.getProviders();
        providers.registerEntityProvider(HardcoreRevivalData.class, hardcoreRevivalDataCapability);
    }

    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerEntity(hardcoreRevivalDataCapability, EntityType.PLAYER, (player, context) -> new HardcoreRevivalDataImpl());
    }
}
