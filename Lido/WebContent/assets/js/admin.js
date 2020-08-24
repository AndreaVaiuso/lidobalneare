var selectedUser;

// Move to the selected page of the results list.
function goToPage(i){
	location.href="adminPage.jsp?currentPage="+i;
}

// Mark the i-th row as selected. 
function selectRow(i,email){
	var selected = document.getElementsByClassName("tablerowselected");
	for(var j=0;j<selected.length;j++){
		selected[j].className = "";
	}
	selectedUser = email;
	document.getElementById("table_entry_"+i).className= "tablerowselected";
}

// Button onclick event handlers.

document.getElementById("admin_subscriptioninfobutton").onclick = function(){
	
};

document.getElementById("admin_checkreservationbutton").onclick = function(){
	
};

$("#send_btn").click(
	function(){
		var msg = $("#msg").val();
		
		
	}
);