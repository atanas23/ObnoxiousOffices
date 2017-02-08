package game.ui.states;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import game.ui.buttons.MenuButton;
import game.ui.interfaces.ImageLocations;
import game.ui.interfaces.Vals;

/**
 * The rules page accessible from the main menu. Provides the user with a guide
 * on how to play.
 * 
 * @author iichr
 *
 */
public class Rules extends BasicGameState {
	private MenuButton backButton;
	private String mouseCoords;
	private String gameTitle;
	private String rules;

	// testing with some arbitrary sprites
	// TODO fetch from the SpriteLocations interface once finalised.
	private final String chairLoc = "/res/sprites/tiles/chair.png";
	private final String deskLoc = "/res/sprites/tiles/desk.png";
	private final String coffeeLoc = "/res/sprites/tiles/coffee.png";
	private final String pcLoc = "/res/sprites/tiles/pc.png";
	private Image chair, desk, coffee, pc;
	

	public Rules(int state) {

	}

	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException {
		Image back = new Image(ImageLocations.BACK);
		Image backR = new Image(ImageLocations.BACK_ROLLOVER);
		backButton = new MenuButton(10.0f, 10.0f, 40, 40, back, backR);

		// Fonts - temp
		// only load those actually needed.
		ArrayList<UnicodeFont> fontList = new ArrayList<UnicodeFont>();
		fontList.add(Vals.FONT_RULES);
		fontList.add(Vals.FONT_HEADING1);

		for (UnicodeFont font : fontList) {
			font.addAsciiGlyphs();
			font.getEffects().add(new ColorEffect());
			font.loadGlyphs();
		}

		// Object images
		chair = new Image(chairLoc).getScaledCopy(50, 50);
		desk = new Image(deskLoc).getScaledCopy(50, 50);
		coffee = new Image(coffeeLoc).getScaledCopy(50, 50);
		pc = new Image(pcLoc).getScaledCopy(50, 50);

		gameTitle = "DevWars";
		rules = " <insert catchy intro here> \n"
				+ "The main game actions are:"
				+ "\n\n"
				+ "Work - increases project completion rate. That’s what they hired you for after all, better deliver!"
				+ "\n\n"
				+ "Hack - you need to interact with the other players’ computers to hinder their progress. Failure to engage in\n"
				+ "the office politics will make your own progress plateau, there is only so much one could achieve alone…\n"
				+ "That’s not all, hacking is not that easy, you will be faced with challenges along the way. It also goes\n "
				+ "both waysso be wary of notifications that someone’s on your computer doing monkey business."
				+ "\n\n"
				+ "Coffee break - feeling tired of the incessant wörk wörk wörk? Why not head for a coffee then, your office\n"
				+ "provides coffee machines, so you can go back to working reinvigorated. What could possibly go wrong?"
				+ "\n\n"
				+ "Naps - under new regulations from Brussels (?!!) the modern 21st century office ought to provide its employees\n"
				+ "with a place for relaxation. Use that to reduce your fatigue, without negative consequences. I promise."
				+ "\n\n"
				+ "Share ideas (player-to-player interaction) - provides a boost to productivity to you and whom you’re chatting away with.";
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
		// debugging
		g.drawString(mouseCoords, 10, 50);

		// title
		g.setFont(Vals.FONT_HEADING1);
		g.drawString(gameTitle, (Vals.SCREEN_WIDTH - Vals.FONT_MAIN.getWidth(gameTitle)) / 2, 30);

		g.setFont(Vals.FONT_RULES);

		g.drawImage(chair, Vals.RULES_SECT_LEFT_W / 2 + chair.getWidth() / 2, 100);
		g.drawImage(desk, Vals.RULES_SECT_LEFT_W / 2 + desk.getWidth() / 2, 235);
		g.drawImage(coffee, Vals.RULES_SECT_LEFT_W / 2 + desk.getWidth() / 2, 510);
		g.drawImage(pc, Vals.RULES_SECT_LEFT_W / 2 + desk.getWidth() / 2, 715);
		// ^ + instead of - to push them closer to the text.
		// TODO add all corresponding to the main actions listed above. + proper alignment

		// add back button
		backButton.render();

		// do -50 of the first image rendered to account for xline height.
		drawRules(g, rules, 100);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		int mouseX = Mouse.getX();
		int mouseY = gc.getHeight() - Mouse.getY();
		mouseCoords = mouseX + " ," + mouseY;

		backButton.update(gc, game, mouseX, mouseY, Vals.MENU_STATE);
	}

	@Override
	public int getID() {
		return Vals.RULES_STATE;
	}

	/**
	 * Draw a long string, entering a new line every time the \n character is
	 * encountered. Each line will be left-aligned according to the
	 * RULES_SECT_RIGHT_W variable.
	 * 
	 * @param g
	 *            The graphics
	 * @param s
	 *            The string to be displayed
	 * @param x
	 *            The x coordinate of the string
	 * @param y
	 *            The y coordinate of the string.
	 */
	public void drawRules(Graphics g, String s, int y) {
		for (String line : s.split("\n"))
			// acts as error handling
			if (Vals.FONT_RULES.getWidth(line) > Vals.RULES_SECT_RIGHT_W) {
				g.drawString("CONSIDER SHORTENING THIS LINE: " + line.substring(0, 20), Vals.SCREEN_WIDTH - Vals.RULES_SECT_RIGHT_W,
						y += Vals.FONT_RULES.getLineHeight() * 2);
			} else {
				g.drawString(line, Vals.SCREEN_WIDTH - Vals.RULES_SECT_RIGHT_W,
						y += Vals.FONT_RULES.getLineHeight() * 1.7);
			}
	}

}