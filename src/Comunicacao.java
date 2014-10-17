import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;

public class Comunicacao {

	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Digite \"E\" para esperar pedido comunicação.");
		System.out.println("Digite \"S\" para começar um pedido de conexão.");
		String console = readConsole(br);
		BigInteger numero = new BigInteger(console);
		System.out.println(RaizPrimitiva.getRaizPrimitiva(numero));
		if (console.equals("E")) {
			try {
				ServerSocket srvr = new ServerSocket(1234);
				Socket skt = srvr.accept();
				System.out.print("Conexão estabelecida!\n");
				OutputStream outputStream = skt.getOutputStream();
				InputStream inputStream = skt.getInputStream();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (console.equals("S")) {
			try {
				System.out.println("Digite endereço IP para começar um pedido de conexão.");
				console = readConsole(br);
				Socket skt = new Socket(console, 1234);
				InputStream inputStream = skt.getInputStream();
				OutputStream outputStream = skt.getOutputStream();
				DiffeHellmanDto hellmanDto = new DiffeHellmanDto();
				// outputStream.write(seria);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Digite \"S\" para começar um pedido de conexão.");
		}
	}

	public static String readConsole(BufferedReader br) {
		try {
			return br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}
