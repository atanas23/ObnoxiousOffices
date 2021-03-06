package game.core.sync;

import game.core.event.CreateAIPlayerRequest;
import game.ai.AIPlayer;
import game.core.event.CreateAIPlayerRequest;
import game.core.event.Events;
import game.core.event.GameStartedEvent;
import game.core.event.chat.ChatMessageCreatedEvent;
import game.core.event.player.PlayerInputEvent;
import game.core.input.InputType;
import game.core.input.InputTypeInteraction;
import game.core.input.InputTypeMovement;
import game.core.input.InteractionType;
import game.core.minigame.MiniGame;
import game.core.player.Player;
import game.core.player.state.PlayerState;
import game.core.world.Direction;
import game.core.world.Location;
import game.core.world.World;
import game.core.world.tile.Tile;
import game.core.world.tile.type.TileType;
import game.util.Sets;
import game.networking.ServerListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by samtebbs on 12/02/2017.
 */
public class ServerSync {

	static Map<Class<? extends InteractionType>, Set<TileType>> interactionMap = new HashMap<Class<? extends InteractionType>, Set<TileType>>() {{
		put(InteractionType.InteractionTypeHack.class, Sets.asSet(TileType.COMPUTER));
		put(InteractionType.InteractionTypeWork.class, Sets.asSet(TileType.COMPUTER));
		put(InteractionType.InteractionTypeOther.class, Sets.asSet(TileType.CHAIR, TileType.COFFEE_MACHINE, TileType.SOFA));
	}};
	public static void init() {
		Events.on(PlayerInputEvent.class, ServerSync::onPlayerInput);
		Events.on(ChatMessageCreatedEvent.class, ServerSync::onChatMessageCreated);
		Events.on(GameStartedEvent.class, ServerSync::onGameStarted);
		Events.on(CreateAIPlayerRequest.class, ServerSync::addAIPLayer);
	}

    public static void onGameStarted(GameStartedEvent event) {
        event.world.getPlayers().forEach(player -> {
            // Make player sit on chair and face correct way
            player.setFacing(player.getLocation().getTile().facing);
            player.status.addState(PlayerState.sitting);
        });
    }

	public static void onChatMessageCreated(ChatMessageCreatedEvent event) {
		Events.trigger(event.toChatReceivedEvent());
	}

    public static void onPlayerInput(PlayerInputEvent event) {
        MiniGame playerMiniGame = World.world.getMiniGame(event.playerName);
        if(playerMiniGame != null) playerMiniGame.onInput(event);
        else {
            InputType type = event.inputType;
            Player player = World.world.getPlayer(event.playerName);
            if (type.isMovement()) processMovement(type, player);
            else processInteraction((InputTypeInteraction) type, player);
        }
    }
	private static void addAIPLayer(CreateAIPlayerRequest event) {
		ServerListener sl = (ServerListener) event.serverListener;
		int aiNumber = event.aiNumber;
		AIPlayer ai = new AIPlayer("Volker_" + aiNumber, Direction.SOUTH,
				World.world.getSpawnPoint(World.world.getMaxPlayers() - ServerListener.NUM_AI_PLAYERS + aiNumber), "h");
		sl.addAIToGame(ai);
	}

    public static void processInteraction(InputTypeInteraction type, Player player) {
	    if (!player.status.canInteract()) return;
        Tile targetTile = player.getLocation().forward(player.getFacing()).getTile();
        if(targetTile != null) {
            boolean valid = interactionMap.get(type.type.getClass()).stream().anyMatch(t -> t.equals(targetTile.type));
            if(valid) targetTile.onInteraction(player, type.type);
        }
    }

    public static void processMovement(InputType type, Player player) {
	    if(!player.status.canMove()) return;
        Direction direction = null;
        Location loc = player.getLocation();
        if(type instanceof InputTypeMovement) {
            switch (((InputTypeMovement) type).type) {
                case MOVE_UP:
                    direction = Direction.NORTH;
                    break;
                case MOVE_DOWN:
                    direction = Direction.SOUTH;
                    break;
                case MOVE_LEFT:
                    direction = Direction.WEST;
                    break;
                case MOVE_RIGHT:
                    direction = Direction.EAST;
                    break;
            }
        }
        player.setFacing(direction);
        Location forwards = loc.forward(direction);
        Tile tile;
        if(forwards.checkBounds() && (tile = forwards.getTile()).type.canWalkOver()) {
            player.setLocation(forwards);
            tile.onWalkOver(player);
        }
    }

}
