drop table if exists books;
-- Create the books table, setting the ISBN_13 as the primary key.

CREATE TABLE books (
  isbn_13 varchar (13) PRIMARY KEY,
  title varchar (100),
  author varchar (80),
  publish_date date,
  price decimal (6,2),
  content bytea
);

-- Populating a few entries into the books table for testing and manipulation.
INSERT INTO books VALUES (
  '1111111111111',          	-- id
  'The Adventures of Steve',    -- title
  'Russell Barron', 			-- author
  current_date,    				-- publishDate
  123.50,   					-- price
  null							-- blob
);

INSERT INTO books VALUES (
  '1111111111112',          	-- id
  'Dancing With Stars',    		-- title
  'Marie', 						-- author
  current_date,    				-- publishDate
  15.50,   						-- price
  null							-- blob
);

INSERT INTO books VALUES (
  '1111111111113',          	-- id
  'Art of Fisticuffs',    		-- title
  'Big Easy', 					-- author
  current_date,    				-- publishDate
  15.50,   						-- price
  null							-- blob
);

INSERT INTO books VALUES (
  '1111111111114',          	-- id
  'How to Kill a Witch',    	-- title
  'George', 					-- author
  current_date,    				-- publishDate
  15.50,   						-- price
  null							-- blob
);

INSERT INTO books VALUES (
  '1111111111115',          	-- id
  'Space Force',    		    -- title
  'Homer', 						-- author
  current_date,    				-- publishDate
  15.50,   						-- price
  null							-- blob
);

/*
 Create the book_tags table. We need a foreign key to reference books table. Additionally, this table has a composite key with its two columns to maintain normalization. It will also disallow multiples of the same tag to be added to a specific book.
*/
CREATE TABLE BOOK_TAGS (
	tag_name varchar,
	isbn_13 varchar(13) NOT NULL,
	PRIMARY KEY (isbn_13, tag_name),
	FOREIGN KEY(isbn_13) REFERENCES BOOKS (isbn_13)
);
	
-- Populating the book_tags again with a few entries.
INSERT INTO book_tags VALUES (
	'dragons',
	'1111111111111'
);

INSERT INTO book_tags VALUES (
	'dancing',
	'1111111111112'
);

INSERT INTO book_tags VALUES (
	'boxing',
	'1111111111113'
);

INSERT INTO book_tags VALUES (
	'outer space',
	'1111111111115'
);
