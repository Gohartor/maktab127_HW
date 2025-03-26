drop table if exists departments, employees, projects, tasks;
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
    name       varchar(50),
    start_date int,
    end_date   int,
    priority   priority_type
);


create table employees
(
    employee_id   int primary key,
    first_name    varchar(50),
    last_name     varchar(50),
    department_id int references departments (department_id),
    project_id    int references projects (project_id)
);


create table tasks
(
    task_id     serial primary key,
    project_id  int references projects (project_id),
    employee_id int references employees (employee_id),
    description varchar(255),
    start_date  date,
    end_date    date,
    due_date    date,
    status      varchar(20) check (status in ('todo', 'in_progress', 'done'))
);



insert into departments
values ('hr', 3),
       ('design', 2),
       ('tech', 1);

insert into projects
values (11, 'project 1', 1403, 1404, 'low'),
       (12, 'project 2', 1402, 1403, 'medium'),
       (13, 'project 3', 1385, 1403, 'low'),
       (14, 'project 4', 1380, 1403, 'high');


insert into employees
values (1, 'ali', 'alavi', 1, 11),
       (2, 'hasan', 'hasani', 2, 11),
       (3, 'naqi', 'naqavi', 3, 11),
       (4, 'fateme', 'fatemi', 2, 12),
       (5, 'narges', 'mohammadi', 2, 13),
       (6, 'qoli', 'qolami', 2, 13),
       (7, 'nima', 'bonyadi', 1, 14),
       (8, 'melody', 'shahrestanaki', 1, 13),
       (9, 'helia', 'helali', 1, 12),
       (10, 'ahmad', 'ahmadi', 1, null);



insert into tasks (project_id, employee_id, start_date, end_date, due_date, status)
values
    (11, 1,  '1404-01-01', '1404-01-10', '1404-01-15', 'in_progress'),
    (11, 1,  '1404-01-11', '1404-01-20', '1404-01-25', 'todo'),
    (11, 2,  '1404-01-21', '1404-01-30', '1404-01-31', 'done'),
    (12, 3,  '1404-01-05', '1404-01-15', '1404-01-20', 'done'),
    (12, 3, '1404-01-16', '1404-01-31', '1404-02-05', 'in_progress'),
    (13, 4,  '1404-02-01', '1404-02-10', '1404-02-15', 'done'),
    (14, 4, '1404-02-11', '1404-02-25', '1404-03-05', 'todo');



--Q1
select p.name as project_name,
    (count(case when t.status = 'done' then 1 end) * 100.0 / count(t.task_id)) as percentage
from projects p
left join tasks t on p.project_id = t.project_id
group by p.name;


--Q2
select e.first_name, e.last_name,
    count( case when t.due_date < current_date and t.status != 'done' then 1 end) as overdue_tasks
from projects p
join tasks t on p.project_id = t.project_id
join employees e on t.employee_id = e.employee_id
group by e.first_name, e.last_name
order by overdue_tasks;

