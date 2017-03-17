package game.ai.pathFinding.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import game.ai.AIPlayer;
import game.ai.pathFinding.Pair;
import game.core.player.Player;
import game.core.world.Direction;
import game.core.world.World;
import game.ai.ruleBasedAI.WorkingMemory.activityValues;;

public class FireRulesTest {
	
	public World world;
	public AIPlayer ai;
	public Player p;
	ArrayList<Pair<Integer, Integer>> path = new ArrayList<Pair<Integer, Integer>>();


	@Test
	public void test() {
		ai.fr.fireRules();
		assertTrue(ai.wm.getHasProgressedMore() != activityValues.Unknown);
		assertTrue(ai.wm.getIsHacking() != activityValues.Unknown);
		assertTrue(ai.wm.getIsRefreshing() != activityValues.Unknown);
		assertTrue(ai.wm.getIsWorking() != activityValues.Unknown);
		assertTrue(ai.wm.getWMplayer() == p);
	}
	
	@Before
	public void createWorld() {
		// create the world
		try {
			this.world = World.load(Paths.get("data/office" + 2 + "Player.level"), 2);
			World.world = this.world;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// create normal player
		p = new Player("Player", Direction.SOUTH, world.getSpawnPoint(0));
		
		//add progress to the player so AI can start firing rules
		while (p.getProgress() < 20) {
			p.status.player.addProgress();
		}
		
		// add them to the world
		World.world.addPlayer(p);
		
		// create the ai player
		ai = new AIPlayer("Volker", Direction.SOUTH, world.getSpawnPoint(1), "e");
		//add the ai to the world
		World.world.addPlayer(ai);		
	}

}
