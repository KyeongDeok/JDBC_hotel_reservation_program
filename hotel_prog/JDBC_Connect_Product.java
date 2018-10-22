import java.io.FileReader;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class JDBC_Connect_Product {
	private String username;
    private String password;	
	public JDBC_Connect_Product(String username1, String password1){
		username = username1;
		password = password1;
		System.out.println(username);
	}
	
	//SQL 스크립트 실행하기
	public void fileopen(String filepath){
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			JDBC_GUI.conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", username, password);
			
//			String sqlStr2 = "@"+filepath;
//			System.out.println(sqlStr2);
//			JDBC_GUI.stmt = JDBC_GUI.conn.prepareStatement(sqlStr2);
//			JDBC_GUI.stmt.executeQuery(sqlStr2);
			//스크립트를 실행합니다.
			SqlScriptRunner script = new SqlScriptRunner(JDBC_GUI.conn,true);
			FileReader createTables = new FileReader(filepath);
			script.runScript(createTables);
			System.out.println(filepath);

			roomState();
			JDBC_GUI.stmt.close();
		} catch(Exception e) {
			
		} finally {
			try {
				if(JDBC_GUI.stmt != null)
					JDBC_GUI.stmt.close();
				if(JDBC_GUI.conn != null)
					JDBC_GUI.conn.close();
			} catch(SQLException e){
				
			}
		}
	}
	//객실 조회
	public void roomSearch(String roomnumber) {
		String room_num = "";
		String avail = "";
		String state = "";
		String type = "";
		String maxcust ="";
		String maxcustnum ="";
		String maxstaff ="";
		String maxstaffnum ="";
		
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			JDBC_GUI.conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", username, password);
			
			String sqlStr = "select * from Room where Room_num = \'" + roomnumber + "\'";
			JDBC_GUI.stmt = JDBC_GUI.conn.prepareStatement(sqlStr);
			ResultSet rs  = JDBC_GUI.stmt.executeQuery(sqlStr);
			
			while(rs.next()) {
				room_num = rs.getString("Room_num");
				avail = rs.getString("Avail_guest");
				state = rs.getString("state");
				type = rs.getString("room_type");
				
				System.out.println(roomnumber);
			}
			
			rs.close();
			JDBC_GUI.stmt.close();
			
			//최다 스태프 찾기
			String sqlStr1 = "with m as (select staff_id, count(staff_id) as S from room natural join reservation where room_num = \'"
					+ room_num +"\'group by staff_id) select staff_id, max(S) from m group by staff_id";
					JDBC_GUI.stmt = JDBC_GUI.conn.prepareStatement(sqlStr1);
					ResultSet rs1  = JDBC_GUI.stmt.executeQuery(sqlStr1);
					
					while(rs1.next()) {
						maxstaff = rs1.getString("staff_id");
						maxstaffnum = rs1.getString("max(S)");
						System.out.println(maxstaff);
					}

			rs1.close();
			JDBC_GUI.stmt.close();
			
			//최다 투숙 고객 찾기
			String sqlStr2 = "with m as (select cust_id, count(cust_id) as C from room natural join reservation where room_num = \'"+ room_num 
					+"\'group by cust_id) select cust_id, max(C) from m group by cust_id";
					
			JDBC_GUI.stmt = JDBC_GUI.conn.prepareStatement(sqlStr2);
			ResultSet rs2  = JDBC_GUI.stmt.executeQuery(sqlStr2);
					
					while(rs2.next()) {
						maxcust = rs2.getString("cust_id");
						maxcustnum = rs2.getString("max(C)");
						System.out.println(maxcust);
					}
					
			rs2.close();
			JDBC_GUI.stmt.close();
			
			String sqlStr3 = "select cust_name , staff_name from customer, staff where cust_id = \'"
			+maxcust+"\' and staff_id = \'"+maxstaff+"\'";
			
			JDBC_GUI.stmt = JDBC_GUI.conn.prepareStatement(sqlStr3);
			ResultSet rs3  = JDBC_GUI.stmt.executeQuery(sqlStr3);
					
					while(rs3.next()) {
						maxcust = rs3.getString("cust_name");
						maxstaff = rs3.getString("staff_name");
						System.out.println(maxcust);
					}
					
			rs3.close();
			JDBC_GUI.stmt.close();
			
			JDBC_GUI.TapProductTextArea2.setText('\n' + "객실번호 : " + room_num  + '\n' + "수용가능인원 : " + avail + '\n' + "상태 : "+ state+ '\n'+ "종류 : "+ type + '\n'
					+"투숙고객(최다): "+maxcust+" ("+maxcustnum+")회"+"\n"+"객실전담직원(최다):"+maxstaff+" ("+maxstaffnum+")회");
			
		} catch(Exception e) {
			
		} finally {
			try {
				if(JDBC_GUI.stmt != null)
					JDBC_GUI.stmt.close();
				if(JDBC_GUI.conn != null)
					JDBC_GUI.conn.close();
			} catch(SQLException e){
			}
		}
	}
	
	//고객 조회
	public void customerSearch(String customer) {
		String cust_name="";
		String sex = "";
		String addr = "";
		String phone = "";
		String allstay ="";
		String recent = "";
		String maxstaff = "";
		String maxstaffnum = "";
		
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			JDBC_GUI.conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", username, password);
			
			String sqlStr0 = "select * from Customer where cust_name = \'"+customer+"\'";
					JDBC_GUI.stmt = JDBC_GUI.conn.prepareStatement(sqlStr0);
					ResultSet rs  = JDBC_GUI.stmt.executeQuery(sqlStr0);
					
					while(rs.next()) {
						cust_name = rs.getString("cust_name");
						sex = rs.getString("sex");
						addr = rs.getString("cust_addr");
						phone = rs.getString("cust_phone");
					}
					rs.close();
					JDBC_GUI.stmt.close();
					
				if(cust_name.equals("") || phone.equals("") || sex.equals("") || addr.equals("")){
					JOptionPane.showMessageDialog(null, "등록되지 않은 회원입니다.", "고객조회", JOptionPane.INFORMATION_MESSAGE, null);
					return;
				}
				
			//총 투숙기간 구하기
			String sqlStr = "select to_char(sum(stay_num)) as S from Customer natural join reservation where cust_name = \'"+ cust_name+"\'";
			JDBC_GUI.stmt = JDBC_GUI.conn.prepareStatement(sqlStr);
			ResultSet rs1  = JDBC_GUI.stmt.executeQuery(sqlStr);
			
			while(rs1.next()) {
				allstay = rs1.getString("S");
			}
			
			if(allstay == null){
				allstay = "";
			}
			
			rs1.close();
			JDBC_GUI.stmt.close();
			
			String sqlStr3 = "select distinct to_char(max(to_date(checkout_date)), 'YYYY/MM/DD') as TS from Customer natural join reservation where cust_name = \'"+cust_name+"\'";

			JDBC_GUI.stmt = JDBC_GUI.conn.prepareStatement(sqlStr3);
			ResultSet rs3  = JDBC_GUI.stmt.executeQuery(sqlStr3);
			
			while(rs3.next()) {
				recent = rs3.getString("TS");
				System.out.println(recent);
			}
			
			if(recent == null){
				recent = "";
			}
			
			rs3.close();
			JDBC_GUI.stmt.close();
			
			//최대 직원 구하기
			String sqlStr1 = "with m as (select staff_id, count(staff_id) as S from customer natural join reservation where cust_name = \'"
			+ cust_name +"\'group by staff_id) select staff_name, max(S) from staff natural join m group by staff_name";
			JDBC_GUI.stmt = JDBC_GUI.conn.prepareStatement(sqlStr1);
			ResultSet rs2  = JDBC_GUI.stmt.executeQuery(sqlStr1);
			
			while(rs2.next()) {
				maxstaff = rs2.getString("staff_name");
				maxstaffnum = rs2.getString("max(S)");
				System.out.println(maxstaff);
			}
			
			JDBC_GUI.TapProductTextArea.setText('\n' + "고객명 : " + cust_name + '\n' + "성별 : " + sex + '\n' + "주소 : "+ addr+ '\n' +"전화번호 : "+ phone + '\n'+ "총 투숙기간 : "+ allstay + '\n'+ "최근투숙일 : "+ recent + '\n'
					+ "객실전담직원 최다 : "+ maxstaff +" ("+maxstaffnum+")회"+ '\n');
			rs2.close();
			JDBC_GUI.stmt.close();
		} catch(Exception e) {
		} finally {
			try {
				if(JDBC_GUI.stmt != null)
					JDBC_GUI.stmt.close();
				if(JDBC_GUI.conn != null)
					JDBC_GUI.conn.close();
			} catch(SQLException e){
			}
		}
	}
	
	//직원 조회
	public void staffSearch(String staff) {
		String staff_name = "";
		String sex = "";
		String addr = "";
		String phone = "";
		String maxcust = "";
		String maxcustnum = "";
		String maxroom = "";
		String maxroomnum = "";
		
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			JDBC_GUI.conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", username, password);
			
			String sqlStr = "select * from Staff where staff_name = \'" + staff + "\'";
			JDBC_GUI.stmt = JDBC_GUI.conn.prepareStatement(sqlStr);
			ResultSet rs  = JDBC_GUI.stmt.executeQuery(sqlStr);
			
			while(rs.next()) {
				staff_name = rs.getString("staff_name");
				sex = rs.getString("sex");
				addr = rs.getString("staff_addr");
				phone = rs.getString("staff_phone");
				
				System.out.println(staff_name);
			}
			
			if(staff_name.equals("") || phone.equals("") || sex.equals("") || addr.equals("")){
				JOptionPane.showMessageDialog(null, "등록되지 않은 직원입니다.", "직원조회", JOptionPane.INFORMATION_MESSAGE, null);
				return;
			}
			
			// 최다 관리 고객
			String sqlStr1 = "with m as (select cust_id, count(cust_id) as C from staff natural join reservation where staff_name = \'"
					+ staff_name +"\'group by cust_id) select cust_name, max(C) from customer natural join m group by cust_name";
					JDBC_GUI.stmt = JDBC_GUI.conn.prepareStatement(sqlStr1);
					ResultSet rs1  = JDBC_GUI.stmt.executeQuery(sqlStr1);
					
					while(rs1.next()) {
						maxcust = rs1.getString("cust_name");
						maxcustnum = rs1.getString("max(C)");
						System.out.println(maxcust);
					}
					
					rs1.close();
					JDBC_GUI.stmt.close();
					
			//최다 관리 객실
			String sqlStr2 = "with m as (select room_num, count(room_num) as R from room natural join reservation natural join staff where staff_name = \'"
					+ staff_name +"\'group by room_num) select room_num, max(R) from room natural join m group by room_num";
					JDBC_GUI.stmt = JDBC_GUI.conn.prepareStatement(sqlStr2);
					ResultSet rs2  = JDBC_GUI.stmt.executeQuery(sqlStr2);
					
					while(rs2.next()) {
						maxroom = rs2.getString("room_num");
						maxroomnum = rs2.getString("max(R)");
						System.out.println(maxcust);
					}
					
					rs2.close();
					JDBC_GUI.stmt.close();
							
					
			JDBC_GUI.TapProductTextArea3.setText('\n' + "직원명 : " + staff_name + '\n' + "성별 : " + sex + '\n' + "주소 : "+ addr+ '\n'+ "연락처 : "+ phone + '\n'
					+"접대 고객 (최다): "+maxcust+" ("+maxcustnum+"회) \n"+"관리객실 (최다):"+maxroom+" ("+maxroomnum+")회");
			rs.close();
			JDBC_GUI.stmt.close();
		} catch(Exception e) {
		} finally {
			try {
				if(JDBC_GUI.stmt != null)
					JDBC_GUI.stmt.close();
				if(JDBC_GUI.conn != null)
					JDBC_GUI.conn.close();
			} catch(SQLException e){
			}
		}
	}
	
	//회원가입
	public boolean signup(String customer,String sex,String addr,String phone) {
		String cust_id="";
		String cust_name = customer;
		String nsex = sex;
		String naddr = addr;
		String nphone = phone;
		
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			JDBC_GUI.conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", username, password);
			
			String sqlStr2 = "select count(*) as cust_id from customer";
			JDBC_GUI.stmt = JDBC_GUI.conn.prepareStatement(sqlStr2);
			ResultSet rs  = JDBC_GUI.stmt.executeQuery(sqlStr2);
			
			while(rs.next()){
				cust_id = rs.getString("cust_id");
			}
			
			if(customer.equals("") || phone.equals("")){
				JOptionPane.showMessageDialog(null, "기재사항을 모두 입력해주세요", "회원가입", JOptionPane.INFORMATION_MESSAGE, null);
				return false;
			}
			
			String sqlStr = "insert into Customer values("+cust_id+",\'"+cust_name+"\',\'"+nsex+"\',\'"+naddr+"\',\'"+nphone+"\')";
			
			JDBC_GUI.stmt = JDBC_GUI.conn.prepareStatement(sqlStr);
			JDBC_GUI.stmt.executeUpdate(sqlStr);
			
//			ResultSet rs2  = JDBC_GUI.stmt.executeQuery(sqlStr);
//			
//			while(rs2.next()) {
//				cust_name = rs.getString("cust_name");
//				sex = rs.getString("sex");
//				addr = rs.getString("cust_addr");
//				phone = rs.getString("cust_phone");
//				
//				System.out.println(cust_id+customer);
//			}
			
//			JDBC_GUI.TapProductTextArea.setText('\n' + "고객명 : " + cust_name + '\n' + '\n' + "성별 : " + sex + '\n' + "주소 : "+ addr+ '\n' +"전화번호 : "+ phone + '\n');
//			rs.close();
			JDBC_GUI.stmt.close();
		} catch(Exception e) {
			return false;
		} finally {
			try {
				if(JDBC_GUI.stmt != null)
					JDBC_GUI.stmt.close();
				if(JDBC_GUI.conn != null)
					JDBC_GUI.conn.close();
			} catch(SQLException e){
				return false;
			}
		}
		return true;
	}
	
	//직원 등록
	public boolean Staff_signup(String staff,String sex,String addr,String phone) {
		String staff_id="";
		String staff_name = staff;
		String nsex = sex;
		String naddr = addr;
		String nphone = phone;
		
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			JDBC_GUI.conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", username, password);
			
			if(staff.equals("") || phone.equals("")){
				JOptionPane.showMessageDialog(null, "기재사항을 모두 입력해주세요", "직원등록", JOptionPane.INFORMATION_MESSAGE, null);
				System.out.println("SSS");
				return false;
			}
			
			String sqlStr2 = "select count(*) as cust_id from staff";
			JDBC_GUI.stmt = JDBC_GUI.conn.prepareStatement(sqlStr2);
			ResultSet rs  = JDBC_GUI.stmt.executeQuery(sqlStr2);
			
			while(rs.next()){
				staff_id = rs.getString("cust_id");
			}
			
			String sqlStr = "insert into Staff values("+staff_id+",\'"+staff_name+"\',\'"+nsex+"\',\'"+naddr+"\',\'"+nphone+"\')";
			
			JDBC_GUI.stmt = JDBC_GUI.conn.prepareStatement(sqlStr);
			JDBC_GUI.stmt.executeUpdate(sqlStr);
			
//			ResultSet rs2  = JDBC_GUI.stmt.executeQuery(sqlStr);
//			
//			while(rs2.next()) {
//				cust_name = rs.getString("cust_name");
//				sex = rs.getString("sex");
//				addr = rs.getString("cust_addr");
//				phone = rs.getString("cust_phone");
//				
//				System.out.println(cust_id+customer);
//			}
			
//			JDBC_GUI.TapProductTextArea.setText('\n' + "고객명 : " + cust_name + '\n' + '\n' + "성별 : " + sex + '\n' + "주소 : "+ addr+ '\n' +"전화번호 : "+ phone + '\n');
//			rs.close();
			JDBC_GUI.stmt.close();
		} catch(Exception e) {
			return false;
		} finally {
			try {
				if(JDBC_GUI.stmt != null)
					JDBC_GUI.stmt.close();
				if(JDBC_GUI.conn != null)
					JDBC_GUI.conn.close();
			} catch(SQLException e){
				return false;
			}
		}
		return true;
	}
	
	//예약하기
	public boolean reservation(String revname,String checkin,String stay,String room) {
		String rev_id="";
		String cust_id="";
		String staff_id="";
		String Rv_date="";
		String checkout_date="";
		
		String Rev_name = revname;
		String checkin_date = checkin;
		String stay_num = stay;
		String room_num = room;
		String day_check = "";
		
		if(revname.equals("") || checkin.equals("")){
			JOptionPane.showMessageDialog(null, "기재사항을 모두 입력해주세요", "Title", JOptionPane.INFORMATION_MESSAGE, null);
			return false;
		}
		
		try {
				
			Class.forName("oracle.jdbc.OracleDriver");
			JDBC_GUI.conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", username, password);
			
			//고객 아이디 가져오기
			String sqlStr3 = "select cust_id from customer where cust_name = \'"+ Rev_name +"\'";
			JDBC_GUI.stmt = JDBC_GUI.conn.prepareStatement(sqlStr3);
			ResultSet rs2  = JDBC_GUI.stmt.executeQuery(sqlStr3);
			
			while(rs2.next()){
				cust_id = rs2.getString("cust_id");
			}
			
			if(cust_id.equals("")){
				JOptionPane.showMessageDialog(null, "고객등록이 되어있지 않습니다. 고객등록 먼저 해주세요.", "고객 등록 요청", JOptionPane.INFORMATION_MESSAGE, null);
				return false;
			}
				
			System.out.println(cust_id);
			rs2.close();
			JDBC_GUI.stmt.close();
			
			
			//예약 번호 설정하기			
			String sqlStr2 = "select count(*) as rev_id from Reservation";
			JDBC_GUI.stmt = JDBC_GUI.conn.prepareStatement(sqlStr2);
			ResultSet rs  = JDBC_GUI.stmt.executeQuery(sqlStr2);
			
			while(rs.next()){
				rev_id = rs.getString("rev_id");
			}
			rs.close();
			JDBC_GUI.stmt.close();
			System.out.println(rev_id);
			
			
			// 랜덤으로 스태프 아이디 구하기
			String sqlStr4 = "SELECT S.staff_id FROM (SELECT * FROM Staff ORDER BY DBMS_RANDOM.VALUE) S WHERE ROWNUM = 1";
			JDBC_GUI.stmt = JDBC_GUI.conn.prepareStatement(sqlStr4);
			ResultSet rs3  = JDBC_GUI.stmt.executeQuery(sqlStr4);
			
			while(rs3.next()){
				staff_id = rs3.getString("staff_id");
			}
			System.out.println(staff_id);
			rs3.close();
			JDBC_GUI.stmt.close();
			
			//체크아웃 시간 구하기
			String sqlStr5 = "select to_char(sysdate,'yyyy/mm/dd') as T, to_char(to_date(\'"+checkin_date+"\') +"+stay_num+", 'yyyy/mm/dd') as O from dual";
			JDBC_GUI.stmt = JDBC_GUI.conn.prepareStatement(sqlStr5);
			ResultSet rs4  = JDBC_GUI.stmt.executeQuery(sqlStr5);
			
			while(rs4.next()){
				Rv_date = rs4.getString("T");
				checkout_date = rs4.getString("O");
			}
			rs4.close();
			JDBC_GUI.stmt.close();
			System.out.println(Rv_date);
			System.out.println(checkin_date);
			System.out.println(checkout_date);
			
			//숙박기간 확인 하기			
			String sqlStr7 = "select room_num from reservation where (to_date(Checkin_date) between to_date(\'"+checkin_date+"\') and to_date(\'"+checkout_date+"\')) OR (to_date(Checkout_date) between to_date(\'"+checkin_date+"\') and to_date(\'"+checkout_date+"\'))"
					+ "OR (to_date(Checkin_date) <= to_date(\'"+checkin_date+"\') AND to_date(Checkout_date) >= to_date(\'"+checkout_date+"\') and room_num = \'"+room_num+"\')";
			JDBC_GUI.stmt = JDBC_GUI.conn.prepareStatement(sqlStr7);
			ResultSet rs7  = JDBC_GUI.stmt.executeQuery(sqlStr7);
			
			while(rs7.next()){
				day_check = rs7.getString("room_num");
			}
			
			if(!day_check.equals("")){
				JOptionPane.showMessageDialog(null, "해당 기간에는 이미 예약이 되어 있습니다.", "예약이 되어있음", JOptionPane.INFORMATION_MESSAGE, null);
				return false;
			}
			
			rs7.close();
			JDBC_GUI.stmt.close();
			System.out.println(rev_id);
			
			//reservation table에 데이터 저장
			String sqlStr = "insert into reservation values(\'"+rev_id+"\',\'"+Rv_date+"\',\'"+cust_id+"\',\'"+staff_id+"\',\'"+room_num+"\',\'"+checkin_date+"\',\'"+checkout_date+"\',\'"+stay_num+"\')";
			
			JDBC_GUI.stmt = JDBC_GUI.conn.prepareStatement(sqlStr);
			JDBC_GUI.stmt.executeUpdate(sqlStr);
			JDBC_GUI.stmt.close();
			
			//객실 상태 갱신하기
			roomState();
//			String sqlStr0 = "update room set state = 'Not available' where room_num = \'"+room_num+"\' and \'"+room_num+"\' in (select room_num from reservation where to_date(Checkin_date) <= to_date(Rv_date) and to_date(Checkout_date) >= to_date(Rv_date))";
//			JDBC_GUI.stmt = JDBC_GUI.conn.prepareStatement(sqlStr0);
//			JDBC_GUI.stmt.executeUpdate(sqlStr0);
//			JDBC_GUI.stmt.close();
			
			
		} catch(Exception e) {
			
		} finally {
			try {
				if(JDBC_GUI.stmt != null)
					JDBC_GUI.stmt.close();
				if(JDBC_GUI.conn != null)
					JDBC_GUI.conn.close();
			} catch(SQLException e){
				
			}
		}
		return true;
	}
	
	//예약취소 하기
	public boolean cancelRev(String revname,String checkin,String stay,String room) {
		String cust_id="";
		String checkin_date = checkin;
		String stay_num = stay;
		String room_num = room;
		
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			JDBC_GUI.conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", username, password);
			
			// 예약 확인
			String sqlStr1 = "select cust_id from Reservation natural join customer where cust_name = \'"+revname+"\' and checkin_date = \'"+checkin+"\' and stay_num = \'"+stay+"\' and room_num = \'"+room+"\'";
			JDBC_GUI.stmt = JDBC_GUI.conn.prepareStatement(sqlStr1);
			ResultSet rs  = JDBC_GUI.stmt.executeQuery(sqlStr1);
			
			while(rs.next()) {
				cust_id = rs.getString("cust_id");
				}
			
			if(cust_id.equals("") || revname.equals("") || checkin.equals("")){
				JOptionPane.showMessageDialog(null, "기재사항을 정확히 입력해주세요.(고객명, 체크인, 숙박, 호실)", "예약취소", JOptionPane.INFORMATION_MESSAGE, null);
				return false;
			}
			
			rs.close();
			JDBC_GUI.stmt.close();
			
			// 예약 삭제 실행
			String sqlStr = "delete from Reservation where cust_id =\'"+cust_id+"\' and checkin_date = \'"+checkin_date+"\' and stay_num = \'"+stay_num+"\' and room_num = \'"
					+ room_num +"\'";
			
			JDBC_GUI.stmt = JDBC_GUI.conn.prepareStatement(sqlStr);
			JDBC_GUI.stmt.executeUpdate(sqlStr);
			
			JDBC_GUI.stmt.close();
			
			//객실 상태 갱신하기
			roomState();
			
		} catch(Exception e) {
			return false;
		} finally {
			try {
				if(JDBC_GUI.stmt != null)
					{
					JDBC_GUI.stmt.close();
					}				
				if(JDBC_GUI.conn != null)
					{
					JDBC_GUI.conn.close();
					}
			} catch(SQLException e){
				return false;
			}
		}
		return true;
	}
	
	// 방이 사용가능한지 알아보는 메소드
	public void roomState(){
		String time ="";
		try {
			
		Class.forName("oracle.jdbc.OracleDriver");
		JDBC_GUI.conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", username, password);
		
		//현재시간 가져오기
		String sqlStr = "select to_char(sysdate,'yyyy/mm/dd') as T from dual";
		JDBC_GUI.stmt = JDBC_GUI.conn.prepareStatement(sqlStr);
		ResultSet rs  = JDBC_GUI.stmt.executeQuery(sqlStr);
		
		while(rs.next()) {
			time = rs.getString("T");
			}
		
		rs.close();
		JDBC_GUI.stmt.close();
		System.out.println(time);
		
		//객실 상태 갱신하기
		String sqlStr1 = "update room set state = case when room_num in (select room_num from reservation where to_date(\'"
				+ time+ "\') between to_date(Checkin_date) and to_date(Checkout_date)) then 'Not available' else 'available' end ";
		JDBC_GUI.stmt = JDBC_GUI.conn.prepareStatement(sqlStr1);
		JDBC_GUI.stmt.executeUpdate(sqlStr1);
		
		JDBC_GUI.stmt.close();
		
		} catch(Exception e) {
		} finally {
			try {
				if(JDBC_GUI.stmt != null)
					{
					JDBC_GUI.stmt.close();
					}				
				if(JDBC_GUI.conn != null)
					{
					JDBC_GUI.conn.close();
					}
			} catch(SQLException e){
			}
		}
		
	}
	
	//예약 호실 확인하기
	public boolean revRoomSearch(String roomnumber) {
		String room_num = "";
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			JDBC_GUI.conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", username, password);
			
			//객실 상태 갱신하기
			
			String sqlStr2 = "select room_num from Room where room_num ="+roomnumber+" and state = 'Not available'";
			JDBC_GUI.stmt = JDBC_GUI.conn.prepareStatement(sqlStr2);
			ResultSet rs2  = JDBC_GUI.stmt.executeQuery(sqlStr2);
			
			while(rs2.next()) {
				room_num = rs2.getString("room_num");
				
				if( room_num != null){
					rs2.close();
					JDBC_GUI.stmt.close();
					return true;
				}
				else{
					rs2.close();
					JDBC_GUI.stmt.close();
					break;
				}
				}
			
		} catch(Exception e) {
			return false;
		} finally {
			try {
				if(JDBC_GUI.stmt != null)
					{
					JDBC_GUI.stmt.close();
					}				
				if(JDBC_GUI.conn != null)
					{
					JDBC_GUI.conn.close();
					}
			} catch(SQLException e){
				return false;
			}
		}
		return false;
	}
	
	//현재 시간 구하기
	public String findtime() {
		String time = "";
		
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			JDBC_GUI.conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", username, password);
			
			String sqlStr = "select to_char(sysdate,'yyyy/mm/dd') as T from dual";
			JDBC_GUI.stmt = JDBC_GUI.conn.prepareStatement(sqlStr);
			ResultSet rs  = JDBC_GUI.stmt.executeQuery(sqlStr);
			
			while(rs.next()) {
				time = rs.getString("T");
				}
			
		} catch(Exception e) {
		} finally {
			try {
				if(JDBC_GUI.stmt != null)
					{
					JDBC_GUI.stmt.close();
					}				
				if(JDBC_GUI.conn != null)
					{
					JDBC_GUI.conn.close();
					}
			} catch(SQLException e){
			}
		}
		return time;
	}
}