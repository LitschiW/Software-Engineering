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
		// TODO: Implementieren Sie diese Methode, so dass nach deren Aufruf
		// alle Werte im Graph gesetzt sind und die beiden verfuegbaren Sets
		// korrekt befuellt wurden. Veraendern Sie nicht die Workpackage-Klasse!
		// Zum Testen koennen Sie die beigefuegte Test-Klasse verwenden und
		// erweitern. Beachten Sie, dass ihre Implementierung generell
		// funktionieren und nicht nur dieses eine Problem loesen soll.

		// process start nodes
		for (Workpackage startnode : startNodes) {
			startnode.setEarliestStart(0);
			startnode.setEarliestFinish(startnode.getDuration());
		}

		LinkedList<Workpackage> toCheck = new LinkedList<>(startNodes);

		int latestEarlyFinish = 0;
		while (!toCheck.isEmpty()) {
			Workpackage current = toCheck.poll();

			latestEarlyFinish = Math.max(current.getEarliestFinish(), latestEarlyFinish);

			if (current.getSuccessors().isEmpty()) {
				endNodes.add(current);
			}

			for (Workpackage next : current.getSuccessors()) {
				next.setEarliestStart(Math.max(current.getEarliestFinish(), next.getEarliestStart()));
				next.setEarliestFinish(next.getEarliestStart() + next.getDuration());
				toCheck.push(next);
			}
		}

		toCheck = new LinkedList<>(endNodes);
		for(Workpackage endNode: endNodes)
		{
			endNode.setLatestFinish(latestEarlyFinish);
			endNode.setLatestStart(endNode.getLatestFinish()-endNode.getDuration());
			endNode.setSlack(endNode.getLatestStart()-endNode.getEarliestStart());
		}
		
		while (!toCheck.isEmpty()) {
			Workpackage current = toCheck.poll();
			
			if(current.getSlack()==0) criticalPathNodes.add(current);
			
			for(Workpackage prev: current.getPredecessors()){
				prev.setLatestFinish(Math.min(current.getLatestStart(), prev.getLatestFinish()));
				prev.setLatestStart(prev.getLatestFinish()-prev.getDuration());
				prev.setSlack(prev.getLatestStart()-prev.getEarliestStart());
				toCheck.push(prev);
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
