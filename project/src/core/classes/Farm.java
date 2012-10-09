package core.classes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class Farm implements Comparator<Sheep> {
	
	private int id;
	private String name;
	ArrayList<Sheep> sheepList;
	
	int sorterEtter = 1;
	/*Variabelen sorterEtter bestemmer om vi skal sortere etter default settings(1),
	 * navn(2), alder(3) eller id(4). Litt uproff måte å gjøre det på, men det er ihvertfall
	 * mine foreløpige løsning. -Tor
	 */
	
	public boolean addSheep(Sheep sheep){
		return false;
	}
	
	public ArrayList<Sheep> getDefaultSheepList(){
		sorterEtter = 1;
		return sheepList;
	}
	
	public ArrayList<Sheep> getSheepByName(){
		sorterEtter = 2;
		
		return sheepList;		
	}

	public ArrayList<Sheep> getSheepByAge(){
		sorterEtter = 3;
		return sheepList;		
	}
	
	public ArrayList<Sheep> getSheepById(){
		sorterEtter = 4;
		return sheepList;		
	}


	@Override
	public int compare(Sheep sheep1, Sheep sheep2) {
		if(sorterEtter == 1){
			
		}
		else if(sorterEtter == 2){
			
		}
		else if(sorterEtter == 3){
			
		}
		else if(sorterEtter == 4){
			
		}
		return 0;
	}
	
}
