package tspTest2;

import java.awt.GraphicsEnvironment;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;



public class TrialMain {

	public static void main(String[] args) {
		File file;
		if(args.length > 0)
		{
			file = new File(args[0]);
		}
		else
		{
			JFileChooser chooser = new JFileChooser();
			
			int response = chooser.showOpenDialog(null);
			if(response != JFileChooser.APPROVE_OPTION)
				return;
			
			file = chooser.getSelectedFile();
		}
		
		BufferedReader reader = null;
		try{
			reader = new BufferedReader(new FileReader(file));
		}
		catch(IOException e)
		{
			alert("Error loading file " + e);
			System.exit(1);
		}
		
		
		
		
		int dimension = 0;
		try{
				String line;
			while (!(line = reader.readLine()).equals("NODE_COORD_SECTION")){
				String[] entry = line.split(": ", 1);
				switch(entry[0].trim())
				{
				case "TYPE":
						if(!entry[1].trim().equals("TSP"))
							throw new Exception("File not in TSP format");
						break;
				case "DIMENSION":
						dimension = Integer.parseInt(entry[1]);
						break;
				}
			}
		}
		catch(Exception e)
		{
			alert("Error in fucking header " + e);
			System.exit(1);
		}
		
		ArrayList<City> cities = new ArrayList<City>(dimension);
		
		
		try
		{
			String line;
			while ((line = reader.readLine()) != null && !line.equals("EOF"))
					{
				line =line.trim();
                line.replaceAll("  ", " ");
                line.replaceAll("  ", " ");
                System.out.println(line);
                String[] entry = line.split(" ");
                System.out.println("'"+entry[1]+"'");
                System.out.println("'"+entry[2]+"'");
                cities.add(new City(entry[0], Double.parseDouble(entry[1]), Double.parseDouble(entry[2])));
					}
			
			reader.close();
		}
		catch(Exception e)
		{
			alert("Error parsing data mother fucker " + e);
			System.exit(1);
		}
		//Insert Solution solver here - call it 
		Solver solution = new Solver(cities);
		solution.Solution();
		
		String message = cities.toString();
		System.out.println(message);
		
		
		
		
		
		
		
	}
	private static void alert(String message)
	{
		if(GraphicsEnvironment.isHeadless())
			System.out.println(message);
		else
			JOptionPane.showMessageDialog(null, message);
	}
}
