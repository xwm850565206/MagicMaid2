package com.xwm.magicmaid2.client.render.util;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

@SideOnly(Side.CLIENT)
public class LightningBoltRenderer
{
    private Collection<Line> segments;

    private int totalTime;

    private int currentTime;

    private float lineWidth;

    public LightningBoltRenderer(
            int time,
            Collection < Line > segments,
            float lineWidth) {
        this.totalTime = time;
        this.segments = segments;
        this.currentTime = time;
        this.lineWidth = lineWidth;
    }

    public void update(int delta) {
        currentTime -= delta;
        if (currentTime <= 0) currentTime = 0;
    }

    public void render() {
        float alpha = (float) currentTime / (float) totalTime;
//        alpha = (float) Math.sqrt(alpha);
//        alpha = 1f;

        /***** Begin OpenGl *****/
        GlStateManager.pushMatrix();
        // Notice that the internal update will make these values all
        // simultaneously drop to 0, making it black, thus ending the
        // effect.
        GlStateManager.color(alpha, alpha, alpha, alpha);
        GlStateManager.glLineWidth(lineWidth);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);
        for (Line segment: segments) {
            bufferBuilder.pos(segment.x1, segment.y1, segment.z1).endVertex();
            bufferBuilder.pos(segment.x2, segment.y2, segment.z2).endVertex();
        }

        tessellator.draw();
        GlStateManager.popMatrix();
        /***** End OpenGl *****/
    }

    public boolean isDone() {
        return currentTime <= 0;
    }

    /**
     * Generates a lightning bolt.
     *
     * @param p0 - the starting vector.
     * @param p1 - the Ending Vector
     * @param duration - the duration of the effect
     */
    public static LightningBoltRenderer generateLightingBolt(Vector3f p0, Vector3f p1, int duration) {
        Collection < Line > segments = new ArrayList< Line >();

        segments.add(new Line(p0, p1));

        float offset = 1f;

        double probability = 0.3; // probability to generate new partitions

        float height = 1f;

        Random random = new Random();

        int partitions = 4;

        for (int i = 0; i < partitions; i++) {

            Collection < Line > newSegments = new ArrayList < Line > ();

            for (Line segment: segments) {

                Vector3f midPoint = (Vector3f) Vector3f.add(segment.getStart(), segment.getEnd(), null).scale(0.5f);

                Vector3f perpendicular = new Vector3f(midPoint.x + 90, midPoint.y + 90, midPoint.z + 90);

                perpendicular.normalise().scale(

                        random.nextFloat() * offset - (offset / 2));

                midPoint = Vector3f.add(midPoint, perpendicular, midPoint);

                if (random.nextFloat() < probability) {

                    // generate new branch
                    Vector3f direction = Vector3f.sub(midPoint, segment.getStart(), null);

                    direction = Vector3f.add(direction, new Vector3f(random.nextFloat() * height, random.nextFloat() * height, random.nextFloat() * height), direction);

                    newSegments.add(new Line(midPoint, Vector3f.add(midPoint, direction, null)));
                }

                newSegments.add(new Line(segment.getStart(), midPoint));

                newSegments.add(new Line(midPoint, segment.getEnd()));
            }

            segments = newSegments;

            offset /= 2;
        }

        return new LightningBoltRenderer(duration, segments, 2);
    }

    public Collection<Line> getSegments() {
        return segments;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public float getLineWidth() {
        return lineWidth;
    }

    static class Line
    {
        private float x1, y1, z1;
        private float x2, y2, z2;

        public Line()
        {

        }

        public Line(float x1, float y1, float z1, float x2, float y2, float z2) {
            this.x1 = x1;
            this.y1 = y1;
            this.z1 = z1;
            this.x2 = x2;
            this.y2 = y2;
            this.z2 = z2;
        }

        public Line(Vector3f v1, Vector3f v2)
        {
            this(v1.getX(), v1.getY(), v1.getZ(), v2.getX(), v2.getY(), v2.getZ());
        }

        public Vector3f getStart()
        {
            return new Vector3f(x1, y1, z1);
        }

        public Vector3f getEnd()
        {
            return new Vector3f(x2, y2, z2);
        }
    }
}
