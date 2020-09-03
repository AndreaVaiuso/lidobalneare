var currentCard = 0;	// Stores which card is currently open.
						// None is at the beginning.
						// The value is an int corresponding to the category.

// JSON that stores the pending orders for the table.
var pendingOrder = { "orders" : [] }

function cardOpen(card) {
	// Empty dishes div.
	$("#dishesdiv").html("");
	
	// Close previously open cards.
	if (currentCard >= 1 && currentCard <= 5) {
		$("#card_"+currentCard).animate({
			height : '100px'
		}, "fast");
	}
	 
	// Open selected card.
	$("#card_"+card).animate({
		height : '200px'
	}, "fast");
	
	$(".card_"+card).toggle();
	
	currentCard = card;
}

function loadOrders () {
	$("#orderDiv").html("");
	
	var count = 0;
	var total = 0;
	
	for (var d in pendingOrder["orders"]) {
		count++;
		total += d.price;
		$("#orderDiv").append(
			'<div class="card menuMenuItem">' +
				'<div class="card-body">' +
					'<h4 class="card-title menutitleorder">' + d.dish + '</h4>' +
					'<h6 class="text-muted card-subtitle mb-2 menupriceorder">' + d.price + ' &euro;</h6>' +
					'<button class="btn btn-danger menuRemoveOrder" type="button" onclick="removeFromOrder(' + count + ')">Remove</button>' +
				'</div>' +
			'</div>'
		);
	}
	
	$("#orderDiv").append('<hr>' +
		'<div class="card menuMenuItemtotal">' +
			'<div class="card-body">' +
				'<h4 class="card-title menutitleorder">Total:</h4>' +
				'<h6 class="text-muted card-subtitle mb-2 menupriceordertotal" id="total">' + total + ' &euro;</h6>' +
				'<button class="btn btn-primary menuRemoveOrder" type="button" onclick="confirmOrder()">Confirm order</button>' +
			'</div>' +
		'</div>'
	);
	
	
}

function addToOrder (dish, price) {
	pendingOrder["orders"].push({"dish" : dish, "price" : price});
	
	loadOrders();
}

function removeFromOrder (index) {
	delete pendingOrder.orders[index];
}

function confirmOrder () {
	$.post("MenuServlet", pendingOrder, function(response){
		if (response.type == "error") {
			location.href = "errorpage.html";
		} else if (response.type == "success") {
			pendingOrder = { "orders" : [] };
		}
	}, "json");
}