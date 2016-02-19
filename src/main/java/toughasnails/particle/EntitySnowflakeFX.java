package toughasnails.particle;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import toughasnails.core.ClientProxy;

public class EntitySnowflakeFX extends EntityFX
{
    
    public EntitySnowflakeFX(World world, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn)
    {
        this(world, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, 1.0F);
    }
    
    public EntitySnowflakeFX(World world, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, float par14)
    {
        super(world, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
        
        this.particleTextureIndexX = 7;
        this.particleTextureIndexY = 0;
        
        this.motionX *= 0.10000000149011612D;
        this.motionY *= 0.10000000149011612D;
        this.motionZ *= 0.10000000149011612D;
        this.motionX += xSpeedIn;
        this.motionY += ySpeedIn;
        this.motionZ += zSpeedIn;
        this.particleScale *= 0.75F;
        this.particleScale *= par14;
        this.particleMaxAge = (int)((8.0D / (Math.random() * 0.8D + 0.2D)) * 8);
        this.particleMaxAge = (int)((float)this.particleMaxAge * par14);
        this.particleAge = world.rand.nextInt(2);
        this.particleAlpha = 1.0F;
        this.particleGravity = 0.02F;
        this.noClip = false;
    }
    
    @Override
    public int getFXLayer()
    {
        return 2;
    }
    
    @Override
    public void renderParticle(WorldRenderer renderer, Entity entity, float partialTicks, float rotX, float rotXZ, float rotZ, float rotYZ, float rotXY)
    {
        
        // EffectRenderer will by default bind the vanilla particles texture, override with our own
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(ClientProxy.particleTexturesLocation);
        
        float scaleMultiplier = ((float)this.particleAge + partialTicks) / (float)this.particleMaxAge * 32.0F;
        scaleMultiplier = MathHelper.clamp_float(scaleMultiplier, 0.0F, 1.0F);
        this.particleScale = this.particleScale * scaleMultiplier;
        
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 1);

        super.renderParticle(renderer, entity, partialTicks, rotX, rotXZ, rotZ, rotYZ, rotXY);

        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);

    }
    
    @Override
    public void onUpdate()
    {
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;

        if (particleAge++ >= particleMaxAge)
        {
            this.setDead();
        }

        this.particleTextureIndexX = 7 - particleAge * 8 / particleMaxAge;
        this.moveEntity(motionX, motionY, motionZ);

        if (posY == prevPosY)
        {
            motionX *= 1.1D;
            motionZ *= 1.1D;
        }

        motionX *= 0.9599999785423279D;
        motionY *= 0.9599999785423279D;
        motionZ *= 0.9599999785423279D;

        if (onGround)
        {
            motionX *= 0.699999988079071D;
            motionZ *= 0.699999988079071D;
        }
    }
    
    
}