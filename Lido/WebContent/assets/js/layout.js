var currentDragging;
var drag_left;
var drag_top;
var chaircount = 0;

function onDragOver(event) {
  event.preventDefault();
}

function onDragStart(event,id){
	currentDragging = document.getElementById(id);
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

function addChairToLayout(id){
	var chairname = $("#"+id+"_name").val();
	var price = $("#"+id+"_price").val();
	var x = document.getElementById(id).offsetLeft;
	var y = document.getElementById(id).offsetTop;
	$.get("AdminServlet?chair="+chairname+"&price="+price+"&x="+x+"&y="+y,function(response){
		if(response.type="success"){
			
		} else {
			
		}
	}, "json");
}

function removeChairFromLayout(id){
	
}

$("#addchairbtn").click(function(){
	chaircount++;
	var newchair = '<div class="chair" id="chair_'+chaircount+'" draggable="true" ondragstart="onDragStart(event,\'chair_'+chaircount+'\')" ' +
	'onmouseover="showChairPopup(\'chair_'+chaircount+'\')" onmouseout="hideChairPopup()" style="top: 0px; left: 0px;">' +
			'<div class="chairpopup" style="display: none">' +
				'<input type="text" class="chair_'+chaircount+'_name" placeholder="chair name">'+
				'<input type="text" class="chair_'+chaircount+'_price" placeholder="chair name">'+
				'<button class="btn btn-primary btn-sm popupbutton" type="button" onclick="addChairToLayout(\'chair_'+chaircount+'\')">create</button>'+
				'<button class="btn btn-primary btn-sm popupbutton" type="button" onclick="removeChairFromLayout(\'chair_'+chaircount+'\')">delete</button>'+
			'</div>' +
		'</div>';
	document.getElementById("beachlayoutdiv").innerHTML += newchair;
	
});