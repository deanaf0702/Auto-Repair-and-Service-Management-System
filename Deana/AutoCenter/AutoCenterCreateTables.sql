---CarModels
drop table CarModels;
create table CarModels(
    model char(15),
    CONSTRAINT pk_model PRIMARY KEY (model)
);
insert into CarModels values('Honda');
insert into CarModels values('Nissan');
insert into CarModels values('Toyota');
select * from CarModels;

---RepairServices
drop table RepairServices;
create table RepairServices(
    serviceId number(3),
    category char(50),
    name char(50),
    hours number(3),
    constraint pk_RepairServices_key primary key(serviceId)
);

insert into RepairServices values(101, 'Engin Services', 'Belt Replacement', 1);
insert into RepairServices values(102, 'Engin Services', 'Engine Repair', 5);
insert into RepairServices values(103, 'Exhaust Services', 'Exhaust Repair', 4);
insert into RepairServices values(104, 'Exhaust Services', 'Muffler Repair', 2);
insert into RepairServices values(105, 'Electrical Services','Alternator Repair', 4);
insert into RepairServices values(106, 'Electrical Services', 'Power Lock Repair', 5);
insert into RepairServices values(107, 'Transmission Services', 'Axle Repair', 7);
insert into RepairServices values(108, 'Transmission Services', 'Brake Repair', 3);
insert into RepairServices values(109, 'Tire Services', 'Tire Balancing', 2);
insert into RepairServices values(110, 'Tire Services', 'Wheel Alignment', 1);
insert into RepairServices values(111, 'Heating and A/C Services', 'Compressor Repair', 3);
insert into RepairServices values(112, 'Heating and A/C Services', 'Evaporator Repair', 4);
select * from RepairServices;

---MaintenanceServices
drop table MaintenanceServices;
create table MaintenanceServices(
    serviceId number(3),
    scheduleType char(1),
    hours number(3), 
    primary key (serviceId)
);
insert into MaintenanceServices values(113, 'A', 3 );
insert into MaintenanceServices values(114, 'B', 6);
insert into MaintenanceServices values(115, 'C', 9);
select * from MaintenanceServices;

create table MaintHasServices(
    serviceId number(3),
    name char(50),
    serviceType char(2),
    primary key (serviceId, name),
    constraint fk_MaintHasServices_MaintenanceServices foreign key(serviceId) references MaintenanceServices(serviceid) on delete cascade
);
insert into MaintHasServices values(113, 'Oil Changes', 'm');
insert into MaintHasServices values(113, 'Filter Replacements', 'm');
insert into MaintHasServices values(114, 'Oil Changes', 'm');
insert into MaintHasServices values(114, 'Filter Replacements', 'm');
insert into MaintHasServices values(114, 'Brake Repair', 'mr');
insert into MaintHasServices values(114, 'Check Engine Light Diagnostics', 'm');
insert into MaintHasServices values(115, 'Oil Changes', 'm');
insert into MaintHasServices values(115, 'Filter Replacements', 'm');
insert into MaintHasServices values(115, 'Brake Repair', 'mr');
insert into MaintHasServices values(115, 'Check Engine Light Diagnostics', 'm');
insert into MaintHasServices values(115, 'Suspension Repair', 'm');
insert into MaintHasServices values(115, 'Evaporator Repair', 'mr');
select * from MaintHasServices;

--Managers
create table Managers(
managerId number(9) primary key,
roleType char(20),
username char(20),
password char(20),
firstName char(50),
lastName char(50),
email char(50),
phone char(15),
startDate date,
endDate date,
address varchar2(100),
salary number(10, 2)
);

insert into Managers values
(123456789, 'manager', 'jdoe', 'doe', 'John', 'Doe', 'jdoe@gmail.com',
'8636368778', '01-MAY-81', null, '1378 University Woods, Raleigh, NC 27612', 500000.00);
insert into Managers values
(987654321, 'manager', 'rbrooks', 'brooks', 'Rachel', 'Brooks', 'rbrooks@ymail.com',
'8972468552', '01-MAY-81', null, '2201 Gorman Parkwood, Raleigh, NC 27618', 500000.00);
insert into Managers values
(987612345, 'manager', 'csmith', 'Smith', 'Caleb', 'Smith', 'csmith@yahoo.com',
'8547963210', '01-MAY-81', null, '1538 Red Bud Lane, Morrisville, NC 27560', 500000.00);
select m.managerId, s.centerId from Managers m, ServiceCenters s where m.managerId = s.managerId;
---Service Center
drop table ServiceCenters;
create table ServiceCenters(
centerId integer primary key, 
minWage number(10, 2), 
maxWage number(10, 2), 
address varchar2(200),
phone char(15), 
satOpen char(1), 
managerId integer,
constraint fk_ServiceCenters_Managers foreign key(managerId) references Managers(managerId)
);
insert into ServiceCenters values(30001, 30.00, 40.00, '3921 Western Blvd, Raleigh, NC 27606', '3392601234', 'Y', 123456789);
insert into ServiceCenters values(30002, 25.00, 35.00, '4500 Preslyn Dr Suite 103, Raleigh, NC 27616', '8576890280', 'Y', 987654321);
insert into ServiceCenters values(30003, 20.00, 25.00, '9515 Chapel Hill Rd, Morrisville, NC 27560', '987612345', 'N', 987612345);
select * from ServiceCenters;

--Prices
drop table Prices;
create table Prices(
    centerId integer,
    model char(15),
    priceTier number(2),
    dollar number(7, 2),
    constraint pk_Prices_centerId_model_priceTier primary key(centerId, model, priceTier),
    constraint fk_Prices_ServiceCenters foreign key(centerId) references ServiceCenters(centerId),
    constraint fk_Prices_CarModels foreign key(model) references CarModels(model)
);
select * from CarModels;
select * from ServiceCenters;
insert into Prices values (30001,'Honda',1,50);
insert into Prices values (30001,'Nissan',1,70);
insert into Prices values (30001,'Toyota',1,60);
insert into Prices values (30001,'Honda',2,140);
insert into Prices values (30001,'Nissan',2,175);
insert into Prices values (30001,'Toyota',2,160);
insert into Prices values (30001,'Honda',3,400);
insert into Prices values (30001,'Nissan',3,500);
insert into Prices values (30001,'Toyota',3,450);
insert into Prices values (30001,'Honda',4,590);
insert into Prices values (30001,'Nissan',4,700);
insert into Prices values (30001,'Toyota',4,680);
insert into Prices values (30001,'Honda',5,1000);
insert into Prices values (30001,'Nissan',5,1200);
insert into Prices values (30001,'Toyota',5,1100);
insert into Prices values (30001,'Honda',6,120);
insert into Prices values (30001,'Nissan',6,190);
insert into Prices values (30001,'Toyota',6,200);
insert into Prices values (30001,'Honda',7,195);
insert into Prices values (30001,'Nissan',7,210);
insert into Prices values (30001,'Toyota',7,215);
insert into Prices values (30001,'Honda',8,300);
insert into Prices values (30001,'Nissan',8,310);
insert into Prices values (30001,'Toyota',8,305);
insert into Prices values (30002,'Honda',1,70);
insert into Prices values (30002,'Nissan',1,90);
insert into Prices values (30002,'Toyota',1,80);
insert into Prices values (30002,'Honda',2,160);
insert into Prices values (30002,'Nissan',2,195);
insert into Prices values (30002,'Toyota',2,180);
insert into Prices values (30002,'Honda',3,420);
insert into Prices values (30002,'Nissan',3,520);
insert into Prices values (30002,'Toyota',3,470);
insert into Prices values (30002,'Honda',4,610);
insert into Prices values (30002,'Nissan',4,720);
insert into Prices values (30002,'Toyota',4,700);
insert into Prices values (30002,'Honda',5,1020);
insert into Prices values (30002,'Nissan',5,1220);
insert into Prices values (30002,'Toyota',5,1120);
insert into Prices values (30002,'Honda',6,125);
insert into Prices values (30002,'Nissan',6,195);
insert into Prices values (30002,'Toyota',6,205);
insert into Prices values (30002,'Honda',7,200);
insert into Prices values (30002,'Nissan',7,215);
insert into Prices values (30002,'Toyota',7,220);
insert into Prices values (30002,'Honda',8,305);
insert into Prices values (30002,'Nissan',8,315);
insert into Prices values (30002,'Toyota',8,310);
insert into Prices values (30003,'Honda',1,85);
insert into Prices values (30003,'Nissan',1,105);
insert into Prices values (30003,'Toyota',1,95);
insert into Prices values (30003,'Honda',2,175);
insert into Prices values (30003,'Nissan',2,210);
insert into Prices values (30003,'Toyota',2,195);
insert into Prices values (30003,'Honda',3,435);
insert into Prices values (30003,'Nissan',3,535);
insert into Prices values (30003,'Toyota',3,485);
insert into Prices values (30003,'Honda',4,625);
insert into Prices values (30003,'Nissan',4,735);
insert into Prices values (30003,'Toyota',4,715);
insert into Prices values (30003,'Honda',5,1035);
insert into Prices values (30003,'Nissan',5,1235);
insert into Prices values (30003,'Toyota',5,1135);
insert into Prices values (30003,'Honda',6,140);
insert into Prices values (30003,'Nissan',6,180);
insert into Prices values (30003,'Toyota',6,195);
insert into Prices values (30003,'Honda',7,210);
insert into Prices values (30003,'Nissan',7,220);
insert into Prices values (30003,'Toyota',7,215);
insert into Prices values (30003,'Honda',8,310);
insert into Prices values (30003,'Nissan',8,305);
insert into Prices values (30003,'Toyota',8,310);
select * from Prices;
---Employees
drop table Employees;
create table Employees(
employeeId number(9) primary key,
centerId integer,
roleType char(20) not null,
username char(20),
password char(20),
firstName char(50),
lastName char(50),
email char(50),
phone char(15),
startDate date not null,
endDate date,
address varchar2(100),
constraint fk_Employees_ServiceCenters foreign key (centerId) references ServiceCenters(centerId)
);

create table Receptionists(
employeeId number(9) primary key, 
salary number(10, 2), 
constraint fk_Receptionistss_Employees foreign key (employeeId) references Employees(employeeId) on delete cascade
);
create table Mechanics(
employeeId number(9) primary key,
wage number(10, 2),
constraint fk_Mechanics_Employees foreign key (employeeId) references Employees(employeeId) on delete cascade
);
insert into Employees values(234567898, 30001,'receptionist', 'demoReceptionist1', '1234567', 'Demo', 'Receptionist1', 'dlfranks@ncsu.edu', '123-123-1234', '01-MAY-81', null, '1400 Gorman St, Raleigh, NC 27606-2972');
insert into Receptionists values(234567898, 200000.00);

select * from Employees;
insert into Employees values(132457689, 30001,'mechanic', 'jwilliams', 'williams', 'James', 'Williams', 'jwilliams@gmail.com', '4576312882', '02-JUL-2021', null, '1400 Gorman St, Raleigh, NC 27606-2972');
insert into Mechanics values(132457689, 35.00);
insert into Employees values(314275869, 30001,'mechanic', 'djohnson', 'johnson', 'David', 'Johnson', 'djohnson@ymail.com', '9892321100', '25-AUG-2021', null, '686 Stratford Court, Raleigh, NC 27606');
insert into Mechanics values(314275869, 35.00);
insert into Employees values(241368579, 30001,'mechanic', 'mgarcia', 'garcia', 'Maria', 'Garcia', 'mgarcia@yahoo.com', '4573459090', '26-AUG-2021', null, '1521 Graduate Lane, Raleigh, NC 27606');
insert into Mechanics values(241368579, 35.00);
select * from Receptionists;
select * from Mechanics;
select * from Employees;
------------------------

---Vacations
create table Vacations(
vacationId integer primary key,
employeeId number(9) not null,
fromDate date,
toDate date,
constraint fk_Vacations_Mechanics foreign key (employeeId) references Mechanics(employeeId) On delete cascade
);

select * from ServiceCenters;


select * from CustomerVehicles;
---SchedulePriced
create table MaintServicePriced(
    serviceId number(3),
    centerId integer,
    model char(15),
    priceTier number(2),
    price number(7,2),
    constraint pk_MaintServicePriced_key primary key (serviceId, centerId, model),
    constraint fk_MaintServicePriced_MaintenanceServices foreign key (serviceId) references MaintenanceServices(serviceId),
    constraint fk_MaintServicePriced_CarModels foreign key (model) references CarModels(model),
    constraint fk_MaintServicePriced_ServiceCenters foreign key(centerId) references ServiceCenters(centerId)
);
select * from MaintenanceServices;
insert into MaintServicePriced values(113, 30001, 'Honda', 6, 120);
insert into MaintServicePriced values(113, 30001, 'Nissan', 6, 190);
insert into MaintServicePriced values(113, 30001, 'Toyota', 6, 200);
insert into MaintServicePriced values(114, 30001, 'Honda', 7, 195);
insert into MaintServicePriced values(114, 30001, 'Nissan', 7, 210);
insert into MaintServicePriced values(114, 30001, 'Toyota', 7, 215);
insert into MaintServicePriced values(115, 30001, 'Honda', 8, 300);
insert into MaintServicePriced values(115, 30001, 'Nissan', 8, 310);
insert into MaintServicePriced values(115, 30001, 'Toyota', 8, 305);
select * from MaintServicePriced;


--RepairServicePriced
drop table RepairServicePriced;
create table RepairServicePriced(
    centerId int,
    serviceId number(3),
    model char(15),
    priceTier number(2),
    price number(7, 2),
    constraint pk_RepairServicePriced_key primary key(centerId, serviceId, model),
    constraint fk_RepairServicePriced_RepairServices foreign key(serviceId) references RepairServices(serviceId),
    constraint fk_RepairServicePriced_ServiceCenters foreign key(centerId) references ServiceCenters(centerId),
    constraint fk_RepairServicePriced_CarModels foreign key(model) references CarModels(model)
);

insert into RepairServicePriced values(30001, 101, 'Honda', 2, 140);
insert into RepairServicePriced values(30001, 101, 'Nissan', 2, 175);
insert into RepairServicePriced values(30001, 101, 'Toyota', 2, 160);
insert into RepairServicePriced values(30001, 102, 'Honda', 3, 400);
insert into RepairServicePriced values(30001, 102, 'Nissan', 3, 500);
insert into RepairServicePriced values(30001, 102, 'Toyota', 3, 450);
insert into RepairServicePriced values(30001, 103, 'Honda', 4, 590);
insert into RepairServicePriced values(30001, 103, 'Nissan', 4, 700);
insert into RepairServicePriced values(30001, 103, 'Toyota', 4, 680);
insert into RepairServicePriced values(30001, 104, 'Honda', 2, 140);
insert into RepairServicePriced values(30001, 104, 'Nissan', 2, 175);
insert into RepairServicePriced values(30001, 104, 'Toyota', 2, 160);
insert into RepairServicePriced values(30001, 105, 'Honda', 3, 400);
insert into RepairServicePriced values(30001, 105, 'Nissan', 3, 500);
insert into RepairServicePriced values(30001, 105, 'Toyota', 3, 450);
insert into RepairServicePriced values(30001, 106, 'Honda', 3, 400);
insert into RepairServicePriced values(30001, 106, 'Nissan', 3, 500);
insert into RepairServicePriced values(30001, 106, 'Toyota', 3, 450);
insert into RepairServicePriced values(30001, 107, 'Honda', 5, 1000);
insert into RepairServicePriced values(30001, 107, 'Nissan', 5, 1200);
insert into RepairServicePriced values(30001, 107, 'Toyota', 5, 1100);
insert into RepairServicePriced values(30001, 108, 'Honda', 3, 400);
insert into RepairServicePriced values(30001, 108, 'Nissan', 3, 500);
insert into RepairServicePriced values(30001, 108, 'Toyota', 3, 450);
insert into RepairServicePriced values(30001, 109, 'Honda', 1, 50);
insert into RepairServicePriced values(30001, 109, 'Nissan', 1, 70);
insert into RepairServicePriced values(30001, 109, 'Toyota', 1, 60);
insert into RepairServicePriced values(30001, 110, 'Honda', 2, 140);
insert into RepairServicePriced values(30001, 110, 'Nissan', 2, 175);
insert into RepairServicePriced values(30001, 110, 'Toyota', 2, 160);
insert into RepairServicePriced values(30001, 111, 'Honda', 4, 590);
insert into RepairServicePriced values(30001, 111, 'Nissan', 4, 700);
insert into RepairServicePriced values(30001, 111, 'Toyota', 4, 680);
insert into RepairServicePriced values(30001, 112, 'Honda', 3, 400);
insert into RepairServicePriced values(30001, 112, 'Nissan', 3, 500);
insert into RepairServicePriced values(30001, 112, 'Toyota', 3, 450);

select * from RepairServicePriced;
---Customers
drop table Customers;
create table Customers(
customerId integer primary key, 
centerId integer,
firstName char(20),
lastName char(20),
address varchar(100),
status char(1),
active char(1),
constraint fk_Customer_ServiceCenter foreign key(centerId) references ServiceCenters(centerId)
);
insert into Customers values(10001, 30001, 'Peter', 'Parker', '1234 main ST Raleigh NC 27666', '1', '1');
insert into Customers values(10002, 30001, 'Diana', 'Prince', '1234 main ST Raleigh NC 27666', '1', '1');
select * from Customers;

---CustomerVehicles
drop table CustomerVehicles;
create table CustomerVehicles(
vin char(8) primary key,
customerId integer,
model char(15),
mileage int,
year number(4),
lastMClass char(1),
constraint fk_CustomerVehicles_Customers foreign key (customerId) references Customers(customerId),
constraint fk_CustomerVehicles_CarModels foreign key (model) references CarModels(model)
);
insert into CustomerVehicles values('4Y1BL658', 10001, 'Toyota', 53000, 2007, 'B');
insert into CustomerVehicles values('7A1ST264', 10002, 'Honda', 117000, 1999, 'A');





---ServiceEvent to_date('2022/09/23:12:00:00PM', 'yyyy/mm/dd:hh:mi:ssam'))
drop table ServiceEvents;
create table ServiceEvents(
eventId integer primary key,
vin char(8),
mechanicId number(9),
scheduledService char(50),
week number(1),
day number(1),
startDate date,
startTimeSlot number(2),
endTimeSlot number(2),
totalPrice number(10, 2),
totalPaid number(10, 2),
completed char (1),
invoiceStatus char(1),
constraint fk_ServiceEvents_CustomerVehicles foreign key (vin) references CustomerVehicles(vin),
constraint fk_ServiceEvents_Employees foreign key (mechanicId) references Employees(employeeId)
);
alter table ServiceEvents add week number(1);
alter table ServiceEvents add day number(1);
alter table ServiceEvents add startTimeSlot number(2);
alter table ServiceEvents add endTimeSlot number(2);
select * from ServiceEvents;
---EventOnServices
drop table EventOnServices;
create table EventOnServices(
id integer primary key,
eventId integer,
serviceId integer,
serviceType char(20),
constraint fk_EventOnServices_ServiceEvents foreign key(eventId) references ServiceEvents(eventId),
constraint fk_EventOnServices_Services foreign key (serviceId) references Services(serviceId)
);
--insert into EventOnServices(1, );
--Invoices
create table Invoices(
    invoiceId integer primary key,
    eventId integer,
    serviceDate date not null,
    totalPrice number(7, 2),
    totalPaid number(7, 2),
    status char(1),
    constraint fk_Invoices_ServiceEvents foreign key (eventId) references ServiceEvents(eventId)
);

