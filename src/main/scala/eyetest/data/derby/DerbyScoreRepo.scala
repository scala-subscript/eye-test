package eyetest.data
package derby

import subscript.language
import eyetest.util.Predef._

import java.sql._

class DerbyScoreRepo(val conn: Connection) extends ScoreRepo {

  val scoresOfStmt =
    "SELECT * FROM SCORES WHERE USERNAME = ?"

  val writeStmt =
    "INSERT INTO SCORES (${Col.USERNAME}, ${Col.RIGHT_EYE}, ${Col.LEFT_EYE}) VALUES (?, ?, ?)"

  override script..
    scoresOf(user: String) = val pstmt = conn.prepareStatement(scoresOfStmt)
                             let pstmt.setString(1, user)
                             var result: List[Score] = Nil

                             {!
                               val resultSet = pstmt.executeQuery()
                               def dbl(name: String) = resultSet.getDouble(name)
                               while(resultSet.next()) result ::= ((dbl(Col.RIGHT_EYE), dbl(Col.LEFT_EYE), resultSet.getTimestamp(Col.DATE)))
                             !}

                             let pstmt.close()
                             success: result

    write(user: String, score: (Double, Double)) = val pstmt = conn.prepareStatement(writeStmt)
                                                   {!
                                                     pstmt.setString(1, user)
                                                     pstmt.setDouble(2, score._1)
                                                     pstmt.setDouble(3, score._2)
                                                     pstmt.executeUpdate()
                                                   !}
                                                   let pstmt.close()

}