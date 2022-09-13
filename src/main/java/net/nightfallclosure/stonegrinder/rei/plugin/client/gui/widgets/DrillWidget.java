package net.nightfallclosure.stonegrinder.rei.plugin.client.gui.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import me.shedaniel.clothconfig2.api.animator.NumberAnimator;
import me.shedaniel.clothconfig2.api.animator.ValueAnimator;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.REIRuntime;
import me.shedaniel.rei.api.client.gui.widgets.WidgetWithBounds;
import net.minecraft.client.gui.Element;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.nightfallclosure.stonegrinder.StoneGrinderMod;

import java.util.Collections;
import java.util.List;

public final class DrillWidget extends WidgetWithBounds {
    private static final Identifier DRILL_TEXTURE = new Identifier(StoneGrinderMod.MOD_ID,
            "textures/gui/drill.png");
    private static final Identifier DRILL_TEXTURE_DARK_MODE = new Identifier(StoneGrinderMod.MOD_ID,
            "textures/gui/drill_dark_mode.png");

    private final Rectangle bounds;
    private final NumberAnimator<Float> darkShadowAlpha = ValueAnimator.ofFloat()
            .withConvention(() -> REIRuntime.getInstance().isDarkThemeEnabled() ? 1.0F : 0.0F,
                    ValueAnimator.typicalTransitionTime())
            .asFloat();

    public DrillWidget(Rectangle bounds) {
        this.bounds = bounds;
    }

    @Override
    public Rectangle getBounds() {
        return this.bounds;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.darkShadowAlpha.update(delta);
        renderBackground(matrices, false, 1.0F);
        if (darkShadowAlpha.value() > 0.0F) {
            renderBackground(matrices, true, this.darkShadowAlpha.value());
        }
    }

    public void renderBackground(MatrixStack matrices, boolean dark, float alpha) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
        RenderSystem.setShaderTexture(0, dark ? DRILL_TEXTURE_DARK_MODE : DRILL_TEXTURE);
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(770, 771, 1, 0);
        RenderSystem.blendFunc(770, 771);
        drawTexture(matrices, this.bounds.getX(), this.bounds.getY(), 0, 0,
                this.bounds.getWidth(), this.bounds.getHeight(), this.bounds.getWidth(), this.bounds.getHeight());
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public List<? extends Element> children() {
        return Collections.emptyList();
    }
}
