package virusdave.db

import slick.codegen.SourceCodeGenerator

// TODO(dave): I can't remember why i didn't just use the slick codegen main.  Perhaps because of the amount
// of typing it required?  Maybe it should be removed...
//
// Example invocation:
//   runMain virusdave.db.TablesCodeGen "sloppy.db.SlickPgProfile" "org.postgresql.Driver" "jdbc:postgresql://localhost/dave" "db_gen/src/main/scala" "virusdave.db.gen" "dave" "dave"
object TablesCodeGen {
  def main(args: Array[String]): Unit = {
    println(s"${args.toSeq}")
    SourceCodeGenerator.main(args)
  }
}
