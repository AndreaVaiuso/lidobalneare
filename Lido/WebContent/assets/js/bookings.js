var reservation;

function selectPass (email, begin, end, seat) {
	$(".selected").classList.remove("selected");
	this.classList.add("selected");
	reservation = {
		"pass_email" : email,
		"pass_begin" : begin,
		"pass_end" : end,
		"seat" : seat
	};
}

function selectBooking (email, day, slot, seat) {
	$(".selected").classList.remove("selected");
	this.classList.add("selected");
	reservation = {
		"email" : email,
		"day" : day,
		"time_slot" : slot,
		"seat" : seat
	};
}

// Opens the form for editing the selected reservation, wether it be a pass or a single booking.
function editReservation () {
	if ( !reservation.pass_email == null ) {	// It's a Pass object.
		$("pass_edit_form").toggle();
		$("pass_email").val(reservation["pass_email"]);
		$("pass_begin").val(reservation["pass_begin"]);
		$("pass_end").val(reservation["pass_end"]);
		$("pass_seat").val(reservation["seat"]);
	} else if ( !reservation.email == null ) {	// It's a Booking object.
		$("booking_edit_form").toggle();
		$("book_email").val(reservation["email"]);
		$("book_day").val(reservation["day"]);
		$("book_slot").val(reservation["time_slot"]);
		$("book_seat").val(reservation["seat"]);
	} else {	// Something has gone wrong.
		alert("An unknown error has occurred in file: bookings.js - function: editReservation");
		return;
	}
}

function applyPass () {
	if (reservation == null) {
		showerror("No data.", "No reservation has been selected.");
	} else {
		$("#ajaxloaderscreen").toggle();
		
		var prev = reservation;
		var next = reservation;	// To be changed with form eventually.
		
		if ( !reservation.pass_email == null ) {	// It's a Pass object.
			next["pass_email"] = $("pass_email").val();
			next["pass_begin"] = $("pass_begin").val();
			next["pass_end"] = $("pass_end").val();
			next["seat"] = $("pass_seat").val();
		} else if ( !reservation.email == null ) {	// It's a Booking object.
			next["email"] = $("book_email").val();
			next["day"] = $("book_day").val();
			next["time_slot"] = $("book_slot").val();
			next["seat"] = $("book_seat").val();
		}
				
		var objs = {prev, next};
		
		$.post("BookingAdminServlet", objs, function(data){
			$("#ajaxloaderscreen").toggle();
			
			if (data.type == "success") {
				alert("Update successful!");
			} else if (data.type == "error") {
				location.href = "errorpage.html";
			} 
		},"json");
	}
}

function back () {
	reservation = null;
	location.href = "adminPage.jsp";
}