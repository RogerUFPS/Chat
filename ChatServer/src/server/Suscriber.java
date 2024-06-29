package server;

public class Suscriber {
    private String ip;
    private int port;

    public Suscriber(){}

    public Suscriber(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    public String getIp(){
        return this.ip;
    }

    public int getPort(){
        return this.port;
    }
}
