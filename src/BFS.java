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
		
		
		//for the open list, I choose to use BOTH a queue-linkedList, AND a HashTable, to use the advantages of both		
		Queue<Node> open_list_queue = new LinkedList<Node>();
		open_list_queue.add(source);
		Hashtable<Node, Boolean> open_list = new Hashtable<Node, Boolean>();
		open_list.put(source, true);
		
		Hashtable<Node, Boolean> closed_list = new Hashtable<Node, Boolean>();
		
		int nodes_created = 1; //starting from 1 because already created start node
		Node temp = null;
		
		String route = "";
		boolean p = false;
		
		int possible_moves = source.two_blanks_exist? 12 : 4;
		
		while(!open_list_queue.isEmpty()) {
			if (_fileParams.openList) {
				System.out.println("Open-list: \n");
				open_list_queue.stream().forEach(System.out::println);
			}
			//remove the first node on line in the open_list, both in the queue and the Hashtable
			Node popped_node =open_list_queue.remove();
			open_list.remove(popped_node);
			
			//put the popped_node in the closed list
			closed_list.put(popped_node, true);
			
			//if there are two blanks, check which is higher or more to the left, so the predecessor order is as we were told to do in the task instructions.
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
				
				Node temp2 = new Node(temp);
				route = "";
				String new_addition_to_route = "";
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
//				if(route.contentEquals("8R-11D-7L-8U-")) {
//					System.out.println("at answer!");
//				}
				if(route.startsWith("5D-3R-3U-2&5U-7L-7L-5&6D-2R-4D-3R-2U-4L-4L")) {
					System.out.println(route);
					p = true;
				}
				
				if(p) {
					System.out.println(route + "	nodes_created = " + nodes_created + "	closed_list.size() = " + closed_list.size());	
				}
				
				if(!(open_list.containsKey(temp) || closed_list.containsKey(temp))) {
					if(temp.equals(goal)) {
						found_answer = true;
						
						
						
						
						
						
						
//						System.out.println("DONE! closed_list.size() = " + closed_list.size());
						
						
						
						
						
						
						
						
						
						
						
						long end_time = System.currentTimeMillis();
						double elapsedTimeInSecond = answer.getTimeInSeconds(start_time, end_time);
						
						answer.output_answer(temp, nodes_created, _fileParams.print_runtime, elapsedTimeInSecond, true);
						open_list_queue.clear();
						i = possible_moves;
					}
					else {
						open_list_queue.add(temp);
						open_list.put(temp, true);
					}
					
				}
				else {
					if(open_list.containsKey(temp) && closed_list.containsKey(temp)) {
						System.out.println("open_list.containsKey(temp) && closed_list.containsKey(temp)");
					}
					
					else if(open_list.containsKey(temp)) {
						System.out.println("open_list.containsKey(temp)");
					}
					else if(closed_list.containsKey(temp)) {
						System.out.println("closed_list.containsKey(temp)");
					}
					else {
						System.out.println("not supposed to get here");
					}
				}

			}
		}
		if (!found_answer) {
			answer.output_answer(null, nodes_created, _fileParams.print_runtime, answer.getTimeInSeconds(start_time, System.currentTimeMillis()), false);
		}

	}
}
