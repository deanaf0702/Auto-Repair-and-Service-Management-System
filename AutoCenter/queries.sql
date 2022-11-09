--Query 1
select serviceCenterId
from Customers
group by serviceCenterId
order by COUNT(*) desc
fetch next 1 rows only;

--Query 2
select AVG(p.price) from Prices p, Services s
where p.serviceId = s.serviceId
and p.model = 'Honda'
and s.name = 'Evaporator Repair';

-- Query 3
select distinct cv.customerId
from CustomerVehicles cv, ServiceEvents e
where e.centerId = 30003
and e.vin = cv.vin
and e.isPaid = 0;

-- Query 4
select distinct s.name
from Services s
inner join RepairServices rs on s.serviceId = rs.serviceId
inner join MaintHasServices m on m.serviceName = s.name;

--Query 5
select a.p1 - b.p1 as costDiff 
from (select SUM(p.price) as p1
	from Services s
	inner join Prices p on s.serviceId = p.serviceId
	where (s.name = 'Belt Replacement' or s.name = 'A')
	and p.model = 'Toyota'
	and p.centerId = 30002) a,
	(select SUM(p.price) as p1
	from Services s
	inner join Prices p on s.serviceId = p.serviceId
	where (s.name = 'Belt Replacement' or s.name = 'A')
	and p.model = 'Toyota'
	and p.centerId = 30001) b;
	
-- Query 6
select case
when lastMClass = 'A' then 'B'
when lastMClass = 'B' then 'C'
else 'A' end as nextMClass
from CustomerVehicles cv
where cv.vin = '34KLE19D';