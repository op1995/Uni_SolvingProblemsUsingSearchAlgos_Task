import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;


public class BFS {


	static void runBFS(fileParams _fileParams, Node source, Node goal) {
		long start_time = System.currentTimeMillis();
		boolean found_answer = false;
		
//		Node goal = new Node(_fileParams.board_goal, _fileParams.blank_counter, _fileParams.blank_location);
		
		Queue<Node> line = new LinkedList<Node>();
		line.add(source);
//		Hashtable<Node, Boolean> open_list = new Hashtable<Node, Boolean>();
//		open_list.put(source, true);
		Hashtable<Node, Boolean> closed_list = new Hashtable<Node, Boolean>();
//		closed_list.put(source, true);
		
		int nodes_created = 1; //starting from 1 because already created start node
		Node temp = null;
		
//		String route = "";
		
		int possible_moves = source.two_blanks_exist? 12 : 4;
		
		while(!line.isEmpty()) {
			if (_fileParams.openList) {
				System.out.println("Open-list: \n");
				line.stream().forEach(System.out::println);
			}
			Node popped_node =line.remove();
			closed_list.put(popped_node, true);
			int higher_or_more_to_the_left_node = 1;
			int lower_or_more_to_the_right_node = 1;
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
				
//				//test to see moving of 2 blanks
//				if(popped_node.location_of_blank1 == 7 && popped_node.location_of_blank2 == 11) {
//					System.out.println("Look at me, I'm Mr.Meeseeks");
//				}
				
				//check if move is optional, and if not continue

				if (!popped_node.check_move_allowed(mde, how_many_to_check_or_move, which_blank_to_check_or_move)) {
//					System.out.println("continuing");
					continue;
				}

				//if move can be done, create a node for that, and check if it is in closed_list or line
				temp = new Node(popped_node);
				nodes_created++;
				temp.pred = popped_node;
//				System.out.println("Direction: " + mde + " blank to move: " + which_blank_to_check_or_move + "\n");
//				String before = temp.toString();
				temp.move(mde, which_blank_to_check_or_move, how_many_to_check_or_move);
//				System.out.println("before - \n" + before);
//				System.out.println("after - \n" + temp.toString());
				
//				Node temp2 = new Node(temp);
//				route = "";
//				while(temp2.pred!=null) {
//					route = temp2.number_moved_to_get_to_me + answer.moveIntToOutputForamt(temp2.move_made_to_get_to_me) + "-" + route;				
//					temp2 = temp2.pred;
//				}
//				if(route.contentEquals("8R-11D-7L-8U-")) {
//					System.out.println("at answer!");
//				}
//				System.out.println(route);
				
				if(!(line.contains(temp) || closed_list.containsKey(temp))) {
//					closed_list.put(temp, true);
					if(temp.equals(goal)) {
						found_answer = true;
						
						long end_time = System.currentTimeMillis();
						double elapsedTimeInSecond = answer.getTimeInSeconds(start_time, end_time);
						
						answer.output_answer(temp, nodes_created, _fileParams.print_runtime, elapsedTimeInSecond, true);
						line.clear();
						i = possible_moves;
					}
					else {
						line.add(temp);
					}
					
				}

			}


			//			if(!popped_node.check_move_allowed(move_direction_enum.LEFT)) allowed.
			//			
			//			ArrayList<move_direction_enum> enums = new ArrayList<move_direction_enum>();
			//			enums.add(move_direction_enum.LEFT);
			//			enums.add(move_direction_enum.UP);
			//			enums.add(move_direction_enum.RIGHT);
			//			enums.add(move_direction_enum.DOWN);





//			for (move_direction_enum mde : enums) {
//
//				if (!popped_node.check_move_allowed(mde)) continue; //if move not allowed, continue
//				Node temp = new Node(popped_node);
//				temp.move(mde);
//				if(!(line.contains(temp) &&  ))
//			}

//			for (move_direction_enum de : move_direction_enum.values()) {
//				move_direction_enum mde = de;
//				if (!popped_node.check_move_allowed(de)) continue;
//				Node temp = new Node(popped_node);
//				temp.move(direction_enum);
//				if(!(line.contains(temp) &&  ))
//
//
//			}


		}
		if (!found_answer) {
			answer.output_answer(null, nodes_created, _fileParams.print_runtime, answer.getTimeInSeconds(start_time, System.currentTimeMillis()), false);
		}

	}
}
