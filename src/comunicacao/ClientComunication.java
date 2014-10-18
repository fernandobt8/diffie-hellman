package comunicacao;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import utils.SerializationUtils;
import dto.DiffieHellmanDto;
import dto.PublicInfoDto;

public class ClientComunication {

	private Socket skt;

	public ClientComunication(String ip) {
		try {
			this.skt = new Socket(ip, 1234);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initConection() {
		try {
			InputStream inputStream = this.skt.getInputStream();
			OutputStream outputStream = this.skt.getOutputStream();

			DiffieHellmanDto hellmanDto = new DiffieHellmanDto();
			hellmanDto.print();

			SerializationUtils.serialize(hellmanDto.getPublicInfoDto(), outputStream);

			PublicInfoDto publicInfoReceived = (PublicInfoDto) SerializationUtils.deserialize(inputStream);
			System.out.println("\nRecebido: ");
			publicInfoReceived.print();

			hellmanDto.generateK(publicInfoReceived.getPublicKey());

			MessageManager messagemManager = new MessageManager(inputStream, outputStream, hellmanDto);
			Thread listen = new Thread(messagemManager);
			listen.start();
			messagemManager.sendMessage();

			inputStream.close();
			outputStream.close();
			messagemManager.terminate();
			listen.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
