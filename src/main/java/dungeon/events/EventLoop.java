package dungeon.events;

import com.google.common.eventbus.EventBus;

public class EventLoop implements Runnable {

    private static final boolean DEBUG = false;
    private static final int TICKS_PS = 10;

    private final EventBus eventBus;

    public EventLoop(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void run() {

        long initialTime = System.nanoTime();
        final double timeT = 1000000000 / TICKS_PS;
        double deltaT = 0;
        int ticks = 0;
        long timer = System.currentTimeMillis();

        while (true) {
            long currentTime = System.nanoTime();
            deltaT += (currentTime - initialTime) / timeT;
            initialTime = currentTime;

            // time for a tick
            if (deltaT >= 1) {
                if (DEBUG) {
                    System.out.println(String.format("TPS: %s", ticks));
                }
                update();
                ticks++;
                deltaT--;
            }

            // ticks per second achieved
            if (System.currentTimeMillis() - timer > 1000) {
                if (DEBUG) {
                    System.out.println(String.format("TPS: %s", ticks));
                }
                update();
                ticks = 0;
                timer += 1000;
            }
        }
    }

    private void update() {
        eventBus.post(new UpdateEvent());
    }
}
