package virusdave

import sloppy.db.SlickPgProfile.api._
import sloppy.{FlattishShape, QueryContentsAppender, QueryContentsFilter, QueryWithExtractor}
import virusdave.Models._
import virusdave.db.gen.Tables._

object Queries {
  object AddLinked0
      extends QueryContentsAppender[Rep[String], LinkedPersonLift, LinkedPerson] {
    override def appendTo[RA, A, C[_]](
        qwe: QueryWithExtractor[RA, A, C, Rep[String]])(
        implicit aShape: FlattishShape[RA, A])
    : Query[(RA, LinkedPersonLift), (A, LinkedPerson), C] = {
      qwe.query
        .join(Relationships)
        .on { case (a, rhs) => qwe.extract(a) === rhs.from }
        .filter { case (_, rhs) => rhs.relType === "FRIEND" }
        .join(Users)
        .on { case ((_, rel), rhs) => rel.to === rhs.name }
        .map { case ((a, rel), rhs) =>
          (a,
          LinkedPersonLift(
            hasAs = rel.relType,
            person = PersonLift(
              id = rhs.id,
              name = rhs.name,
              sex = rhs.sex,
            ))
          )
        }
    }
  }



  class AddLinkedPersons(users: Query[Users, UsersRow, Seq], relType: String*)
      extends QueryContentsAppender[Rep[String], LinkedPersonLift, LinkedPerson] {
    private val allowedRelationships: Option[Rep[List[String]]] =
      if (relType.nonEmpty) Some(relType.toList) else None

    override def appendTo[RA, A, C[_]](
        qwe: QueryWithExtractor[RA, A, C, Rep[String]])(
        implicit aShape: FlattishShape[RA, A])
    : Query[(RA, LinkedPersonLift), (A, LinkedPerson), C] = {
      qwe.query
        .join(Relationships)
        .on { case (a, rhs) => qwe.extract(a) === rhs.from }
        .filter { case (_, rhs) =>
          // Either the relationship is of the specified type...
          rhs.relType.in(Query(allowedRelationships.getOrElse(List.empty[String].bind).unnest)) ||
          // or we don't care what the relationship is at all.
            allowedRelationships.isEmpty
        }
        .join(users)
        .on { case ((_, rel), rhs) => rel.to === rhs.name }
        .map { case ((a, rel), rhs) =>
          (a,
            LinkedPersonLift(
              hasAs = rel.relType,
              person = PersonLift(
                id = rhs.id,
                name = rhs.name,
                sex = rhs.sex,
              ))
          )
        }
    }
  }
  object AddLinkedPersons {
    def apply(relType: String*): AddLinkedPersons =
      new AddLinkedPersons(Users, relType: _*)
    def apply(users: Query[Users, UsersRow, Seq], relType: String*): AddLinkedPersons =
      new AddLinkedPersons(users, relType: _*)
  }

  val AddLinked = AddLinkedPersons()
  val AddFriends = AddLinkedPersons("FRIEND")




  // Filter queries to only popular people.  That is, people who are
  // the friends of at least some threshold number of other people
  // (not including family and spouses)
  class OnlyPopular(threshold: Int) extends QueryContentsFilter[Rep[String]] {
    val popular =
      Relationships
        .filter(_.relType === "FRIEND")
      .groupBy(_.to)
      .map { case (to, group) => (to, group.size) }
      .filter { case (to, count) => count >= threshold }
      .map { case (to, _) => to }

    override def filter[RA, A, C[_]](
        qwe: QueryWithExtractor[RA, A, C, Rep[String]])(
        implicit _aShape: FlattishShape[RA, A])
    : Query[RA, A, C] = {
      qwe.query
        .filter { a => qwe.extract(a).in(popular) }
    }
  }
  object OnlyPopular {
    def apply(threshold: Int = 4): OnlyPopular = new OnlyPopular(threshold)
  }
}
