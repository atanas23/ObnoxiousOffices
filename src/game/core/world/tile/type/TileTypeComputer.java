package game.core.world.tile.type;

import game.core.event.Event;
import game.core.event.Events;
import game.core.event.GameStartedEvent;
import game.core.player.Player;
import game.core.player.PlayerState;
import game.core.player.action.PlayerAction;
import game.core.player.action.PlayerActionWork;
import game.core.world.Direction;
import game.core.world.Location;
import game.core.world.tile.MetaTile;
import game.core.world.tile.Tile;
import game.core.world.tile.metadata.ComputerMetadata;
import game.util.Sets;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by samtebbs on 23/01/2017.
 */
public class TileTypeComputer extends TileTypeAction {

    static {
        Events.on(GameStartedEvent.class, TileTypeComputer::onGameStarted);
    }

    private static void onGameStarted(GameStartedEvent event) {
        int i = 0;
        List<Tile> computers = event.world.getTiles(TileTypeComputer.class);
        for (Player player : event.world.getPlayers()) {
            MetaTile computer = (MetaTile) computers.get(i++);
            computer.metadata = new ComputerMetadata(computer.location.coords, player.name);
        }
    }

    public TileTypeComputer(int id) {
        super(id, PlayerActionWork.class);
    }

    @Override
    public void onWalkOver(Player player) {

    }

    @Override
    public boolean canWalkOver() {
        return false;
    }

    @Override
    public Collection<PlayerState> getRequiredStates() {
        return Sets.asSet(PlayerState.sitting);
    }

    @Override
    protected PlayerAction getAction(Player player) {
        return new PlayerActionWork(player);
    }

    @Override
    public Collection<Tile> getTiles(Location location, Direction facing) {
        return Collections.singletonList(new MetaTile(location, this, facing, 0, new ComputerMetadata(location.coords, "")));
    }

    public static String getOwningPlayer(MetaTile computerTile) {
        Object player = computerTile.metadata.getVar(ComputerMetadata.PLAYER);
        return player == null ? "" : (String) player;
    }

}
