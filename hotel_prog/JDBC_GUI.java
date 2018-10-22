/**
 * @author Kyeongdeok Park
 * @version 0.1
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class JDBC_GUI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	static Connection conn = null;
	static Statement stmt = null;
	
	JLabel Title = new JLabel("ȣ�� ���� �ý���");
	private Font font2 = new Font("����", Font.BOLD, 20) ;
	
	JPanel MainPanel;
	JTabbedPane TabbedInfoPanel;
	private JTextField nameInput = new JTextField();
	private JTextField staffInput = new JTextField();
	
//	buttons
	private JButton signupButton = new JButton("ȸ������");
	private JButton staffsignupButton = new JButton("�������");
	private JButton searchButton = new JButton("��ȸ");
	private JButton staffButton = new JButton("��ȸ");
	
	JLabel TabProductLabel;
	JLabel TabProductLabel2;
	JLabel TabProductLabel3;
	
	JComboBox<String> TabProductmodelNumberCombobox;
	JComboBox<String> TabProductmodelNumberCombobox2;
	JComboBox<String> TabProductmodelNumberCombobox3;
	
	static JTextArea TapProductTextArea;
	static JTextArea TapProductTextArea2;
	static JTextArea TapProductTextArea3;
	
	// reservation
	
	String revname;
	String checkin;
	
    private JPanel panel;

    private JLabel custLabel = new JLabel("����");
    private JLabel checkinLabel = new JLabel("üũ�� (YYYY/MM/DD)");
    private JLabel stayLabel = new JLabel("��");
    private JLabel roomLabel = new JLabel("����");
    private JTextField custInput = new JTextField();
    private JTextField checkinInput = new JTextField();
    private JButton revButton = new JButton("������/����");
    private JButton cancelButton = new JButton("�������");
    private JComboBox<String> staycheck_box = new JComboBox<String>();
    private JComboBox<String> roomcheck_box = new JComboBox<String>();
    
    //makecomponent3
    private JPanel showpanel= new JPanel();
    
    //time
    String time;
    private JLabel timelabel = new JLabel("File");
    
    //File
    private JFileChooser FileChooser = new JFileChooser();
    JMenuBar mb = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    JMenuItem openItem = new JMenuItem("Open");
    
  //�α���
  	private String username;
      private String password;
      private JPanel loginpanel = new JPanel();

      private JLabel idLabel = new JLabel("���̵�");
      private JLabel pwdLabel = new JLabel("��й�ȣ");
      private JTextField idInput = new JTextField();
      private JPasswordField pwdInput = new JPasswordField();
      private JButton loginButton = new JButton("�α���");
    
	public JDBC_GUI(){
		UIManager.put("swing.boldMetal", Boolean.FALSE);
		
		setTitle("�α���");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(320, 130);
		
		//�α���
		loginpanel.setLayout(null);

        idLabel.setBounds(20, 10, 60, 30);
        pwdLabel.setBounds(20, 50, 60, 30);
        idInput.setBounds(100, 10, 80, 30);
        pwdInput.setBounds(100, 50, 80, 30);
        loginButton.setBounds(200, 25, 80, 35);

        loginpanel.add(idLabel);
        loginpanel.add(pwdLabel);
        loginpanel.add(idInput);
        loginpanel.add(pwdInput);
        loginpanel.add(loginButton);
        loginButton.addActionListener(this);
        getContentPane().add(loginpanel, BorderLayout.CENTER);
//        frame.add(loginpanel);
//        frame.setTitle("�α���");

//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	//�������� �г�
	public void makeComponent(){
		
//		panel.setBounds(0,10,60,30);
		
		//���� üũ�ڽ�
		
		for(int i =1; i< 20; i++){
			staycheck_box.addItem(""+i);
		}
		
        //���ȣ üũ�ڽ�
		for(int i =1; i< 10; i++){
			roomcheck_box.addItem("10"+i);
		}
		roomcheck_box.addItem("110");
		
		for(int i =1; i< 10; i++){
			roomcheck_box.addItem("20"+i);
		}
		roomcheck_box.addItem("210");
        
        custLabel.setBounds(20, 50, 60, 30); //���� �ؽ�Ʈ ��
        checkinLabel.setBounds(20, 95, 150, 30); // üũ�� �ؽ�Ʈ ��
        stayLabel.setBounds(20, 140, 60, 30); // ���� �ؽ�Ʈ �ڽ� ��
        roomLabel.setBounds(20, 180, 60, 30); // ���� �ؽ�Ʈ ��
        
        custInput.setBounds(270, 50, 80, 30); //���� �ؽ�Ʈ �ڽ�
        checkinInput.setBounds(270, 95, 80, 30); //üũ�� �ؽ�Ʈ �ڽ�
        
        staycheck_box.setBounds(270,140,70,30); //���� üũ �ڽ�
        roomcheck_box.setBounds(270,180,70,30); //���� üũ�ڽ�
        
        revButton.setBounds(50, 230, 130, 30); //���� ��ư
        cancelButton.setBounds(220, 230, 100, 30); //��� ��ư
        
        staycheck_box.addActionListener(this);
        roomcheck_box.addActionListener(this);
        revButton.addActionListener(this);
        cancelButton.addActionListener(this);
//        ===================================================== ���� �г� =================================================
        panel.add(custLabel);
        panel.add(checkinLabel);
        panel.add(custInput);
        panel.add(checkinInput);
        
        panel.add(stayLabel);
        panel.add(roomLabel);
        panel.add(staycheck_box);
        panel.add(roomcheck_box);
        
        panel.add(cancelButton);
        panel.add(revButton);
        panel.setBorder(new TitledBorder(new LineBorder(Color.black,1),"��������"));
        panel.setBounds(50,130,380,280);
	}
	
	// ��, ����, ���� ��ȸ �г�
	public void makeComponent2(){
		TabbedInfoPanel = new JTabbedPane();
		JPanel TabbedInfoPanel_Product = new JPanel();
		JPanel TabbedInfoPanel_Product2 = new JPanel();
		JPanel TabbedInfoPanel_Product3 = new JPanel();
		
		TabbedInfoPanel_Product.setLayout(null);
		TabbedInfoPanel_Product2.setLayout(null);
		TabbedInfoPanel_Product3.setLayout(null);
		
		TabbedInfoPanel.addTab("��", TabbedInfoPanel_Product);
		TabbedInfoPanel.addTab("����", TabbedInfoPanel_Product2);
		TabbedInfoPanel.addTab("����", TabbedInfoPanel_Product3);
		
		TabbedInfoPanel.setBounds(10, 30, 730, 190);
		MainPanel.add(TabbedInfoPanel);
		
		
		TabProductLabel = new JLabel();
		TabProductLabel.setText("����");
		TabProductLabel.setIcon(new ImageIcon(""));
		TabProductLabel.setBounds(20,10,80,80);
		TabbedInfoPanel_Product.add(TabProductLabel);
		
		TabProductLabel2 = new JLabel();
		TabProductLabel2.setText("���ǹ�ȣ");
		TabProductLabel2.setIcon(new ImageIcon(""));
		TabProductLabel2.setBounds(20,10,80,80);
		TabbedInfoPanel_Product2.add(TabProductLabel2);
		
		TabProductLabel3 = new JLabel();
		TabProductLabel3.setText("������");
		TabProductLabel3.setIcon(new ImageIcon(""));
		TabProductLabel3.setBounds(20,10,80,80);
		TabbedInfoPanel_Product3.add(TabProductLabel3);
		
		TabProductmodelNumberCombobox2 = new JComboBox<String>();
		
		for(int j=1; j< 10; j++){
			TabProductmodelNumberCombobox2.addItem("10"+j);
		}
		TabProductmodelNumberCombobox2.addItem("110");
		
		for(int j=1; j< 10; j++){
			TabProductmodelNumberCombobox2.addItem("20"+j);
		}
		
		TabProductmodelNumberCombobox2.addItem("210");
		
		signupButton.setBounds(20, 100, 100, 40);
		staffsignupButton.setBounds(20, 100, 100, 40);
		searchButton.setBounds(150, 100, 80, 40);
        staffButton.setBounds(150, 100, 80, 40);
        
		nameInput.setBounds(100,30,130,40);
		staffInput.setBounds(100,30,130,40);
		
		TabProductmodelNumberCombobox2.setBounds(100, 30, 130, 40);

		TabbedInfoPanel_Product.add(nameInput);
		TabbedInfoPanel_Product.add(searchButton);
		TabbedInfoPanel_Product.add(signupButton);
		
		TabbedInfoPanel_Product2.add(TabProductmodelNumberCombobox2);
		
		TabbedInfoPanel_Product3.add(staffInput);
		TabbedInfoPanel_Product3.add(staffButton);
		TabbedInfoPanel_Product3.add(staffsignupButton);
		
		searchButton.addActionListener(this);
		TabProductmodelNumberCombobox2.addActionListener(this);
        staffButton.addActionListener(this);
        signupButton.addActionListener(this);
        staffsignupButton.addActionListener(this);
		
		TapProductTextArea = new JTextArea();
		TapProductTextArea.setFont(new Font("����", 0,12));
		TapProductTextArea.setForeground(Color.black);
		TapProductTextArea.setOpaque(true);
		TapProductTextArea.setBackground(Color.white);
		TapProductTextArea.setBounds(300,15,420,140);
		TapProductTextArea.setBorder(new LineBorder(Color.black, 1));
		TapProductTextArea.setLineWrap(true);
		TapProductTextArea.setEditable(false);
		
		TabbedInfoPanel_Product.add(TapProductTextArea);
		
		TapProductTextArea2 = new JTextArea();
		TapProductTextArea2.setFont(new Font("����", 0,12));
		TapProductTextArea2.setForeground(Color.black);
		TapProductTextArea2.setOpaque(true);
		TapProductTextArea2.setBackground(Color.white);
		TapProductTextArea2.setBounds(300,15,420,140);
		TapProductTextArea2.setBorder(new LineBorder(Color.black, 1));
		TapProductTextArea2.setLineWrap(true);
		TapProductTextArea2.setEditable(false);

		TabbedInfoPanel_Product2.add(TapProductTextArea2);
		
		TapProductTextArea3 = new JTextArea();
		TapProductTextArea3.setFont(new Font("����", 0,12));
		TapProductTextArea3.setForeground(Color.black);
		TapProductTextArea3.setOpaque(true);
		TapProductTextArea3.setBackground(Color.white);
		TapProductTextArea3.setBounds(300,15,420,140);
		TapProductTextArea3.setBorder(new LineBorder(Color.black, 1));
		TapProductTextArea3.setLineWrap(true);
		TapProductTextArea3.setEditable(false);
		
		TabbedInfoPanel_Product3.add(TapProductTextArea3);
	}
	
	//������Ȳ �����ִ� â
	public void makecomponent3()
	{
		//����ȣ�� ���� ǥ���ϱ�
		
		JDBC_Connect_Product JCP = new JDBC_Connect_Product(username, password);
		showpanel.setLayout(new GridLayout(4,5,2,2));
		showpanel.setBorder(new TitledBorder(new LineBorder(Color.black,1),"���ǿ�����Ȳ"));
		showpanel.setBounds(450,130,350,280);
		
		JPanel [] room = new JPanel[20];
		
		for(int i =0; i<= 19; i++){
			room[i] = new JPanel();
			room[i].setBorder(new LineBorder(Color.black,1));
		}
		
		for(int j=1, i =0; i< 9; i++,j++){
			room[i].add(new JLabel("10"+j));
			
			if(JCP.revRoomSearch("10"+j)){
				room[i].setOpaque(true);
				room[i].setFont(new Font("����", Font.BOLD, 30));
				room[i].setBackground(Color.YELLOW);
				room[i].setForeground(Color.RED);
			}
		}
		
		room[9].add(new JLabel("110"));
		if(JCP.revRoomSearch("110")){
			room[9].setOpaque(true);
			room[9].setFont(new Font("����", Font.BOLD, 30));
			room[9].setBackground(Color.YELLOW);
			room[9].setForeground(Color.RED);
		}
		
		for(int j=1, i=10; i< 19; i++, j++){
			room[i].add(new JLabel("20"+j));
			if(JCP.revRoomSearch("20"+i)){
				room[i].setFont(new Font("����", Font.BOLD, 30));
				room[i].setOpaque(true);
				room[i].setBackground(Color.YELLOW);
				room[i].setForeground(Color.RED);
			}
		}
		
		room[19].add(new JLabel("210"));
		if(JCP.revRoomSearch("210")){
			room[19].setFont(new Font("����", Font.BOLD, 30));
			room[19].setOpaque(true);
			room[19].setBackground(Color.YELLOW);
			room[19].setForeground(Color.RED);
		}
		
		showpanel.removeAll();
		
		for(int i=0; i< 20; i++){
			showpanel.add(room[i]);
		}
	}
	
	public void actionPerformed(ActionEvent e){
		Object buttonAction = e.getSource();
		//���� ����
		if(buttonAction == openItem){
			JDBC_Connect_Product JCP = new JDBC_Connect_Product(username, password);
			int ret = FileChooser.showOpenDialog(null);
			
			// ���� ���̾�α� ���
            if(ret != JFileChooser.APPROVE_OPTION) { // ����ڰ�  â�� ������ �ݾҰų� ��� ��ư�� ���� ���
                JOptionPane.showMessageDialog(null,"������ �������� �ʾҽ��ϴ�", "���",JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // ����ڰ� ������ �����ϰ� "����" ��ư�� ���� ���
            String filepath = FileChooser.getSelectedFile().getPath(); // ���� ��θ��� �˾ƿ´�.
            JCP.fileopen(filepath);
            getContentPane().revalidate();
		}
		if(buttonAction == searchButton){
			JDBC_Connect_Product JCP = new JDBC_Connect_Product(username, password);
			String customer = (String) nameInput.getText();
			JCP.customerSearch(customer);
			System.out.println(customer);
		}
		if(buttonAction == TabProductmodelNumberCombobox2){
			JDBC_Connect_Product JCP = new JDBC_Connect_Product(username, password);
			String roomnumber = (String) TabProductmodelNumberCombobox2.getSelectedItem();
			JCP.roomSearch(roomnumber);
			System.out.println(roomnumber);
		}
		if(buttonAction == staffButton){
			JDBC_Connect_Product JCP = new JDBC_Connect_Product(username, password);
			String staff = (String) staffInput.getText();
			JCP.staffSearch(staff);
			System.out.println(staff);
		}
		if(buttonAction == signupButton){
                signup signup = new signup(username, password);
                signup.Signstore();
		}
		if(buttonAction == staffsignupButton){
            signup signup = new signup(username, password);
            signup.staffstore();
		}
		//�����ϱ�
		if(buttonAction == revButton){
			JDBC_Connect_Product JCP = new JDBC_Connect_Product(username, password);
			revname = (String) custInput.getText();
			checkin = (String) checkinInput.getText();
			String stay = (String) staycheck_box.getSelectedItem();
			String room = (String) roomcheck_box.getSelectedItem();
			
			if(JCP.reservation(revname,checkin,stay,room))
			{
				JCP.roomState();
	            makecomponent3();
	            getContentPane().revalidate();
	    		time = JCP.findtime();
	    		timelabel.setText("���� ��¥: ("+time+")");
	    		timelabel.setBounds(660,95,150,50);
	    		getContentPane().revalidate();
//				getContentPane().repaint();
	    		JOptionPane.showMessageDialog(null, "���� �Ǿ����ϴ�.", "���� Ȯ��", JOptionPane.INFORMATION_MESSAGE, null);	
			}			
		}
		//�������
		if(buttonAction == cancelButton){
			JDBC_Connect_Product JCP = new JDBC_Connect_Product(username, password);
			revname = (String) custInput.getText();
			checkin = (String) checkinInput.getText();
			String stay = (String) staycheck_box.getSelectedItem();
			String room = (String) roomcheck_box.getSelectedItem();
            
			if(JCP.cancelRev(revname, checkin, stay, room))
				{
				JCP.roomState();
	            makecomponent3();
	            getContentPane().revalidate();
	            time = JCP.findtime();
	    		timelabel.setText("���� ��¥: ("+time+")");
	    		timelabel.setBounds(660,95,150,50);
	    		getContentPane().revalidate();
	            JOptionPane.showMessageDialog(null, "������ ��� �Ǿ����ϴ�.", "���� ��� Ȯ��", JOptionPane.INFORMATION_MESSAGE, null);
//				getContentPane().repaint();
				}
		}
		//�α���
		if(buttonAction == loginButton)
		{
			username = idInput.getText();
			password = new String(pwdInput.getPassword());
//			setVisible(false);
			getContentPane().removeAll();
//			getContentPane().revalidate();
			setLayout(null);
			setTitle("ȣ�� ���� ���� ���α׷�");
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setBounds(100,20,860,730);
			
			//File Chooser
			openItem.addActionListener(this);
	        fileMenu.add(openItem);
	        mb.add(fileMenu);
	        setJMenuBar(mb);
//			getContentPane().add(mb, BorderLayout.CENTER);
			
			//���� ��¥ ���ϱ�
	        JDBC_Connect_Product JCP = new JDBC_Connect_Product(username, password);
			time = JCP.findtime();
			timelabel.setText("���� ��¥: ("+time+")");
			timelabel.setBounds(660,95,150,50);
			getContentPane().add(timelabel, BorderLayout.CENTER);
			
			//���α׷� Ÿ��Ʋ
			Title.setBounds(180,50,500,50);
			Title.setBorder(new LineBorder(Color.black,3));
			Title.setAlignmentX(CENTER_ALIGNMENT);
			Title.setHorizontalAlignment(JLabel.CENTER);
			Title.setFont(font2);
			getContentPane().add(Title, BorderLayout.CENTER);
			
			MainPanel = new JPanel();
			MainPanel.setLayout(null);
			MainPanel.setBorder(new TitledBorder(new LineBorder(Color.black,1),"���/��ȸ"));
			MainPanel.setBounds(50,420,750,230);
			
			panel = new JPanel();
			panel.setLayout(null);
			makecomponent3();
			getContentPane().add(showpanel, BorderLayout.CENTER);
			makeComponent();
			getContentPane().add(panel, BorderLayout.CENTER);
			makeComponent2();
			getContentPane().add(MainPanel, BorderLayout.CENTER);
		}
	}
}