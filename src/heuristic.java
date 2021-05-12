
public class heuristic {

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
		
		return manhattanDistance;
	}
}
