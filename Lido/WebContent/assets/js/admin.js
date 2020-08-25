var selectedUser;

// Move to the selected page of the results list.
function goToPage(i){
	location.href="adminPage.jsp?currentPage="+i;
}

// Mark the i-th row as selected. 
function selectRow(i,email){
	// Store currently selected row.
	var selected = document.getElementsByClassName("tablerowselected");
	
	// Deselect every row.
	for(var j = 0; j < selected.length; j++){
		selected[j].className = "";
	}
	
	// Select chosen row.
	selectedUser = email;
	document.getElementById("table_entry_"+i).className= "tablerowselected";
}

// Button onclick event handlers.

$("#admin_checkreservationbutton").click(
	function () {
		// Check if a row is selected.
		if (selectedUser == null) {
			// Show an error message.
			showerror("No customer selected.", "Please select a user first.");
			/*
			$("#alerttitle").text("No customer selected.");
			$("#alertcontent").text("Please select a user first.");
			$("#alertscreen").toggle();
			*/
			return;
		} else {
			$("#ajaxloaderscreen").toggle();
			
			// Prepare request JSON.
			var customer = { "customer" : selectedUser };
			
			/*
			CAMBIO DI PROGRAMMA. Fare la post della nuova pagina con a parametro
			l'email dell'utente, poi far fare la seguente post a quella pagina.
			*/
			
			// Do POST.
			$.post("BookingAdminServlet", customer, function(data, status, xhr) {
				$("ajaxloaderscreen").toggle();
				
				
			},"text")
		}
	}
);

$("#send_btn").click(
	function (){
		var msg = $("#msg").val();
		// Send the message to info monitors.
	}
);