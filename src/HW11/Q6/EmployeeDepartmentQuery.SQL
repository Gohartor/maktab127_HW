drop table if exists departments, employees;

create table departments
(
    department_name varchar(50),
    department_id   int unique not null
);

create table employees
(
    employee_id   serial primary key,
    first_name    varchar(50),
    last_name     varchar(50),
    department_id int references departments (department_id)
);


insert into departments
values ('hr', 3),
       ('design', 2),
       ('tech', 1);


insert into employees
values (1,'ali', 'alavi', 1),
       (2,'hasan', 'hasani', 2),
       (3,'naqi', 'naqavi', null),
       (4,'fateme', 'fatemi', 1),
       (5,'ahmad', 'ahmadi', 3);




select e.employee_id, e.first_name, e.last_name, coalesce(d.department_name, 'no department') as department_name
from employees e
left join departments d on e.department_id = d.department_id;



/*

select e.employee_id, e.first_name, e.last_name,
case
when d.department_name is null then 'no department'
from employees e
left join departments d on e.department_id = d.department_id;

*/