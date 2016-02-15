package eyetest.data

import java.io.Closeable

trait Repositories extends Closeable {

  def  userRepo:  UserRepo
  def scoreRepo: ScoreRepo

}