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
		int action;
		int i;
		int pathDistance;
		Node targetGhosts;
		Node gatorLocation = game.getAttacker().getLocation();
		Attacker gator = game.getAttacker(); //gets the attacker (the gator)
		List<Defender> defenders = game.getDefenders(); //list of the 4 defenders in the game
		List<Node> pillList = game.getPillList(); //calls a list of the pills in the maze
		List<Node> powerPillNodes = game.getCurMaze().getPowerPillNodes(); //gets the locations of the power pill nodes in the maze
		List<Node> ghostTargets = new LinkedList<>();

		Node target = gator.getTargetNode(pillList, true); //sets the target to be the list of pills
		action = gator.getNextDir(target, true); //the attacker's action is set to go after the pills


		for (i = 0; i < 4; ++i) {
			if (defenders.get(i).isVulnerable()) { //checks if a defender is vulnerable
				ghostTargets.add(defenders.get(i).getLocation()); //locations of the vulnerable defenders are added to the ghostTargets list
				targetGhosts = gator.getTargetNode(ghostTargets, true ); //targetGhosts is set using the ghostTargets list
				action = gator.getNextDir(targetGhosts, true); // action is set to have attacker go after the closest vulnerable ghost
			}
			else {
				target = defenders.get(i).getLocation(); //target will get the defender's locations
				pathDistance = gatorLocation.getPathDistance(target); //pathDistance finds the # distance b/w gator and defenders
				if (pathDistance < 5) { //checks if the distance b/w the gator and a defender is less than 5
					action = gator.getNextDir(target, false); //has the gator run away from the defender
				}
			}
		}
		
		return action; //returns the action so the gator will move

	}
}