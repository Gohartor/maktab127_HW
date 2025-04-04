drop table if exists departments, employees, projects, tasks, archived;
drop type if exists priority_type;


create table departments
(
    department_name varchar(50),
    department_id   int unique not null
);

CREATE TYPE priority_type AS ENUM ('low', 'medium', 'high');

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

create table archived
(
    project_id    int,
    name          varchar(50),
    start_date    int,
    end_date      int,
    priority      priority_type,
    archived_date date default current_timestamp
);

insert into departments
values ('hr', 3),
       ('design', 2),
       ('tech', 1);

insert into projects
values (11, 'project 1', 1403, 1405, 'low'),
       (12, 'project 2', 1402, 1403, 'medium'),
       (13, 'project 3', 1385, 1403, 'low'),
       (14, 'project 4', 1380, 1401, 'high');


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
values (11, 1, '1404-01-01', '1404-01-10', '1404-01-15', 'in_progress'),
       (11, 1, '1404-01-11', '1404-01-20', '1404-01-25', 'todo'),
       (11, 2, '1404-01-21', '1404-01-30', '1404-01-31', 'done'),
       (12, 3, '1404-01-05', '1404-01-15', '1404-01-20', 'done'),
       (12, 3, '1404-01-16', '1404-01-31', '1404-02-05', 'in_progress'),
       (13, 4, '1404-02-01', '1404-02-10', '1404-02-15', 'done'),
       (14, 4, '1404-02-11', '1404-02-25', '1404-03-05', 'todo');



--Query
insert into archived (project_id, name, start_date, end_date, priority)
select project_id, name, start_date, end_date, priority
from projects
where end_date < 1403;

update employees
set project_id = null
where project_id in (select project_id from projects where end_date < 1403);

update tasks
set project_id = null
where project_id in (select project_id from projects where end_date < 1403);

delete from projects
where end_date < 1403;

