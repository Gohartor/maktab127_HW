drop table if exists departments, employees, projects;
drop type if exists priority_type;

create table departments
(
    department_name varchar(50),
    department_id   int unique not null
);


create type priority_type as enum ('low', 'medium', 'high');

create table projects
(
    project_id int primary key,
    name varchar(50),
    start_date int,
    end_date int,
    priority priority_type
);


create table employees
(
    employee_id   int primary key,
    first_name    varchar(50),
    last_name     varchar(50),
    department_id int references departments (department_id),
    project_id int references projects(project_id)
);



insert into departments
values ('hr', 3),
       ('design', 2),
       ('tech', 1);

insert into projects
values (11,'project 1', 1403, 1404, 'low'),
       (12,'project 2', 1402, 1403, 'medium'),
       (13,'project 3', 1385, 1403, 'low'),
       (14,'project 4', 1380, 1403, 'high');


insert into employees
values (1,'ali', 'alavi', 1,11),
       (2,'hasan', 'hasani', 2,11),
       (3,'naqi', 'naqavi', 3,11),
       (4,'fateme', 'fatemi', 2, 12),
       (5,'narges', 'mohammadi', 2, 13),
       (6,'qoli', 'qolami', 2, 13),
       (7,'nima', 'bonyadi', 1, 14),
       (8,'melody', 'shahrestanaki', 1, 13),
       (9,'helia', 'helali', 1, 12),
       (10,'ahmad', 'ahmadi', 1,null);



--Q1
select p.project_id, p.name, p.priority
from projects p
join employees e on p.project_id = e.project_id
where e.department_id = 3
group by p.project_id;


--Q2
select d.department_name, e.first_name, e.last_name, d.department_name, p.name as project_name
from employees e
join departments d on e.department_id = d.department_id
join projects p on e.project_id = p.project_id
where d.department_id = 3
order by d.department_name;



--Q3
select d.department_name,
count(case when e.project_id is not null then 1 end) as busy,
count(case when e.project_id is null then 1 end) as free
from departments d
left join employees e on d.department_id = e.department_id
group by d.department_name;



