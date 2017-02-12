package game.networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Paths;
import java.util.ArrayList;

import game.core.event.Event;
import game.core.event.Events;
import game.core.event.GameStartedEvent;
import game.core.event.PlayerActionAddedEvent;
import game.core.event.PlayerActionEndedEvent;
import game.core.event.PlayerAttributeChangedEvent;
import game.core.event.PlayerCreatedEvent;
import game.core.event.PlayerEffectAddedEvent;
import game.core.event.PlayerEffectEndedEvent;
import game.core.event.PlayerMovedEvent;
import game.core.event.PlayerProgressUpdateEvent;
import game.core.event.PlayerRotatedEvent;
import game.core.player.Player;
import game.core.world.Direction;
import game.core.world.Location;
import game.core.world.World;

public class ServerListener extends Thread {
	private ArrayList<Player> playerTable;
	private ArrayList<ServerListener> connections;

	private Socket socket = null;
	private ObjectInputStream is;
	private ObjectOutputStream os;
	public World world;

	public static final int NUM_PLAYERS = 4;

	public ServerListener(Socket socket, ArrayList<Player> hash, ArrayList<ServerListener> connection) {
		Events.on(PlayerRotatedEvent.class, this::sendToAllClients);
		Events.on(PlayerProgressUpdateEvent.class, this::sendToAllClients);
		Events.on(PlayerMovedEvent.class, this::sendToAllClients);
		Events.on(PlayerActionAddedEvent.class, this::sendToAllClients);
		Events.on(PlayerActionEndedEvent.class, this::sendToAllClients);
		Events.on(PlayerEffectAddedEvent.class, this::sendToAllClients);
		Events.on(PlayerEffectEndedEvent.class, this::sendToAllClients);
		Events.on(PlayerAttributeChangedEvent.class, this::sendToAllClients);
		this.playerTable = hash;
		this.socket = socket;
		this.connections = connection;
		try {
			this.world = World.load(Paths.get("data/office" + NUM_PLAYERS + "player.level"), NUM_PLAYERS);
			World.world = this.world;
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			this.is = new ObjectInputStream(this.socket.getInputStream());
			this.os = new ObjectOutputStream(this.socket.getOutputStream());
		} catch (IOException e) {
			System.out.println("Can't make Input and Output for connect ~ Droping connection");
			e.printStackTrace();
			try {
				socket.close();
			} catch (IOException e1) {
				System.out.println("Can't close socket");
				e1.printStackTrace();
			}
		}

	}

	@Override
	public void run() {
		boolean running = true;
		while (running) {
			if (this.playerTable.size() < NUM_PLAYERS) {
				try {
					String playerName = is.readObject().toString();
					this.addPlayerToGame(playerName);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				/*
				 * //Allows hard coded AI player to be added for prototype if
				 * (this.playerTable.size() == 3) { Events.trigger(new
				 * CreateAIPlayerRequest()); }
				 */
				if (this.playerTable.size() == NUM_PLAYERS) {
					for (int i = 0; i < playerTable.size(); i++) {
						Player p = playerTable.get(i);
						world.addPlayer(p);
					}
					sendToAllClients(new GameStartedEvent(world));
				}
			} else {
				try {
					Event eventObject = (Event) is.readObject();
					this.sendToAllClients(eventObject);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
	}

	/**
	 * Sends info to all clients
	 * 
	 * @param obj
	 *            The info to send
	 */
	public void sendToAllClients(Object obj) {
		System.out.println("conections.size =" + connections.size());
		for (int i = 0; i < this.connections.size(); i++) {
			this.connections.get(i).forwardInfo(obj);
		}
	}

	/**
	 * Forwards the info to one client
	 * 
	 * @param recieved
	 *            The info to send
	 */
	private void forwardInfo(Object recieved) {
		try {
			os.writeObject(recieved);
			os.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendToOne(Object recieved, String name) {
		for (int i = 0; i < this.playerTable.size(); i++) {
			if (this.playerTable.get(i).name.equals(name)) {
				this.connections.get(i).forwardInfo(recieved);
			}

		}
	}

	/**
	 * Adds a player to the game.
	 * 
	 * @param name
	 *            The name of the player to add.
	 */
	private void addPlayerToGame(String name) {
		if (!this.playerNameUsed(name)) {
			int playerNumber = playerTable.size();
			Player playerObject = new Player(name, Direction.SOUTH, world.getSpawnPoint(playerNumber));
			playerObject.setHair(playerNumber);
			this.playerTable.add(playerObject);
			
			PlayerCreatedEvent event = new PlayerCreatedEvent(name);
			Events.trigger(event);
			sendToOne(event, name);
			System.out.println("Player " + name + " added to the game!");
		} else {
			System.out.println("Player " + name + " has already been added to the game!");
		}
	}

	/**
	 * Remove a player from the game.
	 * 
	 * @param name
	 *            The name of the player to be removed.
	 */
	private void removePlayerFromGame(String name) {
		if (this.playerNameUsed(name)) {
			this.playerTable.remove(name);
			System.out.println("Player " + name + " has been removed from the game!");
		} else {
			System.out.println("Player " + name + " is not currently in the game!");
		}
	}

	/**
	 * Check to see if the player name has been used
	 * 
	 * @param name
	 *            The name to check
	 * @return Whether or not the name is being used
	 */
	private boolean playerNameUsed(String name) {
		for (int i = 0; i < this.playerTable.size(); i++) {
			if (this.playerTable.get(i).name.equals(name)) {
				return true;
			}
		}
		return false;
	}

}