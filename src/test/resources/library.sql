
-- US 01
select count(id) from users; --
-- 1855
-- US 01-1
select count(distinct id) from users;
-- 1855


-- RESULT --> MANUALLY IT IS PASSED


-- US 01-2
select * from users;


-- US 02
select count(*) from book_borrow
where is_returned=0;

-- US 03
select name from book_categories;

select name, isbn, year, author from books;

