
public class OS {
    public static void main(String[] args){
        CPU cpu = new CPU();
    	cpu.long_term_scheduler.start();
		cpu.short_term_scheduler.start();
    }
}