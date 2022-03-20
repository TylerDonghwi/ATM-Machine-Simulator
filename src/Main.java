import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

	public static void main(String[] args) throws IOException {

		User account1 = new User("Tyler Kim");
		int time = getHourOfDay();

		account1.showMainScreen(time);
	}

	public static int getHourOfDay() {

		Date currentDate = new Date();
		SimpleDateFormat timeFormat = new SimpleDateFormat("kk");
		return Integer.valueOf(timeFormat.format(currentDate));
	}
}
