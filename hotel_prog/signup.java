import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class signup  implements ActionListener {
	private String username;
	
	private String username2;
	private String password;
	
    private String phone;
    
	private JFrame frame = new JFrame();
    private JPanel panel = new JPanel();

    private JLabel nameLabel = new JLabel("고객명");
    private JLabel staffLabel = new JLabel("직원명");
    private JLabel phoneLabel = new JLabel("연락처");
    private JLabel sexLabel = new JLabel("성별");
    private JLabel addrLabel = new JLabel("주소");
    private JTextField nameInput = new JTextField();
    private JTextField phoneInput = new JTextField();
    private JButton signupButton = new JButton("가입신청");
    private JButton staffsignupButton = new JButton("직원등록");
    private JComboBox<String> check_box = new JComboBox<String>();
    private JComboBox<String> addrcheck_box = new JComboBox<String>();
    
    public signup(String username1, String password1){
    	username2 = username1;
    	password = password1;
    }
    public void Signstore(){
        panel.setLayout(null);
        check_box.addItem("Man");
        check_box.addItem("Woman");
        
        addrcheck_box.addItem("seoul");
        addrcheck_box.addItem("kyeong-gi");
        addrcheck_box.addItem("chung-num");
        addrcheck_box.addItem("chung-buk");
        addrcheck_box.addItem("jeon-num");
        addrcheck_box.addItem("jeon-buk");
        addrcheck_box.addItem("kyeong-buk");
        addrcheck_box.addItem("kyeong-nam");
        addrcheck_box.addItem("another");
        
        nameLabel.setBounds(60, 60, 60, 30);
        phoneLabel.setBounds(60, 105, 60, 30);
        sexLabel.setBounds(60, 150, 60, 30);
        addrLabel.setBounds(60, 200, 60, 30);
        
        nameInput.setBounds(150, 60, 80, 30);
        phoneInput.setBounds(150, 105, 80, 30);
        
        check_box.setBounds(150,150,70,30);
        addrcheck_box.setBounds(150,200,100,30);
        
        signupButton.setBounds(120,280, 100, 30);
        signupButton.addActionListener(this);
        
        check_box.addActionListener(this);
        addrcheck_box.addActionListener(this);
        
        panel.add(addrcheck_box);
        panel.add(check_box);
        panel.add(nameLabel);
        panel.add(phoneLabel);
        panel.add(sexLabel);
        panel.add(addrLabel);
        
        panel.add(nameInput);
        panel.add(phoneInput);
        panel.add(signupButton);

        frame.add(panel);

        frame.setTitle("회원가입");
        frame.setSize(350,400);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setVisible(true);
    	
    }
    public void staffstore(){
        panel.setLayout(null);
        check_box.addItem("Man");
        check_box.addItem("Woman");
        
        addrcheck_box.addItem("seoul");
        addrcheck_box.addItem("kyeong-gi");
        addrcheck_box.addItem("chung-num");
        addrcheck_box.addItem("chung-buk");
        addrcheck_box.addItem("jeon-num");
        addrcheck_box.addItem("jeon-buk");
        addrcheck_box.addItem("kyeong-buk");
        addrcheck_box.addItem("kyeong-nam");
        addrcheck_box.addItem("another");
        
        staffLabel.setBounds(60, 60, 60, 30);
        phoneLabel.setBounds(60, 105, 60, 30);
        sexLabel.setBounds(60, 150, 60, 30);
        addrLabel.setBounds(60, 200, 60, 30);
        
        nameInput.setBounds(150, 60, 80, 30);
        phoneInput.setBounds(150, 105, 80, 30);
        
        check_box.setBounds(150,150,70,30);
        addrcheck_box.setBounds(150,200,100,30);
        
        staffsignupButton.setBounds(120,280, 100, 30);
        staffsignupButton.addActionListener(this);
        
        check_box.addActionListener(this);
        addrcheck_box.addActionListener(this);
        
        check_box.addActionListener(this);
        addrcheck_box.addActionListener(this);
        
        panel.add(addrcheck_box);
        panel.add(check_box);
        panel.add(staffLabel);
        panel.add(phoneLabel);
        panel.add(sexLabel);
        panel.add(addrLabel);
        
        panel.add(nameInput);
        panel.add(phoneInput);
        panel.add(staffsignupButton);

        frame.add(panel);

        frame.setTitle("직원등록");
        frame.setSize(350,400);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
		Object buttonAction = e.getSource();
		
		if(buttonAction == signupButton){
			username = (String) nameInput.getText();
			phone = (String) phoneInput.getText();
			String sex = (String) check_box.getSelectedItem();
			String addr = (String) addrcheck_box.getSelectedItem();
			JDBC_Connect_Product JCP = new JDBC_Connect_Product(username2, password);
			if(JCP.signup(username,sex,addr,phone))
			{
				JOptionPane.showMessageDialog(null, "가입 되었습니다.", "회원가입", JOptionPane.INFORMATION_MESSAGE, null);
			}
		}
		if(buttonAction == staffsignupButton){
			username = (String) nameInput.getText();
			phone = (String) phoneInput.getText();
			String sex = (String) check_box.getSelectedItem();
			String addr = (String) addrcheck_box.getSelectedItem();
			JDBC_Connect_Product JCP = new JDBC_Connect_Product(username2, password);
			if(JCP.Staff_signup(username,sex,addr,phone))
			{
				JOptionPane.showMessageDialog(null, "등록 되었습니다.", "직원등록", JOptionPane.INFORMATION_MESSAGE, null);
			}
		}
	}
}
