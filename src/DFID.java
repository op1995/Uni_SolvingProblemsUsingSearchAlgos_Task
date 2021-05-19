import java.util.Hashtable;

public class DFID {

	static int nodes_created = 1;
	static long start_time = 0;
	static fileParams myFileParams = null;

	public static void runDFID(fileParams _fileParams, Node source, Node goal) {
		start_time = System.currentTimeMillis();
		myFileParams = _fileParams;

		Hashtable<Node, Integer> closed_list = new Hashtable<Node, Integer>();
		
		for (int depth = 1; depth > -1; depth++) { //the loop will go on forever unless we set i to -2 manually

			closed_list = new Hashtable<Node, Integer>();
			Node result = Limited_DFS(source, goal, depth, closed_list);

			if(result.getName().contentEquals("fail")) {
				//failed - output failed and end DFID
				answer.output_answer(null, nodes_created, _fileParams.print_runtime, answer.getTimeInSeconds(start_time, System.currentTimeMillis()), false);
				depth = -2;
			}
			
			else if(result.getName().contentEquals("found answer")) {
				answer.output_answer(result, nodes_created, myFileParams.print_runtime, answer.getTimeInSeconds(start_time, System.currentTimeMillis()), true);
				depth = -2;
			}
			
			//else if(result is "cutoff") --> continue. No need for this code, it will happen on it's own.
		}

	}

	static Node Limited_DFS(Node n, Node goal, int limit, Hashtable<Node, Integer> closed_list) {
		if(n.equals(goal)) {
			n.setName("found answer");
			return n;
//			
//			long end_time = System.currentTimeMillis();
//
//			double elapsedTimeInSecond = answer.getTimeInSeconds(start_time, end_time);
//			answer.output_answer(n, nodes_created, myFileParams.print_runtime, elapsedTimeInSecond, true);
//
//			return "found_answer";
		}

		else if(limit == 0) {
			n.setName("cutoff");
			return n;
//			return "cutoff";
		}

		else {
			closed_list.put(n, n.getCost_to_me());
			boolean isCutOff = false;

			int possible_moves = n.two_blanks_exist? 12 : 4;
			int higher_or_more_to_the_left_node = -1;
			int lower_or_more_to_the_right_node = -1;

			if(n.two_blanks_exist) {
				//if the location is smaller, necessarily it is either above(higher) or at same height but more to the left)
				higher_or_more_to_the_left_node = n.location_of_blank1<n.location_of_blank2? 1 : 2;
				lower_or_more_to_the_right_node = n.location_of_blank1<n.location_of_blank2? 2 : 1;
			}

			//try each allowed operation
			for (int i = 0; i < possible_moves; i++) {
				move_direction_enum mde = move_direction_enum.LEFT; //initialization of mde variable. gave it to whatever value, because must be initialized when created
				int how_many_to_check_or_move = -1;
				int which_blank_to_check_or_move = -1;


				if(n.two_blanks_exist) {
					switch(i) {
					case 0:
						mde = move_direction_enum.LEFT;
						how_many_to_check_or_move = 2;
						break;
					case 1:
						mde = move_direction_enum.UP;
						how_many_to_check_or_move = 2;
						break;
					case 2:
						mde = move_direction_enum.RIGHT;
						how_many_to_check_or_move = 2;
						break;	
					case 3:
						mde = move_direction_enum.DOWN;
						how_many_to_check_or_move = 2;
						break;
					case 4:
						mde = move_direction_enum.LEFT;
						how_many_to_check_or_move = 1;
						which_blank_to_check_or_move = higher_or_more_to_the_left_node;
						break;
					case 5:
						mde = move_direction_enum.UP;
						how_many_to_check_or_move = 1;
						which_blank_to_check_or_move = higher_or_more_to_the_left_node;
						break;
					case 6:
						mde = move_direction_enum.RIGHT;
						how_many_to_check_or_move = 1;
						which_blank_to_check_or_move = higher_or_more_to_the_left_node;
						break;
					case 7:
						mde = move_direction_enum.DOWN;
						how_many_to_check_or_move = 1;
						which_blank_to_check_or_move = higher_or_more_to_the_left_node;	
						break;
					case 8:
						mde = move_direction_enum.LEFT;
						how_many_to_check_or_move = 1;
						which_blank_to_check_or_move = lower_or_more_to_the_right_node;
						break;
					case 9:
						mde = move_direction_enum.UP;
						how_many_to_check_or_move = 1;
						which_blank_to_check_or_move = lower_or_more_to_the_right_node;
						break;
					case 10:
						mde = move_direction_enum.RIGHT;
						how_many_to_check_or_move = 1;
						which_blank_to_check_or_move = lower_or_more_to_the_right_node;
						break;
					case 11:
						mde = move_direction_enum.DOWN;
						how_many_to_check_or_move = 1;
						which_blank_to_check_or_move = lower_or_more_to_the_right_node;
						break;
					}
				}

				//if there aren't 2 blanks (=1 blank on board), only 4 moves are possible
				else {
					switch (i){
					case 0:
						mde = move_direction_enum.LEFT;
						how_many_to_check_or_move = 1;
						which_blank_to_check_or_move = 1;
						break;
					case 1:
						mde = move_direction_enum.UP;
						how_many_to_check_or_move = 1;
						which_blank_to_check_or_move = 1;
						break;
					case 2:
						mde = move_direction_enum.RIGHT;
						how_many_to_check_or_move = 1;
						which_blank_to_check_or_move = 1;
						break;
					default:
						mde = move_direction_enum.DOWN;
						how_many_to_check_or_move = 1;
						which_blank_to_check_or_move = 1;
					}
				}

				//check if move is optional, and if not continue

				if (!n.check_move_allowed(mde, how_many_to_check_or_move, which_blank_to_check_or_move)) {
					//						System.out.println("continuing");
					continue;
				}
				
				Node temp = new Node(n);
				nodes_created++;

				temp.move(mde, which_blank_to_check_or_move, how_many_to_check_or_move);
				temp.pred = n;
				
				if(closed_list.containsKey(temp)) {
					continue;
				}
				
				else {
					Node _result = Limited_DFS(temp, goal, limit-1, closed_list);
					
					if(_result.getName().contentEquals("cutoff")) {
						isCutOff = true;
					}
					else if(_result.getName().contentEquals("found answer")) { //this is supposed to be _resulst != fail, but I think this will do the same
						return _result;
					}
				}
				
			}//end for loop possible_moves
			
			closed_list.remove(n);
			if (isCutOff) {
				n.setName("cutoff");
				return n;
//				return "cutoff";
			}
			else {
				n.setName("fail");
				return n;
//				return "fail";
			}

		}



	}
}
