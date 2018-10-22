create table Room
	(Room_num	varchar(20) primary key,
		Avail_guest	varchar(30),
		room_type	varchar(20),		
		state		varchar(20)
	);
create table Customer
	(cust_id	varchar(30) primary key,
		cust_name	varchar(30),
		sex			varchar(10),
		cust_addr	varchar(30),
		cust_phone	varchar(20)
	);
create table Staff
	(staff_id	varchar(30) primary key,
		staff_name	varchar(30),
		sex			varchar(10),
		staff_addr	varchar(30),
		staff_phone	varchar(20)
	);
create table Reservation
	(Rv_num		varchar(30) primary key,
		Rv_date		varchar(30),
		cust_id		varchar(30),
		staff_id	varchar(30),
		Room_num	varchar(30),
		Checkin_date	varchar(30) not null,
		Checkout_date	varchar(30) not null,
		stay_num	varchar(30),
		foreign key (cust_id) references Customer,
		foreign key (staff_id) references staff
	);