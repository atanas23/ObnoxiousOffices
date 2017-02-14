package game.networking;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import game.core.event.ConnectionAttemptEvent;
import game.core.event.Events;
import game.core.event.PlayerCreatedEvent;
import game.core.event.PlayerInputEvent;
import game.core.player.Player;

public class Client {

	private Socket server;
	ObjectOutputStream od;

	public Client() {
		Events.on(ConnectionAttemptEvent.class, this::connectToServer);
		Events.on(PlayerCreatedEvent.class, this::setLocalPlayer);
		Events.on(PlayerInputEvent.class, this::sendDataToServer);
	}

	/**
	 * Sends Data to the server for updating the players
	 * 
	 * @param data
	 *            The data to end
	 */
	public void sendDataToServer(Object data) {
		try {
			System.out.println("sending to server: " + data);
			od.writeObject(data);
			od.flush();
			System.out.println("Data Sent!");

		} catch (IOException e) {
			System.out.println("ERROR: Can't create Object Stream to send");
			e.printStackTrace();
		}
	}

	public void connectToServer(ConnectionAttemptEvent event) {
		int port = 8942;
		String hostname = "147.188.195.112";

		try {
			this.server = new Socket(hostname, port);
			try {
				od = new ObjectOutputStream(this.server.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Client Connected To Server!");
		} catch (UnknownHostException e) {
			System.err.println("Unknown host: " + hostname);
			System.exit(1); // Give up.
		} catch (IOException e) {
			System.err.println("The server doesn't seem to be running " + e.getMessage());
			System.exit(1); // Give up.
		}
		this.sendDataToServer(event.name);
		
		new ClientListner(this.server).start();		
	}

	public void setLocalPlayer(PlayerCreatedEvent e) {
		Player.localPlayerName = e.localPlayerName;
	}

}
