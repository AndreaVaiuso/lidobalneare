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

function showEditor () {
	$(".card form").show();
}

// Called when clicking the "Add to order" button of a dish card. Used in menu.jsp.
function addToOrder (dish, price) {
	pendingOrders.push({"dish" : dish, "price" : price});
	
	loadOrders();
}

function removeFromOrder (index) {
	delete pendingOrders[index];
	
	loadOrders();
}

// Reloads the entire array of orders every time an order is added or removed. Used in menu.jsp.
function loadOrders () {
	$("#orderDiv").html("");
	
	var count = 0;
	var total = 0;
	
	for (var d in pendingOrders) {
		count++;
		total += d.price;
		$("#orderDiv").prepend(
			'<div class="card menuMenuItem">' +
				'<div class="card-body">' +
					'<h4 class="card-title menutitleorder">' + d.dish + '</h4>' +
					'<h6 class="text-muted card-subtitle mb-2 menupriceorder">' + d.price + ' &euro;</h6>' +
					'<button class="btn btn-danger menuRemoveOrder" type="button" onclick="removeFromOrder(' + count + ')">Remove</button>' +
				'</div>' +
			'</div>'
		);
	}
	
	$("#total").html(total + "&euro;");
	$("#divtotal").show();
}

function confirmOrder () {
	$.post("MenuServlet", pendingOrders, function(response){
		if (response.type == "error") {
			location.href = "errorpage.html";
		} else if (response.type == "success") {
			pendingOrders = [];
		}
	}, "json");
}

// Used in menuEditor. Called by clicking the "Edit" button of a dish, turns the card into a form.
function dishEdit (id) {
	var name = $("#dish_"+id+" h4").html();
	var price = $("#dish_"+id+" h6").html();
	var ingredients = $("#dish_"+id+" p").html();
	
	$("#dish_"+id).html(
		'<form id="dishEditForm_'+id+'" action="MenuEditorServlet" method="post" class="card-body">' +
        	'<input id="nameEdit_'+id+'" type="text" class="dishInsert h4" placeholder="Dish name" value="' + name + '" required />' +
        	'<input id="priceEdit_'+id+'" type="number" class="price-field dishInsert h6" placeholder="Price" value="' + price + '" required />' +
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
			if (response.type == "success") location.href = "menuEditor.jsp";
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
		if (response.type == "success") location.href = "menuEditor.jsp";
	}, "json");
});