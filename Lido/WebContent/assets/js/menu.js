var currentCard = 0;	// Stores which card is currently open.
						// None is at the beginning.
						// The value is an int corresponding to the category.

// JSON array that stores the pending orders for the table.
var pendingOrders = [];

// Called when clicking on a menu category card. Parameter "card" is an int ranging from 1 to 5.
function cardOpen (card) {
	if (currentCard != card) {	// Does nothing if same card is clicked multiple times in a row.
	// Close previously open cards.
		if (currentCard >= 1 && currentCard <= 5) {
		$("#card_"+currentCard).animate({
			height : '100px'
		}, 300);
	}
	$(".category_"+currentCard).hide();
	 
	// Open selected card.
	$("#card_"+card).animate({
		height : '200px'
	}, 300);
	
	// Shows the dishes in the selected category.
	$(".category_"+card).show();
	
	currentCard = card;
	}
}

// Used in menuEditor. Needs to be loaded after the document is ready.
function showEditor () {
	$(".card form").show();
}

// Called when clicking the "Add to order" button of a dish card. Used in menu.jsp.
function addToOrder (dishId, dishname, price) {
	pendingOrders.push({"dishId" : dishId,
						"dishname" : dishname,
						"price" : price});
	
	loadOrders();
}

function removeFromOrder (index) {
	delete pendingOrders[index];
	
	loadOrders();
}

// Reloads the entire array of orders every time an order is added or removed. Used in menu.jsp.
function loadOrders () {
	$("#orderDiv").html("");
	
	let count = 0;
	let total = 0;
	
	pendingOrders.forEach(function(d){
		total += d.price;
		$("#orderDiv").append(
			'<div class="card menuMenuItem">' +
				'<div class="card-body">' +
					'<h4 class="card-title menutitleorder">' + d.dishname + '</h4>' +
					'<h6 class="text-muted card-subtitle mb-2 menupriceorder">' + d.price + ' &euro;</h6>' +
					'<button class="btn btn-danger menuRemoveOrder" type="button" onclick="removeFromOrder(' + count + ')">Remove</button>' +
				'</div>' +
			'</div>'
		);
		count++;
	});
	
	var filtered = pendingOrders.filter(function (el) {
		  return el != "";
		});
	pendingOrders = filtered;

	if (total > 0) {
		$("#total").html(total.toFixed(2) + "&euro;");
		$("#totalDiv").show();
	} if (total == 0){
		$("#totalDiv").hide();
	}
}

// Shows a confirmation window before proceeding to payment.
function confirmOrder () {
	var tableNum = $("#table_number").html();
	var total = $("#total").html();
	
	$.get("MenuServlet?table_number="+tableNum);
	
	$("#price_label").html(total);
	$("#paymentScreen").fadeIn(500);
	
	$("#paymentyesbtn").click(function(){
		var orders = JSON.stringify(pendingOrders)
		data = {
				"pendingOrders" : orders,
				"table_number" : tableNum
		}
		$.post("MenuServlet", data, function(response){
			if (response.type == "error") {
				location.href = "errorpage.html";
			} else if (response.type == "success") {
				location.href = "menu.jsp?table_number=" + tableNum;
				pendingOrders = [];
				loadOrders();
			}
		}, "json");
	});
	
	$("#paymentnobtn").click(function(){
		$("#paymentScreen").fadeOut(500);
	});
}

// Used in menuEditor. Called by clicking the "Edit" button of a dish, turns the card into a form.
function dishEdit (id) {
	var name = $("#dish_"+id+" h4").html();
	var price = $("#dish_"+id+" h6").html();
	var ingredients = $("#dish_"+id+" p").html();
	
	$("#dish_"+id).html(
		'<form id="dishEditForm_'+id+'" action="MenuEditorServlet" method="post" class="card-body">' +
        	'<input id="nameEdit_'+id+'" type="text" class="dishInsert h4" placeholder="Dish name" value="' + name + '" required />' +
        	'<input id="priceEdit_'+id+'" type="number" step="0.1" class="price-field dishInsert h6" placeholder="Price" value="' + price + '" required />' +
			'<span class="price-span h6">&euro;</span>' +
        	'<input id="ingrEdit_'+id+'" type="text" class="dishInsert ingredients" style="display: block;" placeholder="Ingredients" value="' + ingredients + '" required />' +
            '<button class="btn btn-primary" type="submit">Confirm</button>' +
			'<button class="btn btn-danger" type="button" style="margin-left: 5px" onclick="dishEditCancel('+id+',\''+name+'\','+price+',\''+ingredients+'\')">Cancel</button>' +
        '</form>'
	);
	
	$("#dishEditForm_"+id).submit(function(event){
		/* stop form from submitting normally */
  		event.preventDefault();

		/* get the action attribute from the <form action=""> element */
		var $form = $(this),
	  		url = $form.attr('action'),
			data = {
				"action" : 2,
				"id" : id,
				"dishname" : $("#nameEdit_"+id).val(),
				"ingredients" : $("#ingrEdit_"+id).val(), 
				"price" : $("#priceEdit_"+id).val()
			};
		
		$.post(url, data, function(response){
			if (response.type == "success") location.href = "menuEditor.jsp?openCard=" + currentCard;
		}, "json");
	});
}

// Used in menu.js. Called by clicking the "Cancel" button of a form made by dishEdit().
function dishEditCancel (id, name, price, ingredients) {
	$("#dish_"+id).html(
		'<div class="card-body">' +
	        '<h4 class="card-title">' + name + '</h4>' +
	        '<h6 class="price-span text-muted card-subtitle mb-2">' + price + '</h6>' +
			'<span class="price-span h6"> &euro;</span>' +
			'<br />' +
	        '<p class="card-text">' + ingredients + '<br /></p>' +
	        '<button class="btn btn-primary" type="button" onclick="dishEdit('+id+')">Edit</button>' +
	    '</div>'
	)
}

$("#dishAddForm").submit(function (event) {
	/* stop form from submitting normally */
  	event.preventDefault();

	/* get the action attribute from the <form> element */
	var $form = $(this),
  		url = $form.attr('action'),
		data = {
			"action" : 1,
			"dishname" : $("#nameAdd").val(),
			"category" : currentCard,
			"ingredients" : $("#ingrAdd").val(), 
			"price" : $("#priceAdd").val()
		};
	
	/* Send the data */
	$.post(url, data, function(response){
		if (response.type == "success") location.href = "menuEditor.jsp?openCard=" + currentCard;
	}, "json");
});