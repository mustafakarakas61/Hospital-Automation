package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BasHekim extends User {
//veritabaný iþlemleri için
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null; // hazýrlanabilen statement
	Connection con = conn.connDb();

	public BasHekim(int id, String tcno, String name, String password, String type) {
		super(id, tcno, name, password, type);

	}

	public BasHekim() {
	}

	public ArrayList<User> getDoctorList() throws SQLException {
		ArrayList<User> list = new ArrayList<>();

		User obj;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM user WHERE type='doktor'");
			while (rs.next()) {
				obj = new User(rs.getInt("id"), rs.getString("tcno"), rs.getString("name"), rs.getString("password"),
						rs.getString("type"));
				list.add(obj);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} // finally yazýnca Statement Result connection fala n null deðerleri falan
			// onlarý Arraylist<user> içinde tanýmla

		return list;

	}

	// bir klinikteki doktorlarý listelemek için yazýyorum
	public ArrayList<User> getClinicDoctorList(int clinic_id) throws SQLException {
		ArrayList<User> list = new ArrayList<>();

		User obj;
		try {
			st = con.createStatement();
			rs = st.executeQuery(
					"SELECT u.id,u.tcno,u.type,u.name,u.password FROM worker w LEFT JOIN user u ON w.user_id=u.id WHERE clinic_id= "
							+ clinic_id);
			
			
			while (rs.next()) {
				obj = new User(rs.getInt("u.id"), rs.getString("u.tcno"), rs.getString("u.name"),
						rs.getString("u.password"), rs.getString("u.type"));
				if(rs.getInt("u.id")>0) {
				list.add(obj);
			}
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} // finally yazýnca Statement Result connection fala n null deðerleri falan
			// onlarý Arraylist<user> içinde tanýmla

		return list;

	}

	public boolean addDoctor(String tcno, String password, String name) throws SQLException {

		String query = "INSERT INTO user" + "(tcno,password,name,type) VALUES" + "(?,?,?,?)";
		boolean key = false;
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, tcno);// 1 2 3 4 soru iþaretlerin sýrasý
			preparedStatement.setString(2, password);
			preparedStatement.setString(3, name);
			preparedStatement.setString(4, "doktor");
			preparedStatement.executeUpdate();// veri tabanýna veri eklemek için preparedStatement kullanýoz
			key = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (key)
			return true;
		else
			return false;
	}

	public boolean deleteDoctor(int id) throws SQLException {

		String query = "DELETE FROM  user  WHERE id = ?";
		boolean key = false;
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, id);// 1 2 3 4 soru iþaretlerin sýrasý
			preparedStatement.executeUpdate();
			key = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (key)
			return true;
		else
			return false;
	}

	public boolean updateDoctor(int id, String tcno, String password, String name) throws SQLException {

		String query = "UPDATE user SET name= ?,tcno=?,password=? WHERE id= ?";
		boolean key = false;
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, name);// 1 2 3 4 soru iþaretlerin sýrasý
			preparedStatement.setString(2, tcno);
			preparedStatement.setString(3, password);
			preparedStatement.setInt(4, id);

			preparedStatement.executeUpdate();
			key = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (key)
			return true;
		else
			return false;
	}

	// bashekim eklediðinden buraya eklioz
	public boolean addWorker(int user_id, int clinic_id) throws SQLException {

		String query = "INSERT INTO worker" + "(user_id,clinic_id) VALUES" + "(?,?)";
		boolean key = false;
		int count = 0;// ayný kiþiyi eklememek için oluþturduk
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM worker WHERE clinic_id= " + clinic_id + " AND user_id=" + user_id + "");// týrnak
																														// dikkat
																														// 1
																														// saatte
																														// çözdüm
																														// hatayý
			while (rs.next()) { // sorguda eþitliði sorguladk, varsa while içine girip count u arttýracak,
				count++;
			}
			if (count == 0) {
				preparedStatement = con.prepareStatement(query);
				preparedStatement.setInt(1, user_id);// 1 2 3 4 soru iþaretlerin sýrasý
				preparedStatement.setInt(2, clinic_id);
				preparedStatement.executeUpdate();// veri tabanýna veri eklemek için preparedStatement kullanýoz
			}
			key = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (key)
			return true;
		else
			return false;
	}

}
