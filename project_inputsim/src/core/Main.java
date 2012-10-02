package core;

public class Main {

	public static void main(String[] args) {
		SqlConnection a = new SqlConnection();
//		System.out.println(a.getNumberOfSheep());
		String[][] sheep = {{"1","Sauemonster","32","5","1","100"}};
		a.insertSheep(sheep);
		
		DatabasePopulator pop = new DatabasePopulator();
		pop.addSheep(4,5);
		
//		DatabasePopulator pop = new DatabasePopulator();
//		pop.insertSheep(20,1);

	}

}
