package core.classes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*Ser at ikke alt her er likt som i diagrammet.
 * Er fult villig til å forandre på ting såklart, men vil gjerne snakke om det først.*/

public class Farm implements Comparator<Sheep> {
	
	private int id;
	private String name;
	private ArrayList<Sheep> sheepList;
	boolean sammeOmigjen;	
	int sorterEtter = 1;
	/*Variabelen sorterEtter bestemmer om vi skal sortere etter default settings(1),
	 * navn(2), alder(3) eller id(4). Litt uproff måte å gjøre det på kanskje, men det er
	 * ihvertfall mine foreløpige løsning. -Tor
	 */

	public Farm(int id, String name) {
		this.setId(id);
		this.setName(name);
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	public boolean addSheep(Sheep sheep){
		return false;
	}
	
	public ArrayList<Sheep> getDefaultSheepList(){
		if(sorterEtter == 1){
			sammeOmigjen = true;
		}
		else{
			sorterEtter = 1;
		}
		//Vet ikke om man burde sortere her i det hele tatt. Hva tenker dere?
		return sheepList;
	}
	
	public ArrayList<Sheep> getSheepByName(){
		if(sorterEtter == 2){
			sammeOmigjen = true;
		}
		else{
			sorterEtter = 2;
		}
		Collections.sort(sheepList, this);
		return sheepList;		
	}

	public ArrayList<Sheep> getSheepByAge(){
		if(sorterEtter == 3){
			sammeOmigjen = true;
		}
		else{
			sorterEtter = 3;
		}
		Collections.sort(sheepList, this);
		return sheepList;		
	}
	
	public ArrayList<Sheep> getSheepById(){
		if(sorterEtter == 1){
			sammeOmigjen = true;
		}
		else{
			sorterEtter = 4;
		}
		Collections.sort(sheepList, this);
		return sheepList;		
	}


	public int compare(Sheep sheep1, Sheep sheep2) {
		if(sorterEtter == 1){
			//Vet ikke hva man eventuelt burde sortere etter her.
			return 0;
		}
		else if(sorterEtter == 2){
			return sheep1.name.compareTo(sheep2.name);
		}
		else if(sorterEtter == 3){
			return sheep1.age - sheep2.age;
		}
		else if(sorterEtter == 4){
			return sheep1.id - sheep2.id;
		}
		return 0;
	}



	
	
}
