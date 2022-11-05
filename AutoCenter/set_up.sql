--Comment out up to create tables if running for the first time

--Drop foreign keys if they exist
alter table RepairServices drop constraint repairId_fk;
alter table RepairServices drop constraint repairCategory_fk;

alter table MaintenanceServices drop constraint maintenanceId_fk;

alter table MaintHasServices drop constraint fk_Schedule_MaintenanceServices;

alter table Users drop constraint fk_userRole;
alter table Users drop constraint fk_user_ServiceCenter;

alter table Managers drop constraint fk_Manager_Users;

alter table Receptionists drop constraint fk_Receptionist_Users;

alter table Mechanics drop constraint fk_Mechanic_Users;

alter table Customers drop constraint fk_Customer_Users;

alter table ServiceCenters drop constraint fk_ServiceCenterManager;

alter table Prices drop constraint fk_Prices_ServiceCenters;
alter table Prices drop constraint fk_Prices_Services;
alter table Prices drop constraint fk_Prices_Model;

alter table Schedule drop constraint fk_Schedule_Mechanic;
alter table Schedule drop constraint fk_Schedule_TimeSlot;

alter table CustomerVehicles drop constraint fk_CustomerVehicles_Customers;
alter table CustomerVehicles drop constraint fk_CustomerVehicles_CarModels;

alter table ServiceEvents drop constraint fk_ServiceEvent_vin;
alter table ServiceEvents drop constraint fk_ServiceEvent_centerId;
alter table ServiceEvents drop constraint fk_ServiceEvent_Mechanic;
alter table ServiceEvents drop constraint fk_ServiceEvent_start;
alter table ServiceEvents drop constraint fk_ServiceEvent_end;

alter table EventOnServices drop constraint fk_EventOnServices_eventId;
alter table EventOnServices drop constraint fk_EventOnServices_serviceId;

--Drop triggers if they exist

--Drop any check constraints

--Drop all tables
drop table CarModels;
drop table Services;
drop table RepairServices;
drop table RepairServiceCategory;
drop table MaintenanceServices;
drop table MaintHasServices;
drop table Roles;
drop table Users;
drop table Managers;
drop table Receptionists;
drop table Mechanics;
drop table Customers;
drop table ServiceCenters;
drop table Prices;
drop table TimeSlots;
drop table Schedule;
drop table CustomerVehicles;
drop table ServiceEvents;
drop table EventOnServices;

--Create new tables

---CarModels
create table CarModels(
    model char(15),
    CONSTRAINT pk_model PRIMARY KEY (model)
);

--Services
create table Services(
	serviceId number(3),
	name char(50),
	primary key (serviceId),
);

---RepairServices 
create table RepairServices(
    serviceId number(3),
    category char(50),
    constraint pk_RepairServices_key primary key(serviceId),
    constraint repairId_fk foreign key (serviceId) references Services (serviceId) on delete cascade,
    constraint repairCategory_fk foreign key (category) references RepairServiceCategory (category)
);

--RepairServiceCategory
create table RepairServiceCategory(
	category char(50) primary key
);

--MaintenanceServices
create table MaintenanceServices(
    serviceId number(3),
    primary key (serviceId),
    constraint maintenanceId_fk foreign key (serviceId) references Services (serviceId) on delete cascade
);

--ScheduledServices
create table MaintHasServices(
    serviceId number(3),
    serviceName char(25),
    constraint fk_Schedule_MaintenanceServices foreign key (serviceId) references MaintenanceServices (serviceid) on delete cascade,
);

--Roles
create table Roles(
	roleName char(20) primary key
)

--Users
create table Users(
	userId number(9),
	firstName char(50),
	lastName char(50),
	phone char(25),
	email char(50),
	address varchar2(200),
	serviceCenterId integer,
	username varchar2(50),
	password varchar2(50),
	role char(20),
	primary key (userId, serviceCenterId),
	unique (username),
	constraint fk_userRole foreign key (role) references Roles (roleName),
	constraint fk_user_ServiceCenter foreign key (serviceCenterId) references SerivceCenters(centerId) on delete cascade
);

--Managers
create table Managers(
	userId number(9),
	serviceCenterId integer,
	salary number(10, 2),
	primary key (userId, serviceCenterId),
	constraint fk_Manager_Users foreign key (userId, serviceCenterId) references Users (userId, serviceCenterId) on delete cascade
)

--Receptionists
create table Receptionists(
	userId number(9),
	serviceCenterId integer,
	salary number(10, 2),
	primary key (userId, serviceCenterId),
	constraint fk_Receptionist_Users foreign key (userId, serviceCenterId) references Users (userId, serviceCenterId) on delete cascade
)

--Mechanics
create table Mechanics(
	userId number(9),
	serviceCenterId integer,
	wage number(10, 2),
	primary key (userId, serviceCenterId),
	constraint fk_Mechanic_Users foreign key (userId, serviceCenterId) references Users (userId, serviceCenterId) on delete cascade
)

--Customers
create table Customers(
	userId number(9),
	serviceCenterId integer,
	inGoodStanding number(1),
	isActive number(1),
	primary key (userId, serviceCenterId),
	constraint fk_Customer_Users foreign key (userId, serviceCenterId) references Users (userId, serviceCenterId) on delete cascade
)

--ServiceCenters
create table ServiceCenters(
centerId integer primary key,
minWage number(10, 2),
maxWage number(10, 2),
address varchar2(200),
phone char(25),
satOpen number(1),
isActive number(1)
);

--Prices
create table Prices(
    centerId integer,
    serviceId number(3),
    model char(15),
    price number(7, 2),
    hours integer,
    constraint pk_Prices_centerId_model_serviceId primary key(centerId, serviceId, model),
    constraint fk_Prices_ServiceCenters foreign key(centerId) references ServiceCenters(centerId) on delete cascade,
    constraint fk_Prices_Services foreign key (serviceId) references Services (serviceId) on delete cascade,
    constraint fk_Prices_Model foreign key (model) references CarModels (model)
);

--TimeSlots
create table TimeSlots(
	slotNumber number(2),
	startHour number(2),
	endHour number(2),
	primary key (slotNumber)
);

--Schedule
create table Schedule(
	mechanicId number(9),
	centerId integer,
	week number(1),
	day number(2),
	timeSlot number(2),
	activity char(10),
	primary key (mechanicId, week, day, timeSlot),
	foreign key fk_Schedule_Mechanic foreign key (mechanicId, centerId) references Mechanics (userId, centerId),
	foreign key fk_Schedule_TimeSlot foreign key (timeSlot) references TimeSlots (slotNumber)
);

---CustomerVehicles
create table CustomerVehicles(
vin char(8) primary key,
customerId number(9),
centerId integer,
model char(15),
mileage int,
year number(4),
lastMClass char(1),
constraint fk_CustomerVehicles_Customers foreign key (customerId, centerId) references Customers(userId, centerId),
constraint fk_CustomerVehicles_CarModels foreign key (model) references CarModels(model)
);

--ServiceEvent
create table ServiceEvents(
	serviceEventId integer primary key,
	vin char(8),
	centerId integer,
	mechanicId number(9),
	week number(1),
	day number(2),
	startTimeSlot number(2),
	endTimeSlot number(2),
	isPaid number(1),
	constraint fk_ServiceEvent_vin foreign key (vin) references CustomerVehicles (vin) on delete cascade,
	constraint fk_ServiceEvent_centerId foreign key (centerId) references ServiceCenters (centerId),
	constraint fk_ServiceEvent_Mechanic foreign key (mechanicId, centerId) references Mechanics (userId, centerId),
	constraint fk_ServiceEvent_start foreign key (startTimeSlot) references TimeSlots (slotNumber),
	constraint fk_ServiceEvent_end foreign key (endTimeSlot) references TimeSlots (slotNumber)
);

--EventOnServices
create table EventOnServices(
	eventId integer,
	serviceId number(3),
	primary key (integer, serviceId),
	constraint fk_EventOnServices_eventId foreign key (eventId) references ServiceEvents (serviceEventId),
	constraint fk_EventOnServices_serviceId foreign key (serviceId) references Services (serviceId)
);
