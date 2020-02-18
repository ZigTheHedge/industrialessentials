package com.cwelth.industrialessentials.networking;

import com.cwelth.industrialessentials.tileentities.AnvilTE;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class AnvilSync {
    public int numHits;
    public BlockPos tePos = new BlockPos(0, 0, 0);

    public AnvilSync(){}

    public AnvilSync(int numHits, BlockPos tePos)
    {
        this.numHits = numHits;
        this.tePos = tePos;
    }

    public AnvilSync(PacketBuffer buf) {
        numHits = buf.readInt();
        tePos = buf.readBlockPos();
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeInt(numHits);
        buf.writeBlockPos(tePos);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            AnvilTE te = (AnvilTE)mc.world.getTileEntity(tePos);
            if(te != null)
            {
                te.setCurrentHits(numHits);
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
