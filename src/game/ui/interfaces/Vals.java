package game.ui.interfaces;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import org.newdawn.slick.UnicodeFont;

/**
 * An interface for all shared values in the UI.
 *
 */
public interface Vals {
	
	public static final String GAME_NAME = "DevWars";

	// SCREEN SIZES
	Dimension screenRes = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int SCREEN_WIDTH = (int) screenRes.getWidth();
	public static final int SCREEN_HEIGHT = (int) screenRes.getHeight();

	// STATE IDs
	public static final int INTRO_STATE = 999;
	public static final int EXIT = -1;
	public static final int MENU_STATE = 0;
	public static final int CHARACTER_SELECT_STATE = 1;
	public static final int OPTIONS_STATE = 2;
	public static final int RULES_STATE = 3;
	public static final int PLAY_STATE = 4;
	public static final int PAUSE_STATE = 5;
	public static final int PLAY_TEST_STATE = 6;
	public static final int OPTIONS_STATE_PAGE2 = 7;

	// BUTTON SIZES
	public static final float BUTTON_WIDTH = SCREEN_WIDTH / 7;
	public static final float BUTTON_HEIGHT = SCREEN_HEIGHT / 14;

	// TEXT FIELD SIZES
	public static final int TFIELD_WIDTH = SCREEN_WIDTH / 5;
	public static final int TFIELD_HEIGHT = SCREEN_HEIGHT / 15;
	public static final int TFIELD_ALIGN_CENTRE_W = (SCREEN_WIDTH - TFIELD_WIDTH) / 2;

	// FONTS
	public static final UnicodeFont FONT_MAIN = new UnicodeFont(new Font("Arial", Font.BOLD, 20));
	public static final UnicodeFont FONT_RULES = new UnicodeFont(new Font("Arial", Font.BOLD, 18));
	public static final UnicodeFont FONT_HEADING1 = new UnicodeFont(new Font("Arial", Font.BOLD, 30));
	public static final UnicodeFont FONT_PLAY = new UnicodeFont(new Font("Arial", Font.BOLD, 16));

	// RULES Alignment
	public static final int RULES_SECT_LEFT_W = SCREEN_WIDTH / 4;
	public static final int RULES_SECT_RIGHT_W = SCREEN_WIDTH - RULES_SECT_LEFT_W;

	// The coords of the screen centre for a Menu Button
	public static final float BUTTON_ALIGN_CENTRE_W = SCREEN_WIDTH / 2 - BUTTON_WIDTH / 2;
	public static final float BUTTON_ALIGN_CENTRE_H = SCREEN_HEIGHT / 2 - BUTTON_HEIGHT / 2;

	// OPTIONS - KEYBOARD BINDINGS Alignment
	public static final float OPTIONS_CONTR_X = BUTTON_ALIGN_CENTRE_W + SCREEN_WIDTH / 7;
	public static final float OPTIONS_CONTR_Y = BUTTON_ALIGN_CENTRE_H - SCREEN_HEIGHT / 4;

	// Input checking
	public static final long INPUT_INTERVAL = 100;
}
