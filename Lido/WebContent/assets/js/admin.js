var selectedUser;

function goToPage(i){
	location.href="adminPage.jsp?currentPage="+i;
}

function selectRow(i,email){
	var selected = document.getElementsByClassName("tablerowselected");
	for(var j = 0; j < selected.length; j++){
		selected[j].className = "";
	}
	selectedUser = email;
	document.getElementById("table_entry_"+i).className= "tablerowselected";
}

$("#admin_checkreservationbutton").click(
	function () {
		if (selectedUser == null) {
			showerror("No customer selected.", "Please select a user first.");
			return;
		} else {
			$("#ajaxloaderscreen").toggle();
			$.get("Admin?request=customer&selecteduser="+selectedUser, function(response) {
				$("ajaxloaderscreen").toggle();
				if(response.type=="success"){
					location.href = "bookingsAdmin.jsp?unregistered=NO";
				} else {
					showDefaultError();
				}
			});
		}
	}
);

$("#admin_checkunregreservationbutton").click(function(){
	location.href = "bookingsAdmin.jsp?unregistered=YES";
});


$("#send_btn").click(
	function (){
		let title = $("#title").val();
		let msg = $("#msg").val();
		let msgtype = $("#messagetype option:selected").text().toUpperCase();
		$.get("Admin?request=message&message="+msg+"&title="+title+"&messagetype="+msgtype,function(response){
			if(response.type=="success"){
				$("#communication_form").fadeOut(500);
			} else {
				$("#communication_form").fadeOut(500);
				showDefaultError();
			}
		})
	}
);