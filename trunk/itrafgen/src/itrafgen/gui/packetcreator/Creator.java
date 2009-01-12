/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package itrafgen.gui.packetcreator;

/**
 *
 * @author sebastienhoerner
 */

import itrafgen.ItrafgenApp;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JTree;
import javax.swing.JComboBox;
import java.awt.Rectangle;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JList;
import javax.swing.JScrollPane;

import org.jdom.Element;



import itrafgen.gui.packetcreator.HexEditor.JHexEditor;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import jpcap.JpcapCaptor;
import jpcap.JpcapSender;
import jpcap.NetworkInterface;
import jpcap.NetworkInterfaceAddress;
import jpcap.packet.EthernetPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
import javax.swing.JTextField;

public class Creator extends JFrame {
    private int theoriclength = 0;
	private JPanel jContentPane = null;
	private JList isolist = null;
	private MultiComponentTable2 Config = null;
	private JHexEditor Packetdetail = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JButton jButton = null;
	private JComboBox proto = null;
	private JButton jButton1 = null;
	private JButton jButton2 = null;
	private JButton jButton3 = null;
	private Vector<String> iso = new Vector<String>();  //  @jve:decl-index=0:
	private MultiComponentTable2 Config1 = null;
	private DefaultListModel model;
	private ComboBoxModel model2;
	private Packet packet = new Packet();  //  @jve:decl-index=0:
	private int taillecourante = 0;
	public Packet trame;
	public int vlan;
	public int configlayer;
	private JButton jButton4 = null;
	private JButton Header = null;
	private JTextField jTextField = null;
	private JLabel jLabel3 = null;
    private final static char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6',
                                              '7', '8', '9', 'A', 'B', 'C', 'D',
                                              'E', 'F' };
	/**
	 * This is the default constructor
	 */
	public Creator() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 *
	 * @return void
	 */
	private void initialize() {
		this.setSize(756, 654);
		this.setContentPane(getJContentPane());
		this.setTitle("Encapsulation");
		File entryFile = new File("protocol/Ethernet.xml");
		Config1.load(entryFile);
		Config1.packetfrm();
	}

	/**
	 * This method initializes jContentPane
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(407, 281, 188, 16));
			jLabel3.setText("Taille des données du paquet");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(302, 7, 318, 16));
			jLabel2.setText("Configuration");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(30, 8, 250, 16));
			jLabel1.setText("Couche ISO");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(31, 318, 327, 16));
			jLabel.setText("Packet Header Details Hexadecimal / ASCII");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getIsolist(), null);
			jContentPane.add(getPacketdetail(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getProto(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(getJButton2(), null);
			jContentPane.add(getJButton3(), null);
			jContentPane.add(getConfig1(), null);
			jContentPane.add(getJButton4(), null);
			jContentPane.add(getHeader(), null);
			jContentPane.add(getJTextField(), null);
			jContentPane.add(jLabel3, null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes isolist
	 *
	 * @return javax.swing.JList
	 */
	private JList getIsolist() {
		if (isolist == null) {
			model =  new DefaultListModel();
			isolist = new JList(model);





			model.addElement("Ethernet");




			isolist.setBounds(new Rectangle(29, 36, 252, 203));
			isolist.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
				public void valueChanged(javax.swing.event.ListSelectionEvent e) {
					//System.out.println("valueChanged()"); // TODO Auto-generated Event stub valueChanged()



					if(isolist.getSelectedIndex() < configlayer){
						isolist.setSelectedIndex(configlayer);
					}
					if(!Config1.nf.equals(((String) model.getElementAt(isolist.getSelectedIndex())) + ".xml")){

						jButton2.setEnabled(true);
						jButton1.setEnabled(false);
						jButton.setEnabled(false);
						proto.setEnabled(false);
					File entryFile = new File(("protocol/" + (String) model.getElementAt(isolist.getSelectedIndex())) + ".xml");
					Config1.load(entryFile);
					Config1.packetfrm();
					Config1.updateUI();
					}

				}
			});
		}
		return isolist;
	}


	/**
	 * This method initializes Packetdetail
	 *
	 * @return javax.swing.JPanel
	 */

	private JPanel getPacketdetail() {
		if (Packetdetail == null) {
			byte[] ar;
	        ar=new byte[16*16*100];
	        Arrays.fill(ar,(byte)0);
			Packetdetail = new JHexEditor(ar);

			Packetdetail.setBounds(new Rectangle(31, 342, 600, 239));
		}
		return Packetdetail;
	}

	/**
	 * This method initializes jButton
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(33, 264, 122, 29));
			jButton.setEnabled(false);
			jButton.setText("Ajouter");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					configlayer++;
					model.addElement((String) proto.getSelectedItem());
					isolist.setSelectedIndex(configlayer);
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes proto
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getProto() {
		if (proto == null) {
			//model2 =  new ComboBoxModel();

			proto = new JComboBox();
			proto.setBounds(new Rectangle(77, 237, 170, 27));
			proto.setEnabled(false);
		}
		return proto;
	}

	/**
	 * This method initializes jButton1
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new Rectangle(157, 265, 134, 29));
			jButton1.setEnabled(false);
			jButton1.setText("Effacer derniere");
		}
		return jButton1;
	}

	/**
	 * This method initializes jButton2
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(new Rectangle(644, 245, 100, 29));
			jButton2.setText("OK");

			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					packet.header = new byte[1000];
					//configlayer++;
					int tinit = taillecourante;
					String binheader = "";
					for(int i = 0;i<(Config1.taille);i++){
						byte[] tmp = Config1.dm.getValueAt(i, 2).toString().getBytes();
						int a = 0;
						int siz = Integer.parseInt(Config1.dm.getValueAt(i, 1).toString());
						int ofz = Integer.parseInt(Config1.dm.getValueAt(i, 5).toString());

						if(!(ofz <0)){

						if(Config1.dm.getValueAt(i, 6) == null || !Config1.dm.getValueAt(i, 6).toString().equals("no")){
						if(Config1.dm.getValueAt(i, 2).toString().equals("true")){
							binheader = binheader + "1";
						}
						else if(Config1.dm.getValueAt(i, 2).toString().equals("false")){
							binheader = binheader + "0";
						}
						else{
							//split Config1.dm.getValueAt(i, 4).toString()
							String[] wow;
							boolean sep;
							if(Config1.dm.getValueAt(i, 4) == null  || Config1.dm.getValueAt(i, 4).toString().equals("")){
								wow = new String[1];
								wow[0] = Config1.dm.getValueAt(i, 2).toString();
								sep = false;
							}
							else{
								wow = Config1.dm.getValueAt(i, 2).toString().split("\\" + Config1.dm.getValueAt(i, 4).toString());
								sep = true;
							}

							for(int k = 0;k<wow.length;k++){
								String bintmp = Integer.toBinaryString(Integer.parseInt(wow[k].split("\\(")[0],Integer.parseInt(Config1.dm.getValueAt(i, 3).toString())));
								String tmp3 = "";
								if(!sep){
								if(bintmp.length() != (Integer.parseInt(Config1.dm.getValueAt(i, 1).toString()))){
									int len = bintmp.length();
									for(int o = 0;o<(Integer.parseInt(Config1.dm.getValueAt(i, 1).toString()))-len;o++){
										tmp3 = tmp3 + "0";

									}
									bintmp = tmp3 + "" +  bintmp;
								}
							}
							else{
								if(bintmp.length() != ((Integer.parseInt(Config1.dm.getValueAt(i, 1).toString())))/wow.length){
									int len = bintmp.length();
									for(int o = 0;o<((Integer.parseInt(Config1.dm.getValueAt(i, 1).toString())))/wow.length-len;o++){
										tmp3 = tmp3 + "0";
									}
									bintmp = tmp3 + "" + bintmp;
								}
							}



								binheader = binheader + "" +  bintmp;
							}

						}
						//System.out.println("loul" + binheader);

						//int a = Integer.parseInt(Config1.dm.getValueAt(i, 2).toString(), 2);
						/*a = Integer.parseInt(Config1.dm.getValueAt(i, 2).toString(), 2);

						//taillecourante = 0;
						for(int j = 0;j<tmp.length;j++){
							Packetdetail.buff[taillecourante] = tmp[j];
							//packet.header[taillecourante] = tmp[j];
							taillecourante++;
							System.out.println(taillecourante);
						}*/


						}
						}
						else{
							//offset negatif : deplacement et ajout ...
							//incrementer la taille courante

							String binheader2 = "";
							if(Config1.dm.getValueAt(i, 6) == null || !Config1.dm.getValueAt(i, 6).toString().equals("no")){
								if(Config1.dm.getValueAt(i, 2).toString().equals("true")){
									binheader2 = binheader2 + "1";
								}
								else if(Config1.dm.getValueAt(i, 2).toString().equals("false")){
									binheader2 = binheader2 + "0";
								}
								else{
									//split Config1.dm.getValueAt(i, 4).toString()
									String[] wow;
									boolean sep;
									if(Config1.dm.getValueAt(i, 4) == null  || Config1.dm.getValueAt(i, 4).toString().equals("")){
										wow = new String[1];
										wow[0] = Config1.dm.getValueAt(i, 2).toString();
										sep = false;
									}
									else{
										wow = Config1.dm.getValueAt(i, 2).toString().split("\\" + Config1.dm.getValueAt(i, 4).toString());
										sep = true;
									}

									for(int k = 0;k<wow.length;k++){
										String bintmp = Integer.toBinaryString(Integer.parseInt(wow[k].split("\\(")[0],Integer.parseInt(Config1.dm.getValueAt(i, 3).toString())));
										String tmp3 = "";
										if(!sep){
										if(bintmp.length() != (Integer.parseInt(Config1.dm.getValueAt(i, 1).toString()))){
											int len = bintmp.length();
											for(int o = 0;o<(Integer.parseInt(Config1.dm.getValueAt(i, 1).toString()))-len;o++){
												tmp3 = tmp3 + "0";

											}
											bintmp = tmp3 + "" +  bintmp;
										}
									}
									else{
										if(bintmp.length() != ((Integer.parseInt(Config1.dm.getValueAt(i, 1).toString())))/wow.length){
											int len = bintmp.length();
											for(int o = 0;o<((Integer.parseInt(Config1.dm.getValueAt(i, 1).toString())))/wow.length-len;o++){
												tmp3 = tmp3 + "0";
											}
											bintmp = tmp3 + "" + bintmp;
										}
									}



										binheader2 = binheader2 + "" +  bintmp;
									}

								}
								//System.out.println("louldecaler********" + binheader2);

								//int a = Integer.parseInt(Config1.dm.getValueAt(i, 2).toString(), 2);
								/*a = Integer.parseInt(Config1.dm.getValueAt(i, 2).toString(), 2);

								//taillecourante = 0;
								for(int j = 0;j<tmp.length;j++){
									Packetdetail.buff[taillecourante] = tmp[j];
									//packet.header[taillecourante] = tmp[j];
									taillecourante++;
									System.out.println(taillecourante);
								}*/


								}



							byte[] tmpe = new byte[-ofz/8];

							//DECALAGE ...
							int j2 = 0;
							//sauvegarde  de ce qu'il faut deplacer...
							for(int j = taillecourante + ofz / 8;j<taillecourante;j++){
								tmpe[j2] = Packetdetail.buff[j];
								j2++;
							}

							//ECRIT
							for(int j = 0;j<binheader2.length()/8;j++){
								//Packetdetail.buff[taillecourante] = tmp[j];
								//packet.header[taillecourante] = tmp[j];
								Packetdetail.buff[taillecourante + ofz / 8] = (byte) Integer.parseInt(binheader2.substring(j*8, (j+1)*8),2);
								taillecourante++;
								//System.out.println(taillecourante);
							}
							//DECALE
							for(int j = 0;j<tmpe.length;j++){
								//Packetdetail.buff[taillecourante] = tmp[j];
								//packet.header[taillecourante] = tmp[j];
								Packetdetail.buff[taillecourante + ofz / 8 + j] = tmpe[j];
								//taillecourante++;
								//System.out.println(taillecourante);
							}











						}

					}

					//byte[] tmp = Binary.binaryStringToBytes(binheader);
					byte[] tmp = new byte[binheader.length()/8];


					byte[] chkbuf = new byte[binheader.length()/8];
					for(int j = 0;j<binheader.length()/8;j++){
						//Packetdetail.buff[taillecourante] = tmp[j];
						//packet.header[taillecourante] = tmp[j];
						Packetdetail.buff[taillecourante] = (byte) Integer.parseInt(binheader.substring(j*8, (j+1)*8),2);
						chkbuf[j] = Packetdetail.buff[taillecourante];
						taillecourante++;
						//System.out.println(taillecourante);
					}

					//System.out.println("HEADER CHECKSUM !" + sum(chkbuf));
					Packetdetail.repaint();
					List list = Config1.getnextproto();
					proto.removeAllItems();
			       	for (Iterator i = list.iterator(); i.hasNext();) {
		        	    Element u = (Element) i.next();
		        	    proto.addItem(u.getValue());

			       	}
			       	jButton2.setEnabled(false);
					proto.setEnabled(true);
					jButton.setEnabled(true);
					jButton1.setEnabled(true);


				}

			});
		}
		return jButton2;
	}

	 private static int sum(byte[] bb) {
			int sum = 0;
			int i = 0;
			while(i<bb.length){
			    if ((sum & 1) != 0)
				sum = (sum >> 1) + 0x8000;
			    else
				sum >>= 1;
			    sum += bb[i] & 0xff;
			    i++;
			    sum &= 0xffff;
			}
            sum = (sum ^ 0xffff) & 0xffff;
            char[] buf = new char[8];

             for (int k = 7; k >= 0; k--) {
                buf[i] = HEX_DIGITS[sum & 0x0F];
                   sum >>>= 4;
               }
			//System.out.println("##### CHECKSUM HEX : ####"  + Integer.toHexString(sum));
            JFrame mainFrame = ItrafgenApp.getApplication().getMainFrame();

			new AlertBox(mainFrame,"Header Checksum",Convert.intToHexString(sum));
			return sum;



		    }
	/**
	 * This method initializes jButton3
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setBounds(new Rectangle(645, 597, 75, 29));
			jButton3.setText("OK");
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					trame = new Packet();
					//trame.header = new byte[taillecourante];
					EthernetPacket ethhead = new EthernetPacket();
					ethhead.src_mac = new byte[6];
					ethhead.dst_mac = new byte[6];
					for(int i = 0;i<6;i++){
						ethhead.src_mac[i] = Packetdetail.buff[i];

					}
					for(int i = 6;i<12;i++){
						ethhead.dst_mac[i - 6] = Packetdetail.buff[i];

					}

						byte[] t = new byte[2];
						t[0] = Packetdetail.buff[12];
						t[1] = Packetdetail.buff[13];

						BigInteger bi = new BigInteger(t);
					    //System.out.println("frame type" + bi.toString(16));

						//ethhead.frametype =  Short.parseShort(bi.toString(16));
					    ethhead.frametype = 2048;
					trame.datalink = ethhead;
					TCPPacket p=new TCPPacket(12,34,56,78,false,false,false,false,true,true,true,true,10,10);

					//specify IPv4 header parameters

					trame.data = new byte[taillecourante + Integer.parseInt(jTextField.getText())];
					for(int i = 0;i<taillecourante;i++){
						trame.data[i] = Packetdetail.buff[i];
					}
					//ajoute les données
					for(int i = taillecourante;i<taillecourante + Integer.parseInt(jTextField.getText());i++){
						trame.data[i] = (byte) 0;
					}
					//trame.data = "fin".getBytes();
					//trame.header = p.header;
					//trame.data  = "hello".getBytes();
					//pour le test a enlever ensuite
					//open a network interface to send a packet to
					/*NetworkInterface[] devices = JpcapCaptor.getDeviceList();

					JpcapSender sender = null;
					try {
						sender = JpcapSender.openDevice(devices[0]);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}




					//send the packet p
					sender.sendPacket(trame);


					sender.close();*/
                    ItrafgenApp.flux.get(vlan).control();
                    hide();
				}
			});
		}
		return jButton3;
	}

	/**
	 * This method initializes Config1
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getConfig1() {
		if (Config1 == null) {
			Config1 = new MultiComponentTable2();
			Config1.setBounds(new Rectangle(297, 31, 447, 211));
			//Config1.setBounds(new Rectangle(359, 75, 10, 10));
		}
		return Config1;
	}

	/**
	 * This method initializes jButton4
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setBounds(new Rectangle(478, 244, 163, 29));
			jButton4.setText("Ajouter un champ");
		}
		return jButton4;
	}

	/**
	 * This method initializes Header
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getHeader() {
		if (Header == null) {
			Header = new JButton();
			Header.setBounds(new Rectangle(314, 244, 163, 29));
			Header.setText("Header Checksum");
			Header.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					int taillecourante2 = taillecourante;

					//packet.header = new byte[1000];
					//configlayer++;
					int tinit = taillecourante2;
					String binheader = "";
					for(int i = 0;i<(Config1.taille);i++){
						byte[] tmp = Config1.dm.getValueAt(i, 2).toString().getBytes();
						int a = 0;
						int siz = Integer.parseInt(Config1.dm.getValueAt(i, 1).toString());
						int ofz = Integer.parseInt(Config1.dm.getValueAt(i, 5).toString());

						if(!(ofz <0)){

						if(Config1.dm.getValueAt(i, 6) == null || !Config1.dm.getValueAt(i, 6).toString().equals("no")){
						if(Config1.dm.getValueAt(i, 2).toString().equals("true")){
							binheader = binheader + "1";
						}
						else if(Config1.dm.getValueAt(i, 2).toString().equals("false")){
							binheader = binheader + "0";
						}
						else{
							//split Config1.dm.getValueAt(i, 4).toString()
							String[] wow;
							boolean sep;
							if(Config1.dm.getValueAt(i, 4) == null  || Config1.dm.getValueAt(i, 4).toString().equals("")){
								wow = new String[1];
								wow[0] = Config1.dm.getValueAt(i, 2).toString();
								sep = false;
							}
							else{
								wow = Config1.dm.getValueAt(i, 2).toString().split("\\" + Config1.dm.getValueAt(i, 4).toString());
								sep = true;
							}

							for(int k = 0;k<wow.length;k++){
								String bintmp = Integer.toBinaryString(Integer.parseInt(wow[k].split("\\(")[0],Integer.parseInt(Config1.dm.getValueAt(i, 3).toString())));
								String tmp3 = "";
								if(!sep){
								if(bintmp.length() != (Integer.parseInt(Config1.dm.getValueAt(i, 1).toString()))){
									int len = bintmp.length();
									for(int o = 0;o<(Integer.parseInt(Config1.dm.getValueAt(i, 1).toString()))-len;o++){
										tmp3 = tmp3 + "0";

									}
									bintmp = tmp3 + "" +  bintmp;
								}
							}
							else{
								if(bintmp.length() != ((Integer.parseInt(Config1.dm.getValueAt(i, 1).toString())))/wow.length){
									int len = bintmp.length();
									for(int o = 0;o<((Integer.parseInt(Config1.dm.getValueAt(i, 1).toString())))/wow.length-len;o++){
										tmp3 = tmp3 + "0";
									}
									bintmp = tmp3 + "" + bintmp;
								}
							}



								binheader = binheader + "" +  bintmp;
							}

						}
						//System.out.println("loul" + binheader);

						//int a = Integer.parseInt(Config1.dm.getValueAt(i, 2).toString(), 2);
						/*a = Integer.parseInt(Config1.dm.getValueAt(i, 2).toString(), 2);

						//taillecourante2 = 0;
						for(int j = 0;j<tmp.length;j++){
							Packetdetail.buff[taillecourante2] = tmp[j];
							//packet.header[taillecourante2] = tmp[j];
							taillecourante2++;
							System.out.println(taillecourante2);
						}*/


						}
						}


					}

					//byte[] tmp = Binary.binaryStringToBytes(binheader);
					byte[] tmp = new byte[binheader.length()/8];


					byte[] chkbuf = new byte[binheader.length()/8];
					for(int j = 0;j<binheader.length()/8;j++){
						//Packetdetail.buff[taillecourante2] = tmp[j];
						//packet.header[taillecourante2] = tmp[j];
						chkbuf[j] = (byte) Integer.parseInt(binheader.substring(j*8, (j+1)*8),2);
						//chkbuf[j] = Packetdetail.buff[taillecourante2];
						taillecourante2++;
						//System.out.println(taillecourante);
					}

					System.out.println("HEADER CHECKSUM !" + nexchk(chkbuf));
					Packetdetail.repaint();
					List list = Config1.getnextproto();
					proto.removeAllItems();
			       	for (Iterator i = list.iterator(); i.hasNext();) {
		        	    Element u = (Element) i.next();
		        	    proto.addItem(u.getValue());

			       	}
			       /*	jButton2.setEnabled(false);
					proto.setEnabled(true);
					jButton.setEnabled(true);
					jButton1.setEnabled(true);*/


				}
			});
		}
		return Header;
	}

	 public static short nexchk(byte[] pakt){
		 short ch,o0,o1;
		 ch = o0 = o1 = 0;
		    for (int i = 0; i < pakt.length; i += 2) {
		o0 += (short) (pakt[i] & 0xff);
		o1 += (short) (pakt[i+1] & 0xff);
		    }

		    ch = (short) ~(((o0 >> 8) & 0xff) + (o0 << 8) + o1);
		    new AlertBox(getFrames()[getFrames().length - 1],"Header Checksum",Integer.toHexString(ch));

		    return ch;
	 }

	/**
	 * This method initializes jTextField
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(new Rectangle(600, 279, 111, 22));
			jTextField.setText("0");
		}
		return jTextField;
	}

}
