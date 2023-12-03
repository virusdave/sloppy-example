package virusdave

import sloppy.db.SlickPgProfile.api._
import sloppy.FlattishShape

object Models {
  type Name = String
  type Relationship = String

  case class Person(id: Int, name: Name, sex: String)
  case class LinkedPerson(hasAs: Relationship, person: Person)




  case class PersonLift(
      id: Rep[Int], name: Rep[Name], sex: Rep[String])
  case class LinkedPersonLift(
      hasAs: Rep[Relationship], person: PersonLift)







  implicit val PersonShape: FlattishShape[PersonLift, Person] =
    new CaseClassShape(PersonLift.tupled, Person.tupled)
  implicit val linkedPersonShape: FlattishShape[LinkedPersonLift, LinkedPerson] =
    new CaseClassShape(LinkedPersonLift.tupled, LinkedPerson.tupled)
}
