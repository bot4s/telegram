
case class StringContainer(str: String)
trait CommandImplicits {

  implicit def stringToCommandFilter(s: String): StringContainer = StringContainer {
    val target = s.trim().stripPrefix("/")
    require(target.matches("""\w+"""))
    target
  }

  implicit def symbolToCommandFilter(s: Symbol) = {
    stringToCommandFilter(s.name)
  }
}
trait Dummy
class External {
  def showCmd(cmd: StringContainer) = println(cmd.str)
}

class MyCustomClass extends Dummy with CommandImplicits {
  trait Nested {
    val external = new External() with Dummy with CommandImplicits {
      showCmd("first")
      showCmd("second")
      showCmd("third")
    }
  }

  def test(): Unit = {
    new Nested {
      external.showCmd("cmd")
      external.showCmd('a)
    }
  }
}

new MyCustomClass().test
