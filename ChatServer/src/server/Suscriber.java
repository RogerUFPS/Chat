package server;

import model.User;

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
 
    @Override
	public boolean equals(Object other){
		if(other == null)
			return false;
		if(!(other instanceof Suscriber))
			return false;
		Suscriber o = (Suscriber)other;
		return this.getIp().equals(o.getIp()) && this.getPort() == o.getPort();
	}
}
