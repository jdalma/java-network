package basic;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.*;
import java.util.Collections;
import java.util.Enumeration;

public class JavaNetTest {

    @Test
    void usingTheNetworkInterfaceClass() {
        try {
            Enumeration<NetworkInterface> interfaceEnum = NetworkInterface.getNetworkInterfaces();
            System.out.printf("Name      Display name\n");
            for (NetworkInterface element : Collections.list(interfaceEnum)) {
                System.out.printf("%-8s  %-32s\n",
                        element.getName(), element.getDisplayName());

                Collections.list(element.getInetAddresses())
                    .forEach((inetAddress) -> {
                        System.out.printf("    InetAddress: %s\n", inetAddress);
                    });
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    void Uri() {
        try {
            URI uri1 = new URI("https://www.packtpub.com/books/content/support");
            URI uri2 = new URI("https://en.wikipedia.org/wiki/" + "URL_normalization#Normalization_process");
            URI uri3 = new URI("https", "en.wikipedia.org", "/wiki/URL_normalization", "Normalization_process");

            displayURI(uri1);
            displayURI(uri2);
            displayURI(uri3);
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        }
    }

    private void displayURI(URI uri) {
        System.out.println("URI Information");
        System.out.println("getAuthority: " + uri.getAuthority());
        System.out.println("getScheme: " + uri.getScheme());
        System.out.println("getSchemeSpecificPart: " + uri.getSchemeSpecificPart());
        System.out.println("getHost: " + uri.getHost());
        System.out.println("getPath: " + uri.getPath());
        System.out.println("getQuery: " + uri.getQuery());
        System.out.println("getFragment: " + uri.getFragment());
        System.out.println("getUserInfo: " + uri.getUserInfo());
        System.out.println("normalize: " + uri.normalize());
    }

    @Test
    void Url() throws MalformedURLException {
        Assertions.assertThatThrownBy(() -> new URL("www.packtpub.com"))
                .isInstanceOf(MalformedURLException.class);

        URL url1 = new URL("https://www.packtpub.com/books/content/support");
        URL url2 = new URL("http://pluto.jhuapl.edu/");
        URL url3 = new URL("http", "pluto.jhuapl.edu", 80, "News-Center/index.php");
        URL url4 = new URL("https://en.wikipedia.org/wiki/Uniform_resource_locator#Syntax");
        URL url5 = new URL("https://www.google.com/webhp?sourceid=chrome-instant&ion=1&espv=2&ie=UTF-8#q=url+syntax");

        displayURL(url1);
        displayURL(url2);
        displayURL(url3);
        displayURL(url4);
        displayURL(url5);
    }

    private void displayURL(URL url) {
        System.out.println("URL: " + url);
        System.out.printf("  Protocol: %-32s  Host: %-32s\n", url.getProtocol(), url.getHost());
        System.out.printf("      Port: %-32d  Path: %-32s\n", url.getPort(), url.getPath());
        System.out.printf(" Reference: %-32s  File: %-32s\n", url.getRef(), url.getFile());
        System.out.printf(" Authority: %-32s Query: %-32s\n", url.getAuthority(), url.getQuery());
        System.out.println(" User Info: " + url.getUserInfo());
    }

}
