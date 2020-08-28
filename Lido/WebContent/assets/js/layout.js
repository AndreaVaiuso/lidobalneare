var currentDragging;
var currentDraggingChairName;
var drag_left;
var drag_top;
var chaircount = 0;

function onDragOver(event) {
  event.preventDefault();
}

function onDragStart(event,id,chairname){
	currentDragging = document.getElementById(id);
	currentDraggingChairName = chairname;
	drag_left = event.clientX;
	drag_top = event.clientY;
}

function onDrop(event) {
	
	movx = event.clientX - drag_left;
	movy = event.clientY - drag_top;
	dragx = currentDragging.offsetLeft + movx;
	dragy = currentDragging.offsetTop + movy;
	currentDragging.style.left = dragx + "px" ;
	currentDragging.style.top = dragy + "px" ;
	
	$.get("Admin?movedchair="+currentDraggingChairName+"&x="+dragx+"&y="+dragy,function(response){
		
	});
}

function showChairPopup(id) {
	var chair = document.getElementById(id);
	var popup = chair.firstElementChild;
	if(chair.offsetTop <= 80){
		popup.style.bottom = "-70px";
	} else popup.style.bottom = "70px";
	popup.style.display = "block";
}

function hideChairPopup(){
	var chairs = document.getElementsByClassName("chair");
	for(let i = 0; i<chairs.length; i++){
		chairs[i].firstElementChild.style.display = "none";
	}
}

/*
function addChairToLayout(id){
	var chairname = $("#"+id+"_name").val();
	var price = $("#"+id+"_price").val();
	var x = document.getElementById(id).offsetLeft;
	var y = document.getElementById(id).offsetTop;
	$.get("Admin?chair="+chairname+"&price="+price+"&x="+x+"&y="+y,function(response){
		if(response.type="success"){
			
		} else {
			
		}
	});
}
*/

function updateChairToLayout(chairname){
	let chairinfo;
	$.get("Admin?chairequest="+chairname,function(response){
		if(response.type="success"){
			chairinfo = response;
			alert(chairinfo.price);
			$("#chairname_field").val(chairinfo.chairname);
			$("#timeslot_price_field").val(chairinfo.price);
			$("#allday_price_field").val(chairinfo.daylyprice);
			$("#pass_price_field").val(chairinfo.passprice);
			$("#note_field").val(chairinfo.details);
			$("#updatechairbtn").show();
			$("#createchairbtn").hide();
			$("#newchairwindow").fadeIn(500);
		} else {
			showDefaultError();
			return;
		}
	});
	
}

function removeChairFromLayout(chairname){
	
}

$("#createchairbtn").click(function(){
	let chairname = $("#chairname_field").val();
	let price = $("#timeslot_price_field").val();
	let dailyprice = $("#allday_price_field").val();
	let passprice = $("#pass_price_field").val();
	let details = $("#note_field").val();
	$.get("Admin?chair="+chairname+"&price="+price+"&x=0&y=0&dailyprice="+dailyprice+"&passprice="+passprice+"&details="+details,function(response){
		if(response.type="success"){
			$("#newchairwindow").fadeOut(500);
			location.href = "layoutEditor.jsp";
		} else if(response.type="duplicate") {
			$("#newchairwindow").fadeOut(500);
			showError("Duplicate chair", "A chair with that name already exists, please try again");
		} else if(response.type="fielderror") {
			$("#newchairwindow").fadeOut(500);
			showError("Field error", "Missing informations or type error for fields! Please retype chair informations");
		}
		else {
			$("#newchairwindow").fadeIn(500);
			showDefaultError();
		} 
	});
});

$("#updatechairbtn").click(function(){
	let chairname = $("#chairname_field").val();
	let price = $("#timeslot_price_field").val();
	let dailyprice = $("#allday_price_field").val();
	let passprice = $("#pass_price_field").val();
	let details = $("#note_field").val();
	$.get("Admin?updatechair="+chairname+"&price="+price+"&dailyprice="+dailyprice+"&passprice="+passprice+"&details="+details,function(response){
		if(response.type="success"){
			$("#newchairwindow").fadeOut(500);
		} else {
			showDefaultError();
		}
	});
})

$("#cancelbtn").click(function(){
	$("#newchairwindow").fadeOut(500);
	$("#updatechairbtn").hide();
	$("#createchairbtn").show();
});


$("#addchairbtn").click(function(){
	$("#updatechairbtn").hide();
	$("#createchairbtn").show();
	$("#newchairwindow").fadeIn(500);
});