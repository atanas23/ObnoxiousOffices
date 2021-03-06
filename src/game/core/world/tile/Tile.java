package game.core.world.tile;

import game.core.input.InteractionType;
import game.core.player.Player;
import game.core.world.Direction;
import game.core.world.Location;
import game.core.world.tile.type.TileType;

import java.io.Serializable;

/**
 * Created by samtebbs on 20/01/2017.
 */
public class Tile implements Serializable {

    public final Location location;
    public final TileType type;
    public final Direction facing;
    public final int multitileID;

    public Tile(Location location, TileType type, Direction facing, int multitileID) {
        this.location = location;
        this.type = type;
        this.facing = facing;
        this.multitileID = multitileID;
    }

    public Tile(Location location, TileType type, Direction facing) {
        this(location, type, facing, -1);
    }

    /**
     * {@link TileType#onWalkOver(Player)}
     * @param player
     */
    public void onWalkOver(Player player) {
        type.onWalkOver(player);
    }

    /**
     * {@link TileType#onInteraction(Player, Tile, InteractionType)}
     * @param player
     * @param type
     */
    public void onInteraction(Player player, InteractionType type) {
        this.type.onInteraction(player, this, type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        Tile tile = (Tile) o;

        if (multitileID != tile.multitileID) return false;
        if (!location.equals(tile.location)) return false;
        if (!type.equals(tile.type)) return false;
        return facing == tile.facing;

    }

    @Override
    public int hashCode() {
        int result = location.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + facing.hashCode();
        result = 31 * result + multitileID;
        return result;
    }

    public boolean isMultiTile() {
        return multitileID >= 0;
    }
}
