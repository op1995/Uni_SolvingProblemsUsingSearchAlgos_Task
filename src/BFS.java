import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;


public class BFS {


	static void runBFS(fileParams _fileParams, Node source) {
		long start_time = System.nanoTime();
		
		
		Node goal = new Node(_fileParams.board_goal, _fileParams.blank_counter, _fileParams.blank_location);
		
		Queue<Node> line = new LinkedList<Node>();
		line.add(source);
		Hashtable<Node, Boolean> open_list = new Hashtable<Node, Boolean>();
		open_list.put(source, true);
		Hashtable<Node, Boolean> closed_list = new Hashtable<Node, Boolean>();
//		closed_list.put(source, true);
		
		int nodes_created = 1; //starting from 1 because already created start node
		Node temp = null;
		
		String route = "";
		
		while(!line.isEmpty()) {
			Node popped_node =line.remove();
			closed_list.put(popped_node, true);

			//try each allowed operation
			for (int i = 0; i < 4; i++) {
				move_direction_enum mde = move_direction_enum.LEFT;
				switch (i){
				case 0:
					mde = move_direction_enum.LEFT;
					break;
				case 1:
					mde = move_direction_enum.UP;
					break;
				case 2:
					mde = move_direction_enum.RIGHT;
					break;
				default:
					mde = move_direction_enum.DOWN;
				}

				//check if move is optional, and if not continue
				if (!popped_node.check_move_allowed(mde)) {
//					System.out.println("continuing");
					continue;
				}

				//if move can be done, create a node for that, and check if it is in closed_list or line
				temp = new Node(popped_node);
				nodes_created++;
				temp.pred = popped_node;
				System.out.println(mde + "\n");
				String before = temp.toString();
				temp.move(mde);
//				System.out.println("before - \n" + before);
//				System.out.println("after - \n" + temp.toString());
				
				Node temp2 = new Node(temp);
				route = "";
				while(temp2.pred!=null) {
					route = temp2.number_moved_to_get_to_me + answer.moveIntToOutputForamt(temp2.move_made_to_get_to_me) + "-" + route;				
					temp2 = temp2.pred;
				}
				if(route.contentEquals("8R-11D-7L-8U-")) {
					System.out.println("at answer!");
				}
//				System.out.println(route);
				
				if(!(line.contains(temp) || closed_list.containsKey(temp))) {
//					closed_list.put(temp, true);
					if(temp.equals(goal)) {
						
						double elapsedTimeInSecond = answer.getTimeInSeconds(start_time, System.nanoTime());
						
						answer.output_answer(temp, nodes_created, _fileParams.print_runtime, elapsedTimeInSecond);
						line.clear();
						i = 4;
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

	}
}
