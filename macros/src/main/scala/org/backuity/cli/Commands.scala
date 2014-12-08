package org.backuity.cli

case class Commands( options : Set[CliOption[_]],
                     commands : Set[Command]) {

  val commandsSortedByLabel = commands.toList.sortBy(_.label)

  def findByName(name: String) : Option[Command] = {
    commands.find( _.label == name)
  }
}

object Commands {
  /** Build a Commands by infering its global arguments */
  def apply[T <: Command : Manifest](commands : T*) : Commands = {
    if( commands.size < 2 ) sys.error("A Commands must have at least 2 commands")

    val commonArgs = commands.map(_.options).reduce( _.intersect(_) )
    new Commands(commonArgs, commands.toSet)
  }
}