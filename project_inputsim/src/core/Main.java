package core;

public class Main {

	public static void main(String[] args) {
		SqlConnection a = new SqlConnection();
//		System.out.println(a.getNumberOfSheep());
		String[][] sheep = {{"1","Sauemonster","32","5","1","100"}};
		a.insertSheep(sheep);

	}

}
