package game.core.world.tile.type;

import game.core.player.Player;
import game.core.player.PlayerState;
import game.core.world.World;
import game.core.world.tile.Tile;

/**
 * Created by samtebbs on 27/01/2017.
 */
public class TileTypeChair extends TileType {

    public TileTypeChair(int id) {
        super(id);
    }

    @Override
    public void onWalkOver(Player player) {

    }

    @Override
    public boolean canWalkOver() {
        return false;
    }

    @Override
    public void onInteraction(Player player, Tile tile) {
        if(!World.world.playerAt(tile.location)) {
            player.status.addState(PlayerState.sitting);
            player.setLocation(tile.location);
            player.setFacing(tile.facing);
        }
    }

}
