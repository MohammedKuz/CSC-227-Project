import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class FileHandler {
	
	public static void genRandomFile(int processes) {
		Random rand = new Random();
		File f = new File("processes.txt");
			try {
				f.createNewFile();
				FileWriter fw = new FileWriter(f.getPath());
				System.out.println(f.getPath());
				for (int i=0;i<processes;i++) {
					int burstMul = rand.nextInt(10)+1; // how many bursts?
					int currentMemUsed = 0;
					int pid = i;
					int arrTime = rand.nextInt(80)+1;
					fw.write(pid+"\t"+arrTime+"\t");
					for (int j = 0; j < burstMul; j++) {
						if (j%2==0) {
							int burstTime = rand.nextInt(100)+10;
							fw.write("CPU\t"+burstTime+"\t"); // CPU burst
						} else {
							int burstTime = rand.nextInt(60)+20;
							fw.write("IO\t"+burstTime+"\t"); // IO burst
						}
						int mem = rand.nextInt(200)+5;
						if (rand.nextInt(2)<0.5) {
							if (mem*-1 < currentMemUsed) { // never free more than used memory
								mem = 0;
							} else {
								mem *= -1;
							}
						}
						currentMemUsed+=mem;
						fw.write(mem+"\t"); // memory
					}
					currentMemUsed = 0;
					fw.write("\n");
				}
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
	}
	
	public static PriorityQueue readFile() {
		PriorityQueue jobQueue = new PriorityQueue();
		try {
			File f = new File("processes.txt");
			Scanner reader = new Scanner(f);
			
			while (reader.hasNextLine()) {
				String[] proc = reader.nextLine().split("\t");
				// store PID and Arrival Time
				PCB process = new PCB(Integer.parseInt(proc[0]), Integer.parseInt(proc[1]));
				for (int i=2; i<proc.length-1; i+=3) {
					String cpuOrIO = proc[i];
					int burstTime = Integer.parseInt(proc[i+1]);
					int mem = Integer.parseInt(proc[i+2]);
					Bursttype burst_type = (cpuOrIO.equalsIgnoreCase("CPU")) ? Bursttype.cpuBurst : Bursttype.ioBurst;
					Burst burst = new Burst(burst_type, mem, burstTime);
					process.addBurst(burst);
				}
				
				jobQueue.enqueue(process, process.getArrivalTime());
			}
			
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return jobQueue;
	}
	
//	For testing purposes
	public static void main(String[] args) {
		genRandomFile(1000);
		PriorityQueue jobQueue = readFile();
		PQNode tmp = jobQueue.serve();
		System.out.println(tmp.data.getArrivalTime());
		LinkedList<Burst> bursts = tmp.data.getBursts();
		System.out.println(bursts);
	}
}
