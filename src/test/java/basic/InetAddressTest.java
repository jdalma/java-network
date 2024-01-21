package basic;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.assertj.core.api.Assertions.*;

public class InetAddressTest {

    @Test
    void inetAddress() throws IOException {

        assertThatThrownBy(() -> InetAddress.getByName("notexist"))
                .isInstanceOf(UnknownHostException.class);

        InetAddress address = InetAddress.getByName("www.naver.com");
        assertThat(address.getHostAddress()).isEqualTo("223.130.200.104");
    }
}
