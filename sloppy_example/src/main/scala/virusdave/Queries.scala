package virusdave

import sloppy.db.SlickPgProfile.api._
import sloppy.{FlattishShape, QueryContentsAppender, QueryContentsFilter, QueryWithExtractor}
import virusdave.Models._
import virusdave.db.gen.Tables._

object Queries {
  import sloppy.QueryCompositionOps._



  object AddFriends0
      extends QueryContentsAppender[
        Name, LinkedPersonLift, LinkedPerson] {
    override def appendTo[RA, A, C[_]](
        qwe: QWE[RA, A, C])(
        implicit aShape: FlattishShape[RA, A])
    : Query[(RA, LinkedPersonLift), (A, LinkedPerson), C] = {
      qwe.query
        .join(Relationships)
        .on { case (a, rhs) => qwe.extract(a) === rhs.from }
        .filter { case (_, rhs) => rhs.relType === "FRIEND" } // ***
        .join(Users)                                          // ***
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






  // TODO: Instead of using `Users` and `UsersRow`, this could better take a
  //  `QueryWithExtractor[UserLift, User]` to maximize
  //  reusability and compositionality.
  class AddLinkedPersons[_RA, _A, _C[_]](
      users: QueryWithExtractor[_RA, _A, _C, Users, UsersRow],
      relType: Relationship*)
      extends QueryContentsAppender[
        Name, LinkedPersonLift, LinkedPerson] {
    private val allowedRelationships: Option[Rep[List[Relationship]]] =
      if (relType.nonEmpty) Some(relType.toList) else None

    override def appendTo[RA, A, C[_]](
        qwe: QWE[RA, A, C])(
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
        .join(users.extracted)
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



  // Helper methods to construct the Appenders
  object AddLinkedPersons {
    def apply(relType: Relationship*) =
      new AddLinkedPersons(Users.asIs, relType: _*)
    def apply(users: Query[Users, UsersRow, Seq], relType: Relationship*) =
      new AddLinkedPersons(users.asIs, relType: _*)
  }


  // Pre-created appenders
  val AddLinked = AddLinkedPersons()
  val AddFriends = AddLinkedPersons("FRIEND")






  // Filter queries to only popular people.  That is, people who are
  // the friends of at least some threshold number of other people
  // (not including family and spouses)
  class OnlyPopular(threshold: Int)
      extends QueryContentsFilter[Name] {

    private[this] val popular = {
      Relationships
        .filter(_.relType === "FRIEND")
        .groupBy(_.to)
        .map { case (to, group) => (to, group.size) }
        .filter { case (to, count) => count >= threshold }
        .map { case (to, _) => to }
    }

    override def filter[RA, A, C[_]](
        qwe: QWE[RA, A, C])(
        implicit _aShape: FlattishShape[RA, A])
    : Query[RA, A, C] = {
      qwe.query
        .filter { a => qwe.extract(a).in(popular) }
    }
  }

  // Helpers to create Filters
  object OnlyPopular {
    def apply(threshold: Int = 4): OnlyPopular = new OnlyPopular(threshold)
  }
}
