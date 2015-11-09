package eyetest.data
package derby

import subscript.language
import eyetest.util.Predef._

import java.sql._

class DerbyUserRepo(val conn: Connection) extends UserRepo {

  override script..
    all = val stmt = conn.createStatement
          var result: List[String] = Nil

          {!
            val resultSet = stmt.executeQuery("SELECT * FROM USERS")
            while (resultSet.next()) result ::= resultSet.getString("USERNAME")
          !}

          let stmt.close()
          success: result

    write(username: String) = val pstmt = conn.prepareStatement("INSERT INTO USERS VALUES (?)")
                              {!
                                pstmt.setString(1, username)
                                pstmt.executeUpdate()
                                pstmt.close()
                              !}


}

