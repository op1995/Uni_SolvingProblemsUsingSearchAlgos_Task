import java.util.Hashtable;
import java.util.Stack;

public class IDAstar {

	public static void runIDAstar(fileParams _fileParams, Node source, Node goal) {
		long start_time = System.currentTimeMillis();
		int nodes_created = 1;

		source.updateMyManhattanDistance(goal);
		
		Hashtable<Integer, Node> open_list = new Hashtable<Integer, Node>();
		Stack<Node> _stack = new Stack<>(); 
		
		double threshold = source.myManhattanDistance;

		while(threshold != Double.POSITIVE_INFINITY) {
			double minF = Double.POSITIVE_INFINITY;
			
			source.setName(""); //need to erase the name\marking of source as "out" every time we get here, which is when the stack is empty and we update the threshold
			
			_stack.push(source);
			open_list.put(source.hashCode(), source);

			while(!_stack.isEmpty()) {
				
				if (_fileParams.openList) {
					System.out.println("Open-list: \n");
					//				System.out.println(open_list.toString());
					open_list.values().forEach(System.out::println);
				}
				
				Node n = _stack.pop();
				if(n.getName().contentEquals("out")) {
					open_list.remove(n.hashCode());
				}
				else {
					//mark n as out and L.insert(n)
					n.setName("out");
					_stack.push(n);


					int possible_moves = source.two_blanks_exist? 12 : 4;

					//if there are two blanks, check which is higher or more to the left, so the predecessor order is as we were told to do in the task instructions.
					int higher_or_more_to_the_left_node = 1;
					int lower_or_more_to_the_right_node = 1;
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


						if(source.two_blanks_exist) {
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
							//							System.out.println("continuing");
							continue;
						}

						//if move can be done, create a node for that, and check if it is in closed_list or line
						Node temp = new Node(n);
						nodes_created++;
						temp.pred = n;

						temp.move(mde, which_blank_to_check_or_move, how_many_to_check_or_move);
						temp.updateMyManhattanDistance(goal);

						//check if the move didn't bring us over the threshold
						if(temp.getCost_to_me() + temp.myManhattanDistance > threshold) {
							minF = Math.min((temp.getCost_to_me() + temp.myManhattanDistance), minF);
							continue;
						}
						//else if newly created temp node is in the open list and is marked out
						else if(open_list.containsKey(temp.hashCode()) && open_list.get(temp.hashCode()).getName().contentEquals("out")) {
							continue;
						}
						//else if newly created temp node is in the open list and is NOT marked out, then
						else if(open_list.containsKey(temp.hashCode()) && !open_list.get(temp.hashCode()).getName().contentEquals("out")) {
							//if price to temp is cheaper then price found to that same mode (how the board is)
							if(open_list.get(temp.hashCode()).getCost_to_me() > temp.getCost_to_me()) {
								open_list.remove(temp.hashCode());
								_stack.remove(temp);
							}
							//otherwise, meaning this board mode was already found before, but not marked out, and found before for cheaper price, skip this
							else {
								continue;
							}

						}
						
						//getting here mean we didn't get continue for anything, so check if it is goal. 
						// If it is, we are done. Print out the result.
						// If it isn't, add it to the stack and open list and go on.
						if(temp.equals(goal)) {
							answer.output_answer(temp, nodes_created, _fileParams.print_runtime, answer.getTimeInSeconds(start_time, System.currentTimeMillis()), true);
							return; //TODO - check, will this work? I think so, but not sure.
						}
						else {
							_stack.push(temp);
							open_list.put(temp.hashCode(), temp);
						}
					}

				}
			}//end while stack isn't empty
			//getting here means the threshold has been met, and we should go on to the next iterative depth, with the new minF as the threshold
			//Though, if the minF stayed as infinity, that means we couldn't find the answer and should return false (=print to output no path);
			threshold = minF;
		}
		//getting here means minF was = infinity, meaning we didn't find an answer (there is no answer, as this algorithm is complete).
		answer.output_answer(null, nodes_created, _fileParams.print_runtime, answer.getTimeInSeconds(start_time, System.currentTimeMillis()), false);
	}


}
