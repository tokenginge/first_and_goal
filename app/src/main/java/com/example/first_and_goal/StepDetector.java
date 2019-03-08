package com.example.first_and_goal;


//code and algorithms created by gadget saint
//takes updates from inbuilt accellemotor and passes through filter

public class StepDetector {

    private static final int ACCEL_RING_SIZE = 50;
    private static final int VEL_RING_SIZE = 10;
    private static final float STEP_THRESHOLD = 60f;
    private static final int STEP_DELAY_NS = 250000000;

    private int accelRingCounter = 0;
    private float[] accelRingX = new float[ACCEL_RING_SIZE];
    private float[] accelRingY = new float[ACCEL_RING_SIZE];
    private float[] accelRingZ = new float[ACCEL_RING_SIZE];
    private int velRingCounter = 0;
    private float[] velRing = new float[VEL_RING_SIZE];
    private long lasStepTimeNs = 0;
    private float oldVelocityEstimate = 0;

    private StepListener listener;

    public void registerListener(StepListener listener){
        this.listener = listener;
    }

    public void updateAccel (long timeNs, float x, float y, float z){
        float[] currentAccel = new float[3];
        currentAccel[0] = x;
        currentAccel[1] = y;
        currentAccel[2] = z;

        accelRingCounter++;
        accelRingX[accelRingCounter%ACCEL_RING_SIZE] = currentAccel[0];
        accelRingY[accelRingCounter%ACCEL_RING_SIZE] = currentAccel[1];
        accelRingZ[accelRingCounter%ACCEL_RING_SIZE]=currentAccel[2];

        float[] worldz = new float[3];
        worldz[0] = SensorFilter.sum(accelRingX)/Math.min(accelRingCounter, ACCEL_RING_SIZE);
        worldz[1] = SensorFilter.sum(accelRingY)/Math.min(accelRingCounter, ACCEL_RING_SIZE);
        worldz[2] = SensorFilter.sum(accelRingZ)/Math.min(accelRingCounter, ACCEL_RING_SIZE);
        float normalisation_factor = SensorFilter.norm(worldz);

        worldz[0] = worldz[0]/normalisation_factor;
        worldz[1] = worldz[1]/normalisation_factor;
        worldz[2] =worldz[2]/normalisation_factor;

        float currentZ = SensorFilter.dot(worldz, currentAccel) - normalisation_factor;
        velRingCounter++;
        velRing[velRingCounter%VEL_RING_SIZE] = currentZ;

        float velocityEstimate = SensorFilter.sum(velRing);

        if (velocityEstimate > STEP_THRESHOLD && oldVelocityEstimate <=STEP_THRESHOLD&&(timeNs - lasStepTimeNs > STEP_DELAY_NS)){
            listener.step(timeNs);
            lasStepTimeNs = timeNs;
        }
        oldVelocityEstimate = velocityEstimate;
    }
}
