package com.storage;

import java.util.ArrayList;
import core.classes.Sheep;

public class Sheeps {
	private static ArrayList<Sheep> sheeps;
	
	public Sheeps(){
		sheeps = new ArrayList<Sheep>();
	}
	
	public static ArrayList<Sheep> getSHeeps(){
		return sheeps;
	}
}
