import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

public class answer {

	public static void output_answer(Node last, int nodes_created, boolean print_runtime, double elapsedTimeInSecond) {
		File _file = new File("output.txt");
		try {
			Files.deleteIfExists(_file.toPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			if (!_file.createNewFile()) {
				System.out.println("Output file already exists.");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			FileWriter myWriter = new FileWriter("output.txt");

			String route = "";			
			int cost = 0;

			Node temp = last;
			
			//check if the received state was the final state, so no route exists
			if(!(temp.pred==null)) {
				boolean first = true;
				String new_addition_to_route = "";
				while(temp.pred!=null) {
					
					if(temp.moved_2_to_get_to_me) {
						new_addition_to_route = temp.numbers_moved_to_get_to_me[0] + "&" + temp.numbers_moved_to_get_to_me[1] + moveIntToOutputForamt(temp.move_made_to_get_to_me);
						cost+=7;
					}
					
					else {
						new_addition_to_route = temp.numbers_moved_to_get_to_me[0] + moveIntToOutputForamt(temp.move_made_to_get_to_me);
						cost+=5;
					}
					
					if(first) {
						route = new_addition_to_route;
						first = false;
					}
					else {
						route = new_addition_to_route + "-" + route;
					}
					temp = temp.pred;
				}
			}
			
			
			

			myWriter.write(route + "\n");
			myWriter.write("Num: " + nodes_created + "\n");
			myWriter.write("Cost: " + cost + "\n");
			if(print_runtime) myWriter.write(elapsedTimeInSecond + " seconds");
			
			myWriter.close();



		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	static String moveIntToOutputForamt(int input) {
		String answer ="";

		switch (input) {
		case 0:
			answer = "L";
			break;
		case 1:
			answer = "U";
			break;
		case 2:
			answer = "R";
			break;
		default:
			answer = "D";
		}

		return answer;
	}


	static double getTimeInSeconds(long start, long end) {
		long elapsedTime = end - start;
		
		long elapsedTimeInSecond_long = TimeUnit.SECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS);
		
		   
		
        double elapsedTimeInSecond = (double) elapsedTime / 1_000_000_000;

		return elapsedTimeInSecond;

	}
}
