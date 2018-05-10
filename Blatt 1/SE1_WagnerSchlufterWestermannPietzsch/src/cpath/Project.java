package cpath;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/**
 * Stellt ein Projekt dar, das aus Arbeitspaketen besteht. Im Prinzip nichts
 * anderes als ein Graph mit Arbeitspaketen als Knoten. Das Projekt kennt nur
 * die Startknoten, diese kennen jeweils ihren Nachfolger.
 * 
 * @author Lan Schlufter Cayapa, 3222681, st149553@stud.uni-stuttgart.de
 * @author Lion Wagner, 3231355, st148345@stud.uni-stuttgart.de
 * @author Jannis Westermann, 3288735, st153444@stud.uni-stuttgart.de
 * @author Lukas Pietzsch, 3227178, st151191@stud.uni-stuttgart.de
 */
public class Project {

	private List<Workpackage> startNodes = new ArrayList<Workpackage>();
	private List<Workpackage> endNodes = new ArrayList<Workpackage>();
	private Set<Workpackage> criticalPathNodes = new HashSet<Workpackage>();
	private Set<Workpackage> allNodes = new HashSet<Workpackage>();
	private Stack<Workpackage> currentNodes = new Stack<Workpackage>();

	public List<Workpackage> getStartNodes() {
		return startNodes;
	}

	public void setStartNodes(List<Workpackage> startNodes) {
		this.startNodes = startNodes;
	}

	public Set<Workpackage> getCriticalPathNodes() {
		return criticalPathNodes;
	}

	public Set<Workpackage> getAllNodes() {
		return allNodes;
	}

	public void computeCriticalPath() {
		resetLists();

		// process start nodes
		for (Workpackage startnode : startNodes) {
			startnode.setEarliestStart(0);
			startnode.setEarliestFinish(startnode.getDuration());
		}

		LinkedList<Workpackage> toCheck = new LinkedList<>(startNodes);

		int latestEarlyFinish = 0; // stor the latest early finishing
		while (!toCheck.isEmpty()) {
			Workpackage current = toCheck.poll();
			latestEarlyFinish = Math.max(current.getEarliestFinish(), latestEarlyFinish); // update latestEarly Finish

			if (current.getSuccessors().isEmpty()) { // if a node has no successors it will be considered as an end node
				endNodes.add(current);
				continue;
			}

			for (Workpackage next : current.getSuccessors()) {
				// Update Values of the Successors
				next.setEarliestStart(Math.max(current.getEarliestFinish(), next.getEarliestStart()));
				next.setEarliestFinish(next.getEarliestStart() + next.getDuration());
				toCheck.push(next);// add successor to be checked next
			}
		}

		// clear Queue and put all end nodes in it for the backtracing.
		toCheck = new LinkedList<>(endNodes);

		// Plug-in the latestEarlyFinish variable and update the latest start and slack
		// of the end nodes
		for (Workpackage endNode : endNodes) {
			endNode.setLatestFinish(latestEarlyFinish);
			endNode.setLatestStart(endNode.getLatestFinish() - endNode.getDuration());
			endNode.setSlack(endNode.getLatestStart() - endNode.getEarliestStart());
		}

		while (!toCheck.isEmpty()) {
			Workpackage current = toCheck.poll();

			if (current.getSlack() == 0) { //if a node has a slack of 0 it will be considered part of the critical path
				criticalPathNodes.add(current);
			}

			//update the values of all predecessors
			for (Workpackage prev : current.getPredecessors()) {
				prev.setLatestFinish(Math.min(current.getLatestStart(), prev.getLatestFinish()));
				prev.setLatestStart(prev.getLatestFinish() - prev.getDuration());
				prev.setSlack(prev.getLatestStart() - prev.getEarliestStart());
				toCheck.push(prev); //add the next node to be checked
			}
		}
	}

	private void resetLists() {
		currentNodes.clear();
		endNodes.clear();
		criticalPathNodes.clear();
		allNodes.clear();
	}
}
