package eyetest.data
package derby

import subscript.language

import subscript.Predef._

import java.sql._

class DerbyUserRepo(val conn: Connection) extends UserRepo {


  def doWrite(username: String) = {val      pstmt = conn.prepareStatement("INSERT INTO USERS VALUES (?)")
                                   try     {pstmt.setString(1, username)
                                            pstmt.executeUpdate()}
                                   catch   {case e:Throwable => e.printStackTrace()}
                                   finally {pstmt.close()}
                                  }

  override script..

    all = {* val stmt = conn.createStatement
             var result: List[String] = Nil
             try {val resultSet = stmt.executeQuery("SELECT * FROM USERS")
                  while (resultSet.next()) result ::= resultSet.getString("USERNAME")}
           //catch   {case e:Throwable => e.printStackTrace()}
             finally {stmt.close()}
             result
          *}

    write(username: String) = {* doWrite(username) *}


}

