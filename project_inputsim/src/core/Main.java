package core;

public class Main {

	public static void main(String[] args) {
//		SqlConnection a = new SqlConnection();
//		System.out.println(a.getNumberOfSheep());
//		String[][] sheep = {{"1","Sauemonster","32","5","1","100"}};
//		a.insertSheep(sheep);
		
//		DatabasePopulator pop = new DatabasePopulator();
//		pop.deleteSheep();
//		pop.addSheep(5,1);
//		a.deleteSheepStatus();
		
//		pop.addFarms(3);
		SheepStatusSim sim = new SheepStatusSim(6000);
		sim.init();
		
		
//		DatabasePopulator pop = new DatabasePopulator();
//		pop.insertSheep(20,1);

	}

}
