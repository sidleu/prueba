package com.reconosersdk.reconosersdk.optimizedCamera;

/** Describing a frame info. */
public class FrameMetadata {

    private final int width;
    private final int height;
    private final int rotation;
    private final int cameraFacing;
    private final int angle;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getRotation() {
        return rotation;
    }

    public int getCameraFacing() {
        return cameraFacing;
    }

    public int getAngle() {
        return angle;
    }

    private FrameMetadata(int width, int height, int rotation, int facing, int angle) {
        this.width = width;
        this.height = height;
        this.rotation = rotation;
        cameraFacing = facing;
        this.angle = angle;
    }

    /** Builder of {@link FrameMetadata}. */
    public static class Builder {

        private int width;
        private int height;
        private int rotation;
        private int cameraFacing;
        private int angleCamera;

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder setRotation(int rotation) {
            this.rotation = rotation;
            return this;
        }

        public Builder setCameraFacing(int facing) {
            cameraFacing = facing;
            return this;
        }

        public Builder setAngle(int angle) {
            angleCamera = angle;
            return this;
        }

        public FrameMetadata build() {
            return new FrameMetadata(width, height, rotation, cameraFacing, angleCamera);
        }
    }
}
