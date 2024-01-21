package basic;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class URLConnectionTest {

    @Test
    void connection() throws IOException {

        URL url = new URL("http://www.google.com");

        String content1 = toContentByBufferedReader(url.openConnection().getInputStream());
        String content2 = toContentByChannelAndBuffer(url.openConnection().getInputStream());
        System.out.println(content1.length());
        System.out.println(content2.length());
    }

    private String toContentByBufferedReader(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        StringBuilder sb = new StringBuilder();
        String line;
        while((line = bufferedReader.readLine()) != null) {
            sb.append(line);
        }
        bufferedReader.close();
        return sb.toString();
    }

    private String toContentByChannelAndBuffer(InputStream inputStream) throws IOException {
        ReadableByteChannel channel = Channels.newChannel(inputStream);
        ByteBuffer buffer = ByteBuffer.allocate(64);
        StringBuilder sb = new StringBuilder();
        while(channel.read(buffer) > 0) {
            sb.append(new String(buffer.array()));
            buffer.clear();
        }
        return sb.toString();
    }
}
