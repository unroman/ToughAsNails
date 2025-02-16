/*******************************************************************************
 * Copyright 2021, the Glitchfiend Team.
 * All rights reserved.
 ******************************************************************************/
package toughasnails.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;
import toughasnails.api.thirst.IThirst;
import toughasnails.api.thirst.ThirstHelper;

public class MessageUpdateThirst
{
    public int thirstLevel;
    public float hydrationLevel;

    public MessageUpdateThirst(int thirstLevel, float hydrationLevel)
    {
        this.thirstLevel = thirstLevel;
        this.hydrationLevel = hydrationLevel;
    }

    public static void encode(MessageUpdateThirst packet, FriendlyByteBuf buf)
    {
        buf.writeInt(packet.thirstLevel);
        buf.writeFloat(packet.hydrationLevel);
    }

    public static MessageUpdateThirst decode(FriendlyByteBuf buf)
    {
        return new MessageUpdateThirst(buf.readInt(), buf.readFloat());
    }

    public static class Handler
    {
        public static void handle(final MessageUpdateThirst packet, CustomPayloadEvent.Context context)
        {
            context.enqueueWork(() ->
            {
                if (FMLEnvironment.dist != Dist.CLIENT)
                    return;

                updateThirst(packet.thirstLevel, packet.hydrationLevel);
            });
            context.setPacketHandled(true);
        }

        @OnlyIn(Dist.CLIENT)
        private static void updateThirst(int thirstLevel, float hydration)
        {
            Player player = Minecraft.getInstance().player;
            IThirst thirst = ThirstHelper.getThirst(player);

            thirst.setThirst(thirstLevel);
            thirst.setHydration(hydration);
        }
    }
}
