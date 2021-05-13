//import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
//import java.util.Iterator;
//import java.util.Scanner; // Import the Scanner class to read text files

public class Ex1 {

	public static void main(String[] args) {
		try {
			fileParams _fileParams = new fileParams();
			Node source = new Node(_fileParams.board_initial, _fileParams.blank_counter, _fileParams.blank1_location, _fileParams.blank2_location);
			Node goal = new Node(_fileParams.board_goal, _fileParams.blank_counter, _fileParams.blank1_location, _fileParams.blank2_location);
			
			switch(_fileParams.Algo) {
			case "BFS":
				BFS.runBFS(_fileParams, source, goal);
				break;
			
			case "A*":
				Astar.runAstar(_fileParams, source, goal);
				break;
				
			case "DFID":
				DFID.runDFID(_fileParams, source, goal);
				break;
			
			
			
			case "IDA*":

				break;
			
			case "DFBnB":
				
				break;
			
			default:
				System.out.println("Check Algo!");

			}


		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}

}
