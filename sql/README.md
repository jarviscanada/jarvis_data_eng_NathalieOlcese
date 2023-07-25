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
This script inserts a new record and automaically generates the value for the facid
```
insert into cd.facilities
    (facid, name, membercost, guestcost, initialoutlay, monthlymaintenance)
    select (select max(facid) from cd.facilities)+1, 'Spa', 20, 30, 100000, 800;  
```

this sctript modifies the  the outlay in the facilities table
```

update cd.facilities
    set initialoutlay = 10000
    where facid = 1;
```
This script updates the code in the facilities table
```
update cd.facilities facs
    set
        membercost = (select membercost * 1.1 from cd.facilities where facid = 0),
        guestcost = (select guestcost * 1.1 from cd.facilities where facid = 0)
    where facs.facid = 1;  
```

 This script delete the data from the bookins table      
```
truncate cd.bookings;
```
This script deletes an record from the members table that has an specific characteristic
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
This script cretaes a list of facilities that have aspecific pattern of characters in their name
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
This scriot shows the dtails of the members that joine after an scpecific date
```
select memid, surname, firstname, joindate 
	from cd.members
	where joindate >= '2012-09-01';   
```
This script poduces a combine list of all surnames and all facility names
```
select surname 
	from cd.members
union
select name
	from cd.facilities; 
```
This script podcuce a list of start time bookins by member who has a certain name
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
This script prouce a list of start for bookins  of tennis court orderes by time
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
this scroipt produces the list of member and the member that reocmemnded name order by surname,firstname
```
select mems.firstname as memfname, mems.surname as memsname, recs.firstname as recfname, recs.surname as recsname
	from 
		cd.members mems
		left outer join cd.members recs
			on recs.memid = mems.recommendedby
order by memsname, memfname; 
```
This script displays the list of members who recommendedanother member without duplicate snad ordered by surname, firstname
```
select distinct recs.firstname as firstname, recs.surname as surname
	from 
		cd.members mems
		inner join cd.members recs
			on recs.memid = mems.recommendedby
order by surname, firstname;          
```
This script produces the list of member and the member who recomended them with no joins and ordered by surname,firstname
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
select recommendedby, count(*) 
	from cd.members
	where recommendedby is not null
	group by recommendedby
order by recommended

select recommendedby, count(*) 
	from cd.members
	where recommendedby is not null
	group by recommendedby
order by recommendedby;

select facid, sum(slots) as "Total Slots"
	from cd.bookings
	where
		starttime >= '2012-09-01'
		and starttime < '2012-10-01'
	group by facid
order by sum(slots);   

select facid, extract(month from starttime) as month, sum(slots) as "Total Slots"
	from cd.bookings
	where extract(year from starttime) = 2012
	group by facid, month
order by facid, month;      

select count(distinct memid) from cd.bookings  

	from cd.bookings bks
	inner join cd.members mems on
		mems.memid = bks.memid
	where starttime >= '2012-09-01'
	group by mems.surname, mems.firstname, mems.memid
order by mems.memid; 

select count(*) over(), firstname, surname
	from cd.members
order by joindate          

select row_number() over(order by joindate), firstname, surname
	from cd.members
order by joindate          

select facid, total from (
	select facid, sum(slots) total, rank() over (order by sum(slots) desc) rank
        	from cd.bookings
		group by facid
	) as ranked
	where rank = 1          

	
select surname || ', ' || firstname as name from cd.members    

select memid, telephone from cd.members where telephone ~ '[()]';   

select substr (mems.surname,1,1) as letter, count(*) as count 
    from cd.members mems
    group by letter
    order by letter         
