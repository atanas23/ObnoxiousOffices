package game.core.player;

import game.core.Updateable;
import game.core.event.Events;
import game.core.event.GameFinishedEvent;
import game.core.event.player.PlayerMovedEvent;
import game.core.event.player.PlayerProgressUpdateEvent;
import game.core.event.player.PlayerRotatedEvent;
import game.core.player.action.PlayerAction;
import game.core.world.Direction;
import game.core.world.Location;

import java.io.Serializable;

/**
 * Created by samtebbs on 15/01/2017.
 */
public class Player implements Updateable, Serializable {

    public final String name;
    public final PlayerStatus status = new PlayerStatus(this);
    private double progress = 0;
    private Direction facing;
    private Location location;
    private int hair = BLONDE;
    public boolean isAI = false;
    private int progressUpdates = 0;
    
    public static String localPlayerName = "";
    public static int BLONDE = 0;
    public static int BROWN = 1;
    public static int DARK = 2;
    public static int PINK = 3;
    public int timesDrunkCoffee = 0;
    public static final int PROGRESS_UPDATE_THRESHOLD = 3;

    public Player(String name, Direction facing, Location location) {
        this.name = name;
        this.facing = facing;
        this.location = location;
    }

    /**
     * Move forwards one space in the given direction
     * @param direction
     */
    public void move(Direction direction) {
        setLocation(location.forward(direction));
    }

    /**
     * Gets the current facing of the player
     * @return Direction the current facing of the player
     */
    public Direction getFacing() {
        return facing;
    }

    /**
     * Sets the player's facing
     * @param facing
     */
    public void setFacing(Direction facing) {
        this.facing = facing;
        Events.trigger(new PlayerRotatedEvent(facing, this.name), true);
    }

    /**
     * Sets the player's location
     * @param location
     */
    public void setLocation(Location location) {
        if(!this.location.equals(location)) {
            status.getActions().stream().filter(PlayerAction::cancelsOnMove).forEach(status::cancelAction);
            status.getStates().stream().filter(PlayerState::cancelsOnMove).forEach(status::removeState);
            this.location = location;
            Events.trigger(new PlayerMovedEvent(location.coords, this.name), true);
        }
    }

    public Location getLocation() {
        return location;
    }

    /**
     * Move forwards one space in the direction of the player's facing
     */
    public void moveForwards() {
        move(facing);
    }

    /**
     * Move backwards one space in the direction of the player's facing
     */
    public void moveBackwards() {
        setLocation(location.backward(facing));
    }

    /**
     * Rotate the 90 degrees player left
     */
    public void rotateLeft() {
        setFacing(facing.left());
    }

    /**
     * Rotate the 90 degrees player left
     */
    public void rotateRight() {
        setFacing(facing.right());
    }

    public void update() {
        status.update(this);
    }

    @Override
    public boolean ended() {
        return false;
    }

    /**
     * Set the player's work progress
     * @param progress
     */
    public void setProgress(double progress) {
        if(progress != this.progress) {
            if (progress < 0) progress = 0;
            this.progress = progress;
            if (this.progress >= 100) {
                onProgressDone();
                this.progress = 0;
            }
            if(++progressUpdates >= PROGRESS_UPDATE_THRESHOLD) {
                Events.trigger(new PlayerProgressUpdateEvent(this.progress, this.name), true);
                progressUpdates = 0;
            }
        }
    }

    public double getProgress() {
        return progress;
    }

    private void onProgressDone() {
        Events.trigger(new GameFinishedEvent(), true);
    }

    public void removeProgress() {
        setProgress(getProgress() - 1);
    }

    /**
     * Add the standard amount of progress (using multiplier)
     */
    public void addProgress() {
        double toAdd = 1.0;// * getProgressMultiplier();
        setProgress(progress + toAdd);
    }

    /**
     * Gets the player's progress multiplier, which depends on attributes and effects
     * @return
     */
    // TODO: Consider fatigue
    private double getProgressMultiplier() {
        return status.getAttribute(PlayerStatus.PlayerAttribute.PRODUCTIVITY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;

        Player player = (Player) o;

        return name.equals(player.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public int getHair() {
        return hair;
    }

    public void setHair(int hair) {
        this.hair = hair;
    }
}
