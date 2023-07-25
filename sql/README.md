# SQL

## introduction

In this project different sql queries were use in order to ouput the ata ruquired different types of SQL commands were used such as GROUP BY , WHERE,HAVING. Many type od sql operation ere perfomed such as agrregations and joins. In some of the sql queries some other script could have also returned the desired output by the parctice but in each of the script the most simple query was selected since it is agood pratcice to always use the simplest query with less commands. 

## Scripts
This script insterts a new record in the facilities table
```
insert into cd.facilities
    (facid, name, membercost, guestcost, initialoutlay, monthlymaintenance)
    values (9, 'Spa', 20, 30, 100000, 800);

```
This script inserts a new record and automatically generates the value for the facid
```
insert into cd.facilities
    (facid, name, membercost, guestcost, initialoutlay, monthlymaintenance)
    select (select max(facid) from cd.facilities)+1, 'Spa', 20, 30, 100000, 800;  
```

This sctript modifies the  the outlay in the facilities table
```

update cd.facilities
    set initialoutlay = 10000
    where facid = 1;
```
This script updates the cost in the facilities table
```
update cd.facilities facs
    set
        membercost = (select membercost * 1.1 from cd.facilities where facid = 0),
        guestcost = (select guestcost * 1.1 from cd.facilities where facid = 0)
    where facs.facid = 1;  
```

 This script deletes the data from the bookings table      
```
truncate cd.bookings;
```
This script deletes records from the members table that has an specific characteristic
```
delete from cd.members where memid = 37;   
```
This script identifies the facilities that charges a fee but the fee is lesss than an specific amount
```
select facid, name, membercost, monthlymaintenance 
	from cd.facilities 
	where 
		membercost > 0 and 
		(membercost < monthlymaintenance/50.0);
```
This script cretaes a list of facilities that have a specific pattern of characters in their name
```
select *
	from cd.facilities 
	where 
		name like '%Tennis%';
```
 This script retrieves the information of some facilities with specific id
```
select *
	from cd.facilities 
	where 
		facid in (1,5);  
```
This script shows the dtails of the members that joined after a scpecific date
```
select memid, surname, firstname, joindate 
	from cd.members
	where joindate >= '2012-09-01';   
```
This script poduces a combined list of all surnames and all facility names
```
select surname 
	from cd.members
union
select name
	from cd.facilities; 
```
This script podcuces a list of start time bookings by member who has a certain name
```
select bks.starttime 
	from 
		cd.bookings bks
		inner join cd.members mems
			on mems.memid = bks.memid
	where 
		mems.firstname='David' 
		and mems.surname='Farrell';
```  
This script prouce a list of start for bookings  of tennis court ordered by time
```
select bks.starttime as start, facs.name as name
	from 
		cd.facilities facs
		inner join cd.bookings bks
			on facs.facid = bks.facid
	where 
		facs.name in ('Tennis Court 2','Tennis Court 1') and
		bks.starttime >= '2012-09-21' and
		bks.starttime < '2012-09-22'
order by bks.starttime; 
```
This script produces the list of member and the member that recommended t name order by surname,firstname
```
select mems.firstname as memfname, mems.surname as memsname, recs.firstname as recfname, recs.surname as recsname
	from 
		cd.members mems
		left outer join cd.members recs
			on recs.memid = mems.recommendedby
order by memsname, memfname; 
```
This script displays the list of members whorecommended another member without duplicates snad ordered by surname, firstname
```
select distinct recs.firstname as firstname, recs.surname as surname
	from 
		cd.members mems
		inner join cd.members recs
			on recs.memid = mems.recommendedby
order by surname, firstname;          
```
This script produces the list of member and the member who recommended them with no joins and ordered by surname,firstname
```
select distinct mems.firstname || ' ' ||  mems.surname as member,
	(select recs.firstname || ' ' || recs.surname as recommender 
		from cd.members recs 
		where recs.memid = mems.recommendedby
	)
	from 
		cd.members mems
order by member;
      ```
This script displays a list of how many members each member has recommended  order by id
```
select recommendedby, count(*) 
	from cd.members
	where recommendedby is not null
	group by recommendedby
order by recommended
```
This script produces a list of total slots booked in all facilities ordered by facilities id
```
select facid, sum(slots) as "Total Slots"
	from cd.bookings
	group by facid
order by facid;      
```

This script produces a list of the total slots booked by facility in a pecific time frame order by number od slots
```
select facid, sum(slots) as "Total Slots"
	from cd.bookings
	where
		starttime >= '2012-09-01'
		and starttime < '2012-10-01'
	group by facid
order by sum(slots); 
```
This script produces a list of slots booked in each time of the year ordered by id and month
```
select facid, extract(month from starttime) as month, sum(slots) as "Total Slots"
	from cd.bookings
	where extract(year from starttime) = 2012
	group by facid, month
order by facid, month;      
```
This script produces a list of members and guests who had made at least one booking
```
select count(distinct memid) from cd.bookings  
```
This script produces a list of each member and their bookings at a  specific time order by id
```
select mems.surname, mems.firstname, mems.memid, min(bks.starttime) as starttime
	from cd.bookings bks
	inner join cd.members mems on
		mems.memid = bks.memid
	where starttime >= '2012-09-01'
	group by mems.surname, mems.firstname, mems.memid
order by mems.memid;
``` 

This script displays a list of memebers including member count ordered by join date and includes guest members
```
select count(*) over(), firstname, surname
	from cd.members
order by joindate          
```

This script porduces a monotonically numbered list od members order by their date of joining
```
select row_number() over(order by joindate), firstname, surname
	from cd.members
order by joindate          
```
This script outputs the facility id that has the most number of members
```
select facid, total from (
	select facid, sum(slots) total, rank() over (order by sum(slots) desc) rank
        	from cd.bookings
		group by facid
	) as ranked
	where rank = 1      

```
This script dipslays the names of all members as surname,firstname
```
select surname || ', ' || firstname as name from cd.members    
```
This script finds all telephone numbers that contain parentheses and returns the member id and telephone number sorted by member
```
select memid, telephone from cd.members where telephone ~ '[()]';   
```
This script produces list count of membesr whose last name start with the same letter and sorts by letter
```
select substr (mems.surname,1,1) as letter, count(*) as count 
    from cd.members mems
    group by letter
    order by letter         
```
