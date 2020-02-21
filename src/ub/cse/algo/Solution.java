package ub.cse.algo;

import java.util.*;

/**
 * For use in CSE 331 HW1.
 * This is the class you will be editing and turning in. It will be timed against our implementation
 * NOTE that if you declare this file to be in a package, it will not compile in Autolab
 */

public class Solution {
	private int _nHospital;
	private int _nStudent;

    // The following represent the preference list of hospitals and students.
    // The KEY represents the integer representation of a given hospital or student.
    // The VALUE is a list, from most preferred to least.
    // For hospital, first element of the list is number of available slots
	private HashMap<Integer, ArrayList<Integer>> _hospitalList;
	private HashMap<Integer, ArrayList<Integer>> _studentList;
    
    
    /**
     * The constructor simply sets up the necessary data structures.
     * The grader for the homework will first call this class and pass the necessary variables.
     * There is no need to edit this constructor.
     * @param m Number of hospitals
     * @param n Number of students
     * @param A map linking each hospital with its preference list
     * @param A map linking each student with their preference list
     * @return
     */
	public Solution(int m, int n, HashMap<Integer, ArrayList<Integer>> hospitalList, HashMap<Integer, ArrayList<Integer>> studentList) {
		_nHospital = m;
		_nStudent = n;
		_hospitalList = hospitalList;
		_studentList = studentList;
	}
    
    /**
     * This method must be filled in by you. You may add other methods and subclasses as you see fit,
     * but they must remain within the HW1_Student_Solution class.
     * @return Your stable matches
     */
	public ArrayList<Match> getMatches() {
		Queue<Integer> freehospital = new LinkedList<>();
		ArrayList<ArrayList<Integer>> hpreferlist = new ArrayList<>();
		for (int i = 1; i <= _nHospital; i++){ // need to make a queue of all slot not for all hospital
			hpreferlist.add(_hospitalList.get(i));
			for (int slot = 0; slot < _hospitalList.get(i).get(0); slot++){
				freehospital.add(i);
			}
		}
//		System.out.println(_studentList);
//		System.out.println(hpreferlist);
		ArrayList<Match> tem = new ArrayList<>();
		ArrayList<Integer> freestudent = new ArrayList<>();
		for (int i = 1; i <= _nStudent; i++){
			freestudent.add(i);
		}
		while (freehospital.size() != 0){
			int i = freehospital.peek();
			for (int index = 1; index < hpreferlist.get(i-1).size(); index++){
				if (freestudent.contains(hpreferlist.get(i-1).get(index))){
					tem.add(new Match(i, hpreferlist.get(i-1).get(index)));
					freestudent.remove(hpreferlist.get(i-1).get(index));
					freehospital.remove(i);
					break;
				}
				else {
					ArrayList<Integer> spreferlist = _studentList.get(hpreferlist.get(i-1).get(index)); // the prefernce list for the student which i+1 hospital want now
					int hnow = 0; // the number of the hospital which the studeng pair with now
					int indexofpair = 0;
					for (Match pair: tem) {
						if (pair.student.equals(hpreferlist.get(i-1).get(index))){
						hnow = pair.hospital;
						indexofpair = tem.indexOf(pair);
						break;
						}
					}
					if (spreferlist.indexOf(i) < spreferlist.indexOf(hnow) && hnow != 0){
						tem.remove(indexofpair);
						tem.add(new Match(i, hpreferlist.get(i-1).get(index)));
//						freestudent.remove(hpreferlist.get(i-1).get(index));
						freehospital.remove(i);
						freehospital.add(hnow);
						break;
					}
				}
			}
		}
//		System.out.println(freehospital);
//		System.out.println(freestudent);
        return tem;
	}
}
