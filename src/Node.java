import java.util.Arrays;
import java.util.Iterator;
//import java.util.Iterator;

public class Node implements Comparable<Node> {
//	int[] location_of_each_number;
	int location_of_blank1 = -1;
	int location_of_blank2 = -1;
	
	boolean two_blanks_exist = false;
	
	String name = "";
	int[][] board;
	
	int numbers_moved_to_get_to_me[] = new int[1];
	int move_made_to_get_to_me = -1;
	boolean moved_2_to_get_to_me = false;
	
	public Node pred = null;
	
	private int cost_to_me = 0;
	int myManhattanDistance = -1;
	
	long creation_time = 0;
	
	Node (int[][] board, int blankCounter, int blank1_location, int blank2_location){
		this.board = board;
		this.location_of_blank1 = blank1_location;
		
		if (blank2_location != -1) {
			this.location_of_blank2 = blank2_location;
			this.two_blanks_exist = true;
		}
		
		this.creation_time = System.currentTimeMillis();
		
		
	}
	
	Node(){
		System.out.println("empty constructor Node");
	}
	
	
	//copy constructor
	Node (Node copyMe){
		this.board = new int[copyMe.board.length][copyMe.board[0].length];

		
		for (int i = 0; i < copyMe.board.length; i++) {
			for (int j = 0; j < copyMe.board[0].length; j++) {
				this.board[i][j] = copyMe.board[i][j];
				
			}
		}
		this.location_of_blank1 = copyMe.getLocation_of_blank1();
		
		if(copyMe.two_blanks_exist) {
			this.location_of_blank2 = copyMe.getLocation_of_blank2();
			this.two_blanks_exist = true;
		}
		
		else {//TODO is this needed? not sure. The values set here are supposed to be what the node has when initialized. Check!
			this.location_of_blank2 = -1;
			this.two_blanks_exist = false;
		}
		
		
		
		this.pred = copyMe.pred;
		this.move_made_to_get_to_me = copyMe.move_made_to_get_to_me;
		this.numbers_moved_to_get_to_me = copyMe.getNumbers_moved_to_get_to_me();
		this.cost_to_me = copyMe.getCost_to_me();
		this.myManhattanDistance = copyMe.myManhattanDistance;
		this.moved_2_to_get_to_me = copyMe.moved_2_to_get_to_me;
		
		this.creation_time = System.currentTimeMillis();
	}
	
	
	public void move(move_direction_enum direction, int blank_to_move, int num_of_blanks_to_move) {
		if(num_of_blanks_to_move==1) {
			move1(direction, blank_to_move);
		}
		else {
			move2(direction);
		}
	}
	
	//move 1 number
	//move direction - 0,1,2,3 - left, up, right, down
	public void move1(move_direction_enum direction, int blank_to_move) {
		cost_to_me+=5;
		
		moved_2_to_get_to_me = false; //not sure needed, as it is false when initialized, but maybe neede because of copy constructor ot other reasons,
									  //and very cheap 
		
		int location_of_relevant_blank = blank_to_move==1? location_of_blank1: location_of_blank2;
		
		int row_of_blank = location_of_relevant_blank / this.board[0].length;
		int column_of_blank = location_of_relevant_blank % this.board[0].length;
		
		this.numbers_moved_to_get_to_me = new int[1];
		
		switch(direction) {
		case LEFT:
			//move left - need to move the number to the right of the empty slot
			
			//first set the values of the node, regarding what made the node as it is
			this.numbers_moved_to_get_to_me[0] = this.board[row_of_blank][column_of_blank+1];
			this.move_made_to_get_to_me = 0;
			
			//copy the one to the right of the blank to where the blank is currently
			this.board[row_of_blank][column_of_blank] = this.board[row_of_blank][column_of_blank+1]; 
			//put blank in the new spot
			this.board[row_of_blank][column_of_blank+1] = -1;
			//mark the new location of the blank
			
			if(blank_to_move==1) {
				this.location_of_blank1 +=1;
			}
			else {
				this.location_of_blank2 +=1;
			}
			
			break;
			
		case UP:
			//move up - need to move the number currently under the blank
			
			//first set the values of the node, regarding what made the node as it is
			this.numbers_moved_to_get_to_me[0] = this.board[row_of_blank+1][column_of_blank];
			this.move_made_to_get_to_me = 1;
			
			
			//copy the one under the blank to where the blank is currently
			this.board[row_of_blank][column_of_blank] = this.board[row_of_blank+1][column_of_blank];
			//put blank in the new spot
			this.board[row_of_blank+1][column_of_blank] = -1;	
			//mark the new location of the blank			
			if(blank_to_move==1) {
				this.location_of_blank1 += this.board[0].length;
			}
			else {
				this.location_of_blank2 += this.board[0].length;
			}
						
			break;
			
		case RIGHT:
			//move right - need to move the number currently left of the blank
			
			//first set the values of the node, regarding what made the node as it is
			this.numbers_moved_to_get_to_me[0] = this.board[row_of_blank][column_of_blank-1];
			this.move_made_to_get_to_me = 2;
			
			//copy the one to the left to the blank to where the blank is currently
			this.board[row_of_blank][column_of_blank] = this.board[row_of_blank][column_of_blank-1];
			//put blank in the new spot
			this.board[row_of_blank][column_of_blank-1] = -1;	
			//mark the new location of the blank
			if(blank_to_move==1) {
				this.location_of_blank1 += -1;
			}
			else {
				this.location_of_blank2 += -1;
			}
			
			break;
			
		case DOWN:
			//move down - need to move the number currently left of the blank
			
			//first set the values of the node, regarding what made the node as it is
			this.numbers_moved_to_get_to_me[0] = this.board[row_of_blank-1][column_of_blank];
			this.move_made_to_get_to_me = 3;
			
			//copy the one to the left to the blank to where the blank is currently
			this.board[row_of_blank][column_of_blank] = this.board[row_of_blank-1][column_of_blank];
			//put blank in the new spot
			this.board[row_of_blank-1][column_of_blank] = -1;	
			//mark the new location of the blank
			if(blank_to_move==1) {
				this.location_of_blank1 += -this.board[0].length;
			}
			else {
				this.location_of_blank2 += -this.board[0].length;
			}
			
			break;
			
		default:
			System.out.println("ERROR!! Should not reach here! Node move1 function.");
		}
		
			
		
	}

	//move 2 numbers together
	public void move2(move_direction_enum direction) {
		moved_2_to_get_to_me = true;
		
		
		int higher_or_lefter_blank_row = location_of_blank1<location_of_blank2? location_of_blank1 / this.board[0].length : location_of_blank2 / this.board[0].length ;
		int higher_or_lefter_blank_column = location_of_blank1<location_of_blank2? location_of_blank1 % this.board[0].length : location_of_blank2 % this.board[0].length;
		
		int lower_or_righter_blank_row = location_of_blank1<location_of_blank2? location_of_blank2 / this.board[0].length : location_of_blank1 / this.board[0].length;
		int lower_or_righter_blank_column = location_of_blank1<location_of_blank2? location_of_blank2 % this.board[0].length : location_of_blank1 % this.board[0].length;
		
		this.numbers_moved_to_get_to_me = new int[2];
		
		

		
		switch(direction) {
		case LEFT:
			cost_to_me+=6;
			//move left - need to move the numbers to the right of the empty slots
			
			//first set the values of the node, regarding what made the node as it is
			this.numbers_moved_to_get_to_me[0] = this.board[higher_or_lefter_blank_row][higher_or_lefter_blank_column+1];
			this.numbers_moved_to_get_to_me[1] = this.board[lower_or_righter_blank_row][lower_or_righter_blank_column+1];
			this.move_made_to_get_to_me = 0;
			
			//copy the numbers to the right of the blanks to where the blanks are currently
			this.board[higher_or_lefter_blank_row][higher_or_lefter_blank_column] = this.board[higher_or_lefter_blank_row][higher_or_lefter_blank_column+1]; 
			this.board[lower_or_righter_blank_row][lower_or_righter_blank_column] = this.board[lower_or_righter_blank_row][lower_or_righter_blank_column+1]; 
			//put blanks in the new spots
			this.board[higher_or_lefter_blank_row][higher_or_lefter_blank_column+1] = -1;
			this.board[lower_or_righter_blank_row][lower_or_righter_blank_column+1] = -1;
			//mark the new locations of the blanks
			
			this.location_of_blank1 +=1;
			this.location_of_blank2 +=1;
			
			break;
		
		case UP:
			cost_to_me+=7;
			//move up - need to move the numbers below the empty slots
			
			//first set the values of the node, regarding what made the node as it is
			this.numbers_moved_to_get_to_me[0] = this.board[higher_or_lefter_blank_row+1][higher_or_lefter_blank_column];
			this.numbers_moved_to_get_to_me[1] = this.board[lower_or_righter_blank_row+1][lower_or_righter_blank_column];
			this.move_made_to_get_to_me = 1;
			
			//copy the numbers to the right of the blanks to where the blanks are currently
			this.board[higher_or_lefter_blank_row][higher_or_lefter_blank_column] = this.board[higher_or_lefter_blank_row+1][higher_or_lefter_blank_column];
			this.board[lower_or_righter_blank_row][lower_or_righter_blank_column] = this.board[lower_or_righter_blank_row+1][lower_or_righter_blank_column];
			//put blanks in the new spots
			this.board[higher_or_lefter_blank_row+1][higher_or_lefter_blank_column] = -1;
			this.board[lower_or_righter_blank_row+1][lower_or_righter_blank_column] = -1;
			//mark the new locations of the blanks
			
			this.location_of_blank1 += this.board[0].length;
			this.location_of_blank2 += this.board[0].length;
			
			break;
		
		case RIGHT:
			cost_to_me+=6;
			//move right - need to move the numbers to the left of the empty slots
			
			//first set the values of the node, regarding what made the node as it is
			this.numbers_moved_to_get_to_me[0] = this.board[higher_or_lefter_blank_row][higher_or_lefter_blank_column-1];
			this.numbers_moved_to_get_to_me[1] = this.board[lower_or_righter_blank_row][lower_or_righter_blank_column-1];
			this.move_made_to_get_to_me = 2;
			
			//copy the numbers to the right of the blanks to where the blanks are currently
			this.board[higher_or_lefter_blank_row][higher_or_lefter_blank_column] = this.board[higher_or_lefter_blank_row][higher_or_lefter_blank_column-1]; 
			this.board[lower_or_righter_blank_row][lower_or_righter_blank_column] = this.board[lower_or_righter_blank_row][lower_or_righter_blank_column-1]; 
			//put blanks in the new spots
			this.board[higher_or_lefter_blank_row][higher_or_lefter_blank_column-1] = -1;
			this.board[lower_or_righter_blank_row][lower_or_righter_blank_column-1] = -1;
			//mark the new locations of the blanks
			
			this.location_of_blank1 += -1;
			this.location_of_blank2 += -1;
			
			break;
		
		case DOWN:
			cost_to_me+=7;
			//move up - need to move the numbers below the empty slots
			
			//first set the values of the node, regarding what made the node as it is
			this.numbers_moved_to_get_to_me[0] = this.board[higher_or_lefter_blank_row-1][higher_or_lefter_blank_column];
			this.numbers_moved_to_get_to_me[1] = this.board[lower_or_righter_blank_row-1][lower_or_righter_blank_column];
			this.move_made_to_get_to_me = 4;
			
			//copy the numbers to the right of the blanks to where the blanks are currently
			this.board[higher_or_lefter_blank_row][higher_or_lefter_blank_column] = this.board[higher_or_lefter_blank_row-1][higher_or_lefter_blank_column];
			this.board[lower_or_righter_blank_row][lower_or_righter_blank_column] = this.board[lower_or_righter_blank_row-1][lower_or_righter_blank_column];
			//put blanks in the new spots
			this.board[higher_or_lefter_blank_row-1][higher_or_lefter_blank_column] = -1;
			this.board[lower_or_righter_blank_row-1][lower_or_righter_blank_column] = -1;
			//mark the new locations of the blanks
			
			this.location_of_blank1 += -this.board[0].length;
			this.location_of_blank2 += -this.board[0].length;
			
			break;
		
		
		default:
			System.out.println("ERROR!! Should not reach here! Node move2 function.");		
		}
		
	}
	
	public boolean check_move_allowed(move_direction_enum direction, int how_many_to_move, int which_blank_to_check) {
		if(how_many_to_move==1) {
			return check_move_allowed1(direction, which_blank_to_check);
		}
		else {
			return check_move_allowed2(direction);
		}
		
		
	}
	
	
	public boolean check_move_allowed1(move_direction_enum direction, int which_blank_to_check) {
		int location_of_relevant_blank;
		if(which_blank_to_check==1) {
			location_of_relevant_blank = location_of_blank1;
		}
		else {
			location_of_relevant_blank = location_of_blank2;
		}
		
		int row_of_blank = location_of_relevant_blank / this.board[0].length;
		int column_of_blank = (int) location_of_relevant_blank % this.board[0].length;
		
		switch(direction) {
		case LEFT:
			//first check we aren't repeating the move that got us here
			//this is also relevant if 2 blocks were moved before this move, as if 2 were moved to the right, moving 1 to the left is also not allowed and will be checked here
			if (this.move_made_to_get_to_me == move_direction_enum.RIGHT.ordinal()) return false;
			
			//check, in case there are 2 blanks, that this move will not switch between them
			if(this.two_blanks_exist) {
				int blank_being_checked_location = which_blank_to_check==1? this.location_of_blank1 : this.location_of_blank2;
				int other_blank_location = this.location_of_blank1 + this.location_of_blank2 - blank_being_checked_location;
				
				int blank_being_checked_row = blank_being_checked_location / this.board[0].length;
				int blank_being_checked_column = blank_being_checked_location % this.board[0].length;
				
				int other_blank_row = other_blank_location / this.board[0].length;
				int other_blank_column = other_blank_location % this.board[0].length;

				if((blank_being_checked_row == other_blank_row) && (other_blank_column == blank_being_checked_column+1)) return false;
				
			}
			
			//check there is an element to the right (blank not at the edge)
			return column_of_blank != this.board[0].length-1;
		case UP:
			//first check we aren't repeating the move that got us here
			if (this.move_made_to_get_to_me == move_direction_enum.DOWN.ordinal()) return false;
			
			
			//check, in case there are 2 blanks, that this move will not switch between them
			if(this.two_blanks_exist) {
				int blank_being_checked_location = which_blank_to_check==1? this.location_of_blank1 : this.location_of_blank2;
				int other_blank_location = this.location_of_blank1 + this.location_of_blank2 - blank_being_checked_location;
				
				int blank_being_checked_row = blank_being_checked_location / this.board[0].length;
				int blank_being_checked_column = blank_being_checked_location % this.board[0].length;
				
				int other_blank_row = other_blank_location / this.board[0].length;
				int other_blank_column = other_blank_location % this.board[0].length;

				if((blank_being_checked_row == other_blank_row-1) && (other_blank_column == blank_being_checked_column)) return false;
				
			}
			
			//check there is an element below (blank not at the edge)
			return row_of_blank != this.board.length-1;
		case RIGHT:
			//first check we aren't repeating the move that got us here
			if (this.move_made_to_get_to_me == move_direction_enum.LEFT.ordinal()) return false;
			
			//check, in case there are 2 blanks, that this move will not switch between them
			if(this.two_blanks_exist) {
				int blank_being_checked_location = which_blank_to_check==1? this.location_of_blank1 : this.location_of_blank2;
				int other_blank_location = this.location_of_blank1 + this.location_of_blank2 - blank_being_checked_location;
				
				int blank_being_checked_row = blank_being_checked_location / this.board[0].length;
				int blank_being_checked_column = blank_being_checked_location % this.board[0].length;
				
				int other_blank_row = other_blank_location / this.board[0].length;
				int other_blank_column = other_blank_location % this.board[0].length;

				if((blank_being_checked_row == other_blank_row) && (other_blank_column == blank_being_checked_column-1)) return false;
				
			}
			
			//check there is an element to the left (blank not at the edge)
			return column_of_blank!=0;
		case DOWN:
			//first check we aren't repeating the move that got us here
			if (this.move_made_to_get_to_me == move_direction_enum.UP.ordinal()) return false;
			
			//check, in case there are 2 blanks, that this move will not switch between them
			if(this.two_blanks_exist) {
				int blank_being_checked_location = which_blank_to_check==1? this.location_of_blank1 : this.location_of_blank2;
				int other_blank_location = this.location_of_blank1 + this.location_of_blank2 - blank_being_checked_location;
				
				int blank_being_checked_row = blank_being_checked_location / this.board[0].length;
				int blank_being_checked_column = blank_being_checked_location % this.board[0].length;
				
				int other_blank_row = other_blank_location / this.board[0].length;
				int other_blank_column = other_blank_location % this.board[0].length;

				if((blank_being_checked_row == other_blank_row+1) && (other_blank_column == blank_being_checked_column)) return false;
				
			}
			
			//check there is an element below (blank not at the edge)
			return  row_of_blank!=0;
		}
		System.err.println("!!!ERROR - not supposed to get here. At function check_move_allowed1!!!");
		return false;
		
	}


	public boolean check_move_allowed2(move_direction_enum direction) {
		
		
		int row_of_blank1 = location_of_blank1 / this.board[0].length;
		int column_of_blank1 = location_of_blank1 % this.board[0].length;
		
		int row_of_blank2 = location_of_blank2 / this.board[0].length;
		int column_of_blank2 = location_of_blank2 % this.board[0].length;
		
		switch(direction) {
		case LEFT:
			//first I check a left move is possible for 2 blocks - meaning the 2 empty blocks are ABOVE each other.
			//otherwise moving 2 to the left isn't optional
			if((Math.abs(row_of_blank1-row_of_blank2) != 1) || (column_of_blank1!=column_of_blank2)) return false;
			
			//then check we aren't repeating the move that got us here, or that 1 number was moved to the right, and now that number and the number below\above it will be moved together to the left (as this could be done with 1 move instead of 2, and is a waste)
			if (this.move_made_to_get_to_me == move_direction_enum.RIGHT.ordinal()) return false;			
			
			//finally, check there are elements to the right (blanks not at the edge) - meaning the move is even possible
			return column_of_blank1 != this.board[0].length-1;
		
		case UP:
			//first I check a up move is possible for 2 blocks - meaning the 2 empty blocks are NEXT TO each other.
			//otherwise moving 2 up isn't optional
			if((row_of_blank1!=row_of_blank2) || (Math.abs(column_of_blank1 - column_of_blank2) !=1)) return false;
			
			//then check we aren't repeating the move that got us here, or that 1 number was moved down, and now that number and the number next to it will be moved together upwards (as this could be done with 1 move instead of 2, and is a waste)
			if (this.move_made_to_get_to_me == move_direction_enum.DOWN.ordinal()) return false;
			
			//finally, check there are elements below (blanks not at the edge\bottom) - meaning the move is even possible
			return row_of_blank1 != this.board.length-1;
		
		case RIGHT:
			//first I check a right move is possible for 2 blocks - meaning the 2 empty blocks are ABOVE each other.
			//otherwise moving 2 to the left isn't optional
			if((Math.abs(row_of_blank1-row_of_blank2) != 1) || (column_of_blank1!=column_of_blank2)) return false;
			
			//then check we aren't repeating the move that got us here, or that 1 number was moved to the left, and now that number and the number below\above it will be moved together to the right (as this could be done with 1 move instead of 2, and is a waste)
			if (this.move_made_to_get_to_me == move_direction_enum.LEFT.ordinal()) return false;			
			
			//finally, check there are elements to the left (blanks not at the edge) - meaning the move is even possible
			return column_of_blank1 != 0;
			
		case DOWN:
			//first I check a down move is possible for 2 blocks - meaning the 2 empty blocks are NEXT TO each other.
			//otherwise moving 2 down isn't optional
			if((row_of_blank1!=row_of_blank2) || (Math.abs(column_of_blank1 - column_of_blank2) !=1)) return false;
			
			//then check we aren't repeating the move that got us here, or that 1 number was moved up, and now that number and the number next to it will be moved together downwards (as this could be done with 1 move instead of 2, and is a waste)
			if (this.move_made_to_get_to_me == move_direction_enum.DOWN.ordinal()) return false;
			
			//finally, check there are elements above (blanks not at the edge\top) - meaning the move is even possible
			return row_of_blank1 != 0;
		}
		System.err.println("!!!ERROR - not supposed to get here. At function check_move_allowed2!!!");
		return false;
		
	}
	
	@Override
	public int hashCode() {
		return Arrays.hashCode(this.board);
//	    return board.hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null) {
			System.out.println("!!!Null object compared!!!");
			return false;
		}
		
	    if (other == this) return true; //reference comparison
	    
	    if (!(other instanceof Node)) {
	    	System.out.println("!!!None Node object compared to Node!!!");
	    	return false;
	    }
	    
	    Node other_node = (Node)other;
	    return Arrays.deepEquals(this.board, other_node.board);
		
		
	}
	
	public int getLocation_of_blank1() {
		return location_of_blank1;
	}
	
	public int getLocation_of_blank2() {
		return location_of_blank2;
	}
	
	public int[] getNumbers_moved_to_get_to_me() {
		return Arrays.copyOf(numbers_moved_to_get_to_me, numbers_moved_to_get_to_me.length);
		//TODO - check this works as intended
	}

	public int getCost_to_me() {
		return cost_to_me;
	}
	
	public int getMyManhattanDistance() {
		return myManhattanDistance;
	}
	
	public void updateMyManhattanDistance(Node goal) {
		this.myManhattanDistance = heuristic.generateManhattanDistance(this, goal);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
//		return Integer.toString(this.hashCode());
		String return_value = "";
		for (int i = 0; i < board.length; i++) {
			return_value = return_value + Arrays.toString(board[i]) + "\n";	
		}
		return return_value;
	}

	@Override
	public int compareTo(Node other) {
		
		if (this.cost_to_me + this.myManhattanDistance > other.cost_to_me + other.myManhattanDistance) return 1;
		
		else if(this.cost_to_me + this.myManhattanDistance < other.cost_to_me + other.myManhattanDistance) return -1;
		
		//getting here means cost_to_me + heuristic are equal for both, so we compare creating time
		//where node created later in time are considered bigger
		else {
			if(this.creation_time > other.creation_time) return 1;
			
			else if(this.creation_time < other.creation_time) return -1;
			
			else return 0;
		}
		
	}
	
}
