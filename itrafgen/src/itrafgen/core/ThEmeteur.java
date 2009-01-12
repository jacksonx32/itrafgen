/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package itrafgen.core;

/**
 *
 * @author sebastienhoerner
 */
import itrafgen.ItrafgenApp;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Vector;
import java.util.concurrent.TimeUnit;





import jpcap.*;

import jpcap.packet.EthernetPacket;
import jpcap.packet.Packet;

import jpcap.packet.IPPacket;

import jpcap.packet.TCPPacket;



public class ThEmeteur implements Runnable

{
	public Packet paquet;
	public int repetition;
	public double separation; //separation reelle plus elever ... calculer l'erreur en bouclant un cable sur les interface avec generateur pro et sans
	public String dest;
	public int vlan ;
	public JpcapSender sender = null;
    public double pause = 0;



	@SuppressWarnings({ "unchecked", "deprecation" })
	public void run() {
        System.out.println("lets go!!!!" + ItrafgenApp.flux.get(vlan).modedemarrage + " and " + ItrafgenApp.flux.get(vlan).modeenvoi);
		long demarrage = 0;
		if(ItrafgenApp.flux.get(vlan).modedemarrage == 1){
			//;
			try {
				TimeUnit.MILLISECONDS.sleep(Integer.parseInt(ItrafgenApp.flux.get(vlan).getJTextField2().getText()));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(ItrafgenApp.flux.get(vlan).modeenvoi == 1){
			//;
			 java.util.Date todayz = new java.util.Date();
			demarrage = Integer.parseInt(ItrafgenApp.flux.get(vlan).getJTextField3().getText()) + todayz.getTime();
		}
		separation = 500;
		ItrafgenApp.launched.set(vlan, new Boolean(true));
		double debit = ItrafgenApp.flux.get(vlan).debit;
		int tailleunitaire = ItrafgenApp.flux.get(vlan).encap.trame.data.length;
		//nb de paquet par seconde
		//int nbpaquetparsec = (debit/(tailleunitaire/1000));
		//exemple 200 packet par seconde -> 1 paquet par 1/200 sec
		//en ms
        
            pause = 1000/((double)debit*1000/((double)(tailleunitaire)));
        
          System.out.println("deb: " +debit + "tailleunitaire:" + tailleunitaire);
		separation = pause;
        System.out.println("sep : " + separation);
		//calcul la séparation pour le bon debit

		paquet = new Packet();//chope la trame



		try {
			sender = JpcapSender.openDevice(ItrafgenApp.intemission);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//paquet.data = "eeeeeeeeeeeeeeeeeeeeeeeeee".getBytes();
		byte[] datasave =  ItrafgenApp.flux.get(vlan).encap.trame.data.clone();
        

		long num = 0;
		//fixer le nombre de paquet
		while(true){
            //ajuste le debit (changement possible via slide bar)
            debit = ItrafgenApp.flux.get(vlan).debit;
            pause = 1000/((double)debit*1000/((double)(tailleunitaire)));
            separation = pause;
            

		    java.util.Date today = new java.util.Date();
            if(ItrafgenApp.flux.get(vlan).modeenvoi == 1){
		    if(today.getTime() >= demarrage){
		    	 ItrafgenApp.flux.get(vlan).em.stop();
		    	 ItrafgenApp.emflux.get(vlan).sender.close();
		    	 ItrafgenApp.launched.set(vlan, new Boolean(false));
		    }
            }
		    //trouve la date ...
		    byte[] dat = ("!itrafgen num:" + num + " vlan:" + vlan + " ts:" + today.getTime() + "").getBytes();
		    
                paquet = ItrafgenApp.flux.get(vlan).encap.trame;
                paquet.data = new byte[datasave.length + dat.length];
		    	System.arraycopy(datasave, 0, paquet.data, 0, datasave.length);
		        System.arraycopy(dat, 0, paquet.data, datasave.length, dat.length);

				//paquet.data = (save + " nb:" + i + "  ts:" + today.getTime()).getBytes();

			//Gener.envoipaquet(sender, paquet, num, vlan);
		    sender.sendPacket(paquet);
            paquet.data = datasave;
           //System.out.println("send");
		   //essais à 10 microseconde
		    try {
				TimeUnit.MILLISECONDS.sleep((int) separation);



			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            if(num == Long.MAX_VALUE){
                num = 0;
            }
            else{
			num++;
            }
		}






	}




}

