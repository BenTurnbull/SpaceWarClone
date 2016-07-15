package dungeon;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import dungeon.events.RenderEvent;
import dungeon.events.UpdateEvent;

import java.util.Random;

public class DungeonModel {

    private final EventBus eventBus;
    private final int numberOfBlocks;
    private final int[][] gridModel;

    public DungeonModel(EventBus eventBus, int numberOfBlocks) {
        eventBus.register(this);

        this.eventBus = eventBus;
        this.numberOfBlocks = numberOfBlocks;
        this.gridModel = new int[numberOfBlocks][numberOfBlocks];

        // starting location
        final Random random = new Random();
        gridModel[random.nextInt(numberOfBlocks)][random.nextInt(numberOfBlocks)] = 1;
    }

    @Subscribe
    public void update(UpdateEvent update) {

        // TODO: calculate FoW and send render event

        eventBus.post(new RenderEvent());
    }
}
