import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class fileParams {
	String Algo;
	boolean print_runtime;
	boolean openList;
	int number_of_rows;
	int number_of_columns;
	int[][] board_initial;
	int[][] board_goal;
	int blank_counter;
	int blank_location;

	fileParams() throws FileNotFoundException{
		File inputFile = new File("input.txt");

		Scanner myReader = new Scanner(inputFile);

		this.Algo = myReader.nextLine();
		this.print_runtime = myReader.nextLine().contains("with");
		this.openList = myReader.nextLine().contains("with");

		String[] parts = myReader.nextLine().split("x");
		this.number_of_rows = Integer.parseInt(parts[0]);
		this.number_of_columns = Integer.parseInt(parts[1]);

		this.board_initial = new int[this.number_of_rows][this.number_of_columns];
		this.board_goal = new int[this.number_of_rows][this.number_of_columns];

		String[] board_initial_strings = new String[this.number_of_rows];
		for (int i = 0; i < this.number_of_rows; i++) {
			board_initial_strings[i] = myReader.nextLine();
		}

		read_board_init_or_goal(this.board_initial, board_initial_strings, true);

		String goal_state_line = myReader.nextLine();
		if (!goal_state_line.contains("Goal state")){
			System.out.println("Error reading input.txt! Expected to read Goal state, but instead got " + goal_state_line);
		};


		String[] board_goal_strings = new String[this.number_of_rows];
		for (int i = 0; i < this.number_of_rows; i++) {
			board_goal_strings[i] = myReader.nextLine();
		}

		read_board_init_or_goal(this.board_goal, board_goal_strings, false);

		myReader.close();





	}

	//gets an int array representing the board, at initial or goal state, and an array of strings
	//containing value of each element on the board, in the form of strings, each representing a row
	//parses the strings in to the array
	void read_board_init_or_goal(int[][] board, String[] strings, boolean init) {
		for (int i = 0; i < board.length; i++) {
			String[] temp = strings[i].split(",");
			for (int j = 0; j < board[0].length; j++) {
				if(temp[j].contentEquals("_")) {
					board[i][j] = -1;
					if (init) this.blank_counter += 1;
					blank_location = i*number_of_columns + j;
				}
				else {
					board[i][j] = Integer.parseInt(temp[j]);
				}	
			}	
		}
	}
}
