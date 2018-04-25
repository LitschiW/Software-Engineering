package tests;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.junit.BeforeClass;
import org.junit.Test;

import cpath.Project;
import cpath.Workpackage;

public class Aufgabe1 {
	
	private static Workpackage A01 = new Workpackage("A01", 3);
	private static Workpackage A02 = new Workpackage("A02", 4);
	private static Workpackage A03 = new Workpackage("A03", 5);
	private static Workpackage A04 = new Workpackage("A04", 4);
	private static Workpackage A05 = new Workpackage("A05", 9);
	private static Workpackage A06 = new Workpackage("A06", 4);
	private static Workpackage A07 = new Workpackage("A07", 2);
	private static Workpackage A08 = new Workpackage("A08", 4);
	private static Workpackage A09 = new Workpackage("A09", 2);
	private static Workpackage A10 = new Workpackage("A10", 3);
	private static Workpackage A11 = new Workpackage("A11", 3);
	private static Workpackage A12 = new Workpackage("A12", 2);
	private static Workpackage A13 = new Workpackage("A13", 4);
	private static Workpackage A14 = new Workpackage("A14", 7);
	private static Workpackage A15 = new Workpackage("A15", 2);
	private static Workpackage A16 = new Workpackage("A16", 4);

	@Test
	public void test() throws Exception {
		Project graph = new Project();

		graph.getStartNodes().add(A01);
		graph.getStartNodes().add(A02);
		graph.getStartNodes().add(A03);
		
		A01.addSuccessor(A04).addSuccessor(A08).addSuccessor(A12).addSuccessor(A15).addSuccessor(A16);
		A01.addSuccessor(A05).addSuccessor(A12).addSuccessor(A14);
		A02.addSuccessor(A06).addSuccessor(A09).addSuccessor(A14);
		A06.addSuccessor(A10).addSuccessor(A13).addSuccessor(A16);
		A09.addSuccessor(A15);
		A03.addSuccessor(A07).addSuccessor(A11).addSuccessor(A13);		

		graph.computeCriticalPath();
		
		ArrayList<Workpackage> nodesSorted = new  ArrayList<>(graph.getCriticalPathNodes());
		
		Collections.sort(nodesSorted,(o1, o2) -> o1.getName().compareTo(o2.getName()));
		
		for(Workpackage node : nodesSorted)
		{
			System.out.println(node.getName() + " ES: "+ node.getEarliestStart() + 
												" EF: " + node.getEarliestFinish() + 
												" LS: " + node.getLatestStart() + 
												" LF: " + node.getLatestFinish());
			
		}
	}
}
