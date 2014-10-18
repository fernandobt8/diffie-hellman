package comunicacao;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import utils.SerializationUtils;
import dto.DiffieHellmanDto;
import dto.PublicInfoDto;

public class ServerComunication {

	private ServerSocket srvr;

	public ServerComunication() {
		try {
			this.srvr = new ServerSocket(1234);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void waitConetion() {
		try {
			Socket skt = this.srvr.accept();
			OutputStream outputStream = skt.getOutputStream();
			InputStream inputStream = skt.getInputStream();
			System.out.print("Conexão estabelecida!\nRecebido:\n");

			PublicInfoDto publicInfoReceived = (PublicInfoDto) SerializationUtils.deserialize(inputStream);
			publicInfoReceived.print();

			DiffieHellmanDto hellmanDto = new DiffieHellmanDto(publicInfoReceived);
			hellmanDto.print();
			hellmanDto.generateK(publicInfoReceived.getPublicKey());

			PublicInfoDto myPublicInfo = hellmanDto.getPublicInfoDto();
			SerializationUtils.serialize(myPublicInfo, outputStream);

			MessageManager messageManager = new MessageManager(inputStream, outputStream, hellmanDto);
			Thread listen = new Thread(messageManager);
			listen.start();

			messageManager.sendMessage();

			outputStream.close();
			inputStream.close();
			skt.close();
			messageManager.terminate();
			listen.join();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
