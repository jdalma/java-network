
# 네트워크 프로그래밍 시작

네트워크 통신의 목적은 서로 다른 애플리케이션 간에 정보를 전송하는 것으로 **버퍼** 와 **채널** 을 이용하면 용이하다.  

> 서버는 IP 주소와 함께 **머신** 에 할당된다.  
> 하나 이상의 서버는 머신에서 지정된 시간에 실행 가능하며, 운영체제는 머신에 대한 서비스 요청을 수신할 때 포트 번호도 받을 것이다.  
> 서버는 IP 주소와 포트 번호의 조합에 의해 식별되니 포트 번호는 요청을 전달받는 장소에서 서버를 식별할 것이다.  
> IP 주소는 InetAddress 클래스로 나타낼 수 있고, 주소는 식별된 특정 주소로 전송되는 **유니 캐스트** 이거나, 메시지가 하나 이상의 주소로 전송되는 **멀티 캐스트** 일 수 있다.  
> public 생성자가 없으며, 인스턴스를 얻으려면 여러가지 정적 get 메서드를 사용해야 한다.  

## NIO

- **채널** : 애플리케이션간의 통신을 단순화하는 추상적 개념이며 애플리케이션 간의 데이터 흐름을 나타낸다.
  - FileChannel
  - DatagramChannel : UDP 통신을 지원
  - SocketChannel : TCP 클라이언트와 함께 사용
  - ServerSocketChannel : TCP 서버와 함께 사용
- **버퍼** : 애플리케이션에 의해서 처리될 때까지 임시적으로 정보를 보유하며 데이터를 처리하기 위한 채널과 함께 동작한다.
  - 문자, 정수, 부동소수점 같은 기본 데이터 타입을 지원하는 다양한 버퍼 클래스가 있다.
- **셀렉터** : 다중 채널을 처리하기 위한 싱글 스레드를 허용하는 기술이다.

채널과 버퍼는 통상적으로 연관돼 있으며 **데이터는 버퍼에서 채널 혹은 채널에서 버퍼로 전달될 수 있다.**  

## 소켓

통신을 하기 위해 메시지를 송수신하는 데 있어서 **소켓** 이라고 하는 소프트웨어를 사용한다.  
하나의 소켓은 클라이언트에 위치하고, 다른 소켓은 서버에 위치한다.

1. 데이터그램 소켓
2. TCP를 주로 사용하는 스트림 소켓
3. IP레벨에서 동작하는 로우 소켓

```java
try(ServerSocket serverSocket = new ServerSocket(6000)) {
  System.out.println("Waiting for connection ...");
  Socket clientSocket = serverSocket.accept();
  System.out.println("Connected to client !!!");
  
  // 클라이언트의 메시지를 읽기 위한 BufferedReader를 생성 (InputStream)
  try(BufferedReader br = new BufferedReader(
    new InputStreamReader(clientSocket.getInputStream()))
  ) {
    // ...
    // 클라이언트에 응답하기 위한 PrintWriter를 생성 (OutputStream)
    PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
  }
} catch (IOException ex) {
    // ...
}
```