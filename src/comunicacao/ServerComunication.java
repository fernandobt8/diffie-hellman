package comunicacao;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;

import utils.SerializationUtils;
import dto.DiffieHellmanDto;

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
			MessageManager.print("Conexão estabelecida!");

			// fica esperando receber um Ya.
			BigInteger publicKeyReceived = (BigInteger) SerializationUtils.deserialize(inputStream);
			MessageManager.print("Recebido Ya: " + publicKeyReceived);

			// gera um Xb aletório Xb < q e um Yb alfa^Xb mod q
			DiffieHellmanDto hellmanDto = new DiffieHellmanDto("b");
			hellmanDto.print();

			// envia seu Yb
			SerializationUtils.serialize(hellmanDto.getPublicKey(), outputStream);

			// gera um k baseado no Ya recebido terminando o acordo de chaves.
			hellmanDto.generateK(publicKeyReceived);

			// inicia um nova tread para ficar recebendo mensagens e outra para enviar mensagens com seu Yb junto.
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
