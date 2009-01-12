/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package itrafgen.gui.packetcreator;

import itrafgen.ItrafgenApp;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.jdom.Attribute;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;


import itrafgen.XMLPARSER.champ;
import itrafgen.XMLPARSER.choice;

/**
 *
 * @author sebastienhoerner
 */
public class MultiComponentTable2 extends JPanel{
	DefaultTableModel dm;
	EachRowEditor rowEditor;
	public JTable table;
	EachRowRenderer rowRenderer;
	int taille = 0;
	public JScrollPane scroll;
	public org.jdom.Document document = null ;
	public String nf;


	public MultiComponentTable2() {




    dm = new DefaultTableModel() {
      public boolean isCellEditable(int row, int column) {
        if (column == 2) {
          return true;
        }
        if (column == 3) {
            return true;
          }
        if (column == 4) {
            return true;
          }
        if (column == 5) {
            return true;
          }
        if (column == 6) {
            return true;
          }
        return false;
      }
    };
    dm.setDataVector(new Object[][] {},
        new Object[] {
        "Champ", "Taille", "Valeur", "Base", "Separateur", "Offset","Opt"
        });

    //CheckBoxRenderer checkBoxRenderer = new CheckBoxRenderer();
     rowRenderer = new EachRowRenderer();
 //   rowRenderer.add(2, checkBoxRenderer);
 //   rowRenderer.add(3, checkBoxRenderer);
     table = new JTable(dm);

    this.setSize(400,300);
    JComboBox comboBox = new JComboBox();
    comboBox.addItem("5");
    comboBox.addItem("6");
    JCheckBox checkBox = new JCheckBox();
    checkBox.setHorizontalAlignment(JLabel.CENTER);
    DefaultCellEditor comboBoxEditor = new DefaultCellEditor(comboBox);
    DefaultCellEditor checkBoxEditor = new DefaultCellEditor(checkBox);



// modified by java2s.com

    // rowEditor = new EachRowEditor(table);
     /*rowEditor.setEditorAt(0, comboBoxEditor);
    rowEditor.setEditorAt(1, comboBoxEditor);
    rowEditor.setEditorAt(2, checkBoxEditor);
    rowEditor.setEditorAt(3, checkBoxEditor);*/

// end

   // table.getColumn("Valeur").setCellRenderer(rowRenderer);
   // table.getColumn("Valeur").setCellEditor(rowEditor);
   // this.add(table);
    //table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

    scroll = new JScrollPane(table);
    scroll.setSize(400, 180);
    table.setPreferredScrollableViewportSize(new Dimension(400, 180));
    this.add(scroll);

    setVisible(true);
   /* ok.addActionListener(new java.awt.event.ActionListener() {
		public void actionPerformed(java.awt.event.ActionEvent e) {
			//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()

		//	table.getSize().height + 1 ;
			//String a = "";

			//voir afficahge en hexa ou en decimal
			System.out.println("-----------------");
			Vector<Byte> pp = new Vector<Byte>();
			for(int i =0;i<3;i++){
				System.out.println(table.getValueAt(i, 2));

				if(table.getValueAt(i, 2).toString().equals("true")){
					pp.add(Byte.parseByte("1"));
				}
				else if(table.getValueAt(i, 2).toString().equals(false)){
					pp.add(Byte.parseByte("1"));
				}
				else{
					Integer ii = Integer.parseInt(table.getValueAt(i, 2).toString(), 16);


				}
			}


		}
	});*/
    //ContentPane = this.getContentPane().set;
	/*this.getContentPane().setLayout(new BorderLayout());
	this.getContentPane().add(scroll, BorderLayout.NORTH);
	this.getContentPane().add(ok, BorderLayout.SOUTH);*/
    /*Vector<choice> tmp = new Vector<choice>();
    tmp.add(new choice("IP","5"));
    tmp.add(new choice("IP2","6"));
    ajchmp(new champ("u","3","4","5",tmp),0);

    Vector<choice> tmp2 = new Vector<choice>();
    tmp2.add(new choice("TIP","5"));
    tmp2.add(new choice("TIP2","6"));
    ajchmp(new champ("u2","3","4","6",tmp2),1);


    ajchmp(new champ("u3","1","4","6",null),2);
   // ajchmp(new champ("u4","3","4","12",null),2);
    ajchmp(new champ("u5","5","4","3",null),3);*/
  }

  public void ajchmp(champ o,int num){

		//ajout du champs
	  taille ++;
	  String sep = null;
	  String base = "16";
	  if(o.name.equals("src_mac")){
		  o.valdef = ItrafgenApp.macemission;
		  sep = ":";
	  }
	  if(o.name.equals("dst_mac")){
		  o.valdef = ItrafgenApp.macreception;
		  sep = ":";
	  }
	  if(o.name.equals("Src_IP address")){
		  o.valdef = ItrafgenApp.ipemission;
		  sep = ".";
		  base = "10";
	  }
	  if(o.name.equals("Dst_IP address")){
		  o.valdef = ItrafgenApp.ipreception;
		  sep = ".";
		  base = "10";
	  }
	  if(o.lch == null){
	  if(Integer.parseInt(o.longueur) != 1){

		  dm.addRow(new Object[]{o.name,o.longueur,o.valdef,base,sep,o.offset});
		//  rowEditor.setEditorAt(num, null);

	  }
	  else{
		  if(o.valdef.equals("1")){
		  dm.addRow(new Object[]{o.name,o.longueur,new Boolean(true),base,sep,o.offset});
		  }
		  else{
			  dm.addRow(new Object[]{o.name,o.longueur,new Boolean(false),base,sep,o.offset});
		  }
		    JCheckBox checkBox = new JCheckBox();
		    checkBox.setHorizontalAlignment(JLabel.CENTER);
		    DefaultCellEditor checkBoxEditor = new DefaultCellEditor(checkBox);
		    rowEditor.setEditorAt(num, checkBoxEditor);
		    CheckBoxRenderer checkBoxRenderer = new CheckBoxRenderer();
		    rowRenderer.add(num, checkBoxRenderer);

	  }
	  }
	  else{
		    JComboBox comboBox = new JComboBox();
		    for(int i = 0;i<o.lch.size();i++){
		    	 comboBox.addItem(o.lch.get(i).value + "(" + o.lch.get(i).nom + ")");
		    }

		    dm.addRow(new Object[]{o.name,o.longueur,o.valdef,base,sep,o.offset});
		    DefaultCellEditor comboBoxEditor = new DefaultCellEditor(comboBox);
		    rowEditor.setEditorAt(num, comboBoxEditor);
		    //table.getColumn("Valeur").setCellEditor(rowEditor);

	  }
	  if(o.name.contains("(opt)")){
		  //champs optionnel
		  dm.setValueAt("yes", num, 6);

	  }




	// modified by java2s.com

	    /*
	    rowEditor.setEditorAt(0, comboBoxEditor);
	    rowEditor.setEditorAt(1, comboBoxEditor);
	    rowEditor.setEditorAt(2, checkBoxEditor);
	    rowEditor.setEditorAt(4, checkBoxEditor);*/

	// end




  }

  /**
   * @param _FilePath
   * Methode pour charger mon fichier xml qui contient un protocol
   */
  public void load(File _FilePath) {
	  	nf = _FilePath.getName();
	    rowEditor = new EachRowEditor(table);
	    rowRenderer = new EachRowRenderer();
	    table.getColumn("Valeur").setCellRenderer(rowRenderer);
	    table.getColumn("Valeur").setCellEditor(rowEditor);
		for(int i = dm.getRowCount(); i > 0; --i){
		    dm.removeRow(i-1);
		}
		taille = 0;
      document = null ;
      try {
          /* On crée une instance de SAXBuilder */
          SAXBuilder sxb = new SAXBuilder();
          document = sxb.build(_FilePath);
      } catch (IOException e) {
          System.out.println("Erreur lors de la lecture du fichier "
				+ e.getMessage() );
          e.printStackTrace();
      } catch (JDOMException e){
          System.out.println("Erreur lors de la construction du fichier JDOM "
				+ e.getMessage() );
          e.printStackTrace();
      }


  }
  //renvoi le nom du protocole
  public String nomprotocole(){
  	 Element racine = document.getRootElement();
       Attribute a = racine.getAttribute("name");
       return a.getValue();
}

  public String nomlongprotocole(){
 	 Element racine = document.getRootElement();
      Attribute a = racine.getAttribute("longname");
      return a.getValue();
  }

  public String descriptionprotocole(){
 	 Element racine = document.getRootElement();
      Attribute a = racine.getAttribute("description");
      return a.getValue();
}
  public String commentaireprotocole(){
    	 Element racine = document.getRootElement();
         Attribute a = racine.getAttribute("comment");
         return a.getValue();
  }



  public void packetfrm(){
  	Element racine = document.getRootElement();
  	try {
			XPath x = XPath.newInstance("//protocol/name");
			Element e = (Element) x.selectSingleNode(document);
			String nprotoc = e.getValue();
			//System.out.println(e.getValue()); //nom du protocole
			x = XPath.newInstance("//protocol/layer");
			e = (Element) x.selectSingleNode(document);
			//System.out.println(e.getValue()); //layer du protocole
			x = XPath.newInstance("//protocol/fields/field");
			List list    = x.selectNodes(document);
			int iter = 0;
	       	for (Iterator i = list.iterator(); i.hasNext();) {
	        	    Element u = (Element) i.next();
	        	   // System.out.println(u.getChild("name").getValue());
	        	    //System.out.println(u.getChild("offset").getValue());
	        	   // System.out.println(u.getChild("length").getValue());
	        	   // System.out.println(u.getChild("defaultvalue").getValue());
	        	    XPath xx = XPath.newInstance("/protocol[name='" + nprotoc + "']/fields/field[name='" + u.getChild("name").getValue() + "']/choices/choice");
	        	    List list2    = xx.selectNodes(document);
	        	    Vector<choice> lch = null;
	        	    if(!list2.isEmpty()){
	        	    	lch = new Vector<choice>();
	        	    	//System.out.println(":)");
	        	    	for (Iterator ii = list2.iterator(); ii.hasNext();) {
	        	    		Element ui = (Element) ii.next();
	        	    		//System.out.println(ui.getChild("name").getValue());
	        	    		//System.out.println(ui.getChild("value").getValue());
	        	    		lch.add(new choice(ui.getChild("name").getValue(),ui.getChild("value").getValue()));

	        	    	}
	        	    }

	        	    //String name, String longueur, String offset, String valdef, Vector<choice> lch

	        	    ajchmp(new champ(u.getChild("name").getValue(),u.getChild("length").getValue(),u.getChild("offset").getValue(),u.getChild("defaultvalue").getValue(), lch),iter);
	        	    iter++;
	       	}
			//System.out.println(e.getValue()); //nom du protocole


		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  }

  public List getnextproto(){
	  Element racine = document.getRootElement();
	  	try {
				XPath x = XPath.newInstance("//protocol/encapsulation/nextproto");
				List list    = x.selectNodes(document);
				return list;




			} catch (JDOMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
	  }


}
