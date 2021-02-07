/*******************************************************************************
 * Copyright 2021, the Glitchfiend Team.
 * All rights reserved.
 ******************************************************************************/
package toughasnails.network;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkEvent;
import toughasnails.api.thirst.ThirstHelper;
import toughasnails.api.thirst.IThirst;
import toughasnails.core.ToughAsNails;

import java.util.function.Supplier;

public class MessageUpdateThirst
{
    public int thirstLevel;
    public float hydrationLevel;

    public MessageUpdateThirst(int thirstLevel, float hydrationLevel)
    {
        this.thirstLevel = thirstLevel;
        this.hydrationLevel = hydrationLevel;
    }

    public static void encode(MessageUpdateThirst packet, PacketBuffer buf)
    {
        buf.writeInt(packet.thirstLevel);
        buf.writeFloat(packet.hydrationLevel);
    }

    public static MessageUpdateThirst decode(PacketBuffer buf)
    {
        return new MessageUpdateThirst(buf.readInt(), buf.readFloat());
    }

    public static class Handler
    {
        public static void handle(final MessageUpdateThirst packet, Supplier<NetworkEvent.Context> context)
        {
            context.get().enqueueWork(() ->
            {
                ToughAsNails.proxy.updateThirstClient(packet.thirstLevel, packet.hydrationLevel);
            });
            context.get().setPacketHandled(true);
        }
    }
}
