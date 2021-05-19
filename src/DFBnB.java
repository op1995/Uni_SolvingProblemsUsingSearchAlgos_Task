import java.util.Hashtable;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class DFBnB {

	public static void runDFBnB(fileParams _fileParams, Node source, Node goal) {
		long start_time = System.currentTimeMillis();
		int nodes_created = 1;

		boolean debug_mode = true;
		long last_print_time = System.currentTimeMillis();
		int skipped_illegal_move = 0;
		int skipped_over_threshold = 0;
		int skipped_marked_out = 0;
		int skipped_exists_cheaper = 0;
		
		int found_goal = 0;
		
		int replaced_found_cheaper = 0;
		

		Hashtable<Integer, Node> open_list = new Hashtable<Integer, Node>();
		Stack<Node> _stack = new Stack<>(); 

		_stack.push(source);
		open_list.put(source.hashCode(), source);

		Node result = null;
		double threshold = 1040; //in 2016 it was proven that the upper bound of the 24 puzzle is 205 single-tile moves. 205*5 = 1020. I am guessing we will not be running or expected to be able to run a 24 puzzle, so this is an at least reasonable upper bound.
//		double threshold = source.getMyManhattanDistance();
		

		while(!_stack.empty()) {
			if(debug_mode) {
				double time_passed = answer.getTimeInSeconds(last_print_time, System.currentTimeMillis());
				if(time_passed >= 5) {
					last_print_time = System.currentTimeMillis();
					System.out.println("####################");
					System.out.println("open_list.size() = " + open_list.size());
					System.out.println("skipped_illegal_move = " + skipped_illegal_move);
					System.out.println("skipped_over_threshold = " + skipped_over_threshold);
					System.out.println("skipped_marked_out = " + skipped_marked_out);
					System.out.println("skipped_exists_cheaper = " + skipped_exists_cheaper);
					System.out.println("");
					System.out.println("replaced_found_cheaper = " + replaced_found_cheaper);
					System.out.println("");
					System.out.println("found_goal = " + found_goal);
					System.out.println("####################");
				}
				
			}
			
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
				n.setName("out");
				_stack.push(n);


				//Now we create every possible son of n and sort them according to their cost (f = g + h -> cost to them + cost from them according to heuristic)
				Queue<Node> pq = new PriorityQueue<>();

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
						skipped_illegal_move++;
						continue;
					}

					Node temp = new Node(n);
					nodes_created++;

					temp.move(mde, which_blank_to_check_or_move, how_many_to_check_or_move);
					temp.pred = n;
					temp.updateMyManhattanDistance(goal);

					pq.add(temp);
				}//finished creating all son nodes
				//for each son, we check the following several possible situations -

				//I create a stack, to put all polled node from the priorityQueue in, in reverse order, to put them in open_list and _stack afterwards
				Stack<Node> _stack_sons = new Stack<>(); 

				while(!pq.isEmpty()) {
					Node temp = pq.poll();
					_stack_sons.push(temp);

					//1 - it is predicted to be more expensive to get from this node to the goal, then what the threshold, i.e. what was already found, is at the moment 
					if(temp.getCost_to_me() + temp.getMyManhattanDistance() > threshold) {
						skipped_over_threshold++;
						pq.clear();
						_stack_sons.pop();
						//as nodes are ordered in increasing value of cost to them + heuristic, and this node already passed the threshold,
						//all nodes on the priorityqueue will be more expensive, and should be ignored.
						//I also pop _stack_sons, as it is used to add nodes to open_list and _stack to devolpe late, and temp is at it's top, and we shouldn't add temp to the nodes that will be developed 
					}
					//if we have already seen this node, and it is marked out, skip it.
					//the original psuedo-code said to do "remove g from N"(N being the sons of n), but as I am using a priorityqueue, and temp was already removed from it.
					//there is no need to do so.
					else if(open_list.containsKey(temp.hashCode()) && open_list.get(temp.hashCode()).getName().contentEquals("out")) {
						skipped_marked_out++;
						continue;
					}
					//if we have already seen this node, but it is NOT marked as out, check if got there for cheaper, and if so, remove it from the open list
					//if we didn't get there for cheaper, ignore it
					else if(open_list.containsKey(temp.hashCode()) && !open_list.get(temp.hashCode()).getName().contentEquals("out")) {
						if(open_list.get(temp.hashCode()).getCost_to_me() > temp.getCost_to_me()) {
							open_list.remove(temp.hashCode());
							_stack.remove(temp);
							replaced_found_cheaper++;
						}
						else {
							skipped_exists_cheaper++;
							continue;
						}
					}
					// if we reached here, f(temp) < threshold
					//if it is the goal state, update threshold to it's cost_to_me, and remove all other sons from priority queue (they can't offer lower cost to goal, as they are ordered in increasing cost)
					else if(temp.equals(goal)) { 
						found_goal++;
						threshold = temp.getCost_to_me(); //no need to add heuristic cost, as heuristic of goal should be 0
						result = temp;
						pq.clear();
					}
				}
				//getting here means we went over all sons, and now should put them in reverse order in the open_list and _stack
				while(!_stack_sons.isEmpty()) {
					Node temp = _stack_sons.pop();
					_stack.push(temp);
					open_list.put(temp.hashCode(), temp);
				}

			}


		}
		//getting here means the stack is empty, and that means no more nodes remain to check, so retunr the found answer

		if(result == null) {
			answer.output_answer(null, nodes_created, _fileParams.print_runtime, answer.getTimeInSeconds(start_time, System.currentTimeMillis()), false);
		}
		else {
			answer.output_answer(result, nodes_created, _fileParams.print_runtime, answer.getTimeInSeconds(start_time, System.currentTimeMillis()), true);
		}


	}
}
