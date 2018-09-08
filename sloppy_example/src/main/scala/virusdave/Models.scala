package virusdave

import sloppy.db.SlickPgProfile.api._
import sloppy.FlattishShape

object Models {

  case class Person(id: Int, name: String, sex: String)
  case class LinkedPerson(hasAs: String, person: Person)

  case class PersonLift(id: Rep[Int], name: Rep[String], sex: Rep[String])
  implicit val PersonShape: FlattishShape[PersonLift, Person] =
    new CaseClassShape(PersonLift.tupled, Person.tupled)

  case class LinkedPersonLift(hasAs: Rep[String], person: PersonLift)
  implicit val linkedPersonShape: FlattishShape[LinkedPersonLift, LinkedPerson] =
    new CaseClassShape(LinkedPersonLift.tupled, LinkedPerson.tupled)
}
