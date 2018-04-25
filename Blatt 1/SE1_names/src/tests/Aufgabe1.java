package tests;

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

		A01.addSuccessor(A04).addSuccessor(A05);
		A02.addSuccessor(A06);
		A03.addSuccessor(A07);
		A04.addSuccessor(A08);
		A06.addSuccessor(A09).addSuccessor(A10);
		A07.addSuccessor(A11);
		A05.addSuccessor(A12);
		A08.addSuccessor(A12);		
		A10.addSuccessor(A13);
		A11.addSuccessor(A13);	
		A09.addSuccessor(A14);
		A12.addSuccessor(A14);
		A09.addSuccessor(A15);
		A12.addSuccessor(A15);
		A13.addSuccessor(A16);
		A15.addSuccessor(A16);
		

		graph.computeCriticalPath();
		for(Workpackage node : graph.getCriticalPathNodes())
		{
			System.out.println(node.getName());
		}
		@SuppressWarnings("unused")
		int i =0;
	}
}
