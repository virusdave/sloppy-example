package virusdave

import scala.concurrent.duration._
import scala.concurrent.Await
import slick.jdbc.PostgresProfile.api._
import sloppy.QueryCompositionOps._
import virusdave.Models.PersonLift
import virusdave.db.gen.Tables._
import virusdave.Queries._

object Examples {
  def main(args: Array[String]): Unit = {

    /**
      * 1) Simple use of just Slick: Find Arya in the users
      */
    val users = Users.map { row =>
      PersonLift(id = row.id, name = row.name, sex = row.sex)
    }

    val arya = users.filter(_.name === "Arya")
    val sansa = users.filter(_.name === "Sansa")

    print("arya", arya.result.head)
    print("sansa", sansa.result.head)


//    /**
//      * 2) Simple use of a query appender: Find people Arya knows
//      */
//    // Who are arya's peoples ?
//    val aryaFriends = arya.byUsing(_.name).append(AddLinked0)
//    prints("arya friends", aryaFriends.result)


//    /**
//      * 3) Use the generalized version of "linked user" appender
//      *    (But still find Arya's peoples)
//      */
//    val aryaFriends2 = arya.byUsing(_.name).append(AddLinked)
//    prints("generalized implementation", aryaFriends2.result)


//    /**
//      * 4) Use the generalized version to find siblings
//      */
    // but who are her siblings?
    val siblings = AddLinkedPersons("BROTHER", "SISTER") // ****
//
//    val aryaSiblings = arya.byUsing(_.name).append(siblings)
//    prints("arya siblings", aryaSiblings.result)


//    /**
//      * 5) Chain together some appenders to find "friends of parents"
//      */
    val parents = AddLinkedPersons("MOTHER", "FATHER") // ****
//
//    var aryaFriendsOfParents =
//      arya.byUsing(_.name).append(parents).byUsing(_._2.person.name).append(AddLinked)
//    prints("arya friends of parents", aryaFriendsOfParents.result)


//    /**
//      * 6) That can also be done via composition of appenders
//      */
//    val addFriendsOfParents = parents.thenByUsing(_.person.name)(AddLinked)
//
//    val aryaFriendsOfParents2 = arya.byUsing(_.name).append(addFriendsOfParents)
//    prints("arya friends of parents 2", aryaFriendsOfParents2.result)


//    /**
//      * 7) And we can combine these a lot
//      */
    val mothers = AddLinkedPersons("MOTHER") // ****
    val spouse = AddLinkedPersons("HUSBAND", "WIFE") // ****
    val mothersInLaw = spouse.thenByUsing(_.person.name)(mothers) // ****
    val siblingsMothersInLaw = siblings.thenByUsing(_.person.name)(mothersInLaw) // ****
    val siblingsPeoplesMothersInLaw = // ****
      siblings // ****
        .thenByUsing(_.person.name)(AddLinkedPersons()) // ****
        .thenByUsing(_._2.person.name)(mothersInLaw) // ****
//
//    prints("Arya mother",
//      arya.byUsing(_.name).append(mothers).result)
//    prints("Sansa motherInLaw",
//      sansa.byUsing(_.name).append(mothersInLaw).result)
//    prints("Arya siblingsMothersInLaw",
//      arya.byUsing(_.name).append(siblingsMothersInLaw).result)
//    prints("Arya siblingsPeoplesMothersInLaw",
//      arya.byUsing(_.name).append(siblingsPeoplesMothersInLaw).result)


//    /**
//      * 8) Compositions with filtering
//      */
//    val popularPeople = users.byUsing(_.name).filter(OnlyPopular())
//    prints("Popular people", popularPeople.result)
//
//    val popularPeoplesPopularPeople =
//      popularPeople
//        .byUsing(_.name)
//        .append(AddLinkedPersons(
//          Users.byUsing(_.name).filter(OnlyPopular())))
//    prints("Social Elite", popularPeoplesPopularPeople.result)


//    /**
//      * 9) Who should send happy mothers day wishes to whom?
//      */
//    val happyMothersDay =
//      users.byUsing(_.name).append(mothers) ++
//      users.byUsing(_.name).append(mothersInLaw).map { case (a, (_, b)) => (a, b.copy(hasAs = "MOTHER IN LAW")) }
//    prints("Happy mother's day wishes", happyMothersDay.result)
//
//    // Note that these work on arbitrary queries!
//    val fromJustTheMen =
//      users.filter(_.sex === "M")
//        .byUsing(_.name).append(mothers) ++
//      users.filter(_.sex === "M")
//        .byUsing(_.name).append(mothersInLaw).map { case (a, (_, b)) => (a, b.copy(hasAs = "MOTHER IN LAW")) }
//    prints("Happy mother's day wishes from the men", fromJustTheMen.result)
//
//    println(s"SQL:\n  ${fromJustTheMen.result.statements.head}")
  }





  // Annoying printer helpers and initial setup
  def prints[A](prefix: String, as: DBIO[Seq[A]]): Unit = {
    val res = Await.result(db.run(as), 30.seconds)
    println(s"$prefix -->")
    res.foreach(a => println(s"  $a"))
    println("\n\n")
  }
  def print[A](prefix: String, a: DBIO[A]): Unit = { val res = Await.result(db.run(a), 30.seconds);  println(s"$prefix --> $res\n\n") }
  def print[A](a: DBIO[A]): Unit = print("", a)


  private val db = {
    // Touch the driver to force it to register itself.
    new org.postgresql.Driver: Unit

    Database.forURL(
      "jdbc:postgresql://localhost/dave", "dave", "dave", keepAliveConnection = true)
  }
}
