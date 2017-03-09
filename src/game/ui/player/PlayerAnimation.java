package game.ui.player;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import game.core.world.Direction;
import game.ui.interfaces.SpriteLocations;

public class PlayerAnimation {
	private Animation moveNorth, moveSouth, moveEast, moveWest, seatedNorth, seatedSouth, sleepNorth, sleepSouth, move;

	public PlayerAnimation(int colour, Direction initialDirection) throws SlickException {
		Image[] n = new Image[8];
		Image[] e = new Image[1];
		Image[] s = new Image[1];
		Image[] w = new Image[1];
		Image[] cN = new Image[1];
		Image[] cS = new Image[1];
		Image[] sN = new Image[1];
		Image[] sS = new Image[1];

		switch (colour) {
		case 0:
			n[0] = new Image(SpriteLocations.PLAYER_BLONDE_NORTH_WALK1, false, Image.FILTER_NEAREST);
			n[1] = new Image(SpriteLocations.PLAYER_BLONDE_NORTH_WALK2, false, Image.FILTER_NEAREST);
			n[2] = new Image(SpriteLocations.PLAYER_BLONDE_NORTH_WALK3, false, Image.FILTER_NEAREST);
			n[3] = new Image(SpriteLocations.PLAYER_BLONDE_NORTH_WALK4, false, Image.FILTER_NEAREST);
			n[4] = new Image(SpriteLocations.PLAYER_BLONDE_NORTH_WALK5, false, Image.FILTER_NEAREST);
			n[5] = new Image(SpriteLocations.PLAYER_BLONDE_NORTH_WALK6, false, Image.FILTER_NEAREST);
			n[6] = new Image(SpriteLocations.PLAYER_BLONDE_NORTH_WALK7, false, Image.FILTER_NEAREST);
			n[7] = new Image(SpriteLocations.PLAYER_BLONDE_NORTH_WALK8, false, Image.FILTER_NEAREST);

			e[0] = new Image(SpriteLocations.PLAYER_BLONDE_STANDING_EAST, false, Image.FILTER_NEAREST);
			s[0] = new Image(SpriteLocations.PLAYER_BLONDE_STANDING_SOUTH, false, Image.FILTER_NEAREST);
			w[0] = new Image(SpriteLocations.PLAYER_BLONDE_STANDING_WEST, false, Image.FILTER_NEAREST);
			
			cN[0] = new Image(SpriteLocations.PLAYER_BLONDE_NORTH_SEAT, false, Image.FILTER_NEAREST);
			cS[0] = new Image(SpriteLocations.PLAYER_BLONDE_STANDING_SOUTH, false, Image.FILTER_NEAREST);
			
			sN[0] = new Image(SpriteLocations.PLAYER_BLONDE_NORTH_SLEEP, false, Image.FILTER_NEAREST);
			sS[0] = new Image(SpriteLocations.PLAYER_BLONDE_SOUTH_SLEEP, false, Image.FILTER_NEAREST);
			break;
		case 1:
			n[0] = new Image(SpriteLocations.PLAYER_DARK_STANDING_NORTH, false, Image.FILTER_NEAREST);
			n[1] = new Image(SpriteLocations.PLAYER_DARK_STANDING_NORTH, false, Image.FILTER_NEAREST);
			n[2] = new Image(SpriteLocations.PLAYER_DARK_STANDING_NORTH, false, Image.FILTER_NEAREST);
			n[3] = new Image(SpriteLocations.PLAYER_DARK_STANDING_NORTH, false, Image.FILTER_NEAREST);
			n[4] = new Image(SpriteLocations.PLAYER_DARK_STANDING_NORTH, false, Image.FILTER_NEAREST);
			n[5] = new Image(SpriteLocations.PLAYER_DARK_STANDING_NORTH, false, Image.FILTER_NEAREST);
			n[6] = new Image(SpriteLocations.PLAYER_DARK_STANDING_NORTH, false, Image.FILTER_NEAREST);
			n[7] = new Image(SpriteLocations.PLAYER_DARK_STANDING_NORTH, false, Image.FILTER_NEAREST);

			e[0] = new Image(SpriteLocations.PLAYER_DARK_STANDING_EAST, false, Image.FILTER_NEAREST);
			s[0] = new Image(SpriteLocations.PLAYER_DARK_STANDING_SOUTH, false, Image.FILTER_NEAREST);
			w[0] = new Image(SpriteLocations.PLAYER_DARK_STANDING_WEST, false, Image.FILTER_NEAREST);
			
			cN[0] = new Image(SpriteLocations.PLAYER_DARK_NORTH_SEAT, false, Image.FILTER_NEAREST);
			cS[0] = new Image(SpriteLocations.PLAYER_DARK_STANDING_SOUTH, false, Image.FILTER_NEAREST);
			
			sN[0] = new Image(SpriteLocations.PLAYER_DARK_NORTH_SLEEP, false, Image.FILTER_NEAREST);
			sS[0] = new Image(SpriteLocations.PLAYER_DARK_SOUTH_SLEEP, false, Image.FILTER_NEAREST);
			break;
		case 2:
			n[0] = new Image(SpriteLocations.PLAYER_BROWN_STANDING_NORTH, false, Image.FILTER_NEAREST);
			n[1] = new Image(SpriteLocations.PLAYER_BROWN_STANDING_NORTH, false, Image.FILTER_NEAREST);
			n[2] = new Image(SpriteLocations.PLAYER_BROWN_STANDING_NORTH, false, Image.FILTER_NEAREST);
			n[3] = new Image(SpriteLocations.PLAYER_BROWN_STANDING_NORTH, false, Image.FILTER_NEAREST);
			n[4] = new Image(SpriteLocations.PLAYER_BROWN_STANDING_NORTH, false, Image.FILTER_NEAREST);
			n[5] = new Image(SpriteLocations.PLAYER_BROWN_STANDING_NORTH, false, Image.FILTER_NEAREST);
			n[6] = new Image(SpriteLocations.PLAYER_BROWN_STANDING_NORTH, false, Image.FILTER_NEAREST);
			n[7] = new Image(SpriteLocations.PLAYER_BROWN_STANDING_NORTH, false, Image.FILTER_NEAREST);

			e[0] = new Image(SpriteLocations.PLAYER_BROWN_STANDING_EAST, false, Image.FILTER_NEAREST);
			s[0] = new Image(SpriteLocations.PLAYER_BROWN_STANDING_SOUTH, false, Image.FILTER_NEAREST);
			w[0] = new Image(SpriteLocations.PLAYER_BROWN_STANDING_WEST, false, Image.FILTER_NEAREST);
			
			cN[0] = new Image(SpriteLocations.PLAYER_BROWN_NORTH_SEAT, false, Image.FILTER_NEAREST);
			cS[0] = new Image(SpriteLocations.PLAYER_BROWN_STANDING_SOUTH, false, Image.FILTER_NEAREST);
			
			sN[0] = new Image(SpriteLocations.PLAYER_BROWN_NORTH_SLEEP, false, Image.FILTER_NEAREST);
			sS[0] = new Image(SpriteLocations.PLAYER_BROWN_SOUTH_SLEEP, false, Image.FILTER_NEAREST);
			break;
		case 3:
			n[0] = new Image(SpriteLocations.PLAYER_PINK_STANDING_NORTH, false, Image.FILTER_NEAREST);
			n[1] = new Image(SpriteLocations.PLAYER_PINK_STANDING_NORTH, false, Image.FILTER_NEAREST);
			n[2] = new Image(SpriteLocations.PLAYER_PINK_STANDING_NORTH, false, Image.FILTER_NEAREST);
			n[3] = new Image(SpriteLocations.PLAYER_PINK_STANDING_NORTH, false, Image.FILTER_NEAREST);
			n[4] = new Image(SpriteLocations.PLAYER_PINK_STANDING_NORTH, false, Image.FILTER_NEAREST);
			n[5] = new Image(SpriteLocations.PLAYER_PINK_STANDING_NORTH, false, Image.FILTER_NEAREST);
			n[6] = new Image(SpriteLocations.PLAYER_PINK_STANDING_NORTH, false, Image.FILTER_NEAREST);
			n[7] = new Image(SpriteLocations.PLAYER_PINK_STANDING_NORTH, false, Image.FILTER_NEAREST);

			e[0] = new Image(SpriteLocations.PLAYER_PINK_STANDING_EAST, false, Image.FILTER_NEAREST);
			s[0] = new Image(SpriteLocations.PLAYER_PINK_STANDING_SOUTH, false, Image.FILTER_NEAREST);
			w[0] = new Image(SpriteLocations.PLAYER_PINK_STANDING_WEST, false, Image.FILTER_NEAREST);
			
			cN[0] = new Image(SpriteLocations.PLAYER_PINK_NORTH_SEAT, false, Image.FILTER_NEAREST);
			cS[0] = new Image(SpriteLocations.PLAYER_PINK_STANDING_SOUTH, false, Image.FILTER_NEAREST);
			
			sN[0] = new Image(SpriteLocations.PLAYER_PINK_NORTH_SLEEP, false, Image.FILTER_NEAREST);
			sS[0] = new Image(SpriteLocations.PLAYER_PINK_SOUTH_SLEEP, false, Image.FILTER_NEAREST);
			break;
		}

		createAnimation(n, e, s, w, cN, cS, sN, sS, initialDirection);
	}

	private void createAnimation(Image[] northAnimations, Image[] eastAnimations, Image[] southAnimations,
			Image[] westAnimations, Image [] seatedNorthAnimations, Image [] seatedSouthAnimations, Image [] sleepNorthAnimations, Image [] sleepSouthAnimations, Direction initialDirection) {
		int duration = 100;

		moveNorth = new Animation(northAnimations, duration, false);
		moveEast = new Animation(eastAnimations, duration, false);
		moveSouth = new Animation(southAnimations, duration, false);
		moveWest = new Animation(westAnimations, duration, false);
		seatedNorth = new Animation(seatedNorthAnimations, duration, false);
		seatedSouth = new Animation(seatedSouthAnimations, duration, false);
		sleepNorth = new Animation(sleepNorthAnimations, duration, false);
		sleepSouth = new Animation(sleepSouthAnimations, duration, false);
		
		turn(initialDirection);
	}

	public void drawPlayer(float x, float y, float width, float height) {
		if (move.equals(moveEast) || move.equals(moveWest)) {
			move.draw(x + 8, y, width - 16, height);
		} else {
			move.draw(x, y, width, height);
		}
	}

	public void turn(Direction facing) {
		switch (facing) {
		case NORTH:
			move = moveNorth;
			break;
		case EAST:
			move = moveEast;
			break;
		case SOUTH:
			move = moveSouth;
			break;
		case WEST:
			move = moveWest;
			break;
		}
	}

	public void seated(Direction facing) {
		switch (facing) {
		case NORTH:
			move = seatedNorth;
			break;
		case SOUTH:
			move = seatedSouth;
			break;
		}
	}
	
	public void sleeping(Direction facing) {
		switch (facing) {
		case NORTH:
			move = sleepNorth;
			break;
		case EAST:
			move = sleepNorth;
			break;
		case WEST:
			move = sleepSouth;
			break;
		case SOUTH:
			move = sleepSouth;
			break;
		}
	}
}
