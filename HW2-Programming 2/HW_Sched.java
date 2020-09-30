import java.util.*;

class Assignment implements Comparator<Assignment>{
	int number;
	int weight;
	int deadline;
	
	
	protected Assignment() {
	}
	
	protected Assignment(int number, int weight, int deadline) {
		this.number = number;
		this.weight = weight;
		this.deadline = deadline;
	}
	
	
	
	/**
	 * This method is used to sort to compare assignment objects for sorting. 
	 * Return -1 if a1 > a2
	 * Return 1 if a1 < a2
	 * Return 0 if a1 = a2 
	 */
	@Override
	public int compare(Assignment a1, Assignment a2) {

		// TODO Implement this
		if(a1.weight>a2.weight){
			return -1;
		}
		else if(a1.weight<a2.weight){
			return 1;
		}
		else if(a1.deadline<a2.deadline){
			return -1;
		}
		else if(a1.deadline>a2.deadline){
			return 1;
		}

		return 0;
	}
}

public class HW_Sched {
	ArrayList<Assignment> Assignments = new ArrayList<Assignment>();
	int m;
	int lastDeadline = 0;
	
	protected HW_Sched(int[] weights, int[] deadlines, int size) {
		for (int i=0; i<size; i++) {
			Assignment homework = new Assignment(i, weights[i], deadlines[i]);
			this.Assignments.add(homework);
			if (homework.deadline > lastDeadline) {
				lastDeadline = homework.deadline;
			}
		}
		m =size;
	}
	
	
	/**
	 * 
	 * @return Array where output[i] corresponds to the assignment 
	 * that will be done at time i.
	 */
	public int[] SelectAssignments() {
		//TODO Implement this
		
		//Sort assignments
		//Order will depend on how compare function is implemented
		Collections.sort(Assignments, new Assignment());
		for(Assignment assignment: Assignments){
			System.out.println("Ass: " + assignment.number);
		}

		// If schedule[i] has a value -1, it indicates that the 
		// i'th timeslot in the schedule is empty
		int[] homeworkPlan = new int[lastDeadline];
		for (int i=0; i < homeworkPlan.length; ++i) {
			homeworkPlan[i] = -1;
		}
		int filledSpots = 0;
		for(Assignment assignment: Assignments){
			int homeworkIndex = lastDeadline;
			while((homeworkIndex>=0)){
				if((assignment.deadline-homeworkIndex>0)&&(homeworkPlan[homeworkIndex]==-1)){
					homeworkPlan[homeworkIndex] = assignment.number;
					filledSpots++;
					break;
				}
				homeworkIndex--;
			}
			if(filledSpots==lastDeadline){
				break;
			}
		}
		return homeworkPlan;
	}
}
	



