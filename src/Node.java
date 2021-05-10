import java.util.Arrays;
import java.util.Iterator;
//import java.util.Iterator;

public class Node {
//	int[] location_of_each_number;
	int location_of_blank;
	int[][] board;
	
	int number_moved_to_get_to_me = -1;
	int move_made_to_get_to_me = -1;
	
	public Node pred = null;
	
	Node (int[][] board, int blankCounter, int blank_location){
		this.board = board;
		this.location_of_blank = blank_location;
//		location_of_each_number = new int[board.length * board[0].length - blankCounter];
		
		
//		for (int i = 0; i < board.length; i++) {
//			for (int j = 0; j < board[0].length; j++) {
//				if(board[i][j] == -1) location_of_blank = i*board.length + j;
//				location_of_each_number[board[i][j]] = i*board.length + j;
//			}
//			
//		}
		
	}
	
	Node(){
		System.out.println("empty constructor Node");
	}
	
	
	//copy constructor
	Node (Node copyMe){
//		this.board = copyMe.board.clone();
		this.board = new int[copyMe.board.length][copyMe.board[0].length];
		for (int i = 0; i < copyMe.board.length; i++) {
			for (int j = 0; j < copyMe.board[0].length; j++) {
				this.board[i][j] = copyMe.board[i][j];
				
			}
		}
		this.location_of_blank = copyMe.getLocation_of_blank();
		this.pred = copyMe.pred;
		this.move_made_to_get_to_me = copyMe.move_made_to_get_to_me;
		this.number_moved_to_get_to_me = copyMe.number_moved_to_get_to_me;
	}
	
	
//	//move direction - 0,1,2,3 - left, up, right, down
//	public Node move(move_direction_enum direction) {
//		Node newNode = new Node(this);
//		int row_of_blank = location_of_blank / newNode.board[0].length;
//		int column_of_blank = location_of_blank % newNode.board[0].length;
//		switch(direction) {
//		case LEFT:
//			//move left - need to move the number to the right of the empty slot
//			
//			//copy the one to the right of the blank to where the blank is currently
//			newNode.board[row_of_blank][column_of_blank] = newNode.board[row_of_blank][column_of_blank+1]; 
//			//put blank in the new spot
//			newNode.board[row_of_blank][column_of_blank+1] = -1;
//			//mark the new location of the blank
//			newNode.location_of_blank +=1;
//			
//			return newNode;
//			
//		case UP:
//			//move up - need to move the number currently under the blank
//			
//			//copy the one under the blank to where the blank is currently
//			newNode.board[row_of_blank][column_of_blank] = newNode.board[row_of_blank+1][column_of_blank];
//			//put blank in the new spot
//			newNode.board[row_of_blank+1][column_of_blank] = -1;	
//			//mark the new location of the blank
//			newNode.location_of_blank += newNode.board.length;
//		
//			return newNode;
//			
//		case RIGHT:
//			//move right - need to move the number currently left of the blank
//			
//			//copy the one to the left to the blank to where the blank is currently
//			newNode.board[row_of_blank][column_of_blank] = newNode.board[row_of_blank][column_of_blank-1];
//			//put blank in the new spot
//			newNode.board[row_of_blank][column_of_blank-1] = -1;	
//			//mark the new location of the blank
//			newNode.location_of_blank += -1;
//			
//			return newNode;
//			
//		case DOWN:
//			//move down - need to move the number currently left of the blank
//			
//			//copy the one to the left to the blank to where the blank is currently
//			newNode.board[row_of_blank][column_of_blank] = newNode.board[row_of_blank-1][column_of_blank];
//			//put blank in the new spot
//			newNode.board[row_of_blank-1][column_of_blank] = -1;	
//			//mark the new location of the blank
//			newNode.location_of_blank += -1;
//			
//			return newNode;
//		}
//		System.out.println("ERROR!! Should not reach here! Node move function.");
//		return newNode;
//			
//		
//	}
	
	//move direction - 0,1,2,3 - left, up, right, down
	public void move(move_direction_enum direction) {
		int row_of_blank = location_of_blank / this.board[0].length;
		int column_of_blank = location_of_blank % this.board[0].length;
		switch(direction) {
		case LEFT:
			//move left - need to move the number to the right of the empty slot
			
			//first set the values of the node, regarding what made the node as it is
			this.number_moved_to_get_to_me = this.board[row_of_blank][column_of_blank+1];
			this.move_made_to_get_to_me = 0;
			
			//copy the one to the right of the blank to where the blank is currently
			this.board[row_of_blank][column_of_blank] = this.board[row_of_blank][column_of_blank+1]; 
			//put blank in the new spot
			this.board[row_of_blank][column_of_blank+1] = -1;
			//mark the new location of the blank
			this.location_of_blank +=1;
			
			break;
			
		case UP:
			//move up - need to move the number currently under the blank
			
			//first set the values of the node, regarding what made the node as it is
			this.number_moved_to_get_to_me = this.board[row_of_blank+1][column_of_blank];
			this.move_made_to_get_to_me = 1;
			
			
			//copy the one under the blank to where the blank is currently
			this.board[row_of_blank][column_of_blank] = this.board[row_of_blank+1][column_of_blank];
			//put blank in the new spot
			this.board[row_of_blank+1][column_of_blank] = -1;	
			//mark the new location of the blank
			this.location_of_blank += this.board[0].length;
		
			break;
			
		case RIGHT:
			//move right - need to move the number currently left of the blank
			
			//first set the values of the node, regarding what made the node as it is
			this.number_moved_to_get_to_me = this.board[row_of_blank][column_of_blank-1];
			this.move_made_to_get_to_me = 2;
			
			//copy the one to the left to the blank to where the blank is currently
			this.board[row_of_blank][column_of_blank] = this.board[row_of_blank][column_of_blank-1];
			//put blank in the new spot
			this.board[row_of_blank][column_of_blank-1] = -1;	
			//mark the new location of the blank
			this.location_of_blank += -1;
			
			break;
			
		case DOWN:
			//move down - need to move the number currently left of the blank
			
			//first set the values of the node, regarding what made the node as it is
			this.number_moved_to_get_to_me = this.board[row_of_blank-1][column_of_blank];
			this.move_made_to_get_to_me = 3;
			
			//copy the one to the left to the blank to where the blank is currently
			this.board[row_of_blank][column_of_blank] = this.board[row_of_blank-1][column_of_blank];
			//put blank in the new spot
			this.board[row_of_blank-1][column_of_blank] = -1;	
			//mark the new location of the blank
			this.location_of_blank += -this.board[0].length;
			
			break;
			
		default:
			System.out.println("ERROR!! Should not reach here! Node move function.");
		}
		
			
		
	}

	public boolean check_move_allowed(move_direction_enum direction) {
		boolean answer = false;
		int row_of_blank = location_of_blank / this.board[0].length;
		int column_of_blank = (int) location_of_blank % this.board[0].length;
		
		switch(direction) {
		case LEFT:
			//first check we aren't repeating the move that got us here
			if (this.move_made_to_get_to_me == move_direction_enum.RIGHT.ordinal()) return false;
			
			//check there is an element to the right (blank not at the edge)
			return column_of_blank < this.board[0].length-1;
		case UP:
			//first check we aren't repeating the move that got us here
			if (this.move_made_to_get_to_me == move_direction_enum.DOWN.ordinal()) return false;
			
			//check there is an element below (blank not at the edge)
			return row_of_blank < this.board.length-1;
		case RIGHT:
			//first check we aren't repeating the move that got us here
			if (this.move_made_to_get_to_me == move_direction_enum.LEFT.ordinal()) return false;
			
			//check there is an element to the left (blank not at the edge)
			return column_of_blank!=0;
		case DOWN:
			//first check we aren't repeating the move that got us here
			if (this.move_made_to_get_to_me == move_direction_enum.UP.ordinal()) return false;
			
			//check there is an element below (blank not at the edge)
			return  row_of_blank!=0;
		}
		
		return answer;
		
	}

	@Override
	public int hashCode() {
	    return board.hashCode();
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
	
	public int getLocation_of_blank() {
		return location_of_blank;
	}

	@Override
	public String toString() {
		String return_value = "";
		for (int i = 0; i < board.length; i++) {
			return_value = return_value + Arrays.toString(board[i]) + "\n";	
		}
		return return_value;
	}
	
}
