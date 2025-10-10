package com.mygdx.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * UIBuilder - Fluent API for creating UI elements
 * Makes it easy to construct complex UI hierarchies
 */
public class UIBuilder {
    private final UIService uiService;

    public UIBuilder(UIService uiService) {
        this.uiService = uiService;
    }

    /**
     * Create a new panel
     */
    public PanelBuilder panel(float x, float y, float width, float height) {
        return new PanelBuilder(uiService, x, y, width, height);
    }

    /**
     * Create a new label
     */
    public LabelBuilder label(String text, float x, float y) {
        return new LabelBuilder(uiService, text, x, y);
    }

    /**
     * Create a new progress bar
     */
    public ProgressBarBuilder progressBar(float x, float y, float width, float height, float maxValue) {
        return new ProgressBarBuilder(uiService, x, y, width, height, maxValue);
    }

    /**
     * Create a new image
     */
    public ImageBuilder image(TextureRegion texture, float x, float y) {
        return new ImageBuilder(uiService, texture, x, y);
    }

    // Builder classes for fluent API
    public static class PanelBuilder {
        private final UIService uiService;
        private final UIPanel panel;

        PanelBuilder(UIService uiService, float x, float y, float width, float height) {
            this.uiService = uiService;
            this.panel = new UIPanel(x, y, width, height);
        }

        public PanelBuilder background(Color color) {
            panel.withBackground(color);
            return this;
        }

        public PanelBuilder border(Color color, float width) {
            panel.withBorder(color, width);
            return this;
        }

        public PanelBuilder padding(float padding) {
            panel.withPadding(padding);
            return this;
        }

        public PanelBuilder anchor(UIAnchor anchor) {
            panel.setAnchor(anchor);
            return this;
        }

        public PanelBuilder alpha(float alpha) {
            panel.setAlpha(alpha);
            return this;
        }

        public PanelBuilder add(UIComponent component) {
            panel.add(component);
            return this;
        }

        public UIPanel build() {
            uiService.addComponent(panel);
            return panel;
        }

        public UIPanel build(String name) {
            uiService.addComponent(name, panel);
            return panel;
        }
    }

    public static class LabelBuilder {
        private final UIService uiService;
        private final UILabel label;

        LabelBuilder(UIService uiService, String text, float x, float y) {
            this.uiService = uiService;
            this.label = new UILabel(text, x, y, uiService.getDefaultFont());
        }

        public LabelBuilder color(Color color) {
            label.withColor(color);
            return this;
        }

        public LabelBuilder shadow(boolean shadow) {
            label.withShadow(shadow);
            return this;
        }

        public LabelBuilder shadowColor(Color color) {
            label.withShadowColor(color);
            return this;
        }

        public LabelBuilder alpha(float alpha) {
            label.setAlpha(alpha);
            return this;
        }

        public UILabel build() {
            uiService.addComponent(label);
            return label;
        }

        public UILabel build(String name) {
            uiService.addComponent(name, label);
            return label;
        }
    }

    public static class ProgressBarBuilder {
        private final UIService uiService;
        private final UIProgressBar progressBar;

        ProgressBarBuilder(UIService uiService, float x, float y, float width, float height, float maxValue) {
            this.uiService = uiService;
            this.progressBar = new UIProgressBar(x, y, width, height, maxValue);
        }

        public ProgressBarBuilder fillColor(Color color) {
            progressBar.withFillColor(color);
            return this;
        }

        public ProgressBarBuilder backgroundColor(Color color) {
            progressBar.withBackgroundColor(color);
            return this;
        }

        public ProgressBarBuilder borderColor(Color color) {
            progressBar.withBorderColor(color);
            return this;
        }

        public ProgressBarBuilder text(String prefix) {
            progressBar.withText(uiService.getDefaultFont(), prefix);
            return this;
        }

        public ProgressBarBuilder animation(boolean animate, float speed) {
            progressBar.withAnimation(animate, speed);
            return this;
        }

        public ProgressBarBuilder alpha(float alpha) {
            progressBar.setAlpha(alpha);
            return this;
        }

        public UIProgressBar build() {
            uiService.addComponent(progressBar);
            return progressBar;
        }

        public UIProgressBar build(String name) {
            uiService.addComponent(name, progressBar);
            return progressBar;
        }
    }

    public static class ImageBuilder {
        private final UIService uiService;
        private final UIImage image;

        ImageBuilder(UIService uiService, TextureRegion texture, float x, float y) {
            this.uiService = uiService;
            this.image = new UIImage(texture, x, y);
        }

        public ImageBuilder size(float width, float height) {
            image.setSize(width, height);
            return this;
        }

        public ImageBuilder tint(Color color) {
            image.withTint(color);
            return this;
        }

        public ImageBuilder alpha(float alpha) {
            image.setAlpha(alpha);
            return this;
        }

        public UIImage build() {
            uiService.addComponent(image);
            return image;
        }

        public UIImage build(String name) {
            uiService.addComponent(name, image);
            return image;
        }
    }
}


