package game.core.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

/**
 * A command based version of the hangman minigame, used for testing.
 * 
 * @author iichr
 */

public class MiniGameHangmanCMD {

	private String word;
	private ArrayList<Character> alreadyEntered;
	private int attempts = 0;
	private int PERMITTED_ATTEMPTS = 5;

	private Scanner input = new Scanner(System.in);
	private static Timer timer;
	private static int interval = 30;

	public MiniGameHangmanCMD() {
		word = pickWord(setDifficulty());
		alreadyEntered = new ArrayList<Character>();
		init();
	}

	/**
	 * Constructor for testing purposes only!
	 * 
	 * @param word
	 *            The word to be guessed.
	 */
	public MiniGameHangmanCMD(String word) {
		this.word = word;
		alreadyEntered = new ArrayList<Character>();
		init();
	}

	/**
	 * Main game loop with timer. Manage victory and loss conditions
	 */
	public void init() {
		displayWord();
		// set up timer, 30 seconds, 1000 milliseconds delay
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				// debugging - countdown timer
				System.out.println(setInterval());
			}
		}, 1000, 1000);

		while (!allGuessed() && !lost()) {
			inputLetter();
		}
		if (allGuessed()) {
			System.out.println("WIN!");
			timer.cancel();
		}
	}

	// countdown in intervals of 1 second.
	private static final int setInterval() {
		if (interval == 1) {
			timer.cancel();
		}
		return --interval;
	}

	private ArrayList<String> setDifficulty() {
		// TODO set PERMITTED_ATTEMPTS
		ArrayList<String> dictionary = new ArrayList<String>();
		String str;
		BufferedReader in = null;
		try {
			try {
				in = new BufferedReader(new FileReader("data/long-words.txt"));
				while ((str = in.readLine()) != null) {
					dictionary.add(str);
				}
			} finally {
				in.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dictionary;
	}

	/**
	 * Check whether a letter has already been input by the user
	 * 
	 * @param c
	 *            The letter to check
	 * @return
	 */
	private boolean checkAlreadyEntered(char c) {
		return alreadyEntered.indexOf(c) >= 0;
	}

	/**
	 * Display a word.
	 */
	private void displayWord() {
		char[] displayed = new char[word.length()];
		for (int i = 0; i < word.length(); i++) {
			char letter = word.charAt(i);
			if (checkAlreadyEntered(letter))
				displayed[i] = letter;
			else
				displayed[i] = '_';
		}
		System.out.println(new String(displayed));
		System.out.println(word);

	}

	private void inputLetter() {
		char userIn = input.next().toLowerCase().charAt(0);
		if (Character.isLetter(userIn)) {
			if (checkAlreadyEntered(userIn)) {
				System.out.println("Already entered: " + alreadyEntered.toString());
			} else {
				// user's char hasn't been encountered before
				alreadyEntered.add(userIn);
				displayWord();
				if (!isInWord(userIn)) {
					attempts++;
				}
			}
		} else {
			System.out.println("Not a valid char.");
		}
	}

	private boolean lost() {
		if (getAttempts() > PERMITTED_ATTEMPTS) {
			System.out.println("Game over.");
			timer.cancel();
			return true;
		} else {
			return false;
		}
	}

	private int getAttempts() {
		return attempts;
	}

	private ArrayList<Character> getAlreadyEntered() {
		return alreadyEntered;
	}

	/**
	 * Pick a random word given an array of strings
	 * 
	 * @param dict
	 * @return A random word.
	 */
	private String pickWord(ArrayList<String> dict) {
		Random randGenerator = new Random();
		int i = randGenerator.nextInt(dict.size());
		word = dict.get(i);

		return word;
	}

	// check if a given char is in a word
	private boolean isInWord(char c) {
		return word.indexOf(c) >= 0;
	}

	// check if the word has been guessed by using the list of characters
	// entered so far
	private boolean allGuessed() {
		return alreadyEntered.containsAll(word.chars().mapToObj(c -> (char) c).collect(Collectors.toList()));
	}
}