package com.jj.jjmod.packets;

import com.jj.jjmod.container.ContainerInventory;
import com.jj.jjmod.init.ModCapabilities;
import com.jj.jjmod.utilities.InvLocation.InvType;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/** Packet to update the ContainerInventory on the Server. */
public class ContainerPacketServer implements IMessage {

    /** Index of the slot in the container. */
    protected int slot;
    /** The stack in the index slot. */
    protected ItemStack stack;
    /** The birth time of the decay capability if applicable. */
    protected long birthTime;

    public ContainerPacketServer() {}

    public ContainerPacketServer(int slot, ItemStack stack) {

        this.slot = slot;
        this.stack = stack;
       
        if (stack.hasCapability(ModCapabilities.CAP_DECAY, null)) {
            
            this.birthTime = stack.getCapability(ModCapabilities.CAP_DECAY,
                    null).getBirthTime();
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {

        this.slot = buf.readInt();
        this.stack = ByteBufUtils.readItemStack(buf);
        this.birthTime = buf.readLong();
    }

    @Override
    public void toBytes(ByteBuf buf) {

        buf.writeInt(this.slot);
        ByteBufUtils.writeItemStack(buf, this.stack);
        buf.writeLong(this.birthTime);
    }

    public static class Handler
            implements IMessageHandler<ContainerPacketServer, IMessage> {

        @Override
        public IMessage onMessage(ContainerPacketServer message,
                MessageContext ctx) {

            ctx.getServerHandler().playerEntity.getServer()
                    .addScheduledTask(new Runnable() {

                        @Override
                        public void run() {

                            processMessage(message, ctx);
                        }
                    });

            return null;
        }

        public void processMessage(ContainerPacketServer message,
                MessageContext ctx) {

            EntityPlayer player = ctx.getServerHandler().playerEntity;
            ItemStack stack = message.stack;
            
            if (stack.hasCapability(ModCapabilities.CAP_DECAY, null)) {
                
                stack.getCapability(ModCapabilities.CAP_DECAY, null)
                .setBirthTime(message.birthTime);
            }

            player.inventoryContainer.inventorySlots.get(message.slot)
                    .putStack(stack);
        }
    }
}
