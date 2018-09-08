-- Create an initial table and populate it with some data.

DROP TABLE IF EXISTS public.scratch_test_table
;
CREATE TABLE public.scratch_test_table
(
  some_pkey serial NOT NULL,
  some_text text,
  some_integer integer,
  some_boolean boolean NOT NULL DEFAULT false,
  CONSTRAINT scratch_test_table_some_pkey PRIMARY KEY (some_pkey)
)
WITH (
  OIDS=FALSE
);

INSERT INTO scratch_test_table(some_text, some_integer, some_boolean)
VALUES
(NULL, 1234, TRUE),
('Hello, world', NULL, FALSE),
('Needs moar cowbell', 42, TRUE)
;



DROP TABLE IF EXISTS public.users
;
CREATE TABLE public.users
(
    id serial NOT NULL,
    name text NOT NULL,
    sex text NOT NULL,

    CONSTRAINT users_id PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);

INSERT INTO users(name, sex)
VALUES
('Aemon', 'M'),
('Grenn', 'M'),
('Samwell', 'M'),
('Aerys', 'M'),
('Jaime', 'M'),
('Robert', 'M'),
('Tyrion', 'M'),
('Tywin', 'M'),
('Alliser', 'M'),
('Mance', 'M'),
('Amory', '?'),
('Oberyn', 'M'),
('Arya', 'F'),
('Anguy', '?'),
('Beric', 'M'),
('Bran', 'M'),
('Brynden', 'M'),
('Cersei', 'F'),
('Gendry', 'M'),
('Gregor', 'M'),
('Joffrey', 'M'),
('Jon', 'M'),
('Rickon', 'M'),
('Roose', 'M'),
('Sandor', 'M'),
('Thoros', 'M'),
('Balon', 'M'),
('Loras', 'M'),
('Belwas', 'M'),
('Barristan', 'M'),
('Illyrio', 'M'),
('Hodor', 'M'),
('Jojen', 'M'),
('Luwin', 'M'),
('Meera', 'F'),
('Nan', 'F'),
('Theon', 'M'),
('Brienne', 'F'),
('Bronn', 'M'),
('Podrick', 'M'),
('Lothar', 'M'),
('Walder', 'M'),
('Catelyn', 'F'),
('Edmure', 'M'),
('Hoster', 'M'),
('Jeyne', 'M'),
('Lysa', 'F'),
('Petyr', 'M'),
('Robb', 'M'),
('Roslin', 'F'),
('Sansa', 'F'),
('Stannis', 'M'),
('Elia', 'F'),
('Ilyn', 'M'),
('Meryn', 'M'),
('Pycelle', 'M'),
('Shae', 'F'),
('Varys', 'M'),
('Craster', 'M'),
('Karl', 'M'),
('Daario', 'M'),
('Drogo', 'M'),
('Irri', '?'),
('Daenerys', 'F'),
('Aegon', 'M'),
('Jorah', 'M'),
('Kraznys', '?'),
('Missandei', 'F'),
('Rakharo', '?'),
('Rhaegar', 'M'),
('Viserys', 'M'),
('Worm', 'M'),
('Davos', 'M'),
('Cressen', 'M'),
('Salladhor', 'M'),
('Eddard', 'M'),
('Eddison', 'M'),
('Gilly', 'F'),
('Qyburn', 'M'),
('Renly', 'M'),
('Tommen', 'M'),
('Janos', 'M'),
('Bowen', 'M'),
('Kevan', 'M'),
('Margaery', 'F'),
('Myrcella', 'F'),
('Dalla', '?'),
('Melisandre', 'F'),
('Orell', 'M'),
('Qhorin', 'M'),
('Rattleshirt', 'M'),
('Styr', 'M'),
('Val', 'M'),
('Ygritte', 'F'),
('Jon Arryn', 'M'),
('Lancel', 'M'),
('Olenna', 'F'),
('Marillion', 'M'),
('Robert Arryn', 'M'),
('Ellaria', 'F'),
('Mace', 'M'),
('Rickard', 'M'),
('Ramsay', 'M'),
('Chataya', '?'),
('Shireen', 'F'),
('Doran', 'M'),
('Walton', 'M')
;

DROP INDEX IF EXISTS public."users_BY_name";

CREATE INDEX "users_BY_name"
  ON public.users
  USING btree
  (name COLLATE pg_catalog."default");



DROP TABLE IF EXISTS public.relationships
;
CREATE TABLE public.relationships
(
    id serial NOT NULL,
    "from" text NOT NULL,
    "to" text NOT NULL,
    "rel_type" text NOT NULL DEFAULT 'FRIEND',

    CONSTRAINT relationships_id PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);

INSERT INTO relationships("from", "to", "rel_type")
VALUES
('Bran',  'Hodor', 'FRIEND'),
('Hodor',  'Bran', 'FRIEND'),
('Jaime',  'Brienne', 'FRIEND'),
('Brienne',  'Jaime', 'FRIEND'),
('Sansa',  'Tyrion', 'HUSBAND'),
('Tyrion',  'Sansa', 'WIFE'),
('Jon',  'Mance', 'FRIEND'),
('Mance',  'Jon', 'FRIEND'),
('Jon',  'Ygritte', 'FRIEND'),
('Ygritte',  'Jon', 'FRIEND'),
('Joffrey',  'Tyrion', 'UNCLE'),
('Tyrion',  'Joffrey', 'NEPHEW'),
('Bran',  'Meera', 'FRIEND'),
('Meera',  'Bran', 'FRIEND'),
('Jon',  'Samwell', 'FRIEND'),
('Samwell',  'Jon', 'FRIEND'),
('Daenerys',  'Jorah', 'FRIEND'),
('Jorah',  'Daenerys', 'FRIEND'),
('Cersei',  'Tyrion', 'BROTHER'),
('Tyrion',  'Cersei', 'SISTER'),
('Bran',  'Jojen', 'FRIEND'),
('Jojen',  'Bran', 'FRIEND'),
('Arya',  'Sandor', 'FRIEND'),
('Sandor',  'Arya', 'FRIEND'),
('Samwell',  'Grenn', 'FRIEND'),
('Grenn',  'Samwell', 'FRIEND'),
('Catelyn',  'Brienne', 'FRIEND'),
('Brienne',  'Catelyn', 'FRIEND'),
('Catelyn',  'Tyrion', 'FRIEND'),
('Tyrion',  'Catelyn', 'FRIEND'),
('Catelyn',  'Jamie', 'FRIEND'),
('Jamie',  'Catelyn', 'FRIEND'),
('Catelyn',  'Lysa', 'SISTER'),
('Lysa',  'Catelyn', 'SISTER'),
('Catelyn',  'Jamie', 'FRIEND'),
('Jamie',  'Catelyn', 'FRIEND'),
('Catelyn',  'Robb', 'SON'),
('Robb',  'Catelyn', 'MOTHER'),
('Catelyn',  'Arya', 'DAUGHTER'),
('Arya',  'Catelyn', 'MOTHER'),
('Catelyn',  'Sansa', 'DAUGHTER'),
('Sansa',  'Catelyn', 'MOTHER'),
('Catelyn',  'Bran', 'SON'),
('Bran',  'Catelyn', 'MOTHER'),
('Rickon',  'Bran', 'BROTHER'),
('Bran',  'Rickon', 'BROTHER'),
('Arya',  'Gendry', 'FRIEND'),
('Gendry',  'Arya', 'FRIEND'),
('Hodor',  'Meera', 'FRIEND'),
('Meera',  'Hodor', 'FRIEND'),
('Tywin',  'Tyrion', 'SON'),
('Tyrion',  'Tywin', 'FATHER'),
('Sansa',  'Margaery', 'FRIEND'),
('Margaery',  'Sansa', 'FRIEND'),
('Samwell',  'Gilly', 'FRIEND'),
('Gilly',  'Samwell', 'FRIEND'),
('Cersei',  'Jaime', 'BROTHER'),
('Jaime',  'Cersei', 'SISTER'),
('Sansa',  'Joffrey', 'HUSBAND'),
('Joffrey',  'Sansa', 'WIFE'),
('Hodor',  'Jojen', 'FRIEND'),
('Jojen',  'Hodor', 'FRIEND'),
('Bran',  'Rickon', 'BROTHER'),
('Rickon',  'Bran', 'BROTHER'),
('Samwell',  'Craster', 'FRIEND'),
('Craster',  'Samwell', 'FRIEND'),
('Jojen',  'Meera', 'SISTER'),
('Meera',  'Jojen', 'BROTHER'),
('Stannis',  'Davos', 'FRIEND'),
('Davos',  'Stannis', 'FRIEND'),
('Robb',  'Edmure', 'UNCLE'),
('Edmure',  'Robb', 'NEPHEW'),
('Tyrion',  'Bronn', 'FRIEND'),
('Bronn',  'Tyrion', 'FRIEND'),
('Jon',  'Qhorin', 'FRIEND'),
('Qhorin',  'Jon', 'FRIEND'),
('Jaime',  'Tyrion', 'BROTHER'),
('Tyrion',  'Jaime', 'BROTHER'),
('Aemon',  'Samwell', 'FRIEND'),
('Samwell',  'Aemon', 'FRIEND'),
('Melisandre',  'Davos', 'FRIEND'),
('Davos',  'Melisandre', 'FRIEND'),
('Jon',  'Aemon', 'FRIEND'),
('Aemon',  'Jon', 'FRIEND'),
('Lysa',  'Petyr', 'HUSBAND'),
('Petyr',  'Lysa', 'WIFE'),
('Tyrion',  'Podrick', 'FRIEND'),
('Podrick',  'Tyrion', 'FRIEND'),
('Sansa',  'Petyr', 'FRIEND'),
('Petyr',  'Sansa', 'FRIEND'),
('Sansa',  'Lysa', 'SISTER'),
('Lysa',  'Sansa', 'SISTER'),
('Joffrey',  'Margaery', 'WIFE'),
('Margaery',  'Joffrey', 'HUSBAND'),
('Robb',  'Walder', 'FRIEND'),
('Walder',  'Robb', 'FRIEND'),
('Daenerys',  'Missandei', 'FRIEND'),
('Missandei',  'Daenerys', 'FRIEND'),
('Daenerys',  'Belwas', 'FRIEND'),
('Belwas',  'Daenerys', 'FRIEND'),
('Tyrion',  'Oberyn', 'FRIEND'),
('Oberyn',  'Tyrion', 'FRIEND'),
('Jon',  'Grenn', 'FRIEND'),
('Grenn',  'Jon', 'FRIEND'),
('Gregor',  'Oberyn', 'FRIEND'),
('Oberyn',  'Gregor', 'FRIEND'),
('Robb',  'Bran', 'BROTHER'),
('Bran',  'Robb', 'BROTHER'),
('Cersei',  'Joffrey', 'SON'),
('Joffrey',  'Cersei', 'MOTHER'),
('Arya',  'Beric', 'FRIEND'),
('Beric',  'Arya', 'FRIEND'),
('Tyrion',  'Gregor', 'FRIEND'),
('Gregor',  'Tyrion', 'FRIEND'),
('Sansa',  'Arya', 'SISTER'),
('Arya',  'Sansa', 'SISTER'),
('Robb',  'Arya', 'SISTER'),
('Arya',  'Robb', 'BROTHER'),
('Bran',  'Arya', 'SISTER'),
('Arya',  'Bran', 'BROTHER'),
('Rickon',  'Arya', 'SISTER'),
('Arya',  'Rickon', 'BROTHER'),
('Tyrion',  'Shae', 'FRIEND'),
('Shae',  'Tyrion', 'FRIEND'),
('Beric',  'Thoros', 'FRIEND'),
('Thoros',  'Beric', 'FRIEND'),
('Stannis',  'Melisandre', 'FRIEND'),
('Melisandre',  'Stannis', 'FRIEND'),
('Jon',  'Rattleshirt', 'FRIEND'),
('Rattleshirt',  'Jon', 'FRIEND'),
('Daenerys',  'Barristan', 'FRIEND'),
('Barristan',  'Daenerys', 'FRIEND')
;

DROP INDEX IF EXISTS public."relationships_BY_from_to";

CREATE INDEX "relationships_BY_from_to"
  ON public.relationships
  USING btree
  ("from" COLLATE pg_catalog."default", "to" COLLATE pg_catalog."default");

DROP INDEX IF EXISTS public."relationships_BY_from_type_to";

CREATE INDEX "relationships_BY_from_reltype_to"
  ON public.relationships
  USING btree
  ("from" COLLATE pg_catalog."default", "rel_type" COLLATE pg_catalog."default", "to" COLLATE pg_catalog."default");

DROP INDEX IF EXISTS public."relationships_BY_to_from";

CREATE INDEX "relationships_BY_to_from"
  ON public.relationships
  USING btree
  ("to" COLLATE pg_catalog."default", "from" COLLATE pg_catalog."default");
