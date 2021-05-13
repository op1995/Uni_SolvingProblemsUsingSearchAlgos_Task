import java.util.Hashtable;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Astar {

	static void runAstar(fileParams _fileParams, Node source, Node goal) {
		long start_time = System.currentTimeMillis();
		boolean found_answer = false;
		
		source.myManhattanDistance = heuristic.generateManhattanDistance(source, goal);
		goal.myManhattanDistance = 0;

		Queue<Node> open_list_pq = new PriorityQueue<>();
		open_list_pq.add(source);

		Hashtable<Node, Integer> open_list = new Hashtable<Node, Integer>();
		open_list.put(source, 0);

		//closed list is nodes reached, and how much it cost to get to them
		Hashtable<Node, Integer> closed_list = new Hashtable<Node, Integer>();
		closed_list.put(source, 0);

//		if(closed_list.containsKey(source)) {
//			System.out.println("contains works for closed_list");
//		}
		
		
		int nodes_created = 1; //starting from 1 because already created start node
		Node temp = null;

		String route = "";
		String new_addition_to_route = "";
		boolean print_route = false;
		
		
		int possible_moves = source.two_blanks_exist? 12 : 4;
		int higher_or_more_to_the_left_node = 1;
		int lower_or_more_to_the_right_node = 1;

		

		while(!open_list_pq.isEmpty()) {
			if (_fileParams.openList) {
				System.out.println("Open-list: \n");
				//				System.out.println(open_list.toString());
				open_list_pq.stream().forEach(System.out::println);
			}

			//			Node popped_node = open_list.poll();
			//pop out the least expensive node, and take it off the open_list hash as well. Also put it in the closed_list.
			Node popped_node = open_list_pq.poll();
			open_list.remove(popped_node);
			
//			if(closed_list.containsKey(popped_node)) {
//				System.err.println("Should never get here. Astar. A node that has already been checked, is being checked again.");
//			}
			closed_list.put(popped_node, popped_node.getCost_to_me());

			//reaching the goal for the first time is supposed to be the cheapest way to get to it,
			//as I check only after adding all the new options (all possible moves from previous states)
			//so the first time the goal is found, it is the cheapest
			if(popped_node.equals(goal)) {

				found_answer = true;
				long end_time = System.currentTimeMillis();
				
				double elapsedTimeInSecond = answer.getTimeInSeconds(start_time, end_time);
				
				answer.output_answer(popped_node, nodes_created, _fileParams.print_runtime, elapsedTimeInSecond, true);
				open_list.clear();
				open_list_pq.clear();
			}

			//if goal wasn't found, try every possible move
			//any node that hasn't been created yet - add to the open list (line)
			//any node that was already seen before - check if I got there for cheaper now, and if so
			//	add it to the open list and update the closed list
			else {
				if(popped_node.two_blanks_exist) {
					//if the location is smaller, necessarily it is either above(higher) or at same height but more to the left)
					higher_or_more_to_the_left_node = popped_node.location_of_blank1<popped_node.location_of_blank2? 1 : 2;
					lower_or_more_to_the_right_node = popped_node.location_of_blank1<popped_node.location_of_blank2? 2 : 1;
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

					if (!popped_node.check_move_allowed(mde, how_many_to_check_or_move, which_blank_to_check_or_move)) {
						//						System.out.println("continuing");
						continue;
					}

					//if move can be done, create a node for that, and check if it exists in the closed_list
					//if it doesn't - 
					//	- if it exists in open list
					//		-if it is more expensive in the open list, replace it
					//	- else - add it to open
					//					
					//if it does - continue. do nothing.
					// that is because of the Lemma - whenever A* selects a node n for expansion, the optimal path to that node has been found.
					//meaning that if the node is already on the closed_list, there is no need to check it again EVER



					//OLD EXPLANATION, WRONG - delete after making sure everything works
					//
					//check if I got there for cheaper (meaning the value in closed_list is higher)
					//if so
					// 	- check if this is already in the open_list
					//			- if it is
					//				- if it is there with a higher price
					//					- replace it
					//					- remove it from closed_list
					//			- if it isn't
					//				- add it to open list
					//				- remove it from closed_list

					// -
					temp = new Node(popped_node);
					nodes_created++;
					temp.pred = popped_node;
//					System.out.println("Direction: " + mde + " how many to move: " + how_many_to_check_or_move + " blank to move: " + which_blank_to_check_or_move + "\n");
//					System.out.println("Num (nodes created): " + nodes_created);
//					System.out.println("move " + mde);
					
					String before = temp.toString();
					
					temp.move(mde, which_blank_to_check_or_move, how_many_to_check_or_move);
					
//					System.out.println("before - \n" + before);
//					System.out.println("after - \n" + temp.toString());
					
					Node temp2 = new Node(temp);
					route = "";
					while(temp2.pred!=null) {
						if(temp2.moved_2_to_get_to_me) {
							new_addition_to_route = temp2.numbers_moved_to_get_to_me[0] + "&" + temp2.numbers_moved_to_get_to_me[1] + answer.moveIntToOutputForamt(temp2.move_made_to_get_to_me);
						}
						else {
							new_addition_to_route = temp2.numbers_moved_to_get_to_me[0] + answer.moveIntToOutputForamt(temp2.move_made_to_get_to_me);
						}

						route = new_addition_to_route + "-" + route;
						
						temp2 = temp2.pred;
					}
					
					
					if(route.contentEquals("5D-3R-3U-2&5U-") || route.contentEquals("5D-3R-3U-5&2U-")) {
						System.out.println("4 firsts. Nodes created = " + nodes_created);
					}
					else if(route.contentEquals("5D-3R-3U-2&5U-7L-") || route.contentEquals("5D-3R-3U-5&2U-7L-") ) {
						System.out.println("5 firsts. Nodes created = " + nodes_created);
					}
					else if(route.contentEquals("5D-3R-3U-2&5U-7L-7L-") || route.contentEquals("5D-3R-3U-5&2U-7L-7L-") ) {
						System.out.println("6 firsts. Nodes created = " + nodes_created);
					}
					else if(route.contentEquals("5D-3R-3U-5&2U-7L-7L-5&6D-") || route.contentEquals("5D-3R-3U-5&2U-7L-7L-6&5D-") ) {
						System.out.println("7 firsts. Nodes created = " + nodes_created);
						System.out.println("open_list.size() = " + open_list.size());
					}
					else if(route.contentEquals("5D-3R-3U-5&2U-7L-7L-5&6D-2R-") || route.contentEquals("5D-3R-3U-5&2U-7L-7L-6&5D-2R-") ) {
						System.out.println("8 firsts. Nodes created = " + nodes_created);
						System.out.println("open_list.size() = " + open_list.size());
//						print_route = true;
					}
					else if(route.contentEquals("5D-3R-3U-5&2U-7L-7L-5&6D-2R-4D-") || route.contentEquals("5D-3R-3U-5&2U-7L-7L-6&5D-2R-4D-") ) {
						System.out.println("9 firsts. Nodes created = " + nodes_created);
					}
					else if(route.contentEquals("5D-3R-3U-5&2U-7L-7L-5&6D-2R-4D-3R-") || route.contentEquals("5D-3R-3U-5&2U-7L-7L-6&5D-2R-4D-3R-") ) {
						System.out.println("10 firsts. Nodes created = " + nodes_created);
					}
					else if(route.contentEquals("5D-3R-3U-5&2U-7L-7L-5&6D-2R-4D-3R-2U-4L-") || route.contentEquals("5D-3R-3U-5&2U-7L-7L-6&5D-2R-4D-3R-2U-4L-") ) {
						System.out.println("11 firsts. Nodes created = " + nodes_created);
					}
					else if(route.contentEquals("5D-3R-3U-5&2U-7L-7L-5&6D-2R-4D-3R-2U-4L-4L-") || route.contentEquals("5D-3R-3U-5&2U-7L-7L-6&5D-2R-4D-3R-2U-4L-4L-") ) {
						System.out.println("12 firsts. Nodes created = " + nodes_created);
					}
					else if(route.contentEquals("5D-3R-3U-5&2U-7L-7L-5&6D-2R-4D-3R-2U-4L-4L-5&6U-") || route.contentEquals("5D-3R-3U-5&2U-7L-7L-6&5D-2R-4D-3R-2U-4L-4L-5&6U-") || route.contentEquals("5D-3R-3U-5&2U-7L-7L-5&6D-2R-4D-3R-2U-4L-4L-6&5U-") || route.contentEquals("5D-3R-3U-5&2U-7L-7L-6&5D-2R-4D-3R-2U-4L-4L-6&5U-") ) {
						System.out.println("13 firsts - ALL!. Nodes created = " + nodes_created);
					}
					
					
					if(print_route) System.out.println(route);
					
					if(!closed_list.containsKey(temp)) {

						if(open_list.contains(temp)) {
							if (temp.getCost_to_me() < open_list.get(temp)) {
								open_list.remove(temp);
								open_list_pq.remove(temp); //as the pq.remove method uses the equals() method. it will remove the node with the same board mode as temp

								//manhattan needs update after move. needs the goal node to update manhattan, available here but not in the move function.
								//only updating the manhattan distance here, as it can be expansive (time wise), and if not inserting it, no need to update it.
								temp.myManhattanDistance = heuristic.generateManhattanDistance(temp, goal);

								
								open_list.put(temp, temp.getCost_to_me());
								open_list_pq.add(temp); //adding again, with an updated pred and price_to_get_to_me
							}
						}
						else {
							//manhattan needs update after move. needs the goal node to update manhattan, available here but not in the move function.
							//only updating the manhattan distance here, as it can be expansive (time wise), and if not inserting it, no need to update it.
							temp.myManhattanDistance = heuristic.generateManhattanDistance(temp, goal);

							open_list.put(temp, temp.getCost_to_me());
							open_list_pq.add(temp);
						}
					}

				}



			}



		}
		//getting here with found_answer = false, means no answer was found and I need to print that to the output file
		if(!found_answer) {
			answer.output_answer(null, nodes_created, _fileParams.print_runtime, answer.getTimeInSeconds(start_time, System.nanoTime()), false);
		}

	}

}
