package com.peculiarrooms.client.renderers.blocks;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.peculiarrooms.PeculiarRooms;
import com.peculiarrooms.server.blocks.blockentity.PeculiarCrystalBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class PeculiarCrystalRenderer<T extends PeculiarCrystalBlockEntity> implements BlockEntityRenderer<PeculiarCrystalBlockEntity> {

    public PeculiarCrystalRenderer(BlockEntityRendererProvider.Context rendererDispatcherIn) {
    }

    public PeculiarCrystalRenderer() {
    }

    public static final ResourceLocation CRYSTAL = new ResourceLocation(PeculiarRooms.MODID, "textures/block/crystal.png");
    private final Vec3 v1 = new Vec3(4.5,0,-4.5);
    private final Vec3 v2 = new Vec3(-4.5,0,-4.5);
    private final Vec3 v3 = new Vec3(-4.5,0,4.5);
    private final Vec3 v4 = new Vec3(4.5,0,4.5);
    private final Vec3 v5 = new Vec3(0,8,0);
    private final Vec3 v6 = new Vec3(0,-8,0);

    private final Vec3[] FACES = {
            //Face 1
            v1,v2,v5,v1,
            //Face 2
            v2,v3,v5,v2,
            //Face 3
            v3,v4,v5,v3,
            //Face 4
            v4,v1,v5,v4,
            //Face 5
            v2,v1,v6,v2,
            //Face 6
            v3,v2,v6,v3,
            //Face 7
            v4,v3,v6,v4,
            //Face 8
            v1,v4,v6,v1
    };

    @Override
    public void render(PeculiarCrystalBlockEntity entity, float p_112308_, PoseStack stack, MultiBufferSource source, int p_112311_, int p_112312_) {
        VertexConsumer builder = source.getBuffer(RenderType.endGateway());
        stack.pushPose();
        PoseStack.Pose matrixstack$entry = stack.last();
        Matrix4f matrix4f = matrixstack$entry.pose();
        Matrix3f matrix3f = matrixstack$entry.normal();
        for (Vec3 vec : FACES) {
            Vec3 vec1 = vec.multiply(0.1 + (Math.cos(entity.ticks_existed) * 0.002),0.1f + (Math.sin(entity.ticks_existed) * 0.002),0.1 + (Math.sin(entity.ticks_existed) * 0.002))
                    .yRot((float) entity.ticks_existed /10f)
                    .xRot((float) (Math.sin(entity.ticks_existed / 2.5f) * 0.03))
                    .zRot((float) (Math.cos(entity.ticks_existed / 2.5f) * 0.02));
            builder.vertex(matrix4f, (float) vec1.x + 0.5f,
                            (float) ((float) vec1.y + 1.9f + Math.sin(entity.ticks_existed / 5f) * 0.1),
                            (float) vec1.z + 0.5f)
                    .color(1f, 1f, 1f, 1f)
                    .uv((float) vec1.normalize().x, (float) vec1.normalize().z)
                    .overlayCoords(OverlayTexture.NO_OVERLAY)
                    .uv2(15728880)
                    .normal(matrix3f, (float) -vec1.x, (float) -vec1.y, (float) -vec1.z)
                    .endVertex();
        }

        for (int i = 0; i < 6; i++) {
            for (Vec3 vec : FACES) {
                Vec3 vec2 = vec.multiply(0.03f, 0.05f, 0.03f).yRot(entity.ticks_existed / 5f).add(1d, Math.sin(entity.ticks_existed / 5f - i) * 0.1, 0d).yRot(entity.ticks_existed / 10f + (i * 1.1f));
                builder.vertex(matrix4f, (float) vec2.x + 0.5f,
                                (float) ((float) vec2.y + 1.9f + Math.sin(entity.ticks_existed / 5f) * 0.1),
                                (float) vec2.z + 0.5f)
                        .color(1f, 1f, 1f, 1f)
                        .uv((float) vec2.normalize().x, (float) vec2.normalize().z)
                        .overlayCoords(OverlayTexture.NO_OVERLAY)
                        .uv2(15728880)
                        .normal(matrix3f, (float) -vec2.x, (float) -vec2.y, (float) -vec2.z)
                        .endVertex();
            }
        }

        stack.popPose();

    }

    @Override
    public boolean shouldRender(PeculiarCrystalBlockEntity entity, Vec3 v1) {
        return true;
    }
}
