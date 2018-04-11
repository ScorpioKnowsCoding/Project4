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


		Attacker gator = game.getAttacker();
		List<Defender> defenders = game.getDefenders();

		List<Node> pillList = game.getPillList();
		List<Node> powerPillNodes = game.getCurMaze().getPowerPillNodes();
		List<Node> ghostTargets = new LinkedList<>();

		Node target = gator.getTargetNode(pillList, true);
		action = gator.getNextDir(target, true);


		for (i = 0; i < 4; ++i) {
			if (defenders.get(i).isVulnerable()) {
				ghostTargets.add(defenders.get(i).getLocation());
				targetGhosts = gator.getTargetNode(ghostTargets, true );
				action = gator.getNextDir(targetGhosts, true);
			}
			else {
				target = defenders.get(i).getLocation();
				pathDistance = gatorLocation.getPathDistance(target);
				if (pathDistance < 5) {
					action = gator.getNextDir(target, false);
				}
			}
		}


		return action;

	}
}