package info.mukel.telegrambot4s.methods

/**
  * Created by mukel on 14.12.16.
  */
trait Compact {
  _ : Product =>

  override def toString(): String = {

    def unwrap(f: Any): Any = f match {
      case Some(x)  => unwrap(x)
      case Left(x)  => unwrap(x)
      case Right(x) => unwrap(x)
      case ls: List[_] => (ls map unwrap).mkString("[", ", ", "]")
      case _        => f
    }

    val fields = getClass.getDeclaredFields
    val values = productIterator
    val fieldNames = fields map (_.getName)

    val pairs = fieldNames
      .zip(values.toSeq)
      .map {
        case (k, v) => (k, unwrap(v))
      }

    val params = pairs.filterNot(_._2 == None) map { case (k, v) => s"$k : $v" }

    getClass.getSimpleName + params.mkString("(", ", ", ")")
  }

}
