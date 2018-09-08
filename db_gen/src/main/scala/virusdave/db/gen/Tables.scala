package virusdave.db.gen
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = sloppy.db.SlickPgProfile
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.jdbc.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Relationships.schema ++ ScratchTestTable.schema ++ Users.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Relationships
   *  @param id Database column id SqlType(serial), AutoInc, PrimaryKey
   *  @param from Database column from SqlType(text)
   *  @param to Database column to SqlType(text)
   *  @param relType Database column rel_type SqlType(text), Default(FRIEND) */
  case class RelationshipsRow(id: Int, from: String, to: String, relType: String = "FRIEND")
  /** GetResult implicit for fetching RelationshipsRow objects using plain SQL queries */
  implicit def GetResultRelationshipsRow(implicit e0: GR[Int], e1: GR[String]): GR[RelationshipsRow] = GR{
    prs => import prs._
    RelationshipsRow.tupled((<<[Int], <<[String], <<[String], <<[String]))
  }
  /** Table description of table relationships. Objects of this class serve as prototypes for rows in queries. */
  class Relationships(_tableTag: Tag) extends profile.api.Table[RelationshipsRow](_tableTag, "relationships") {
    def * = (id, from, to, relType) <> (RelationshipsRow.tupled, RelationshipsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(from), Rep.Some(to), Rep.Some(relType))).shaped.<>({r=>import r._; _1.map(_=> RelationshipsRow.tupled((_1.get, _2.get, _3.get, _4.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column from SqlType(text) */
    val from: Rep[String] = column[String]("from")
    /** Database column to SqlType(text) */
    val to: Rep[String] = column[String]("to")
    /** Database column rel_type SqlType(text), Default(FRIEND) */
    val relType: Rep[String] = column[String]("rel_type", O.Default("FRIEND"))

    /** Index over (from,relType,to) (database name relationships_BY_from_reltype_to) */
    val index1 = index("relationships_BY_from_reltype_to", (from, relType, to))
    /** Index over (from,to) (database name relationships_BY_from_to) */
    val index2 = index("relationships_BY_from_to", (from, to))
    /** Index over (to,from) (database name relationships_BY_to_from) */
    val index3 = index("relationships_BY_to_from", (to, from))
  }
  /** Collection-like TableQuery object for table Relationships */
  lazy val Relationships = new TableQuery(tag => new Relationships(tag))

  /** Entity class storing rows of table ScratchTestTable
   *  @param somePkey Database column some_pkey SqlType(serial), AutoInc, PrimaryKey
   *  @param someText Database column some_text SqlType(text), Default(None)
   *  @param someInteger Database column some_integer SqlType(int4), Default(None)
   *  @param someBoolean Database column some_boolean SqlType(bool), Default(false) */
  case class ScratchTestTableRow(somePkey: Int, someText: Option[String] = None, someInteger: Option[Int] = None, someBoolean: Boolean = false)
  /** GetResult implicit for fetching ScratchTestTableRow objects using plain SQL queries */
  implicit def GetResultScratchTestTableRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[Option[Int]], e3: GR[Boolean]): GR[ScratchTestTableRow] = GR{
    prs => import prs._
    ScratchTestTableRow.tupled((<<[Int], <<?[String], <<?[Int], <<[Boolean]))
  }
  /** Table description of table scratch_test_table. Objects of this class serve as prototypes for rows in queries. */
  class ScratchTestTable(_tableTag: Tag) extends profile.api.Table[ScratchTestTableRow](_tableTag, "scratch_test_table") {
    def * = (somePkey, someText, someInteger, someBoolean) <> (ScratchTestTableRow.tupled, ScratchTestTableRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(somePkey), someText, someInteger, Rep.Some(someBoolean))).shaped.<>({r=>import r._; _1.map(_=> ScratchTestTableRow.tupled((_1.get, _2, _3, _4.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column some_pkey SqlType(serial), AutoInc, PrimaryKey */
    val somePkey: Rep[Int] = column[Int]("some_pkey", O.AutoInc, O.PrimaryKey)
    /** Database column some_text SqlType(text), Default(None) */
    val someText: Rep[Option[String]] = column[Option[String]]("some_text", O.Default(None))
    /** Database column some_integer SqlType(int4), Default(None) */
    val someInteger: Rep[Option[Int]] = column[Option[Int]]("some_integer", O.Default(None))
    /** Database column some_boolean SqlType(bool), Default(false) */
    val someBoolean: Rep[Boolean] = column[Boolean]("some_boolean", O.Default(false))
  }
  /** Collection-like TableQuery object for table ScratchTestTable */
  lazy val ScratchTestTable = new TableQuery(tag => new ScratchTestTable(tag))

  /** Entity class storing rows of table Users
   *  @param id Database column id SqlType(serial), AutoInc, PrimaryKey
   *  @param name Database column name SqlType(text)
   *  @param sex Database column sex SqlType(text) */
  case class UsersRow(id: Int, name: String, sex: String)
  /** GetResult implicit for fetching UsersRow objects using plain SQL queries */
  implicit def GetResultUsersRow(implicit e0: GR[Int], e1: GR[String]): GR[UsersRow] = GR{
    prs => import prs._
    UsersRow.tupled((<<[Int], <<[String], <<[String]))
  }
  /** Table description of table users. Objects of this class serve as prototypes for rows in queries. */
  class Users(_tableTag: Tag) extends profile.api.Table[UsersRow](_tableTag, "users") {
    def * = (id, name, sex) <> (UsersRow.tupled, UsersRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(name), Rep.Some(sex))).shaped.<>({r=>import r._; _1.map(_=> UsersRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(text) */
    val name: Rep[String] = column[String]("name")
    /** Database column sex SqlType(text) */
    val sex: Rep[String] = column[String]("sex")

    /** Index over (name) (database name users_BY_name) */
    val index1 = index("users_BY_name", name)
  }
  /** Collection-like TableQuery object for table Users */
  lazy val Users = new TableQuery(tag => new Users(tag))
}
