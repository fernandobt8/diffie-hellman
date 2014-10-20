package comunicacao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageManager {
	public static String readConsole() {
		try {
			return new BufferedReader(new InputStreamReader(System.in)).readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static void print(String message) {
		System.out.println(" \n" + new SimpleDateFormat("HH:mm:ss.S a").format(new Date()) + "\n" + message);
	}
}
