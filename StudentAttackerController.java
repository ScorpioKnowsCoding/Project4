package edu.ufl.cise.cs1.controllers;

import game.controllers.AttackerController;
import game.models.*;

import java.util.LinkedList;
import java.util.List;


public final class StudentAttackerController implements AttackerController
{
	public void init(Game game) { }
	
	public void shutdown(Game game) { }

	public int update(Game game,long timeDue)
	{
		int action = 0;
		int i;
		int pathDistance;
		Node target;
		Node targetGhosts;
		Node defenderLocation;
		Node gatorLocation = game.getAttacker().getLocation(); //gets location of the gator
		Attacker gator = game.getAttacker(); //gets the attacker (the gator)
		List<Defender> defenders = game.getDefenders(); //list of the 4 defenders in the game
		Node defenderStartLocations = game.getCurMaze().getInitialDefendersPosition(); //gets the initial position of the defenders
		List<Node> pillList = game.getPillList(); //gets a list of the pills in the maze
		List<Node> ghostTargets = new LinkedList<>(); //list that stores the locations of the vulnerable ghosts

		target = gator.getTargetNode(pillList, true); //sets target to go after the pill list
		action = gator.getNextDir(target, true); //gator goes after pills


		for (i = 0; i < 4; ++i) {
			if (defenders.get(i).getLocation() == defenderStartLocations) { //if defender's are in the lair (starting location)
				target = gator.getTargetNode(pillList, true); //gator goes after pill list
				action = gator.getNextDir(target, true); //gator goes after pill list
			}
			if (defenders.get(i).isVulnerable()) { //if defenders are vulnerable
				ghostTargets.add(defenders.get(i).getLocation());
				targetGhosts = gator.getTargetNode(ghostTargets, true ); //finds closest ghost
				action = gator.getNextDir(targetGhosts, true); //gator goes after the closest vulnerable ghost
			}
			else { //else if defenders are not vulnerable
				defenderLocation = defenders.get(i).getLocation();
				pathDistance = gatorLocation.getPathDistance(defenderLocation); //finds distance b/w gator and ghosts
				if (pathDistance < 5) { //if distance is less than 5 nodes away
					action = gator.getNextDir(defenderLocation, false); //gator runs away from defender
				}
			}
		}

		return action; //returns the action so the gator will move

	}
}