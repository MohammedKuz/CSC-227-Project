
public class OS {
    public static void main(String[] args){
        CPU cpu = new CPU();
        try {
        	while(true) {
        	cpu.long_term_scheduler.start();
			Thread.sleep(1000);
			cpu.short_term_scheduler.start();
			Clock.incTime();
			Thread.sleep(1000);
        	}
        
        } catch (InterruptedException e) {
			
			e.printStackTrace();
		}
    }
}