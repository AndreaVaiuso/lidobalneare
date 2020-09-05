var reservation;

function selectPass (email, begin, end, seat) {
	reservation = {
			"request" : "update",
			"type" : "pass",
			"pass_email" : email,
			"pass_begin" : begin,
			"pass_end" : end,
			"seat" : seat,
			
			"new_pass_begin" : "",
			"new_pass_end" : "",
			"new_seat" : ""
	};
	editReservation();
}

function selectBooking (email, day, slot, seat) {
	reservation = {
			"request" : "update",
			"type" : "booking",
			"email" : email,
			"day" : day,
			"time_slot" : slot,
			"seat" : seat,
			
			"new_day" : day,
			"new_time_slot" : slot,
			"new_seat" : seat
	};
	editReservation();
}

//Opens the form for editing the selected reservation, wether it be a pass or a single booking.
function editReservation () {
	if ( reservation.pass_email != null ) {	// It's a Pass object.
		$("#pass_edit_form").fadeIn(500);
		$("#pass_email").val(reservation["pass_email"]);
		$("#pass_begin").val(reservation["pass_begin"]);
		$("#pass_end").val(reservation["pass_end"]);
		$("#pass_seat").val(reservation["seat"]);
	} else if ( reservation.email != null ) {	// It's a Booking object.
		$("#booking_edit_form").fadeIn(500);
		$("#book_email").val(reservation["email"]);
		$("#book_day").val(reservation["day"]);
		document.getElementById("book_slot").selectedIndex = reservation["time_slot"];
		$("#book_seat").val(reservation["seat"]);
	} else {	// Something has gone wrong.
		return;
	}
}

function apply(){
	if(reservation.type=="booking"){
		reservation.new_day=$("#book_day").val();
		reservation.new_time_slot=document.getElementById("book_slot").selectedIndex;
		reservation.new_seat=$("#book_seat").val();
	} else if(reservation.type=="pass"){
		reservation.new_pass_begin=$("#pass_begin").val();
		reservation.new_pass_end=$("#pass_end").val();
		reservation.new_seat=$("#pass_seat").val();
	}
}

function updateReservation() {
	apply();
	$("#pass_edit_form").fadeOut(500);
	$("#booking_edit_form").fadeOut(500);
	reservation.request = "update";
	$("#ajaxloaderscreen").toggle();
	$.post("Admin",reservation,function(response){
		$("#ajaxloaderscreen").toggle();
		if(response.type=="success"){
			location.reload();
		} else {
			showDefaultError();
		}
	});
}

function deleteReservation() {
	$("#pass_edit_form").fadeOut(500);
	$("#booking_edit_form").fadeOut(500);
	reservation.request = "delete";
	$("#ajaxloaderscreen").toggle();
	$.post("Admin",reservation,function(response){
		$("#ajaxloaderscreen").toggle();
		if(response.type=="success"){
			location.reload();
		} else {
			showDefaultError();
		}
	});
	
}

function back () {
	reservation = null;
	location.href = "adminPage.jsp";
}