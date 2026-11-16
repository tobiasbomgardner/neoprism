package net.tobias.neo_prism.particles.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;

public class Flame extends TextureSheetParticle {
    private final SpriteSet sprites;

    private Flame(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet sprites) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);
        this.sprites = sprites;
        friction = 0.92F;
        gravity = 0.0F;
        xd = xSpeed;
        yd = ySpeed;
        zd = zSpeed;
        quadSize = 0.35F;
        lifetime = 18 + random.nextInt(10);
        setSpriteFromAge(sprites);
    }

    @Override
    public void tick() {
        super.tick();
        setSpriteFromAge(sprites);
        setAlpha(Mth.clamp((float) age / (float) lifetime, 0.0F, 1.0F));
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z,
                                     double xSpeed, double ySpeed, double zSpeed) {
            return new Flame(level, x, y, z, xSpeed, ySpeed, zSpeed, sprites);
        }
    }
}