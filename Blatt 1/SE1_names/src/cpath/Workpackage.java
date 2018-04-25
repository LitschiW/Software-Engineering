package cpath;

import java.util.ArrayList;
import java.util.List;

/**
 * Ein einzelnes Arbeitspaket. Es kennt alle Abhaengigkeiten: sowohl seine
 * Vorgaenger, als auch seine Nachfolger.
 */
public class Workpackage {

	private String name;
	private int duration;

	private int slack = 0;
	private int earliestStart = 0;
	private int latestStart = Integer.MAX_VALUE;
	private int earliestFinish = 0;
	private int latestFinish = Integer.MAX_VALUE;

	private List<Workpackage> predecessors = new ArrayList<Workpackage>();
	private List<Workpackage> successors = new ArrayList<Workpackage>();

	public Workpackage(String name, int duration) {
		if (name == null) {
			throw new IllegalArgumentException();
		}
		if (duration <= 0) {
			throw new IllegalArgumentException();
		}
		this.name = name;
		this.duration = duration;
	}

	/**
	 * Gibt die Daten mehrerer Arbeitspakete auf der Konsole aus. Kann zum
	 * Debuggen verwendet werden.
	 * 
	 * @param workpackages
	 *            mehrere Arbeitspakete
	 */
	public static void printInfos(List<Workpackage> workpackages) {
		for (Workpackage workpackage : workpackages) {
			workpackage.printInfo();
		}
	}

	/**
	 * Gibt die Daten dieses Arbeitspakets auf der Konsole aus. Kann zum
	 * Debuggen verwendet werden.
	 */
	public void printInfo() {
		System.out.println("< -------- >");
		System.out.println("Name: " + this.getName());
		System.out.println("Slack: " + slack);
		System.out.println("Earliest: " + earliestStart + " -> " + earliestFinish);
		System.out.println("Latest: " + latestStart + " <- " + latestFinish);
	}

	public List<Workpackage> getPredecessors() {
		return predecessors;
	}

	public void setPredecessors(List<Workpackage> predecessors) {
		this.predecessors = predecessors;
	}

	public List<Workpackage> getSuccessors() {
		return successors;
	}

	public void setSuccessors(List<Workpackage> successors) {
		this.successors = successors;
	}

	public int getSlack() {
		return slack;
	}

	public void setSlack(int slack) {
		this.slack = slack;
	}

	public int getEarliestStart() {
		return earliestStart;
	}

	public void setEarliestStart(int earliestStart) {
		this.earliestStart = earliestStart;
	}

	public int getLatestStart() {
		return latestStart;
	}

	public void setLatestStart(int latestStart) {
		this.latestStart = latestStart;
	}

	public int getEarliestFinish() {
		return earliestFinish;
	}

	public void setEarliestFinish(int earliestFinish) {
		this.earliestFinish = earliestFinish;
	}

	public int getLatestFinish() {
		return latestFinish;
	}

	public void setLatestFinish(int latestFinish) {
		this.latestFinish = latestFinish;
	}

	public String getName() {
		return name;
	}

	public int getDuration() {
		return duration;
	}

	public Workpackage addSuccessor(Workpackage successor) {
		this.successors.add(successor);
		successor.predecessors.add(this);
		return successor;
	}
}
