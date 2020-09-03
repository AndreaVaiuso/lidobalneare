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
			return;
		} else {
			$("#ajaxloaderscreen").toggle();
			
			// Prepare request JSON.
			var customer = { "customer" : selectedUser };
			
			// Do POST.
			$.post("Admin", customer, function(data) {
				$("ajaxloaderscreen").toggle();
				location.href = "bookingsAdmin.jsp?unregistered=NO";
			});
		}
	}
);

$("#admin_checkunregreservationbutton").click(function(){
	location.href = "bookingsAdmin.jsp?unregistered=YES";
});


$("#send_btn").click(
	function (){
		var msg = $("#msg").val();
		$.get("Admin?request=message&message="+msg,function(response){
			if(response.type=="success"){
				$("#communication_form").fadeOut(500);
			} else {
				$("#communication_form").fadeOut(500);
				showDefaultError();
			}
		})
	}
);