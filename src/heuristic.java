
public class heuristic {

//	public static int generateManhattanDistance(Node current, Node goal) {
//		//first get the regular manhattan distance and save it as the best found untill now (=lowest we got back from a heuristic function)
//		int best_currently = generateManhattanDistance_move1Only(current, goal);
//		
//		//if two blanks exist, try every available move of 2 tiles together, and then check the regular manhattan distance for that.
//		//if what was returned is better then the best found so far, update best_currently
//		
////		if(current.two_blanks_exist) {
////			Node temp = null;
////			move_direction_enum mde = move_direction_enum.LEFT; //initialization of mde variable. gave it to whatever value, because must be initialized when created
////			for (int i = 0; i < 4; i++) {
////				switch (i){
////				case 0:
////					mde = move_direction_enum.LEFT;
////					break;
////				case 1:
////					mde = move_direction_enum.UP;
////					break;
////				case 2:
////					mde = move_direction_enum.RIGHT;
////					break;
////				default:
////					mde = move_direction_enum.DOWN;
////				}
////				
////				if (!current.check_move_allowed(mde, 2, -1)) {
////					//System.out.println("continuing");
////					continue;
////				}
////				
////				temp = new Node(current);
////				temp.move(mde, -1, 2); //cost_to_me is updated already in the move function
////				
////				//now check what the manhatan distance of that is - 
////				int current_manhattan = generateManhattanDistance_move1Only(temp, goal);
////				current_manhattan +=7; //add 7 to it, as to get to the new manhattan, "paying" a cost of 7 will be needed.
////				
////				if(best_currently > current_manhattan) {
////					best_currently = current_manhattan;
////				}
////				
////				
////			}
////		}
//		
//		
//		
//		return best_currently;
//	}	
		
		
		
		
	public static int generateManhattanDistance(Node current, Node goal) {
		int[] current_location_of_each_number_row = new int[current.board.length * current.board[0].length];
		int[] current_location_of_each_number_column = new int[current.board.length * current.board[0].length];
		
		int[] goal_location_of_each_number_row = new int[current.board.length * current.board[0].length];
		int[] goal_location_of_each_number_column = new int[current.board.length * current.board[0].length];
		for (int i = 0; i < current.board.length; i++) {
			for (int j = 0; j < current.board[0].length; j++) {
				//if you found a -1 (a blank) don't take it's location, doesn't matter.
				if(!(current.board[i][j] == -1)) {
					current_location_of_each_number_row[current.board[i][j]] = i;
					current_location_of_each_number_column[current.board[i][j]] = j;
				}
				
				if(!(goal.board[i][j] == -1)) {
					goal_location_of_each_number_row[goal.board[i][j]] = i;
					goal_location_of_each_number_column[goal.board[i][j]] = j;
				}
				
			}
		}
		
		int start_from = 1;
		int go_until = current.two_blanks_exist? current_location_of_each_number_row.length-1 : current_location_of_each_number_row.length;
		int manhattanDistance = 0;
		
		for (int i = start_from; i < go_until; i++) {
			manhattanDistance += Math.abs(current_location_of_each_number_row[i] - goal_location_of_each_number_row[i]);
			manhattanDistance += Math.abs(current_location_of_each_number_column[i] - goal_location_of_each_number_column[i]);
		}
		
		if (current.two_blanks_exist) {
			return (int) (manhattanDistance*3.5);
		}
		else {
			return (int) (manhattanDistance*5);
		}
		
	}
}
