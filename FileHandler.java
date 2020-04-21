import java.util.LinkedList;
import java.util.Random;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Queue;

public class FileHandler {

	public static void genRandomFile(int processes) {
		Random rand = new Random();
		File f = new File("processes.txt");
		try {
			f.createNewFile();
			FileWriter fw = new FileWriter(f.getPath());
			for (int i = 1; i < processes + 1; i++) {
				int burstMul = rand.nextInt(4) + 1; // how many bursts?
				int currentMemUsed = 0;
				int pid = i;
				int arrTime = rand.nextInt(80) + 1;
				fw.write(pid + "\t" + arrTime + "\t");
				for (int j = 0; j < burstMul; j++) {
					int cpuBurst = rand.nextInt(100) + 10;
					fw.write(cpuBurst + "\t"); // CPU burst

					int mem = rand.nextInt(200) + 5;
					if (Math.random() < 0.5) {
						if (mem > currentMemUsed) { // never free more than used memory
							mem = 0;
						} else {
							mem *= -1;
						}
					}
					currentMemUsed += mem;
					if (currentMemUsed > 680)
						mem = 0;

					fw.write(mem + "\t"); // memory

					int ioBurst = rand.nextInt(60) + 20;
					fw.write(ioBurst + "\t"); // IO burst
				}
				currentMemUsed = 0;
				fw.write("\n");
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static LinkedList<PCB> readFile() {
		String line;
		LinkedList<PCB> processes = new LinkedList<>();
		try {
			BufferedReader bfr = new BufferedReader(new FileReader("processes.txt"));
			// bfr.readLine();
			while ((line = bfr.readLine()) != null) {
				String[] processesInfo = line.split("\t");
				int sizeOfArray = processesInfo.length;
				int pid = Integer.parseInt(processesInfo[0]);
				int arrTime = Integer.parseInt(processesInfo[1]);
				Queue<Burst> bursts = new LinkedList<>();
				for (int i = 2; i < sizeOfArray; i += 3) {
					int cpu = Integer.parseInt(processesInfo[i]);
					if (i + 1 < sizeOfArray) {
						int memory = Integer.parseInt(processesInfo[i + 1]);
						Burst cpuBurst = new Burst(burstType.CPUBurst, memory, cpu);
						bursts.add(cpuBurst);
						if (i + 2 < sizeOfArray) {
							int io = Integer.parseInt(processesInfo[i + 2]);
							Burst ioBurst = new Burst(burstType.IOBurst, 0, io);
							bursts.add(ioBurst);
						}
					}
				}
				Burst firstBurst = bursts.peek();
				PCB process = new PCB(pid, arrTime, firstBurst.getMemory(), bursts);
				processes.add(process);
			}
			bfr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return processes;
	}

	public static void writeFile(LinkedList<PCB> finishedProcesses) {

		// CPU Status
		int busyTime = CPU.getBusyTime();
		int idleTime = CPU.getIdleTime();

		try {
			File file = new File("output.txt");

			if (!file.exists()) {
				file.createNewFile();
			}

			PrintWriter pw = new PrintWriter(file);

			for (PCB p : finishedProcesses) {
				pw.println(p);
				pw.println("--------------------");
			}
			pw.println("CPU Utilization %" + Math.round((busyTime / (idleTime + busyTime)) * 100));
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}