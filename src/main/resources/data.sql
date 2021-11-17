INSERT INTO AUTHORITY (id,AUTHORITY_NAME) values (1,'ROLE_ADMIN');
INSERT INTO AUTHORITY (id,AUTHORITY_NAME) values (2,'ROLE_USER');
INSERT INTO USER_ENTITY (user_email, password,user_name,nickname) VALUES ('test123@gmail.com', '$2a$10$N97lS2a6Bje23Ljjs6x4i.caq6LGhFatoq.UHZx349ygZ5w8mdq2m','관리자','좋은사람');
INSERT INTO USER_ENTITY (user_email, password,user_name,nickname) VALUES ('test456@gmail.com', '$2a$10$N97lS2a6Bje23Ljjs6x4i.caq6LGhFatoq.UHZx349ygZ5w8mdq2m','사용자hj','사람');
insert into inquiry (user_id,inquiry_type,title,content,is_complete) values (2,'일반문의','inquiry Test1','inquiry Test1 Content',false)
-- insert into inquiry (user_id,inquiry_type,title,content,is_complete) values (2,'허위매물 신고','inquiry Test2','inquiry Test2 Content',false)
-- insert into inquiry (user_id,inquiry_type,title,content,is_complete) values (1,'허위매물 신고','inquiry Test3','inquiry Test3 Content',false)