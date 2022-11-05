insert into CarModels values ('Honda');
insert into CarModels values ('Nissan');
insert into CarModels values ('Toyota');

insert into Services values (101, 'Belt Replacement');
insert into Services values (102, 'Engine Repair');
insert into Services values (103, 'Exhaust Repair');
insert into Services values (104, 'Muffler Repair');
insert into Services values (105, 'Alternator Repair');
insert into Services values (106, 'Power Lock Repair');
insert into Services values (107, 'Axle Repair');
insert into Services values (108, 'Brake Repair');
insert into Services values (109, 'Tire Balancing');
insert into Services values (110, 'Wheel Alignment');
insert into Services values (111, 'Compressor Repair');
insert into Services values (112, 'Evaporator Repair');
insert into Services values (113, 'A');
insert into Services values (114, 'B');
insert into Services values (115, 'C');

insert into RepairServices values (101, 'Engine Services');
insert into RepairServices values (102, 'Engine Services');
insert into RepairServices values (103, 'Exhaust Services');
insert into RepairServices values (104, 'Exhaust Services');
insert into RepairServices values (105, 'Electrical Services');
insert into RepairServices values (106, 'Electrical Services');
insert into RepairServices values (107, 'Transmission Services');
insert into RepairServices values (108, 'Transmission Services');
insert into RepairServices values (109, 'Tire Services');
insert into RepairServices values (110, 'Tire Services');
insert into RepairServices values (111, 'Heating and A/C Services');
insert into RepairServices values (112, 'Heating and A/C Services');

insert into RepairServiceCategory values ('Engine Services');
insert into RepairServiceCategory values ('Exhaust Services');
insert into RepairServiceCategory values ('Electrical Services');
insert into RepairServiceCategory values ('Transmission Services');
insert into RepairServiceCategory values ('Tire Services');
insert into RepairServiceCategory values ('Heating and A/C Services');

insert into MaintenanceServices values (113);
insert into MaintenanceServices values (114);
insert into MaintenanceServices values (115);

insert into MaintHasServices values (113, 'Oil Changes');
insert into MaintHasServices values (113, 'Filter Replacements');
insert into MaintHasServices values (114, 'Oil Changes');
insert into MaintHasServices values (114, 'Filter Replacements');
insert into MaintHasServices values (114, 'Brake Repair');
insert into MaintHasServices values (114, 'Check Engine Light Diagnostics');
insert into MaintHasServices values (115, 'Oil Changes');
insert into MaintHasServices values (115, 'Filter Replacements');
insert into MaintHasServices values (115, 'Brake Repair');
insert into MaintHasServices values (115, 'Check Engine Light Diagnostics');
insert into MaintHasServices values (115, 'Suspension Repair');
insert into MaintHasServices values (115, 'Evaporator Repair');

insert into Roles values ('Manager');
insert into Roles values ('Receptionist');
insert into Roles values ('Mechanic');
insert into Roles values ('Customer');

insert into Users values (123456789, 'John', 'Doe', '8636368778', 'jdoe@gmail.com', '1378 University Woods, Raleigh, NC 27612', 30001, '30001-123456789', 'Doe', 'Manager');
insert into Users values (987654321, 'Rachel', 'Brooks', '8972468552', 'rbrooks@ymail.com', '2201 Gorman Parkwood, Raleigh, NC 27618', 30002, '30002-987654321', 'Brooks', 'Manager');
insert into Users values (987612345, 'Caleb', 'Smith', '8547963210', 'csmith@yahoo.com', '1538 Red Bud Lane, Morrisville, NC 27560', 30003, '30003-987612345', 'Smith', 'Manager');
insert into Users values (111111111, 'Sebastian', 'Aho', '2092923765', 'aho@canes.com', '1234 Red Bud Lane, Morrisville, NC 27560', 30003, '30003-111111111', 'Aho', 'Receptionist');
insert into Users values (132457689, 'James', 'Williams', '4576312882', 'jwilliams@gmail.com', '1400 Gorman St, Raleigh, NC 27606-2972', 30001, '30001-132457689', 'Williams', 'Mechanic');
insert into Users values (314275869, 'David', 'Johnson', '9892321100', 'djohnson@ymail.com', '686 Stratford Court, Raleigh, NC 27606', 30001, '30001-314275869', 'Johnson', 'Mechanic');
insert into Users values (241368579, 'Maria', 'Garcia', '4573459090', 'mgarcia@yahoo.com', '1521 Graduate Lane, Raleigh, NC 27606', 30001, '30001-241368579', 'Garcia', 'Mechanic');
insert into Users values (423186759, 'Ellie', 'Clark', '9892321100', 'eclark@gmail.com', '3125 Avent Ferry Road, Raleigh, NC 27605', 30002, '30002-423186759', 'Clark', 'Mechanic');
insert into Users values (123789456, 'Robert', 'Martinez', '7652304282', 'rmartinez@ymail.com', '1232 Tartan Circle, Raleigh, NC 27607', 30002, '30002-123789456', 'Martinez', 'Mechanic');
insert into Users values (789123456, 'Charles', 'Rodriguez', '9092234281', 'crodriguez@yahoo.com', '218 Patton Lane, Raleigh, NC 27603', 30002, '30002-789123456', 'Rodriguez', 'Mechanic');
insert into Users values (125689347, 'Jose', 'Hernandez', '7653241714', 'jhernandez@gmail.com', '4747 Dola Mine Road, Raleigh, NC 27609', 30002, '30002-125689347', 'Hernandez', 'Mechanic');
insert into Users values (347812569, 'Charlie', 'Brown', '9091237568', 'cbrown@ymail.com', '1 Rockford Mountain Lane, Morrisville, NC 27560', 30003, '30003-347812569', 'Brown', 'Mechanic');
insert into Users values (123456780, 'Jeff', 'Gibson', '3390108899', 'jgibson@yahoo.com', '686 900 Development Drive, Morrisville, NC 27560', 30003, '30003-123456780', 'Gibson', 'Mechanic');
insert into Users values (123456708, 'Isabelle', 'Wilder', '3394561231', 'iwilder@gmail.com', '6601 Koppers Road, Morrisville, NC 27560', 30003, '30003-123456708', 'Wilder', 'Mechanic');
insert into Users values (123456078, 'Peter', 'Titus', '3396723418', 'ptitus@ymail.com', '2860 Slater Road, Morrisville, NC 27560', 30003, '30003-123456078', 'Titus', 'Mechanic');
insert into Users values (123450678, 'Mark', 'Mendez', '3395792080', 'mmendez@yahoo.com', '140 Southport Drive, Morrisville, NC 27560', 30003, '30003-123450678', 'Mendez', 'Mechanic');
insert into Users values (123405678, 'Lisa', 'Alberti', '3391126787', 'lalberti@yahoo.com', '100 Valley Glen Drive, Morrisville, NC 27560', 30003, '30003-123405678', 'Alberti', 'Mechanic');
insert into Users values (10001, 'Peter', 'Parker', '3391126787', 'lalberti@yahoo.com', '100 Valley Glen Drive, Morrisville, NC 27560', 30001, '30001-10001', 'Parker', 'Customer');
insert into Users values (10002, 'Diana', 'Prince', '3391126787', 'lalberti@yahoo.com', '100 Valley Glen Drive, Morrisville, NC 27560', 30001, '30001-10002', 'Prince', 'Customer');
insert into Users values (10053, 'Billy', 'Batson', '3391126787', 'lalberti@yahoo.com', '100 Valley Glen Drive, Morrisville, NC 27560', 30002, '30002-10053', 'Batson', 'Customer');
insert into Users values (10010, 'Bruce', 'Wayne', '3391126787', 'lalberti@yahoo.com', '100 Valley Glen Drive, Morrisville, NC 27560', 30002, '30002-10010', 'Wayne', 'Customer');
insert into Users values (10001, 'Steve', 'Rogers', '3391126787', 'lalberti@yahoo.com', '100 Valley Glen Drive, Morrisville, NC 27560', 30003, '30002-10001', 'Rogers', 'Customer');
insert into Users values (10035, 'Happy', 'Hogan', '3391126787', 'lalberti@yahoo.com', '100 Valley Glen Drive, Morrisville, NC 27560', 30002, '30002-10035', 'Hogan', 'Customer');
insert into Users values (10002, 'Tony', 'Stark', '3391126787', 'lalberti@yahoo.com', '100 Valley Glen Drive, Morrisville, NC 27560', 30003, '30003-10002', 'Stark', 'Customer');
insert into Users values (10003, 'Natasha', 'Romanoff', '3391126787', 'lalberti@yahoo.com', '100 Valley Glen Drive, Morrisville, NC 27560', 30003, '30003-10003', 'Romanoff', 'Customer');
insert into Users values (10011, 'Harvey', 'Bullock', '3391126787', 'lalberti@yahoo.com', '100 Valley Glen Drive, Morrisville, NC 27560', 30003, '30003-10011', 'Bullock', 'Customer');
insert into Users values (10062, 'Sam', 'Wilson', '3391126787', 'lalberti@yahoo.com', '100 Valley Glen Drive, Morrisville, NC 27560', 30003, '30003-10062', 'Wilson', 'Customer');
insert into Users values (10501, 'Wanda', 'Maximoff', '3391126787', 'lalberti@yahoo.com', '100 Valley Glen Drive, Morrisville, NC 27560', 30003, '30003-10501', 'Maximoff', 'Customer');
insert into Users values (10010, 'Virginia', 'Potts', '3391126787', 'lalberti@yahoo.com', '100 Valley Glen Drive, Morrisville, NC 27560', 30003, '30003-10010', 'Potts', 'Customer');

insert into Managers values (123456789, 30001, 44);
insert into Managers values (987654321, 30002, 35);
insert into Managers values (987612345, 30003, 25);

insert into Receptionists values (111111111, 30003, 25);

insert into Mechanics values (132457689, 30001, 35);
insert into Mechanics values (314275869, 30001, 35);
insert into Mechanics values (241368579, 30001, 35);
insert into Mechanics values (423186759, 30002, 25);
insert into Mechanics values (123789456, 30002, 25);
insert into Mechanics values (789123456, 30002, 25);
insert into Mechanics values (125689347, 30002, 25);
insert into Mechanics values (347812569, 30003, 22);
insert into Mechanics values (123456780, 30003, 22);
insert into Mechanics values (123456708, 30003, 22);
insert into Mechanics values (123456078, 30003, 22);
insert into Mechanics values (123450678, 30003, 22);
insert into Mechanics values (123405678, 30003, 22);

insert into Customers values (10001, 30001, 1, 1);
insert into Customers values (10002, 30001, 1, 1);
insert into Customers values (10053, 30002, 1, 1);
insert into Customers values (10010, 30002, 1, 1);
insert into Customers values (10001, 30002, 1, 1);
insert into Customers values (10035, 30002, 1, 1);
insert into Customers values (10002, 30003, 1, 1);
insert into Customers values (10003, 30003, 1, 1);
insert into Customers values (10011, 30003, 1, 1);
insert into Customers values (10062, 30003, 1, 1);
insert into Customers values (10501, 30003, 1, 1);
insert into Customers values (10010, 30003, 1, 1);

insert into ServiceCenters values (30001, 30, 40, '3921 Western Blvd, Raleigh, NC 27606', '3392601234', 1, 0);
insert into ServiceCenters values (30002, 25, 35, '4500 Preslyn Dr Suite 103, Raleigh, NC 27616', '8576890280', 1, 0);
insert into ServiceCenters values (30003, 20, 25, '9515 Chapel Hill Rd, Morrisville, NC 27560', '7798182200', 0, 1);

insert into Prices values (30001, 101, 'Honda', 140, 1);
insert into Prices values (30001, 101, 'Toyota', 160, 1);
insert into Prices values (30001, 101, 'Nissan', 175, 1);
insert into Prices values (30001, 102, 'Honda', 400, 5);
insert into Prices values (30001, 102, 'Toyota', 450, 5);
insert into Prices values (30001, 102, 'Nissan', 500, 5);
insert into Prices values (30001, 103, 'Honda', 590, 4);
insert into Prices values (30001, 103, 'Toyota', 680, 4);
insert into Prices values (30001, 103, 'Nissan', 700, 4);
insert into Prices values (30001, 104, 'Honda', 140, 2);
insert into Prices values (30001, 104, 'Toyota', 160, 2);
insert into Prices values (30001, 104, 'Nissan', 175, 2);
insert into Prices values (30001, 105, 'Honda', 400, 4);
insert into Prices values (30001, 105, 'Toyota', 450, 4);
insert into Prices values (30001, 105, 'Nissan', 500, 4);
insert into Prices values (30001, 106, 'Honda', 400, 5);
insert into Prices values (30001, 106, 'Toyota', 450, 5);
insert into Prices values (30001, 106, 'Nissan', 500, 5);
insert into Prices values (30001, 107, 'Honda', 1000, 7);
insert into Prices values (30001, 107, 'Toyota', 1100, 7);
insert into Prices values (30001, 107, 'Nissan', 1200, 7);
insert into Prices values (30001, 108, 'Honda', 400, 3);
insert into Prices values (30001, 108, 'Toyota', 450, 3);
insert into Prices values (30001, 108, 'Nissan', 500, 3);
insert into Prices values (30001, 109, 'Honda', 50, 2);
insert into Prices values (30001, 109, 'Toyota', 60, 2);
insert into Prices values (30001, 109, 'Nissan', 70, 2);
insert into Prices values (30001, 110, 'Honda', 140, 1);
insert into Prices values (30001, 110, 'Toyota', 160, 1);
insert into Prices values (30001, 110, 'Nissan', 175, 1);
insert into Prices values (30001, 111, 'Honda', 590, 3);
insert into Prices values (30001, 111, 'Toyota', 680, 3);
insert into Prices values (30001, 111, 'Nissan', 700, 3);
insert into Prices values (30001, 112, 'Honda', 400, 4);
insert into Prices values (30001, 112, 'Toyota', 450, 4);
insert into Prices values (30001, 112, 'Nissan', 500, 4);
insert into Prices values (30001, 113, 'Honda', 120, 3);
insert into Prices values (30001, 113, 'Toyota', 200, 3);
insert into Prices values (30001, 113, 'Nissan', 190, 3);
insert into Prices values (30001, 114, 'Honda', 195, 6);
insert into Prices values (30001, 114, 'Toyota', 215, 6);
insert into Prices values (30001, 114, 'Nissan', 210, 6);
insert into Prices values (30001, 115, 'Honda', 300, 9);
insert into Prices values (30001, 115, 'Toyota', 305, 9);
insert into Prices values (30001, 115, 'Nissan', 310, 9);

insert into Prices values (30002, 101, 'Honda', 160, 1);
insert into Prices values (30002, 101, 'Toyota', 180, 1);
insert into Prices values (30002, 101, 'Nissan', 195, 1);
insert into Prices values (30002, 102, 'Honda', 420, 5);
insert into Prices values (30002, 102, 'Toyota', 470, 5);
insert into Prices values (30002, 102, 'Nissan', 520, 5);
insert into Prices values (30002, 103, 'Honda', 610, 4);
insert into Prices values (30002, 103, 'Toyota', 700, 4);
insert into Prices values (30002, 103, 'Nissan', 720, 4);
insert into Prices values (30002, 104, 'Honda', 160, 2);
insert into Prices values (30002, 104, 'Toyota', 180, 2);
insert into Prices values (30002, 104, 'Nissan', 195, 2);
insert into Prices values (30002, 105, 'Honda', 420, 4);
insert into Prices values (30002, 105, 'Toyota', 470, 4);
insert into Prices values (30002, 105, 'Nissan', 520, 4);
insert into Prices values (30002, 106, 'Honda', 420, 5);
insert into Prices values (30002, 106, 'Toyota', 470, 5);
insert into Prices values (30002, 106, 'Nissan', 520, 5);
insert into Prices values (30002, 107, 'Honda', 1020, 7);
insert into Prices values (30002, 107, 'Toyota', 1120, 7);
insert into Prices values (30002, 107, 'Nissan', 1220, 7);
insert into Prices values (30002, 108, 'Honda', 420, 3);
insert into Prices values (30002, 108, 'Toyota', 470, 3);
insert into Prices values (30002, 108, 'Nissan', 520, 3);
insert into Prices values (30002, 109, 'Honda', 70, 2);
insert into Prices values (30002, 109, 'Toyota', 80, 2);
insert into Prices values (30002, 109, 'Nissan', 90, 2);
insert into Prices values (30002, 110, 'Honda', 160, 1);
insert into Prices values (30002, 110, 'Toyota', 180, 1);
insert into Prices values (30002, 110, 'Nissan', 195, 1);
insert into Prices values (30002, 111, 'Honda', 610, 3);
insert into Prices values (30002, 111, 'Toyota', 700, 3);
insert into Prices values (30002, 111, 'Nissan', 720, 3);
insert into Prices values (30002, 112, 'Honda', 420, 4);
insert into Prices values (30002, 112, 'Toyota', 470, 4);
insert into Prices values (30002, 112, 'Nissan', 520, 4);
insert into Prices values (30002, 113, 'Honda', 125, 3);
insert into Prices values (30002, 113, 'Toyota', 205, 3);
insert into Prices values (30002, 113, 'Nissan', 195, 3);
insert into Prices values (30002, 114, 'Honda', 200, 6);
insert into Prices values (30002, 114, 'Toyota', 220, 6);
insert into Prices values (30002, 114, 'Nissan', 215, 6);
insert into Prices values (30002, 115, 'Honda', 305, 9);
insert into Prices values (30002, 115, 'Toyota', 310, 9);
insert into Prices values (30002, 115, 'Nissan', 315, 9);

insert into Prices values (30003, 101, 'Honda', 175, 1);
insert into Prices values (30003, 101, 'Toyota', 195, 1);
insert into Prices values (30003, 101, 'Nissan', 210, 1);
insert into Prices values (30003, 102, 'Honda', 435, 5);
insert into Prices values (30003, 102, 'Toyota', 485, 5);
insert into Prices values (30003, 102, 'Nissan', 535, 5);
insert into Prices values (30003, 103, 'Honda', 6125, 4);
insert into Prices values (30003, 103, 'Toyota', 715, 4);
insert into Prices values (30003, 103, 'Nissan', 735, 4);
insert into Prices values (30003, 104, 'Honda', 175, 2);
insert into Prices values (30003, 104, 'Toyota', 195, 2);
insert into Prices values (30003, 104, 'Nissan', 210, 2);
insert into Prices values (30003, 105, 'Honda', 435, 4);
insert into Prices values (30003, 105, 'Toyota', 485, 4);
insert into Prices values (30003, 105, 'Nissan', 535, 4);
insert into Prices values (30003, 106, 'Honda', 435, 5);
insert into Prices values (30003, 106, 'Toyota', 485, 5);
insert into Prices values (30003, 106, 'Nissan', 535, 5);
insert into Prices values (30003, 107, 'Honda', 1035, 7);
insert into Prices values (30003, 107, 'Toyota', 1135, 7);
insert into Prices values (30003, 107, 'Nissan', 1235, 7);
insert into Prices values (30003, 108, 'Honda', 435, 3);
insert into Prices values (30003, 108, 'Toyota', 485, 3);
insert into Prices values (30003, 108, 'Nissan', 535, 3);
insert into Prices values (30003, 109, 'Honda', 85, 2);
insert into Prices values (30003, 109, 'Toyota', 95, 2);
insert into Prices values (30003, 109, 'Nissan', 105, 2);
insert into Prices values (30003, 110, 'Honda', 175, 1);
insert into Prices values (30003, 110, 'Toyota', 195, 1);
insert into Prices values (30003, 110, 'Nissan', 210, 1);
insert into Prices values (30003, 111, 'Honda', 625, 3);
insert into Prices values (30003, 111, 'Toyota', 715, 3);
insert into Prices values (30003, 111, 'Nissan', 735, 3);
insert into Prices values (30003, 112, 'Honda', 435, 4);
insert into Prices values (30003, 112, 'Toyota', 485, 4);
insert into Prices values (30003, 112, 'Nissan', 535, 4);
insert into Prices values (30003, 113, 'Honda', 140, 3);
insert into Prices values (30003, 113, 'Toyota', 195, 3);
insert into Prices values (30003, 113, 'Nissan', 180, 3);
insert into Prices values (30003, 114, 'Honda', 210, 6);
insert into Prices values (30003, 114, 'Toyota', 215, 6);
insert into Prices values (30003, 114, 'Nissan', 220, 6);
insert into Prices values (30003, 115, 'Honda', 310, 9);
insert into Prices values (30003, 115, 'Toyota', 310, 9);
insert into Prices values (30003, 115, 'Nissan', 305, 9);

insert into TimeSlots values (1, 8, 9);
insert into TimeSlots values (2, 9, 10);
insert into TimeSlots values (3, 10, 11);
insert into TimeSlots values (4, 11, 12);
insert into TimeSlots values (5, 13, 14);
insert into TimeSlots values (6, 14, 15);
insert into TimeSlots values (7, 15, 16);
insert into TimeSlots values (8, 16, 17);
insert into TimeSlots values (9, 17, 18);
insert into TimeSlots values (10, 18, 19);
insert into TimeSlots values (11, 19, 20);

insert into Schedule values (123405678, 30003, 1, 2, 1, 'Work', 1);
insert into Schedule values (123450678, 30003, 1, 2, 1, 'Work', 2);
insert into Schedule values (123456780, 30003, 1, 1, 1, 'Work', 3);
insert into Schedule values (423186759, 30002, 2, 4, 2, 'Work', 4);
insert into Schedule values (423186759, 30002, 2, 4, 3, 'Work', 4);
insert into Schedule values (423186759, 30002, 2, 4, 4, 'Work', 4);
insert into Schedule values (423186759, 30002, 2, 4, 5, 'Work', 4);
insert into Schedule values (125689347, 30002, 2, 1, 3, 'Work', 5);
insert into Schedule values (125689347, 30002, 2, 1, 4, 'Work', 5);
insert into Schedule values (125689347, 30002, 2, 1, 5, 'Work', 5);
insert into Schedule values (789123456, 30002, 2, 6, 2, 'Work', 6);
insert into Schedule values (789123456, 30002, 2, 6, 3, 'Work', 6);
insert into Schedule values (789123456, 30002, 2, 6, 4, 'Work', 6);
insert into Schedule values (789123456, 30002, 2, 6, 5, 'Work', 6);
insert into Schedule values (789123456, 30002, 2, 6, 6, 'Work', 6);
insert into Schedule values (789123456, 30002, 2, 6, 7, 'Work', 6);
insert into Schedule values (125689347, 30002, 3, 5, 5, 'Work', 7);
insert into Schedule values (125689347, 30002, 3, 5, 6, 'Work', 7);
insert into Schedule values (125689347, 30002, 3, 5, 7, 'Work', 7);

insert into CustomerVehicles values ('4Y1BL658', 10001, 30001, 'Toyota', 53000, 2007, 'B');
insert into CustomerVehicles values ('7A1ST264', 10002, 30001, 'Honda', 117000, 1999, 'A');
insert into CustomerVehicles values ('5TR3K914', 10053, 30002, 'Nissan', 111000, 2015, 'C');
insert into CustomerVehicles values ('15DC9A87', 10010, 30002, 'Toyota', 21000, 2020, 'A');
insert into CustomerVehicles values ('18S5R2D8', 10001, 30002, 'Nissan', 195500, 2019, 'C');
insert into CustomerVehicles values ('9R2UHP54', 10035, 30002, 'Honda', 67900, 2013, 'B');
insert into CustomerVehicles values ('88TSM888', 10002, 30003, 'Honda', 10000, 2000, 'A');
insert into CustomerVehicles values ('71HK2D89', 10003, 30003, 'Toyota', 195550, 2004, 'B');
insert into CustomerVehicles values ('34KLE19D', 10011, 30003, 'Toyota', 123800, 2010, 'C');
insert into CustomerVehicles values ('29T56WC3', 10062, 30003, 'Nissan', 51300, 2011, 'A');
insert into CustomerVehicles values ('P39VN198', 10501, 30003, 'Nissan', 39500, 2013, 'B');
insert into CustomerVehicles values ('39YVS415', 10010, 30003, 'Honda', 49900, 2021, 'A');

insert into ServiceEvents values (1, 'P39VN198', 30003, 123405678, 1, 2, 1, 1, 0);
insert into ServiceEvents values (2, '39YVS415', 30003, 123450678, 1, 2, 1, 1, 0);
insert into ServiceEvents values (3, '29T56WC3', 30003, 123456780, 1, 1, 1, 1, 1);
insert into ServiceEvents values (4, '9R2UHP54', 30002, 423186759, 2, 4, 2, 5, 1);
insert into ServiceEvents values (5, '5TR3K914', 30002, 125689347, 2, 1, 3, 5, 1);
insert into ServiceEvents values (6, '15DC9A87', 30002, 789123456, 2, 6, 2, 7, 1);
insert into ServiceEvents values (7, '18S5R2D8', 30002, 125689347, 3, 5, 5, 7, 1);

insert into EventOnServices values (1, 110);
insert into EventOnServices values (2, 101);
insert into EventOnServices values (3, 109);
insert into EventOnServices values (4, 105);
insert into EventOnServices values (5, 111);
insert into EventOnServices values (6, 114);
insert into EventOnServices values (7, 113);





