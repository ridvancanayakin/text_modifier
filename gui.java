package cstech;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class gui implements ActionListener{
	private static JLabel pathLabel;
	private static JLabel islemLabel;
	private static JLabel kelimeLabel;
	private static JLabel yeniLabel;
	private static JLabel pathDone;
	private static JTextField pathText;
	private static JTextField islemText;
	private static JTextField keyText;
	private static JTextField yeniText;
	private static JButton pathButton;

	public static void main(String[] args) {
		// GUI deki label, text ve buttonlar burada tanimlandi
		JPanel panel= new JPanel();
		JFrame frame= new JFrame("Text Modifier");
		frame.setSize(500,250);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		
		panel.setLayout(null);
		pathLabel= new JLabel("PATH");
		pathLabel.setBounds(10,20,115,25);
		panel.add(pathLabel);
		
		pathText=new JTextField(20);
		pathText.setBounds(135,20,300,25);
		panel.add(pathText);
		
		islemLabel=new JLabel("ISLEM");
		islemLabel.setBounds(10,50,115,25);
		panel.add(islemLabel);
		
		islemText=new JTextField(20);
		islemText.setBounds(135,50,300,25);
		panel.add(islemText);
		
		kelimeLabel=new JLabel("ANAHTAR KELIME");
		kelimeLabel.setBounds(10,80,115,25);
		panel.add(kelimeLabel);

		keyText=new JTextField(20);
		keyText.setBounds(135,80,300,25);
		panel.add(keyText);
		
		yeniLabel=new JLabel("YENI KELIME");
		yeniLabel.setBounds(10,110,115,25);
		panel.add(yeniLabel);

		yeniText=new JTextField(20);
		yeniText.setBounds(135,110,300,25);
		panel.add(yeniText);
		
		pathDone= new JLabel("");
		pathDone.setBounds(135,140,300,25);
		panel.add(pathDone);
		
		pathButton= new JButton("Onay");
		pathButton.setBounds(10,140,80,23);
		pathButton.addActionListener(new gui());
		panel.add(pathButton);
		
		
		frame.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		//Buttona tiklandiginda girilen kelimeleri Cstech objesinin deneme methoduna argument olarak veriyor
		Cstech cstech=new Cstech();
		StringBuffer path=new StringBuffer();//path degiskeni degistirilecegi icin String olarak tanimlanmadi
		path.append(pathText.getText());
		String islem=islemText.getText();
		String key= keyText.getText();
		String yeniKelime=yeniText.getText();
		try {
			pathDone.setText(Cstech.deneme(path,islem,key,yeniKelime));
			//eger bu dosya varsa dönen Stringi sonuc textine atiyor
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			pathDone.setText("Boyle bir dosya yok");
			//dosya yoksa bu hatayi basiyoruz.
		}
		
	}
	

}
