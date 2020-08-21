var selectedUser;
function goToPage(i){
	location.href="adminPage.jsp?currentPage="+i;
}

function selectRow(i,email){
	var selected = document.getElementsByClassName("tablerowselected");
	for(var j=0;j<selected.length;j++){
		selected[j].className = "";
	}
	selectedUser = email;
	document.getElementById("table_entry_"+i).className= "tablerowselected";
}