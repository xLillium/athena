-- Users table
CREATE TABLE public.users (
    id integer NOT NULL,
    first_name character varying(255) NOT NULL,
    last_name character varying(255) NOT NULL
);

ALTER TABLE public.users OWNER TO athena;

CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO athena;

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


-- Books table
CREATE TABLE public.books (
    id integer NOT NULL,
    title character varying(255) NOT NULL,
    author character varying(255) NOT NULL,
    isbn character varying(13) NOT NULL,
);

ALTER TABLE public.books OWNER TO athena;

CREATE SEQUENCE public.books_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE public.books_id_seq OWNER TO athena;

ALTER TABLE ONLY public.books ALTER COLUMN id SET DEFAULT nextval('public.books_id_seq'::regclass);

ALTER TABLE ONLY public.books
    ADD CONSTRAINT books_pkey PRIMARY KEY (id);

-- User_Books association table
CREATE TABLE public.user_books (
    user_id integer NOT NULL,
    book_id integer NOT NULL,
    FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES public.books(id) ON DELETE CASCADE,
    PRIMARY KEY(user_id, book_id)
);

ALTER TABLE public.user_books OWNER TO athena;

-- BorrowRecords table
CREATE TABLE public.borrow_records (
    id integer NOT NULL,
    borrower_id integer NOT NULL,
    lender_id integer NOT NULL,
    book_id integer NOT NULL,
    borrow_date date NOT NULL,
    return_date date,
    due_date date NOT NULL,
    FOREIGN KEY (borrower_id) REFERENCES public.users(id) ON DELETE CASCADE,
    FOREIGN KEY (lender_id) REFERENCES public.users(id) ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES public.books(id) ON DELETE CASCADE,
    PRIMARY KEY(id)
);

ALTER TABLE public.borrow_records OWNER TO athena;

CREATE SEQUENCE public.borrow_records_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE public.borrow_records_id_seq OWNER TO athena;
ALTER TABLE ONLY public.borrow_records ALTER COLUMN id SET DEFAULT nextval('public.borrow_records_id_seq'::regclass);