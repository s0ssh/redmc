package sh.s0s.redmc.mixin;

import net.minecraft.client.gui.DrawContext;
import sh.s0s.redmc.RedMCClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {
    protected TitleScreenMixin(Text title) {
        super(title);
    }

    // Mapping changed between 1.21 - 1.21.4
    // New inject from: https://github.com/MeteorDevelopment/meteor-client/blob/85a9a7882209ca682c771462d6c2cc85ac20a169/src/main/java/meteordevelopment/meteorclient/mixin/TitleScreenMixin.java#L36
    // Old Inject: @Inject(at = @At("RETURN"), method = "initWidgetsNormal(II)V")
    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)I", ordinal = 0))
    private void onRenderTitleText(DrawContext context, int x, int y, float d, CallbackInfo ci) {
        Text message = Text.of("redmc v" + RedMCClient.getModVersion("redmc"));
        this.addDrawableChild(new TextWidget(20, 10, textRenderer.getWidth(message), textRenderer.fontHeight, message, textRenderer));
    }
}